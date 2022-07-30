package com.pcloudyhackathon.library;

import org.testng.Assert;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * SessionData class contains setters and getters methods which helps in
 * maintaining the data for each script
 */
public class SessionData {

    /**
     * Session Data variables declarations
     */

    private static int timeOuts = 10000;
    private static int logLevel = 1;
    private static String deviceVersion = "12.0";
    private static String platform = "Android";
    private static String applicationName;
    private static String bundleId;
    private static String appPackage;
    private static String appActivity;
    private static boolean executeOnPCloudy = true;
    private static String pCloudyApiKey;
    private static String pCloudyEmail;
    private static Properties envProp = new Properties();
    private static String deviceName;

    private static String timeStamp;

    private static String pCloudyUrl = "https://device.pcloudy.com";

    /**
     * Get method for timeout
     *
     * @return timeouts - defaults to 10000 ms
     */
    public static int getTimeout() {
        return timeOuts;
    }


    /**
     * Set method for timeout of the Selenide
     *
     * @param timeOut
     */
    public static void setTimeout(String timeOut) {
        // assign only if the parameter is not null when passed
        timeOut = timeOut == null ? System.getProperty("timeout") : timeOut;
        // check if the parameter is passed from mvn command else pick from config.properties
        timeOut = timeOut == null ? envProp.getProperty("time_out").toString() : timeOut.toString();
        timeOuts = Integer.parseInt(timeOut);
    }

    /**
     * Get Logs type, will be mapped to integers
     * INFO 	- 1
     * ERROR	- 1
     * DEBUG 	- 2
     * WARNING	- 2
     *
     * @return logLevel - defaults to info log
     */
    public static int getLogLevel() {
        return logLevel;
    }

    /* Set log types will be mapped to integers
     * INFO 	- 1
     * ERROR	- 1
     * DEBUG 	- 2
     * WARNING	- 2
     *
     * @param log INFO, ERROR, DEBUG, WARNING
     */
    public static void setLogLevel(String log) {

        // assign only if the parameter is not null when passed
        log = log == null ? System.getProperty("logLevel") : log;
        // check if the parameter is passed from mvn command else pick from config.properties
        log = log == null ? envProp.getProperty("log_level").toString() : log.toString();

        switch (log.toUpperCase()) {
            case "DEBUG":
            case "WARNING":
                logLevel = 2;
                break;

            default:
                logLevel = 1;
                break;
        }
    }

    /**
     * fetch the device version
     *
     * @return deviceVersion - defaults to 12.0
     */
    public static String getDeviceVersion() {
        return deviceVersion;
    }

    /**
     * Set device version
     *
     * @param versionNo
     */
    public static void setDeviceVersion(String versionNo) {
        // assign only if the parameter is not null when passed
        versionNo = versionNo == null ? System.getProperty("appName") : versionNo;
        // check if the parameter is passed from mvn command else pick from config.properties
        versionNo = versionNo == null ? envProp.getProperty("app_name").toString() : versionNo.toString();
        deviceVersion = versionNo;
    }

    /**
     * fetch platform name
     *
     * @return platform - defaults to andorid
     */
    public static String getPlatform() {
        return platform;
    }

    /**
     * Set platformName name
     *
     * @param platformName
     */
    public static void setPlatform(String platformName) {
        // assign only if the parameter is not null when passed
        platformName = platformName == null ? System.getProperty("platformName") : platformName;
        // check if the parameter is passed from mvn command else pick from config.properties
        platformName = platformName == null ? envProp.getProperty("platform_name").toString() : platformName.toString();

        platform = platformName;
    }

    /**
     * fetch application name
     *
     * @return applicationName
     */
    public static String getApplicationName() {
        if (applicationName == null) {
            Assert.fail("applicationName is null, refer README.md to understand how to set this value");
        }
        return applicationName;
    }

    /**
     * Get application name
     *
     * @param appName
     */
    public static void setApplicationName(String appName) {
        // assign only if the parameter is not null when passed
        appName = appName == null ? System.getProperty("appName") : appName;
        // check if the parameter is passed from mvn command else pick from config.properties
        appName = appName == null ? envProp.getProperty("app_name").toString() : appName.toString();
        applicationName = appName;
    }

    /**
     * fetch bundle id
     *
     * @return bundleId
     */
    public static String getBundleId() {
        if (bundleId == null) {
            Assert.fail("bundleId is null, refer README.md to understand how to set this value");
        }
        return bundleId;
    }

    /**
     * Set bundle id
     *
     * @param bundle_Id
     */
    public static void setBundleId(String bundle_Id) {
        // assign only if the parameter is not null when passed
        bundle_Id = bundle_Id == null ? System.getProperty("bundleId") : bundle_Id;
        // check if the parameter is passed from mvn command else pick from config.properties
        bundle_Id = bundle_Id == null ? envProp.getProperty("bundle_id").toString() : bundle_Id.toString();
        bundleId = bundle_Id;
    }

