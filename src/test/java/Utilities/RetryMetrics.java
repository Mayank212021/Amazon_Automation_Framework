package Utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class RetryMetrics {

    private static final AtomicInteger retryExecuted =
            new AtomicInteger(0);

    private static final AtomicInteger recoveredByRetry =
            new AtomicInteger(0);

    private static final AtomicInteger finalFailures =
            new AtomicInteger(0);

    public static void markRetryExecuted() {
        retryExecuted.incrementAndGet();
    }

    public static void recordRetryResult(boolean passed) {

        if (passed) {
            recoveredByRetry.incrementAndGet();
        } else {
            finalFailures.incrementAndGet();
        }
    }

    public static void generateReport() {

        File targetFolder = new File("target");

        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }

        File report =
                new File("target/retry-metrics.properties");

        try (FileWriter writer = new FileWriter(report)) {

            writer.write(
                    "retryExecuted="
                            + retryExecuted.get()
                            + System.lineSeparator());

            writer.write(
                    "recoveredByRetry="
                            + recoveredByRetry.get()
                            + System.lineSeparator());

            writer.write(
                    "finalFailures="
                            + finalFailures.get()
                            + System.lineSeparator());

            System.out.println(
                    "📊 Retry Metrics generated: "
                            + report.getAbsolutePath());

        } catch (IOException e) {

            System.err.println(
                    "❌ Unable to generate retry metrics: "
                            + e.getMessage());
        }
    }
}