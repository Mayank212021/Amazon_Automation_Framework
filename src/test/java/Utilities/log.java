package Utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import base.baseClass;

public class log {

    public static Logger logger = LogManager.getLogger(log.class);

    public static void info(String message) {
        String browser = baseClass.getBrowser();
        String scenario = baseClass.scenario.get();
        String thread = Thread.currentThread().getName();

        logger.info(format(browser, scenario, thread, message));
    }

    public static void error(String message) {
        String browser = baseClass.getBrowser();
        String scenario = baseClass.scenario.get();
        String thread = Thread.currentThread().getName();

        logger.error(format(browser, scenario, thread, message));
    }

    private static String format(String browser, String scenario, String thread, String message) {
        return "[BROWSER: " + browser + "] "
             + "[SCENARIO: " + scenario + "] "
             + "[THREAD: " + thread + "] -> "
             + message;
    }
}