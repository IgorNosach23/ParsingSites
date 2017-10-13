import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuoraApi implements IQuoraApi {

    private String nameUser;
    private int numberAnswers;
    private int numberQuestions;
    private int numberUpvotes;    //????

    public QuoraApi(String nameUser) throws IOException {

        this.nameUser = nameUser;

        setNumberAnswers(parseUsersAnswers());

        setNumberQuestions(parseUsersQuestions());

    }


    public String getNumberAnswers() throws JSONException {

        final JSONObject jsonObject = new JSONObject();

        jsonObject.put("answers",numberUpvotes);

        return jsonObject.toString();

    }

    private void setNumberAnswers(int numberAnswers) {

        this.numberAnswers = numberAnswers;

    }

    public String getNumberQuestions() throws JSONException {

        final JSONObject jsonObject = new JSONObject();

        jsonObject.put("questions",numberQuestions);

        return jsonObject.toString();

    }

    private void setNumberQuestions(int numberQuestions) throws IOException {



        this.numberQuestions = numberQuestions;
    }

    public String getNumberUpvotes() throws JSONException {

        final JSONObject jsonObject = new JSONObject();

        jsonObject.put("upvotes",numberUpvotes);

        return jsonObject.toString();

    }

    private void setNumberUpvotes(int numberUpvotes) {

        this.numberUpvotes = numberUpvotes;

    }

    private String getNameUser() {

        return nameUser;
    }

    private void setNameUser(String nameUser) {

        this.nameUser = nameUser;

    }

    private int parseUsersAnswers() throws IOException {

        Pattern findAnswers = Pattern.compile("(\\d+)( Answers)");

        Matcher m = findAnswers.matcher(parseUsersInformation().text());

        m.find(); // may be IllegalStateException

        final int answers = Integer.parseInt(m.group(1));

        return answers;

    }

    private int parseUsersQuestions() throws IOException {

        Pattern findAnswers = Pattern.compile("(\\d+)( Questions)");

        Matcher m = findAnswers.matcher(parseUsersInformation().text());

        m.find(); //may be IllegalStateException

        final int formKey = Integer.parseInt(m.group(1));

        return formKey;
    }

    private Element parseUsersInformation() throws IOException {

        final Document initial = Jsoup.connect("https://www.quora.com/" + getNameUser()).get();

        final Element infUsers = initial.getElementsByClass("horizontal_scroll_inner").first();

        return infUsers;
    }

}
