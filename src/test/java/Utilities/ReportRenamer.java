package Utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportRenamer {

    public static void renameReport() {

        String time = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

        File oldFile = new File("target/ExtentReports/ExtentReport.html");

        if (oldFile.exists()) {
            File newFile = new File("target/ExtentReports/ExtentReport_" + time + ".html");
            oldFile.renameTo(newFile);
        }
    }
}