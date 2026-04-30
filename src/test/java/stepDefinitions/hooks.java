package stepDefinitions;

import java.time.Duration;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import Utilities.log;
import base.baseClass;
import io.cucumber.java.*;
import testRunner.testRunner;

public class hooks {
	
	 
	@Before
	public void setup(Scenario scenarioObj) {

	    String browser = testRunner.getBrowser(); // ✅ always available
	    baseClass.setBrowser(browser);

	    if (browser == null) {
	        throw new RuntimeException("❌ Browser not set");
	    }


	    String scenarioName = scenarioObj.getName() + " [" + browser + "]";
	    baseClass.scenario.set(scenarioName);
	    
	    
	    // ✅ IMPORTANT LINE (name override)
	    ExtentCucumberAdapter.addTestStepLog("🌐 Browser: " + browser);
	    

	    log.info("========== 🚀 SCENARIO STARTED ==========");
	    log.info("Scenario: " + scenarioName);

	    baseClass.initDriver();

	    baseClass.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	    baseClass.getDriver().get("https://www.amazon.in/");
	}

    // 🔥 Screenshot on every failed step
    @AfterStep
    public void captureStepFailure(Scenario scenarioObj) {

        if (scenarioObj.isFailed() && baseClass.getDriver() != null) {

            log.error("❌ Step failed - capturing screenshot");

            byte[] screenshot = ((TakesScreenshot) baseClass.getDriver())
                    .getScreenshotAs(OutputType.BYTES);

            scenarioObj.attach(screenshot, "image/png", "Failed Step Screenshot");
        }
    }

    @After
    public void tearDown(Scenario scenarioObj) {

        log.info("========== 🏁 SCENARIO ENDED ==========");
        log.info("Scenario Status: " + scenarioObj.getStatus());

        if (scenarioObj.isFailed() && baseClass.getDriver() != null) {

            log.error("❌ Scenario failed - final screenshot captured");

            byte[] screenshot = ((TakesScreenshot) baseClass.getDriver())
                    .getScreenshotAs(OutputType.BYTES);

            scenarioObj.attach(screenshot, "image/png", "Final Failure Screenshot");
        }

        baseClass.quitDriver();
    }
}