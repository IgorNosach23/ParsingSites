import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuoraApi implements Api {

    private String nameUser;
    private int numberAnswers;
    private int numberQuestions;

    private int getNumberAnswers() {
        return numberAnswers;
    }

    private int getNumberQuestions() {
        return numberQuestions;
    }

    private void setNumberAnswers(int numberAnswers) {

        this.numberAnswers = numberAnswers;

    }


    private void setNumberQuestions(int numberQuestions) throws IOException {

        this.numberQuestions = numberQuestions;

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

    @Override
    public void authenticate(String userName, String password) throws IOException {
        if (userName == null || userName.isEmpty()) {
            throw new IOException("Incorrect name!");
        }
        setNameUser(userName);

        setNumberAnswers(parseUsersAnswers());

        setNumberQuestions(parseUsersQuestions());
    }

    @Override
    public String reputationJson() throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("answers", getNumberAnswers());
        jsonObject.put("questions", getNumberQuestions());
        return jsonObject.toString();
    }

    @Override
    public void logOut() throws IOException, JSONException {

        setNameUser("");

    }
}
