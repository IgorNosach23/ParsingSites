import org.json.JSONException;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException, JSONException {
        Api quora = new QuoraApi();
        quora.authenticate("Igor", "Lazarev");
        Api stackOverFlow = new StackOverFlowApi();
        stackOverFlow.authenticate("---", "---");
        System.out.println(stackOverFlow.reputationJson());
        System.out.println(quora.reputationJson());
        stackOverFlow.logOut();
        quora.logOut();
    }
}














