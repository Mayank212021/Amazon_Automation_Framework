package pageObject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

WebDriver ldriver;
	
	public LoginPage(WebDriver rDriver) {
		
		ldriver = rDriver;
		PageFactory.initElements(rDriver, this);
	}
	
	@FindBy(xpath = "//span[contains(text(),'Hello')]")
	WebElement HellosignIn;
		
	
	

	@FindBy(xpath = "//span[text()='Sign in']")
	WebElement signInLink;
	
	
	@FindBy(id = "ap_email_login")
	WebElement emailAddress;
	
	@FindBy(xpath = "//input[@type='submit']")
	WebElement continueButton;
	
	@FindBy(id = "ap_password")
	WebElement passWord;
	
	@FindBy(xpath = "//input[@id='signInSubmit']")
	WebElement signInButton;
	
	@FindBy(xpath = "//span[@id='nav-link-accountList-nav-line-1']")
	WebElement helloUserText;
	
	
	@FindBy(xpath = "//p[@class='a-spacing-none a-spacing-top-base']")
	private WebElement errorMessageBox;
	
	@FindBy(xpath = "//div[@class='a-section a-spacing-base auth-pagelet-container']//div[@class='a-box-inner a-alert-container']//div[1]")
	private WebElement incorrectMessage;
	
	
	  public void clickOnHelloSign() {

	         WebDriverWait wait = new WebDriverWait(ldriver, Duration.ofSeconds(15));

		    // Page load wait
		    wait.until(driver -> ((JavascriptExecutor) driver)
		        .executeScript("return document.readyState").equals("complete"));

		    // SAME WebElement pe wait lagao
		    wait.until(ExpectedConditions.visibilityOf(HellosignIn));

		    // Hover
		    Actions act = new Actions(ldriver);
		    act.moveToElement(HellosignIn).perform();
		     }
	
	  
	  public void clickOnSigninLink() {

		    WebDriverWait wait = new WebDriverWait(ldriver, Duration.ofSeconds(10));

		    try {
		        WebElement signIn = wait.until(
		            ExpectedConditions.visibilityOf(signInLink)
		        );

		        signIn.click();
		        System.out.println("✅ Clicked Sign In");

		    } catch (Exception e) {
		        System.out.println("⚠️ Sign In not visible - शायद user already logged in");
		    }
		}
	  
	 
	public void enterEmailAddress(String emailAdd) {
		
		emailAddress.sendKeys(emailAdd);
	}
	
   public void clickOnContinueButton() {
		
		continueButton.click();
	}

   public void enterPassword(String pass) {

	    WebDriverWait wait = new WebDriverWait(ldriver, Duration.ofSeconds(15));

	    // Wait for password field to be visible
	    WebElement passwordField = wait.until(
	        ExpectedConditions.visibilityOf(passWord)
	    );

	    passwordField.sendKeys(pass);
	}
   public void clickOnSigninButton() {
	
	signInButton.click();
	
}
   
   public boolean isUserLoggedIn() {
	    
	    WebDriverWait wait = new WebDriverWait(ldriver, Duration.ofSeconds(10));
	    
	    try {
	        wait.until(ExpectedConditions.visibilityOf(helloUserText));
	        return helloUserText.getText().contains("Hello");
	    } catch (Exception e) {
	        return false;
	    }
	}
   
   
   public boolean isErrorDisplayed() {

	    try {
	        WebDriverWait wait = new WebDriverWait(ldriver, Duration.ofSeconds(10));

	        wait.until(ExpectedConditions.visibilityOf(errorMessageBox));

	        String msg = errorMessageBox.getText();
	        System.out.println("Error Message: " + msg);

	        return msg.toLowerCase().contains("Let's") 
	            || msg.toLowerCase().contains("create");

	    } catch (Exception e) {
	        return false;
	    }
	}
   
   public boolean isWrongPasswordErrorDisplayed() {

	    try {
	        WebDriverWait wait = new WebDriverWait(ldriver, Duration.ofSeconds(10));

	        wait.until(ExpectedConditions.visibilityOf(incorrectMessage));

	        String msg = incorrectMessage.getText();
	        System.out.println("Error Message: " + msg);

	        return msg.toLowerCase().contains("incorrect") 
	            || msg.toLowerCase().contains("problem");

	    } catch (Exception e) {
	        return false;
	    }
	}
   
   
   
}




