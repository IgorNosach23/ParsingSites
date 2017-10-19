import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuoraApi implements Api {

    private String nameUser;
    private int numberAnswers;
    private int numberQuestions;
    private WebDriver _driver;
    private JavascriptExecutor _jsEngine;


    public QuoraApi() {
        setupWebDriver();
    }

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

    private void setupWebDriver()
    {

        String os = System.getProperty("os.name");
        if (os .contains("Windows") ) {
            System.setProperty("webdriver.chrome.driver", "./lib/chromedriver.exe");
            System.setProperty("webdriver.phantomjs.driver", "./lib/lib/phantomjs.exe");
            System.setProperty("webdriver.opera.driver", "./lib/launcher.exe");

        }
        if (os .contains("Mac")) {
            try {
                System.setProperty("webdriver.chrome.driver", "./lib/chromedriver");
            }
            catch (Exception e) {
                System.setProperty("webdriver.chrome.driver", "./lib/chromedriver.dmg");
            }
        }
        WebDriver driver = null;
        JavascriptExecutor jse = (JavascriptExecutor)driver;


            ChromeOptions options = new ChromeOptions();
            try {
                options.addArguments(new String[] { "--start-maximized" });
            } catch (Exception e) {}



/* 102 */       ChromeOptions options2 = new ChromeOptions();
/*     */
/* 104 */         options2.addArguments(new String[] { "--start-maximized" });
/*     */



        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);

        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "./lib/lib/phantomjs.exe");

        driver = new PhantomJSDriver(caps);
      //  driver.manage().window().setSize(new Dimension(1000,800));

            driver.get("https://www.quora.com/" + getNameUser());

            /*
       WebElement loginWindow = driver.findElement(By.name("email"));
      loginWindow.click();
       loginWindow.sendKeys(new CharSequence[] {"igornosach23@gmail.com"});

       WebElement password = driver.findElement(By.name("password"));
        loginWindow.click();
       password.sendKeys(new CharSequence[]{"Next622521"});
*/


      // WebElement element = driver.findElement(By.xpath("//*[@value='Login' AND @class='submit_button ignore_interaction' AND @tabindex='4']"));
        List<WebElement> element = driver.findElements(By.xpath("//span[@class='button_text']"));

        element.forEach((WebElement w)-> System.out.println(w.getText()));










    }

    @Override
    public String reputationJson() throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("answers", getNumberAnswers());
        jsonObject.put("questions", getNumberQuestions());
        return jsonObject.toString();
    }

    @Override
    public void logOut() throws IOException {

        setNameUser("");

    }
}
