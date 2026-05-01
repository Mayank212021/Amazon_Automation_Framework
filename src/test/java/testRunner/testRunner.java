package testRunner;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;


import Utilities.log;
import base.baseClass;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
	    features = "src/test/java/features/AmazonPurchase.feature",
	    glue = "stepDefinitions",
	    tags = "@Regression",
	    monochrome = true,
	    		 plugin = {
	    			        "pretty",
	    			        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
	    			    }
	)


public class testRunner extends AbstractTestNGCucumberTests {
	
	public static ThreadLocal<String> browserThread = new ThreadLocal<>();

	// git update browser
	@Parameters("browser")
	@BeforeMethod(alwaysRun = true)
	public void setUp(@Optional("chrome") String browserName) {

	    browserThread.set(browserName);

	    System.out.println("RUNNING ON: " + browserName +
	        " | THREAD: " + Thread.currentThread().getId());
	}

	public static String getBrowser() {
	    return browserThread.get();
	}
	

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    // ✅ Dynamic Reports
    static {
        String time = new SimpleDateFormat("MM-dd-yyyy_hh-mm-ss").format(new Date());

        System.setProperty("cucumber.plugin",
                "pretty," +
                "html:target/cucumber-report-" + time + ".html," +
                "json:target/cucumber-" + time + ".json," +
                "junit:target/cucumber-" + time + ".xml," +
                "rerun:target/failed_scenario.txt," );

        System.setProperty("extent.reporter.spark.out",
                "target/ExtentReport_" + time + ".html");
        System.setProperty("extent.reporter.spark.inline", "true");
    }
    
   
    //update for git

    // ✅ Failed scenario rerun (Fixed)
    /*
    @AfterSuite
    public void rerunFailedScenarios() {

        File file = new File("target/failed_scenario.txt");

        if (file.exists() && file.length() > 0) {

            log.logger.info("🔥 Rerunning Failed Scenarios...");

            for (int i = 1; i <= 2; i++) {

                org.testng.TestNG testng = new org.testng.TestNG();

                // 🔥 IMPORTANT: Same browser pass karna padega
                testng.setTestClasses(new Class[]{failedTestRunner.class});

                // ✅ Pass parameter properly
                testng.setDefaultSuiteName("RerunSuite");
                testng.setDefaultTestName("RerunTest");

                testng.setVerbose(1);

                testng.run();
            }

        } else {
            log.logger.info("✅ No failed scenarios to rerun");
        }
    }
    */
}


// test12