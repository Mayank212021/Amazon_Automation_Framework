package stepDefinitions;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import Utilities.log;
import base.baseClass;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObject.LoginPage;
import pageObject.cartPage;


public class cartPageStepDefinition {
	
	
	cartPage cartpage = new cartPage(baseClass.getDriver());
	   WebDriver driver = base.baseClass.getDriver();
	
	
	
	// ✅ Verify product present
	@Then("The user verifies product {string} is present in cart")
	public void verify_product_present_in_cart(String productName) {
	    Assert.assertTrue(cartpage.isProductPresentInCart(productName));
	}


	// ✅ Verify cart not empty
	@And("The user verifies cart is not empty")
	public void verify_cart_not_empty() {
	    Assert.assertTrue(cartpage.isCartNotEmpty());
	}
	
	@When("User clicks on Proceed to Buy button")
	public void click_proceed_to_buy() {
	    cartpage.clickProceedToBuy();
	    log.logger.info("Clicking Process to Buy button");
	}

	@Then("User should navigate to the checkout page")
	public void verify_checkout_page() {
	    boolean status = cartpage.isCheckoutPageDisplayed();
	    log.logger.info("User navigate to the Checkout page.");
	    Assert.assertTrue(status, "❌ Checkout page not displayed");
	}

	
	
	
	}
	 
	 

