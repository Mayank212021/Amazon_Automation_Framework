
package Utilities;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class RetryResultListener implements ITestListener {

    @Override
    public void onTestSuccess(ITestResult result) {
        RetryMetrics.recordRetryResult(true);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        RetryMetrics.recordRetryResult(false);
    }
}
