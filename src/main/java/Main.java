import org.json.JSONException;

import java.io.IOException;


public class Main {
    public static void main(String[] args) {

        try {

            QuoraApi statistics1 = new QuoraApi("Oliver-Emberton");

            SiteStatistics statistics2 = new QuoraApi("John-Romero");

            System.out.println("Olivers statistics of " + " answers:" + statistics1.getNumberAnswers()); //125
            System.out.println("Olivers statistics of " + " questions:" + statistics1.getNumberQuestions()); //17

            System.out.println("Johnys statistics of " + " answers:" + statistics2.getNumberAnswers());//98
            System.out.println("Johnys statistics of " + " questions:" + statistics2.getNumberQuestions());//0

            System.out.println(statistics1.getJSONObject()); //...
            System.out.println(statistics2.getJSONObject());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}










