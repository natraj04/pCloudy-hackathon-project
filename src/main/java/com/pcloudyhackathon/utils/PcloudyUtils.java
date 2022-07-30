package com.pcloudyhackathon.utils;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.pcloudyhackathon.library.SessionData;
import com.ssts.pcloudy.Connector;
import com.ssts.pcloudy.exception.ConnectError;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import net.bytebuddy.asm.Advice;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PcloudyUtils {

    final CustomLogger log = new CustomLogger(PcloudyUtils.class);
    Connector connector = null;

    public PcloudyUtils() {
        connector = new Connector(SessionData.getPCloudyUrl());
    }

    public Object getCoordinate(String baseImageId, String text) {
        Map<String, Object> params = new HashMap<>();
        params.put("imageId", baseImageId);
        params.put("word", text);
        Object coordinate = null;
//        try {
            log.info("Try AppiumDriver.executeScript");
            AppiumDriver driver = (AppiumDriver) WebDriverRunner.getWebDriver();
            coordinate = driver.executeScript("mobile:ocr:coordinate", params);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        log.info("coordinate " + coordinate);

        return coordinate;
    }

    public String getBaseImageId(File filePath) throws IOException, ConnectError {
        return connector.getImageId(getAuth(), filePath);
    }

    public String getAuth() throws IOException, ConnectError {
        return connector.authenticateUser(SessionData.getPCloudyEmail(), SessionData.getPCloudyApiKey());
    }
}
