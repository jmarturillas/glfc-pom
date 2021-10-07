import base.Invoker;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import pages.LandingPage;

import java.util.ArrayList;
import java.util.List;


public class AmazonIPhonePricing extends Invoker {

    @Test
    public void TryThis() {
        LandingPage landingPage = new LandingPage(this.driver);

        this.goToHomePage();
        landingPage.enterSearchItem("iPhone");
        landingPage.clickSearch();
        landingPage.clickCellPhonesDept();
        landingPage.setPriceRange("400", "500");

        ArrayList<String> pricing = new ArrayList<String>();

        for (WebElement elem : landingPage.getSearchResult()) {
            pricing.add(elem.getAttribute("innerHTML"));
        }

        for (int counter = 0; counter < 5; counter++) {
            System.out.println(pricing.get(counter));
        }
    }
}
