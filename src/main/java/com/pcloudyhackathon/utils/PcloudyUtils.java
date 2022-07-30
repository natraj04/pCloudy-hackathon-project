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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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

    /**
     * Get coordinates
     * @param baseImageId
     * @param text
     * @return
     */
    public Object getCoordinate(String baseImageId, String text) {
        Map<String, Object> params = new HashMap<>();
        params.put("imageId", baseImageId);
        params.put("word", text);
        Object coordinate = null;

        log.info("Try AppiumDriver.executeScript");
        AppiumDriver driver = (AppiumDriver) WebDriverRunner.getWebDriver();
        coordinate = driver.executeScript("mobile:ocr:coordinate", params);

        log.info("coordinate " + coordinate);

        return coordinate;
    }

    /**
     * Get base image id
     * @param filePath
     * @return
     * @throws IOException
     * @throws ConnectError
     */
    public String getBaseImageId(File filePath) throws IOException, ConnectError {
        return connector.getImageId(getAuth(), filePath);
    }

    /**
     * Get Auth token
     * @return
     * @throws IOException
     * @throws ConnectError
     */
    public String getAuth() throws IOException, ConnectError {
        return connector.authenticateUser(SessionData.getPCloudyEmail(), SessionData.getPCloudyApiKey());
    }

    /**
     * Check if a text is present on the image
     * @param baseImageId
     * @param text
     * @return
     */
    public Object isTextPresent(String baseImageId, String text) {
        Map< String, Object> params = new HashMap<>();
        params.put("imageId", baseImageId);
        params.put("word", text);
        AppiumDriver driver = (AppiumDriver) WebDriverRunner.getWebDriver();
        return driver.executeScript("mobile:ocr:textExists",params);
    }

    /**
     * Check there is a diff in image and take a screen shot of the diff and place it on /target/selenide/[timestamp folder]]/diff.png
     * @param imageId
     * @param secondImageId
     * @return
     * @throws IOException
     */
    public File diffInImage(String imageId, String secondImageId) throws IOException {
        Map< String, Object> params = new HashMap<>();
        //Declare Image ID of Base image
        params.put("baseImageId", imageId);
        //declare Image ID of second image
        params.put("secondImageId", secondImageId);
        //Find the difference between two image
        AppiumDriver driver = (AppiumDriver) WebDriverRunner.getWebDriver();
        String base64=(String) driver.executeScript("mobile:visual:imageDiff",params);
        //Enter path
        File imgFile = new File(System.getProperty("user.dir")+"/target/selenide/"+SessionData.getTimeStamp()+"/diff.png");
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(org.apache.commons.codec.binary.Base64.decodeBase64(base64)));
        ImageIO.write(img, "png", imgFile);

        return imgFile;
    }

}
