package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class LandingPage {

    WebDriver driver;

    @FindBy(xpath = "//input[@id='twotabsearchtextbox']")
    WebElement searchBox;

    @FindBy(xpath = "//input[@id='nav-search-submit-button']")
    WebElement searchButton;

    @FindBy(xpath = "//div/ul/li/span/a/span[contains(text(), 'Cell Phones')]")
    WebElement cellPhonesDept;

    @FindBy(xpath = "//input[@id='low-price']")
    WebElement lowPriceTxt;

    @FindBy(xpath = "//input[@id='high-price']")
    WebElement highPriceTxt;

    @FindBy(xpath = "//span[@id='a-autoid-1']")
    WebElement goPrice;

    @FindBys({@FindBy(xpath="//div[@class='s-main-slot s-result-list s-search-results sg-row']/div/div/span/div/div/div[2]/div[2]/div/div/div[3]/div/div/div/div/a/span/span[1]")})
    public List<WebElement> searchResult;


    public LandingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterSearchItem(String item) {
        searchBox.sendKeys(item);
    }

    public void clickSearch() {
        searchButton.click();
    }

    public void clickCellPhonesDept() {
        cellPhonesDept.click();
    }

    public void setPriceRange(String low, String high) {
        lowPriceTxt.sendKeys(low);
        highPriceTxt.sendKeys(high);
        goPrice.click();
    }

    public List<WebElement> getSearchResult() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Thread.sleep(2000);
        return searchResult;
    }
}
