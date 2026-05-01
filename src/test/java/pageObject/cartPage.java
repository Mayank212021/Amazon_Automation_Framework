package pageObject;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utilities.log;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import stepDefinitions.hooks;

public class cartPage {

	WebDriver ldriver;
	WebDriverWait wait; // ✅ ye line add karo

	public cartPage(WebDriver rDriver) {
		ldriver = rDriver;
		PageFactory.initElements(rDriver, this);
		wait = new WebDriverWait(ldriver, Duration.ofSeconds(20));
	}

	@FindBy(xpath = "//span[contains(text(),'Thermometer')]")
	private List<WebElement> cartProducts;
	// ✅ Cart count (top icon)
	@FindBy(id = "nav-cart-count")
	WebElement cartCount;

	@FindBy(xpath = "//input[@name='proceedToRetailCheckout']")
	WebElement proceedToBuyBtn;

	// 🔥 Verify product present
	public boolean isProductPresentInCart(String productName) {

	    WebDriverWait wait = new WebDriverWait(ldriver, Duration.ofSeconds(40));

	    // scroll (Amazon cart lazy load fix)
	    JavascriptExecutor js = (JavascriptExecutor) ldriver;
	    js.executeScript("window.scrollBy(0,500)");

	    // wait for elements to be present
	    wait.until(ExpectedConditions.visibilityOfAllElements(cartProducts));

	    for (WebElement product : cartProducts) {
	        if (product.getText().contains(productName) && product.isDisplayed()) {
	            return true;
	        }
	    }

	    return false;
	}

	// 🔥 Verify cart not empty
	public boolean isCartNotEmpty() {
		wait.until(driver -> {
			String count = cartCount.getText().trim();
			return !count.isEmpty();
		});

		int count = Integer.parseInt(cartCount.getText().trim());
		log.logger.info("🛒 Cart Count: " + count);

		return count > 0;
	}

	public void clickProceedToBuy() {
		proceedToBuyBtn.click();
	}

	public boolean isCheckoutPageDisplayed() {
		return ldriver.getCurrentUrl().contains("checkout");
	}

}
