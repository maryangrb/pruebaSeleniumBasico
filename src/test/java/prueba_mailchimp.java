import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.GetProperties;

import java.util.concurrent.TimeUnit;

public class prueba_mailchimp {
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

    //Caso de prueba 1:
    @Test (priority = 1)
    public void validarTituloTest(){
        String expectedTitle = "Login | Mailchimp";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    //Caso de prueba 2:
    @Test (priority = 2)
    public void iniciarSesionPageTest(){

        WebElement titulo = driver.findElement(By.xpath("//*[@id=\"login-form\"]/div/h1"));
        WebElement texto = driver.findElement(By.xpath("//*[@id=\"login-form\"]/div/p/span"));

        Assert.assertTrue((titulo.getText().contains("Log In")), "El sitio contiene el texto");
        Assert.assertTrue((texto.getText().contains("Need a Mailchimp account?")), "El sitio contiene el texto");
    }

    //Caso de prueba 3:
    @Test (priority = 3)
    public void loginErrorTest(){
        WebElement usernameLoginField = driver.findElement(By.id("username"));
        usernameLoginField.sendKeys("XXXXX@gmail.com");

        WebElement buttonLoginIn = driver.findElement(By.xpath("//*[@id=\"login-form\"]/fieldset/div[4]/button"));
        buttonLoginIn.click();

        WebElement smsError = driver.findElement(By.xpath("//*[@id=\"empty-error\"]/div/div/div[2]/p"));
        Assert.assertEquals(smsError.getText(), "Looks like you forgot your password there, XXXXX@gmail.com.");

        WebElement checkBox = driver.findElement(By.name("stay-signed-in"));
        System.out.println(checkBox.isSelected());
        Assert.assertTrue(!checkBox.isSelected());
    }

    //Caso de prueba 4:
    @Test (priority = 4)
    public void fakeEmailTest() throws InterruptedException {
        driver .navigate().to( "https://login.mailchimp.com/signup/" );
        WebElement cookies = driver.findElement (By.id("onetrust-accept-btn-handler"));
        cookies.click();
        Thread.sleep(2000);

        Faker fake = new Faker();
        WebElement emailAddress = driver.findElement(By.id("email"));
        String email = fake.internet().emailAddress();
        emailAddress.sendKeys(email);

        Assert.assertTrue(driver.getCurrentUrl().contains("signup"), "La url contiene la palabra");

    }

}
