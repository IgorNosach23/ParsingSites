import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public interface IStackOverFlowApi {

    void logOut() throws IOException;

    String getNumberQuestions() throws IOException,JSONException;

    String getNumberAnswers() throws IOException,JSONException;

    String getReputationCount() throws IOException,JSONException;

    String getBadgeCount() throws IOException,JSONException;

    void authenticate(String login, String password) throws IOException;

}
