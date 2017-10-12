import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuoraApi implements SiteStatistics {

    private String nameUser;
    private int numberAnswers;
    private int numberQuestions;
    private int numberUpvotes;  //????

    public QuoraApi(String nameUser) throws IOException {

        this.nameUser = nameUser;
        setNumberAnswers(parseUsersAnswers());
        setNumberQuestions(parseUsersQuestions());

    }


    public int getNumberAnswers() {
        return numberAnswers;
    }

    private void setNumberAnswers(int numberAnswers) {
        this.numberAnswers = numberAnswers;
    }

    public int getNumberQuestions() {
        return numberQuestions;
    }

    private void setNumberQuestions(int numberQuestions) throws IOException {

        this.numberQuestions = numberQuestions;
    }

    public int getNumberUpvotes() {
        return numberUpvotes;
    }

    private void setNumberUpvotes(int numberUpvotes) {
        this.numberUpvotes = numberUpvotes;
    }

    public String getNameUser() {

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

    public JSONObject getJSONObject() throws JSONException {
       final JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",getNameUser());
        jsonObject.put("answers",getNumberAnswers());
        jsonObject.put("questions",getNumberQuestions());
        return jsonObject;
    }
}
