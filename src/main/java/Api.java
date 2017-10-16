import org.json.JSONException;

import java.io.IOException;

public interface Api {

    void authenticate(String userName, String password) throws IOException;

    String reputationJson() throws IOException, JSONException;

    void logOut() throws IOException;

}
