package stepDefinitions;

import java.time.Duration;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	        try {

	            log.error("❌ Step failed - capturing screenshot");

	            // Capture screenshot
	            byte[] screenshot = ((TakesScreenshot) baseClass.getDriver())
	                    .getScreenshotAs(OutputType.BYTES);

	            // Create screenshots folder
	            Path screenshotFolder = Paths.get("target", "screenshots");
	            Files.createDirectories(screenshotFolder);

	            // Create unique screenshot name
	            String time = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss-SSS")
	                    .format(new Date());

	            String scenarioName = scenarioObj.getName()
	                    .replaceAll("[^a-zA-Z0-9-_]", "_");

	            String browser = baseClass.getBrowser(); 

	            String fileName = scenarioName
	                    + "_" + browser
	                    + "_" + time
	                    + ".png";

	            Path screenshotPath = screenshotFolder.resolve(fileName);

	            // Save screenshot as physical PNG file
	            Files.write(screenshotPath, screenshot);

	            log.info("📸 Screenshot saved: " + screenshotPath);

	            // Keep existing Cucumber report attachment
	            scenarioObj.attach(
	                    screenshot,
	                    "image/png",
	                    "Failed Step Screenshot"
	            );

	        } catch (Exception e) {

	            log.error("❌ Failed to save screenshot: " + e.getMessage());
	        }
	    }
	}

    @After
    public void tearDown(Scenario scenarioObj) {

        log.info(
            "========== SCENARIO ENDED =========="
        );

        log.info(
            "Scenario Status: "
                    + scenarioObj.getStatus()
        );

        // Browser close karo
        baseClass.quitDriver();
    }
}