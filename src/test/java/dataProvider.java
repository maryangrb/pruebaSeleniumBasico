import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.GetProperties;

import java.util.concurrent.TimeUnit;

public class dataProvider {
    public static String MAILCHIMP_URL = "https://login.mailchimp.com/";
    WebDriver driver;

    @BeforeMethod
    public void setUp() throws InterruptedException {
        GetProperties properties = new GetProperties();
        String chromeDriverUrl = properties.getString("CHROMEDRIVER_PATH");
        System.setProperty("webdriver.chrome.driver", chromeDriverUrl);
        driver = new ChromeDriver();
        driver.get(MAILCHIMP_URL);
        driver.manage().timeouts().implicitlyWait(18, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        WebElement cookies = driver.findElement (By.id("onetrust-accept-btn-handler"));
        cookies.click();
        Thread.sleep(2000);
    }

    @AfterMethod
    public void tearDown(){
        driver.close();
    }
    @DataProvider(name = "emails")
    public Object[][] crearEmails(){
        return new Object[][]{
                {"test@test.com"},
                {"1234@gmail.com"},
                {"pepe@hotmail.com"}
        };
    }

    //Caso de prueba 5:
    @Test(dataProvider = "emails")
    public void dataProviderEmailTest (String email) throws InterruptedException {

        WebElement usernameLoginField = driver.findElement(By.id("username"));
        usernameLoginField.sendKeys(email);

        WebElement passwordLoginField = driver.findElement(By.id("password"));
        passwordLoginField.sendKeys("holamundo”.");

        WebElement buttonLoginIn = driver.findElement(By.xpath("//*[@id=\"login-form\"]/fieldset/div[5]/button"));
        buttonLoginIn.click();

        Thread.sleep(2000);

        WebElement smsError = driver.findElement(By.xpath("//*[@id=\"login-form\"]/fieldset/div[1]/div/div/div[2]/p"));
        Assert.assertTrue(smsError.getText().contains("Can we help you recover\n" +
                "your username ?”"), "El mensaje de error contiene el texto");

    }


}
