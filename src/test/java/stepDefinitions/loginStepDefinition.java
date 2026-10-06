package stepDefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utilities.TestCredentials;
import Utilities.log;
import base.baseClass;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.Duration; 
import io.github.bonigarcia.wdm.WebDriverManager;
import pageObject.LoginPage;

public class loginStepDefinition {

    WebDriver driver;
    public LoginPage loginpage;

    // Background Step
    @Given("User is on browser")
    public void user_navigate_to_browser() {

        driver = baseClass.getDriver();   // ✅ ALWAYS get fresh driver
        loginpage = new LoginPage(driver);

        log.logger.info("Browser launched successfully");
    }

    @When("I navigate to Home page of the Amazon web application")
    public void open_amazon_homepage() {
        log.logger.info("Navigating to Amazon home page");
    }

    @When("Go to the Hello,Sign in option")
    public void click_hello_signin() {
        loginpage.clickOnHelloSign();
        log.logger.info("Clicking on Hello, Sign in option");
    }

    @And("Click on the Sign in link")
    public void click_signin_link() {
        loginpage.clickOnSigninLink();
        log.logger.info("Clicking on Sign in link");
    }

    @Then("User move to the Login page")
    public void verify_login_page() {
        log.logger.info("User is on login page");
    }
    
    
    @Given("User logs in with valid credentials {string}")
    public void loginWithValidCredentials(String user) {

        log.info("Logging in with test user: " + user);

        String username = TestCredentials.getUsername(user);
        String password = TestCredentials.getPassword(user);

        loginpage.enterEmailAddress(username);
        loginpage.clickOnContinueButton();
        loginpage.enterPassword(password);
        loginpage.clickOnSigninButton();
    }

    @When("User enters the valid phone number {string} into the fieldbox")
    public void enter_email(String validPhonenumber) {
        loginpage.enterEmailAddress(validPhonenumber);
        log.logger.info("Entering phone number: " + validPhonenumber);
    }

    @And("click on the Continue button")
    public void click_continue() {
        loginpage.clickOnContinueButton();
        log.logger.info("Clicking on Continue button");
    }

    @And("Enter the valid password {string} into password field")
    public void enter_password(String validPassword) {

        try {
            loginpage.enterPassword(validPassword);
            log.logger.info("Entering password");
        } catch (Exception e) {
            log.logger.error("User not registered: " + e.getMessage());
            Assert.fail("Login failed");
        }
    }

    @And("Click on the Sign In button")
    public void click_signin_button() {
        loginpage.clickOnSigninButton();
        log.logger.info("Clicking on Sign In button");
    }

    @Then("user should be logged in as {string}")
    public void verifyLogin(String expectedUserName) {

            Assert.assertTrue(loginpage.isUserLoggedIn(expectedUserName), "Login failed: Expected user '" 
                    + expectedUserName 
                    + "' was not logged in.");
            log.logger.info("User is logged in successfully");

       
    }
    
    @Then("user should see the user not found error")
    public void verifyUserNotFoundError() {

        Assert.assertTrue( 
                loginpage.isErrorDisplayed(),
                "User not found error was not displayed." 
        );

        log.logger.info(
                "User is not logged in because phone number is not registered"
        );
    }
    
    @Then("user should see the wrong password error")
    public void verifyWrongPasswordError() {

        Assert.assertTrue(
                loginpage.isWrongPasswordErrorDisplayed(),
                "Wrong password error was not displayed."
        );

        log.logger.info(
                "User is not logged in because of wrong password"
        );
    }
    
    
    
    
}