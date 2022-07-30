package com.pcloudyhackathon.library;

import com.codeborne.selenide.WebDriverProvider;
import com.codeborne.selenide.WebDriverRunner;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import com.pcloudyhackathon.utils.CustomLogger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;

public class DriverImpl implements WebDriverProvider {
    CustomLogger log = new CustomLogger(DriverImpl.class);

    /**
     * Create capabilities based on parameters
     *
     * @return
     */
    private DesiredCapabilities createDriverCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        // capabilities related only to pCloudy
        if (SessionData.isExecuteOnPCloudy()) {
            capabilities.setCapability("pCloudy_Username", SessionData.getPCloudyEmail());
            capabilities.setCapability("pCloudy_ApiKey", SessionData.getPCloudyApiKey());
            capabilities.setCapability("pCloudy_DurationInMinutes", 5);
            log.info("[createDriverCapabilities] - getDeviceName : "+SessionData.getDeviceName());
            capabilities.setCapability("pCloudy_DeviceVersion", SessionData.getDeviceName());
            capabilities.setCapability("pCloudy_DeviceVersion", SessionData.getDeviceVersion());
            log.info("[createDriverCapabilities] - ApplicationName : "+SessionData.getApplicationName());
            capabilities.setCapability("pCloudy_ApplicationName", SessionData.getApplicationName());
            capabilities.setCapability("pCloudy_WildNet", "false");
            capabilities.setCapability("pCloudy_EnableVideo", "true");
            capabilities.setCapability("pCloudy_EnablePerformanceData", "true");
            capabilities.setCapability("pCloudy_EnableDeviceLogs", "true");
        } else {
            log.info("[createDriverCapabilities] - app : "+SessionData.getApplicationName());
            capabilities.setCapability("appium:app", SessionData.getApplicationName());
        }
        // common capabilities
        capabilities.setCapability("newCommandTimeout", 600);
        capabilities.setCapability("launchTimeout", 90000);
        log.info("[createDriverCapabilities] - getDeviceVersion : "+SessionData.getDeviceVersion());
        log.info("[createDriverCapabilities] - getPlatform : "+SessionData.getPlatform());
        capabilities.setCapability("appium:platformVersion", SessionData.getDeviceVersion());
        capabilities.setCapability("appium:platformName", SessionData.getPlatform());

        // ios capabilities
        if (SessionData.getPlatform().equalsIgnoreCase("ios")) {
            capabilities.setCapability("automationName", "XCUITest");
            log.info("[createDriverCapabilities] - getBundleId : "+SessionData.getBundleId());
            capabilities.setCapability("bundleId", SessionData.getBundleId());

            return capabilities;
        }

        // android capabilities
        capabilities.setCapability("appium:automationName", "uiautomator2");
        log.info("[createDriverCapabilities] - getAppPackage : "+SessionData.getAppPackage());
        log.info("[createDriverCapabilities] - getAppActivity : "+SessionData.getAppActivity());
        capabilities.setCapability("appium:appPackage", SessionData.getAppPackage());
        capabilities.setCapability("appium:appActivity", SessionData.getAppActivity());

        return capabilities;
    }

    /**
     * Launch the application on device
     */
    public void launchApplication() {
        // logic to run on local or pCloudy device
        String baseUrl = SessionData.isExecuteOnPCloudy() ? "https://device.pcloudy.com/appiumcloud/wd/hub" : "http://0.0.0.0:4723/wd/hub";
        DesiredCapabilities capabilities = createDriverCapabilities();
        log.debug("[launchApplication] - DesiredCapabilities : "+ capabilities.toJson().toString());
        try {
            log.info("[launchApplication] - isExecuteOnPCloudy : "+ SessionData.isExecuteOnPCloudy());

            // execution on - android
            if (SessionData.getPlatform().equalsIgnoreCase("android")) {
                WebDriverRunner.setWebDriver(new AndroidDriver(new URL(baseUrl), capabilities));
                return;
            }
            // execution on - ios
            WebDriverRunner.setWebDriver(new IOSDriver(new URL(baseUrl), capabilities));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        String baseUrl = SessionData.isExecuteOnPCloudy() ? SessionData.getPCloudyUrl()+"appiumcloud/wd/hub" : "http://0.0.0.0:4723/wd/hub";
        Capabilities localCap = createDriverCapabilities();
        try {
            // android
            if (SessionData.getPlatform().equalsIgnoreCase("android")) {
                UiAutomator2Options options = new UiAutomator2Options();
                log.debug("[createDriver] - capabilities : "+ localCap);
                options.merge(capabilities);
                options.merge(localCap);
                log.debug("[createDriver] - DesiredCapabilities : "+ options.toJson().toString());
                return new AndroidDriver(new URL(baseUrl), options);
            }
            //ios
            XCUITestOptions options = new XCUITestOptions();
            options.merge(capabilities);
            options.merge(localCap);
            log.debug("[createDriver] - DesiredCapabilities : "+ options.toJson().toString());
            return new IOSDriver(new URL(baseUrl), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
