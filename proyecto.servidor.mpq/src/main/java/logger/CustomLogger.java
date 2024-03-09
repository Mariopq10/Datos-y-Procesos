package logger;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CustomLogger {
    private static final Logger logger = Logger.getLogger(CustomLogger.class.getName());
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static void logEvent(String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("logfile.log", true))) {
            String timestamp = dateFormat.format(new Date());
            String logMessage = timestamp + " - " + message;
            writer.println(logMessage);
            logger.log(Level.INFO, logMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        logEvent("Starting application");
        // Perform some actions...
        logEvent("Application initialized");
        // More actions...
        logEvent("Exiting application");
    }
}