package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

public class Utils {

    public int networkStatus(String URL) {

        int statusCode = 0;
        try {
            java.net.URL url = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            statusCode = connection.getResponseCode();
        } catch (Exception e) {
            e.getMessage();
            System.out.println("Failed to load");
        }
        return statusCode;

    }

    public String consoleResult(WebDriver driver) {

        String msg = "null";
        LogEntries logs = driver.manage().logs().get("browser");

        for (org.openqa.selenium.logging.LogEntry entry : logs) {
            if (entry.getLevel() == Level.SEVERE) {
                msg = entry.getMessage();
            }
        }
        return msg;
    }
}
