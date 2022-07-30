package com.pcloudyhackathon.library;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SimpleReport;
import com.pcloudyhackathon.utils.CustomLogger;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.open;

public class BaseTest {

    protected SimpleReport report;
    final CustomLogger log = new CustomLogger(BaseTest.class);

    @BeforeTest
    @Parameters({"timeout", "logLevel", "pCloudyApiKey", "pCloudyEmail", "bundleId", "appPackage",
            "appActivity", "appName", "executeOnPCloudy", "platformName", "deviceName", "deviceVersion"})
    public void LaunchApp(@Optional String timeout, @Optional String logLevel, @Optional String pCloudyApiKey,
               @Optional String pCloudyEmail, @Optional String bundleId, @Optional String appPackage,
               @Optional String appActivity, @Optional String appName, @Optional String executeOnPCloudy,
               @Optional String platformName, @Optional String deviceName, @Optional String deviceVersion) {
        // Initialization of simple reports to start
        log.info("IN before test");
        report = new SimpleReport();
        report.start();

        // Set parameters to sessionData,
        // if these parameters are not present in testNg xml
        // then read the data from system properties (set using mvn run command)
        // then check env properties files
        SessionData.loadConfigProperty();
        SessionData.setTimeout(timeout);
        SessionData.setLogLevel(logLevel);
        SessionData.setPCloudyApiKey(pCloudyApiKey);
        SessionData.setPCloudyEmail(pCloudyEmail);
        SessionData.setBundleId(bundleId);
        SessionData.setAppPackage(appPackage);
        SessionData.setAppActivity(appActivity);
        SessionData.setApplicationName(appName);
        SessionData.setExecuteOnPCloudy(executeOnPCloudy);
        SessionData.setPlatform(platformName);
        SessionData.setDeviceVersion(deviceVersion);
        SessionData.setDeviceName(deviceName);

        // launch the application and set the driver to global access
        log.info("Launching the application");
        SessionData.setTimeStamp(String.valueOf(new Timestamp(System.currentTimeMillis())));

        // location where screenshots are kept
        Configuration.reportsFolder = "target/selenide/"+SessionData.getTimeStamp();
        Configuration.timeout = SessionData.getTimeout();

        new DriverImpl().launchApplication();

    }

    @AfterTest
    public void tearDown() {
        WebDriverRunner.getWebDriver().quit();
        report.finish(getClass().getSimpleName());
    }
}
