import base.Invoker;
import com.relevantcodes.extentreports.LogStatus;
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


public class TestAmazonIPhonePricing extends Invoker {

    /*
    * Extended exam pseudocode :
    *
    * NOTE :
    * Steps 1 to 6 are declaration steps
    *   - declaration of new class
    *   - declaration of new methods
    *
    * Steps 7 - 10 are the steps for implementation inside the test class (TestAmazonIPhonePricing.java)
    *   - instantiation of the new class created from Step 1 to 6
    *   - usage of method created on Step 1 to 6
    *
    * Put the ff. code in a separate Util file
    *   - File name could be PriceRangeAssessment.java
    *   - with class name PriceRangeAssessment()
    *
    * 1. List the price range by getting it on the page :
    *   String upTo50 = "<= 50"
    *   String from100To150 = ">= 100 and <= 150"
    *   String from150To250 = ">= 150 and <= 250"
    *   String from250To400 = ">= 250 and <= 400"
    *   String from400To600 = ">= 400 and <= 600"
    *   String beyondOrEqual600 = ">= 600"
    *
    * 2. Get the element of specified range
    *
    * 3. Click the specified range
    *
    * 4. Loop through the item price range
    *
    * 5. Create a method that will get the item results
    *   method name : List<WebElement> getPriceResult(String priceRange)
    *   method body : (we're setting it to get the items from price range up to 50)
    *       getPriceResult(upTo50).click()  // This is the implementation of step 3
    *       <List> WebElements itemsPrice = get price of each item result
    *       return itemsPrice
    *
    * 6. Create a method with condition to check the price range based on the extracted price result in the page
    *   method name : isWithinRange(List<WebElement> elements, String upToValue)
    *   method body :
    *       for (WebElement elem : elements)
    *           if (upToValue == upTo50)
    *               if elem.price() <= upToValue { print "PASS : the item is within the {upToValue}" }
    *               else { print "FAIL : the item '{elem.itemName()}' price is beyond the {upToValue}" }
    *           else if (upToValue == beyondOrEqual600)
    *               if elem.price() >= upToValue { print "PASS : the item is beyond the {upToValue}" }
    *               else { print "FAIL :the item '{elem.itemName()}'price is less than the {upToValue}" }
    *           else
    *               if elem <= upToValue { print "PASS : the item is within the {upToValue}" }
    *               else { print "FAIL : the item '{elem.itemName()}' price is beyond the {upToValue}" }
    *
    * 7. Import the PriceRangeAssessment to TestAmazonIPhonePricing.java
    *   - instantiate PriceRangeAssessment priceRangeAst = new PriceRangeAssessment()
    *
    * 8. Remove code from 109 to 129 and replace with the code below :
    * 9. List<WebElements> elements = priceRangeAst.getPriceResult(priceRangeAst.upTo50)
    *
    * 10. call priceRangeAst.isWithinRange(elements, priceRangeAst.upTo50)
    * Loop through the list of elements
    *           for (WebElement itemPrice : itemsPrice)
    *               isWithinRange(itemsPrice, upTo50) "See step 6"
    * */

    @Test
    public void TestPrice() throws NumberFormatException, ParseException, InterruptedException {
        // Instantiate the page factory
        LandingPage landingPage = new LandingPage(this.driver);

        // Navigate through the Amazon and set pricing range
        this.goToHomePage();
        extentTest.log(LogStatus.INFO, "Navigated to Amazon");
        landingPage.enterSearchItem("iPhone");
        extentTest.log(LogStatus.INFO, "Typed iPhone");
        landingPage.clickSearch();
        extentTest.log(LogStatus.INFO, "Clicked search");
        landingPage.clickCellPhonesDept();
        extentTest.log(LogStatus.INFO, "Selected Cellphones Dept.");
        landingPage.setPriceRange("400", "500");
        extentTest.log(LogStatus.INFO, "Set price range to 400 ~ 500");

        ArrayList<String> pricing = new ArrayList<String>();
        ArrayList<Double> priceDouble = new ArrayList<Double>();

        // Get the price as text
        for (WebElement elem : landingPage.getSearchResult()) {
            pricing.add(elem.getAttribute("innerHTML"));
        }

        extentTest.log(LogStatus.INFO, "Got all the pricing.");

        // Cut the result into first 5 items only
        for (int counter = 0; counter < 5; counter++) {
            String price = pricing.get(counter).replaceAll("[^\\d.]+", "");
            Double thisPrice = Double.parseDouble(price);
            priceDouble.add(thisPrice);
        }

        extentTest.log(LogStatus.INFO, "Filtered to 5 items only");

        // Sort the item per price from highest to lowest
        priceDouble.sort(Comparator.reverseOrder());

        System.out.println("Sorted price as list : " + priceDouble);

        extentTest.log(LogStatus.INFO, "============  Below is the name of the items sorted by price : ============");
        for (int counter = 0; counter < 5; counter++ ) {

            String itemName = item(priceDouble.get(counter).toString()).getText();
            extentTest.log(LogStatus.INFO, "$" + priceDouble.get(counter) + " | " + itemName);

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
