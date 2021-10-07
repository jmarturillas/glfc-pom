package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Invoker {

    public WebDriver driver;

    @BeforeAll
    static void setUp() {
         WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUpTest() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
//
//    @AfterEach
//    public void teardown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }

    public void goToHomePage() {
        this.driver.get("https://www.amazon.com");
    }

}
