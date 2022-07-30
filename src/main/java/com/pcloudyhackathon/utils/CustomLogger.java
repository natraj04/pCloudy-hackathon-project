package com.pcloudyhackathon.utils;


import com.codeborne.selenide.Selenide;
import com.pcloudyhackathon.library.SessionData;

import java.text.SimpleDateFormat;
import java.util.Date;


/***
 * Custom logger class that contains all the log functions
 * INFO/ERROR/DEBUG/WARN/PASS/FAIL. These logs will be color coded and be
 * displayed only if a system property is set while execution.
 *
 * e.g. 
 * -DlogLevel=DEBUG -> this logs all the messages 
 * -DlogLevel=INFO -> this logs all the messages except DEBUG and WARN
 *
 * NOTE: Logs sometimes may not be color coded on IDE console please check
 * terminal
 *
 * @author nn
 *
 */
public class CustomLogger {

    /**
     * Constructor to fetch the class name which will be used on logs, do not take screen shot
     *
     * @param class1
     */
    public CustomLogger(Class<?> class1) {
        CLASS_NAME = class1.getSimpleName();
        takeScreenShot = false;
    }

    /**
     * Constructor to fetch the class name which will be used on logs and takes screenshot every time a log is called
     *
     * @param class1
     * @param take_screen_shot
     */
    public CustomLogger(Class<?> class1, Boolean take_screen_shot) {
        CLASS_NAME = class1.getSimpleName();
        takeScreenShot = take_screen_shot;
    }
    // Color code that has to be displayed on the log
    private static final String RED_BOLD = "\033[1;31m"; // RED
    private static final String GREEN_BOLD = "\033[1;32m"; // GREEN
    private static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    private static final String CYAN_BOLD = "\033[1;36m"; // CYAN
    private static final String BLUE_BOLD = "\033[1;94m";
    private static final String RESET = "\033[0m"; // Text Reset
    private static String CLASS_NAME;
    private boolean takeScreenShot;


    /**
     * Informational statements concerning program state
     *
     * @param message:
     */
    public void info(String message) {
        logMessage(message, "[INFO]   ", BLUE_BOLD);
    }

    /**
     * Statements that describe non-fatal errors in the application; this level is
     * used quite often for logging handled exceptions;
     *
     * @param message
     */
    public void error(String message) {
        logMessage(message, "[ERROR]  ", RED_BOLD);
    }

    /**
     * Fine-grained statements concerning program state, typically used for
     * debugging
     *
     * @param message
     */
    public void debug(String message) {
        if (SessionData.getLogLevel() == 2) logMessage(message, "[DEBUG]  ", CYAN_BOLD);
    }

    /**
     * Statements that describe non-fatal warnings
     *
     * @param message
     */
    public void warn(String message) {
        if (SessionData.getLogLevel() == 2) logMessage(message, "[WARNING]", YELLOW_BOLD);
    }

    /**
     * Information that describes the test as passed
     *
     * @param message
     */
    public void pass(String message) {
        logMessage(message, "[PASS]   ", GREEN_BOLD);
    }

    /**
     * Information that describes the test as failed
     *
     * @param message
     */
    public void fail(String message) {
        logMessage(message, "[FAIL]   ", RED_BOLD);
    }

    /**
     * Function that formats all the logs as below
     * [logType] - [ClassName] - [TimeStamp]
     *
     * @param message
     * @param type
     * @param color
     */
    private void logMessage(String message, String type, String color) {
        System.out.println(color + " [" + new SimpleDateFormat("hh:mm:ss.ms").format(new Date()) + "] - " + type + " - [" + CLASS_NAME + "]" + RESET + " " + message);
        if (takeScreenShot) {
            String pngFileName = Selenide.screenshot(message);
            System.out.println("pngFileName == "+pngFileName);
        }
    }

}
