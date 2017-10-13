import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StackOverFlowApi implements IStackOverFlowApi {

    private final String STACK_OVER_FLOW_URL = "https://stackoverflow.com/";

    private String usersPassword;

    private String usersLogin;

    private Connection.Response responseHomePage;

    public StackOverFlowApi() throws IOException {

    }

    public void authenticate(String login, String password) throws IOException {

        this.usersLogin = login;

        this.usersPassword = password;

        setResponseHomePage(getAuthHomePage());
    }

    private Connection.Response getResponseHomePage() {

        return responseHomePage;

    }

    private void setResponseHomePage(Connection.Response responseHomePage) {

        this.responseHomePage = responseHomePage;

    }


    private String getUsersPassword() {

        return usersPassword;

    }

    private String getUsersLogin() {

        return usersLogin;

    }


    private Connection.Response getAuthHomePage() throws IOException {

        Connection.Response loginForm = Jsoup.connect(STACK_OVER_FLOW_URL + "users/login").userAgent("Mozilla/5.0").execute(); //Get login page.

        Pattern pattern = Pattern.compile("(fkey\":\")([^\".]+)\"");

        Matcher matcher = pattern.matcher(loginForm.parse().toString());

        matcher.find();

        String fkey = "";  //Find parameter  "fkey" for method post.

        fkey = matcher.group(2);


        if (getUsersLogin()==null||getUsersPassword()==null){

            System.out.println("Please check you login or password! (method:\"authenticate(String login, String password)\" must be " +
                     "performed before work)");
        }
        Connection.Response response = Jsoup.connect(STACK_OVER_FLOW_URL + "users/login")
                .userAgent("Mozilla/5.0")
                .data("fkey", fkey)
                .data("ssrc", "")
                .data("email", getUsersLogin())
                .data("password", getUsersPassword())
                .data("oauth_version", "")
                .data("oauth_server", "")
                .data("openid_username", "")
                .data("openid_identifier", "")
                .cookies(loginForm.cookies()).
                        method(Connection.Method.POST).execute();

        Document document = response.parse();

        Pattern pattern2 = Pattern.compile("(users)/(\\d+)/([^\".]+)");     //Find id and user name for URl parameters.

        Matcher matcher2 = pattern2.matcher(document.toString());

        matcher2.find();

        String  id = matcher2.group(2);

        String userName = matcher2.group(3);

        Connection.Response homePage = Jsoup.connect(STACK_OVER_FLOW_URL + "users/" +id+ "/" +userName).userAgent("Mozilla/5.0").cookies(response.cookies()).execute();

        return homePage;

    }

    public String getBadgeCount() throws IOException, JSONException {

      String element =   getResponseHomePage().parse()
              .getElementsByClass("badgecount")
              .text();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("countBadges",element);

      return jsonObject.toString();
    }

   public String getReputationCount() throws IOException, JSONException {

        Pattern pattern = Pattern.compile("(Reputation <span>)[(](\\d)[)]+(</span>)");

        Matcher matcher = pattern.matcher(getResponseHomePage().parse().toString());

        matcher.find();

        String count = matcher.group(2);

       JSONObject jsonObject = new JSONObject();

       jsonObject.put("countReputation",count);

       return jsonObject.toString();
    }

    public String getNumberAnswers() throws IOException, JSONException {

        Pattern pattern = Pattern.compile("(Answers <span>)[(](\\d)[)]+(</span>)");

        Matcher matcher = pattern.matcher(getResponseHomePage().parse().toString());

        matcher.find();

        String count = matcher.group(2);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("countAnswers",count);

        return jsonObject.toString();
    }
    public String getNumberQuestions() throws IOException, JSONException {

        Pattern pattern = Pattern.compile("(Questions <span>)[(](\\d)[)]+(</span>)");

        Matcher matcher = pattern.matcher(getResponseHomePage().parse().toString());

        matcher.find();

        String count = matcher.group(2);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("countAnswers",count);

        return jsonObject.toString();
    }

    public void logOut() throws IOException {

        Jsoup.connect(STACK_OVER_FLOW_URL + "/users/logout").cookies(responseHomePage.cookies()).method(Connection.Method.GET).execute();
    }

}
