package testRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import base.baseClass;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "@target/failed_scenario.txt",
    glue = "stepDefinitions",
    monochrome = true
)
public class failedTestRunner extends AbstractTestNGCucumberTests {

    // ✅ SAME AS MAIN RUNNER
    @BeforeClass
    @Parameters("browser")
    public void setUp(String br) {

        System.out.println("🔥 RERUN THREAD: "
                + Thread.currentThread().getId() + " -> " + br);

        baseClass.setBrowser(br);
    }

    // ✅ Parallel rerun support
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    // ✅ Separate reports for rerun
    static {
        String time = new SimpleDateFormat("MM-dd-yyyy_hh-mm-ss").format(new Date());

        System.setProperty("cucumber.plugin",
                "pretty," +
                "html:target/failed-cucumber-report-" + time + ".html," +
                "json:target/failed-cucumber-" + time + ".json," +
                "junit:target/failed-cucumber-" + time + ".xml," +
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:");

        System.setProperty("extent.reporter.spark.out",
                "target/FailedExtentReports/FailedExtentReport_" + time + ".html");
    }
}