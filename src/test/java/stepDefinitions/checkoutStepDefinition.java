package stepDefinitions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import Utilities.log;
import base.baseClass;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObject.checkoutPage;
import pageObject.searchProduct;

public class checkoutStepDefinition {
	
	
	checkoutPage checkout;
	WebDriver driver = base.baseClass.getDriver();
	
	
	@And("User selects delivery address")
	public void select_address() {
		checkout = new checkoutPage(baseClass.getDriver());  
	    checkout.clickChangeAddress();
	    checkout.selectDeliveryAddress();
	    checkout.clickOnDeliveryToThisAddress();
	    
	}

	

	@Then("User should be redirected to payment page")
	public void verify_paymentt_page() {
	    boolean status = checkout.isPaymentPageDisplayed();
	    log.logger.info("Payment page displayed");
	    Assert.assertTrue(status, "❌ Payment page not displayed");
	    checkout.closePrimePopupIfPresent();
	    	}
	
	
	
	
	@And("User selects the payment method")
	public void select_payment_method() {
		
		checkout.selectPaymentMethod();
		checkout.clickAmazonLogo();
		checkout.clickSignOut();
		checkout.clickonHomePage();
		
	}
}
