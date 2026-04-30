package stepDefinitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utilities.log;
import base.baseClass;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.Duration;
import java.util.List;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageObject.LoginPage;
import pageObject.productDetailPage;
import pageObject.searchProduct;



import io.cucumber.java.Before;





public class productDetailStepDefinition {
	
	
	productDetailPage prodDetail = new productDetailPage(baseClass.getDriver());
	   WebDriver driver = base.baseClass.getDriver();
		
	@When("User clicks on the Add to cart button")
	public void user_clicks_on_add_to_cart_button() {
	    prodDetail.clickAddToCart();  
	    log.logger.info("Clicking Add to Cart button");
	}

	    @Then("User clicks on the No Thanks button")
	    public void user_clicks_on_no_thanks_button() {
	    	prodDetail.clickNoThanks();
	    	log.logger.info("Clicking on NoThanks button");
	    }

	    @And("Product should add into the cart")
	    public void product_should_add_into_cart() {
	        Assert.assertTrue(prodDetail.getCartCount() > 0);
	        log.logger.info("Product add into the cart");
	    }


	    @And("User clicks on the Go to Cart button")
	    public void user_clicks_on_go_to_cart_button() {
	        prodDetail.clickGoToCart();
	        log.logger.info("Clicking on Go To Cart button");
	    }

	    @Then("The user navigates to the Cart page")
	    public void user_navigates_to_cart_page() {
	        String currentUrl = driver.getCurrentUrl();
	        Assert.assertTrue(currentUrl.contains("cart"));
	        log.logger.info("User navigate to cart page");
	    }

}
