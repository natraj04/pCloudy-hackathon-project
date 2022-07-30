package com.pcloudyhackathon.utils;

import com.codeborne.selenide.WebDriverRunner;
import com.pcloudyhackathon.library.SessionData;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.OutputType;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Set;
import java.util.SimpleTimeZone;

public class CommonUtils {

    static final CustomLogger log = new CustomLogger(CommonUtils.class);

    /**
     * function to switchTo a view (WEBVIEW or NATIVE)
     *
     * @param whichView: enum of view {WEBVIEW, NATIVE}
     */
    public static void switchTo(view whichView) {
        AndroidDriver driver = (AndroidDriver) WebDriverRunner.getWebDriver();
        Set<String> contexts = driver.getContextHandles();
        log.debug("All the views on the app :: " + contexts);
        String webView = contexts.stream().filter(currentContext -> currentContext.startsWith(whichView.toString())).findFirst().get();
        log.info("The current view on the app is :: " + webView);
        driver.context(webView);
    }

    /**
     * Wait till x milliseconds
     *
     * @param timeInMileSec - time in ms
     */
    public static void waitTill(int timeInMileSec) {
        AndroidDriver driver = (AndroidDriver) WebDriverRunner.getWebDriver();
        synchronized (driver) {
            try {
                driver.wait(timeInMileSec);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Hide the keyboard, there is 1 second wait in-case there is a lag on the device
     */
    public static void hideKeyboard() {
        AndroidDriver driver = (AndroidDriver) WebDriverRunner.getWebDriver();
        driver.hideKeyboard();
        if (driver.isKeyboardShown()) {
            waitTill(1000);
        }
        Assert.assertFalse(driver.isKeyboardShown(), "KeyBoard is still shown");
    }

    public enum view {WEBVIEW, NATIVE}

    /**
     * Function that takes a screen shot of the screen and return the file object
     * @param name
     * @return File
     * @throws IOException
     */
    public static File takePngScreenShot (String name) throws IOException {
        AppiumDriver driver = (AppiumDriver) WebDriverRunner.getWebDriver();
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(org.apache.commons.codec.binary.Base64.decodeBase64(driver.getScreenshotAs(OutputType.BASE64))));
        ImageIO.write(img, "png", new File((System.getProperty("user.dir")+"/target/selenide/"+ SessionData.getTimeStamp() +"/"+(name)).toString()));
        return new File(System.getProperty("user.dir")+"/target/selenide/"+ SessionData.getTimeStamp() +"/"+name);
    }

    public static void navigateBack() {
        WebDriverRunner.getWebDriver().navigate().back();
    }
}
