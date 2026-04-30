package stepDefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import Utilities.log;

import java.util.List;

import base.baseClass;
import io.cucumber.java.en.*;
import pageObject.searchProduct;

public class searchProductStepDefinition {

    searchProduct searchprod = new searchProduct(baseClass.getDriver());
    WebDriver driver = base.baseClass.getDriver();

    @Given("User clicks in the search field box")
    public void click_search_box() {
        searchprod.clickSearchBox();
        log.logger.info("Clicking on search field");
    }

    @When("User enters the product name {string}")
    public void enter_product_name(String productName) {
        searchprod.enterProduct(productName);
        log.logger.info("Searching product: " + productName);
    }

    @And("Clicks on the search icon button")
    public void click_search_button() {
        searchprod.clickSearchButton();
        log.logger.info("Clicking search button");
    }

    @Then("All {string} results should be shown on the page")
    public void verify_search_results(String productName) {

    	log.logger.info("Verifying search results for: " + productName);

        // ✅ Title validation
        String pageTitle = driver.getTitle();
        Assert.assertTrue(pageTitle.toLowerCase().contains(productName.toLowerCase()),
                "❌ Page title does not contain product name");

        // ✅ Product list validation (IMPORTANT)
        List<WebElement> products = driver.findElements(
                By.xpath("//div[@data-component-type='s-search-result']")
        );

        Assert.assertTrue(products.size() > 0, "❌ No search results found");

        log.logger.info("✅ Total products found: " + products.size());
    }

    @And("The user selects a {string} product")
    public void select_product(String productName) throws InterruptedException {
        searchprod.selectProduct(); // optionally use productName inside POM
        log.logger.info("Selecting product: " + productName);
    }

    @Then("The user navigates to the product details page")
    public void verify_product_page() {
        boolean status = searchprod.isProductPageOpened();
        Assert.assertTrue(status, "❌ Product page not opened");
        log.logger.info("Navigated to product details page");
    }
}