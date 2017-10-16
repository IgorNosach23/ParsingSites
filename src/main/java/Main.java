import org.json.JSONException;

import java.io.IOException;


public class Main {


    public static void main(String[] args) throws IOException, JSONException {

        Api statisticsQuora = new QuoraApi();

        statisticsQuora.authenticate("Oliver-Emberton", "3923532");

        Api statisticsQuora2 = new QuoraApi();

        statisticsQuora2.authenticate("John-Romero", "dsd5");

        Api stackOverFlowApi = new StackOverFlowApi();

        stackOverFlowApi.authenticate("---", "---");

        System.out.println(stackOverFlowApi.reputationJson());

        System.out.println(statisticsQuora.reputationJson());

        System.out.println(statisticsQuora2.reputationJson());

        stackOverFlowApi.logOut();

        statisticsQuora.logOut();

        statisticsQuora2.logOut();

    }

}














