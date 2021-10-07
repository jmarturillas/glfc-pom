import base.Invoker;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.LandingPage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AmazonIPhonePricing extends Invoker {

    @Test
    public void navigateThroughAmazon() throws NumberFormatException, ParseException {
        // Instantiate the page factory
        LandingPage landingPage = new LandingPage(this.driver);

        // Navigate through the Amazon and set pricing range
        this.goToHomePage();
        landingPage.enterSearchItem("iPhone");
        landingPage.clickSearch();
        landingPage.clickCellPhonesDept();
        landingPage.setPriceRange("400", "500");

        ArrayList<String> pricing = new ArrayList<String>();
        ArrayList<Double> priceDouble = new ArrayList<Double>();

        // Get the price as text
        for (WebElement elem : landingPage.getSearchResult()) {
            pricing.add(elem.getAttribute("innerHTML"));
        }

        // Cut the result into first 5 items only
        for (int counter = 0; counter < 5; counter++) {
            String price = pricing.get(counter).replaceAll("[^\\d.]+", "");
            Double thisPrice = Double.parseDouble(price);
            priceDouble.add(thisPrice);
        }

        // Sort the item per price from highest to lowest
        priceDouble.sort(Comparator.reverseOrder());

        System.out.println("Sorted price as list : " + priceDouble);

        System.out.println("============  Below is the name of the items sorted by price : ============");
        for (int counter = 0; counter < 5; counter++ ) {
            String itemName = item(priceDouble.get(counter).toString()).getText();
            System.out.println("$" + priceDouble.get(counter) + " | " + itemName);
        }

    }

    // Getting the item name based on its price.
    // NOTE : the limitation of this function is that when there are same price for like 2 or more items, it will get the
    // name of the first found item. By result, the name would be duplicate. XPath returns the first item it gets.
    // Example :
    // The item "OnePlus 8T 5G Dual-SIM 128GB ROM + 8GB RAM (GSM Only | No CDMA) Factory Unlocked Android Smartphone (Lunar Silver) - International Version"
    // might be printed twice because it's sibling result is also 499.99 by price. Which is the :
    // CAT Phone S61 FLIR Thermal Camera, Laser Distance Measure, Air Quality Monitor, IP69 Waterproof & Military MIL SPEC 810G Certified , 4+64GB Dual SIM Factory Unlocked 4G LTE


    public WebElement item(String itemPrice) {
        // This webelement parsing was not included in the LandingPage.java Pagefactory.
        // As Pagefactory doesn't support passing of parameters.
        // In this use case, we'll need to pass parameters such as `itemPrice`
        return this.driver.findElement(By.xpath("//div[@class='s-main-slot s-result-list s-search-results sg-row']" +
                "/div/div/span/div/div/div[2]/div[3]/div/a/span/span[@class='a-offscreen']" +
                "[contains(text(), '" + itemPrice + "')]/parent::span/parent::a/parent::div/" +
                "parent::div/parent::div/div/h2/a/span"));
    }
}
