import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public interface IQuoraApi {

    String getNumberAnswers() throws IOException, JSONException;

    String getNumberQuestions() throws IOException, JSONException;

    String getNumberUpvotes() throws IOException, JSONException;  //???

}
