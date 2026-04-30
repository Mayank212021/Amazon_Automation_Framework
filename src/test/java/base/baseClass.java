package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import Utilities.log;
import io.github.bonigarcia.wdm.WebDriverManager;

public class baseClass {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<String> browser = new ThreadLocal<>();
    public static ThreadLocal<String> scenario = new ThreadLocal<>();

    // Get Driver
    public static WebDriver getDriver() {
        return driver.get();
    }

    // Get Browser (🔥 NEW - needed for logging)
    public static String getBrowser() {
        return browser.get();
    }

    // Set Browser
    public static void setBrowser(String browserName) {
        browser.set(browserName);

        log.info("🚀 Starting execution on browser: " + browserName);
    }

    // Init Driver
    public static void initDriver() {

        String browserName = browser.get();

        if (browserName == null) {
            throw new RuntimeException("❌ Browser not set for thread: " + Thread.currentThread().getId());
        }

        WebDriver driverInstance;

        switch (browserName.toLowerCase()) {

            case "chrome":
                WebDriverManager.chromedriver().setup();
                driverInstance = new ChromeDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driverInstance = new EdgeDriver();
                break;

            default:
                throw new RuntimeException("❌ Invalid browser: " + browserName);
        }

        driver.set(driverInstance);
        driver.get().manage().window().maximize();

        log.info("✅ Browser launched successfully");
    }

    // Quit Driver
    public static void quitDriver() {
        if (driver.get() != null) {

            log.info("🛑 Closing browser");

            driver.get().quit();
            driver.remove();
            browser.remove();
            scenario.remove();

            log.info("✅ Browser closed successfully");
        }
    }
}