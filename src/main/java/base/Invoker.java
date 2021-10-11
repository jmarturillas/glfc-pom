package base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Invoker {

    public WebDriver driver;
    public ExtentReports extentReports;
    public ExtentTest extentTest;

    @BeforeAll
    static void setUp() {
        // It is possible to create a decision maker
        // as to which browser will be used.
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUpTest() {
        extentReports = new ExtentReports(System.getProperty("user.dir") + "\\reports\\GeneratedReport.html");
        extentTest = extentReports.startTest("Amazon IPhone Pricing");
        // In this case we will be using Chrome
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    public void teardown() {
        extentReports.endTest(extentTest);
        extentReports.flush();
        if (driver != null) {
            driver.quit();
        }
    }

    public void goToHomePage() {
        // This can be parameterized based on requirement
        this.driver.get("https://www.amazon.com");
    }

}
