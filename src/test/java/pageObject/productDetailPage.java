package pageObject;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utilities.log;

public class productDetailPage {

	WebDriver ldriver;
	
	public productDetailPage(WebDriver rDriver) {
		
		ldriver = rDriver;
		PageFactory.initElements(rDriver, this);
	}
	
	 @FindBy(xpath = "//input[@id='add-to-cart-button']")
	    WebElement addToCartBtn;

	 @FindBy(id = "attachSiNoCoverage")
	 WebElement noThanksBtn;

	    @FindBy(id = "nav-cart-count")
	    WebElement cartCount;

	    @FindBy(xpath = "//span[contains(text(),'Added to Cart')]")
	    WebElement successMsg;

	    @FindBy(xpath = "//a[@href='/cart?ref_=sw_gtc']")
	    WebElement goToCartBtn;

	    // Actions
	    public void clickAddToCart() {
	        WebDriverWait wait = new WebDriverWait(ldriver, Duration.ofSeconds(10));
	        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
	       
	    }

	    public void clickNoThanks() {
	        WebDriverWait wait = new WebDriverWait(ldriver, Duration.ofSeconds(10));

	        try {
	            wait.until(ExpectedConditions.elementToBeClickable(noThanksBtn)).click();
	            
	        } catch (Exception e) {
	        	log.logger.error("❌ No Thanks popup not displayed");
	        }
	    }

	    public int getCartCount() {
	        WebDriverWait wait = new WebDriverWait(ldriver, Duration.ofSeconds(10));

	        wait.until(driver -> Integer.parseInt(cartCount.getText()) > 0);

	        return Integer.parseInt(cartCount.getText());
	    }

	   

	    public void clickGoToCart() {
	        WebDriverWait wait = new WebDriverWait(ldriver, Duration.ofSeconds(10));
	        wait.until(ExpectedConditions.elementToBeClickable(goToCartBtn)).click();
	    }
			
		}
	
	