    /**
     * fetch app package
     *
     * @return appPackage
     */
    public static String getAppPackage() {
        if (appPackage == null) {
            Assert.fail("appPackage is null, refer README.md to understand how to set this value");
        }
        return appPackage;
    }

    /**
     * Set app package
     *
     * @param applicationPackage
     */
    public static void setAppPackage(String applicationPackage) {
        // assign only if the parameter is not null when passed
        applicationPackage = applicationPackage == null ? System.getProperty("appPackage") : applicationPackage;
        // check if the parameter is passed from mvn command else pick from config.properties
        applicationPackage = applicationPackage == null ? envProp.getProperty("app_package").toString() : applicationPackage.toString();
        appPackage = applicationPackage;
    }

    /**
     * fetch app activity
     *
     * @return appActivity
     */
    public static String getAppActivity() {
        if (appActivity == null) {
            Assert.fail("appActivity is null, refer README.md to understand how to set this value");
        }
        return appActivity;
    }

    /**
     * Set app activity
     *
     * @param applicationActivity
     */
    public static void setAppActivity(String applicationActivity) {
        // assign only if the parameter is not null when passed
        applicationActivity = applicationActivity == null ? System.getProperty("appActivity") : applicationActivity;
        // check if the parameter is passed from mvn command else pick from config.properties
        applicationActivity = applicationActivity == null ? envProp.getProperty("app_activity").toString() : applicationActivity.toString();
        appActivity = applicationActivity;
    }

    /**
     * fetch the execution on pCloudy
     *
     * @return executeOnPCloudy - boolean - defaults to true
     */
    public static boolean isExecuteOnPCloudy() {
        return executeOnPCloudy;
    }

    /**
     * set execution on pCloudy to true or false, false means the execution is on local machine
     *
     * @param execute_on_pCloudy - defaults - true(run test on pCloudy)
     */
    public static void setExecuteOnPCloudy(String execute_on_pCloudy) {
        executeOnPCloudy = execute_on_pCloudy.equalsIgnoreCase("false") ? false : true;
    }

    /**
     * fetch pCloudy Api Key
     *
     * @return
     */
    public static String getPCloudyApiKey() {
        if (pCloudyApiKey == null) {
            Assert.fail("pCloudyApiKey is null, refer README.md to understand how to set this value");
        }
        return pCloudyApiKey;
    }

    /**
     * Set pCloudy key
     *
     * @param pCloudyApiKey
     */
    public static void setPCloudyApiKey(String pCloudyApiKey) {
        // assign only if the parameter is not null when passed
        pCloudyApiKey = pCloudyApiKey == null ? System.getProperty("pCloudyApiKey") : pCloudyApiKey;
        // check if the parameter is passed from mvn command else pick from config.properties
        pCloudyApiKey = pCloudyApiKey == null ? envProp.getProperty("pCloudy_apiKey").toString() : pCloudyApiKey.toString();
        SessionData.pCloudyApiKey = pCloudyApiKey;
    }

    /**
     * fetch pCloudy email
     *
     * @return
     */
    public static String getPCloudyEmail() {
        if (pCloudyEmail == null) {
            Assert.fail("pCloudyEmail is null, refer README.md to understand how to set this value");
        }
        return pCloudyEmail;
    }

    /**
     * Set pCloudy email
     *
     * @param pCloudyEmail
     */
    public static void setPCloudyEmail(String pCloudyEmail) {
        // assign only if the parameter is not null when passed
        pCloudyEmail = pCloudyEmail == null ? System.getProperty("pCloudyEmail") : pCloudyEmail;
        // check if the parameter is passed from mvn command else pick from config.properties
        pCloudyEmail = pCloudyEmail == null ? envProp.getProperty("pCloudy_email").toString() : pCloudyEmail.toString();
        SessionData.pCloudyEmail = pCloudyEmail;
    }

    /**
     * get device name
     * @return
     */
    public static String getDeviceName() {
        if (deviceName == null) {
            Assert.fail("deviceName is null, refer README.md to understand how to set this value");
        }
        return deviceName;
    }

    /**
     * Set device name
     * @param device_name
     */
    public static void setDeviceName(String device_name) {
        // assign only if the parameter is not null when passed
        device_name = device_name == null ? System.getProperty("appName") : device_name;
        // check if the parameter is passed from mvn command else pick from config.properties
        device_name = device_name == null ? envProp.getProperty("app_name").toString() : device_name.toString();
        deviceName = device_name;
    }

    /**
     * Get cloud url
     * @return
     */
    public static String getPCloudyUrl() {
        return pCloudyUrl;
    }

    /**
     * Set pcloudy url
     * @param url
     */
    public static void setPCloudyUrl(String url) {
        pCloudyUrl = url == null ? envProp.get("device_cloud_url").toString() : url;
    }

    public static String getTimeStamp () {
        return timeStamp;
    }

    public static void setTimeStamp (String stamp) {
        timeStamp =stamp;
    }
    /**
     * Load config property file
     */
    public static void loadConfigProperty() {
        try {
            FileReader reader = new FileReader("config.properties");
            envProp.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
