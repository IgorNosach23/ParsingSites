import org.json.JSONException;

import java.io.IOException;


public class Main {


    public static void main(String[] args) throws IOException, JSONException {

        Api statisticsQuora = new QuoraApi();

        statisticsQuora.authenticate("Oliver-Emberton", "3923532");

        Api statisticsStackOverFlow = new QuoraApi();

        statisticsStackOverFlow.authenticate("John-Romero", "dsd5");

        Api stackOverFlowApi = new StackOverFlowApi();

        stackOverFlowApi.authenticate("---", "---");

        System.out.println(stackOverFlowApi.reputationJson());

        System.out.println(statisticsQuora.reputationJson());

        System.out.println(statisticsStackOverFlow.reputationJson());

        stackOverFlowApi.logOut();

        statisticsQuora.logOut();

        statisticsStackOverFlow.logOut();

    }

}














