package pageObject;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.log;

public class checkoutPage {

	WebDriver ldriver;
	WebDriverWait wait;
	JavascriptExecutor js;

	public checkoutPage(WebDriver rDriver) {

		ldriver = rDriver;
		PageFactory.initElements(rDriver, this);
		this.js = (JavascriptExecutor) ldriver; 
		this.wait = new WebDriverWait(ldriver, Duration.ofSeconds(15));

	}

	@FindBy(xpath = "//a[@aria-label='Change delivery address']")
	WebElement changeAddressButton;
	
	@FindBy(xpath = "//span[contains(text(),'Lane No 8 Nevada Sundarpur, Rajender vihar colony,')]")
    WebElement addressText;

	@FindBy(xpath = "//span[@id='checkout-primary-continue-button-id']//input[@type='submit']")
	WebElement deliverButton;
	
	@FindBy(xpath = "//div[@role='dialog']//button")
	WebElement closePrimePopup;

	 // All payment radio buttons (generic)
    @FindBy(xpath = "//input[@type='radio']")
    List<WebElement> paymentOptions;

    // Use this payment button
    @FindBy(xpath = "//span[@id='checkout-primary-continue-button-id']//input[@type='submit']")
    List<WebElement> usePaymentBtn;
    
    @FindBy(xpath = "//a[@id='nav-logo-sprites']")
    List<WebElement> amazonLogo;
    
	@FindBy(xpath = "	//span[@id='nav-link-accountList-nav-line-1']")
	WebElement HelloUser;
	
	@FindBy(xpath = "//span[normalize-space()='Sign Out']")
	WebElement signOut;
	
	@FindBy(xpath = "//i[@aria-label='Amazon']")
	WebElement gotoHomePage;
	
	
	
	
	

	public void clickChangeAddress() {
	    try {
	        if (changeAddressButton.isDisplayed()) {
	            changeAddressButton.click();
	            log.logger.info("✅ Clicked Change Address");
	        }
	    } catch (Exception e) {
	    	log.logger.error("Change Address button not present: " + e.getMessage());
	    }
	}
	
	

	 public void selectDeliveryAddress() {
	        try {
	            WebElement address = wait.until(ExpectedConditions.visibilityOf(addressText));

	            // Scroll into view
	            ((JavascriptExecutor) ldriver).executeScript("arguments[0].scrollIntoView(true);", address);

	            // Click address
	            address.click();

	            log.logger.info("✅ Address selected successfully");

	        } catch (Exception e) {
	        	log.logger.error("❌ Address not found or not clickable");
	            throw e;
	        }
	    }


	public void clickOnDeliveryToThisAddress() {
	    wait.until(ExpectedConditions.elementToBeClickable(deliverButton));

	    try {
	        deliverButton.click();
	    } catch (Exception e) {
	        JavascriptExecutor js = (JavascriptExecutor) ldriver;
	        js.executeScript("arguments[0].click();", deliverButton);
	    }

	    log.logger.info("✅ Delivery button pressed");
	}

	public boolean isPaymentPageDisplayed() {
	    return wait.until(ExpectedConditions.urlContains("checkout"));
	}

	public void closePrimePopupIfPresent() {
	    try {
	        WebDriverWait wait = new WebDriverWait(ldriver, Duration.ofSeconds(5));
	        wait.until(ExpectedConditions.visibilityOf(closePrimePopup));
	        closePrimePopup.click();
	        log.logger.info("✅ Prime popup closed");
	    } catch (Exception e) {
	    	log.logger.error("ℹ️ No popup present");
	    }
	}
	
	
	 public void selectPaymentMethod() {

		 log.logger.info("Selecting Payment Method...");

	        boolean isSelected = false;

	        wait.until(ExpectedConditions.visibilityOfAllElements(paymentOptions));

	        for (WebElement option : paymentOptions) {

	            try {
	                WebElement label = option.findElement(By.xpath("./ancestor::label"));
	                String text = label.getText().toLowerCase();

	                log.logger.info("👉 Payment Option Found: " + text);

	                js.executeScript("arguments[0].scrollIntoView(true);", option);

	                // Priority 1 → Scan & Pay
	                if (text.contains("scan") || text.contains("pay")) {
	                    js.executeScript("arguments[0].click();", option);
	                    log.logger.info("✅ Scan & Pay Selected");
	                    isSelected = true;
	                    break;
	                }

	                // Priority 2 → UPI
	                else if (text.contains("upi")) {
	                    js.executeScript("arguments[0].click();", option);
	                    log.logger.info("✅ UPI Selected");
	                    isSelected = true;
	                    break;
	                }

	                // Priority 3 → COD
	                else if (text.contains("cash") || text.contains("delivery")) {
	                    js.executeScript("arguments[0].click();", option);
	                    log.logger.info("✅ COD Selected");
	                    isSelected = true;
	                    break;
	                }

	            } catch (Exception e) {
	            	log.logger.error("⚠️ Skipping one option");
	            }
	        }

	        if (!isSelected) {
	        	log.logger.error("❌ No payment option matched");
	        }

	        clickUseThisPayment();
	    }

	    public void clickUseThisPayment() {
	        try {
	            if (usePaymentBtn.size() > 0) {
	                wait.until(ExpectedConditions.elementToBeClickable(usePaymentBtn.get(0)));
	                js.executeScript("arguments[0].click();", usePaymentBtn.get(0));
	                log.logger.info("✅ Clicked Use This Payment Method");
	            } else {
	            	log.logger.info("⚠️ Use Payment button not found");
	            }
	        } catch (Exception e) {
	        	log.logger.error("❌ Failed to click Use Payment button");
	        }
	    }

	    
	    public void clickAmazonLogo() {
	    	
	    	 try {
		            if (amazonLogo.size() > 0) {
		                wait.until(ExpectedConditions.elementToBeClickable(amazonLogo.get(0)));
		                js.executeScript("arguments[0].click();", amazonLogo.get(0));
		                log.logger.info("✅ Clicked on amazon logo");
		            } else {
		            	log.logger.info("⚠ Amazon logo is not found");
		            }
		        } catch (Exception e) {
		        	log.logger.error("❌ Failed to click on amazon logo");
		        }
		    }
	    
	    
	    public void clickSignOut() {

	        Actions actions = new Actions(ldriver);

	        // Hover on "Hello, Mayank"
	        actions.moveToElement(HelloUser).perform();

	        // Click on Sign Out
	        signOut.click();
	        log.logger.info("Clicking signout button");
	    }
	    
	    
	    public void clickonHomePage() {
	        try {
	            wait.until(ExpectedConditions.elementToBeClickable(gotoHomePage));
	            js.executeScript("arguments[0].click();", gotoHomePage);
	            log.logger.info("✔ Clicked on Home logo");
	        } catch (Exception e) {
	        	log.logger.error("❌ Failed to click on Home logo");
	        }
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    }




