package com.pcloudyhackathon.pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.impl.Screenshot;
import com.pcloudyhackathon.library.SessionData;
import com.pcloudyhackathon.utils.CommonUtils;
import com.pcloudyhackathon.utils.CustomLogger;
import com.pcloudyhackathon.utils.PcloudyUtils;
import com.ssts.pcloudy.Connector;
import com.ssts.pcloudy.exception.ConnectError;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.OutputType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.random.RandomGenerator;

import static com.codeborne.selenide.Selenide.screenshot;

public class LoginPage {

    final CustomLogger log = new CustomLogger(LoginPage.class);
    private PcloudyUtils pCloudyUtils =  new PcloudyUtils();

    @AndroidFindBy(xpath= "//android.widget.Button[@resource-id='com.pcloudyhackathon:id/login']")
    private SelenideElement loginButton;

    public void clickOnLoginButton()  {
        loginButton.shouldBe(Condition.enabled).click();
    }

    public Object getLoginCoordinate() throws IOException, ConnectError {

        loginButton.shouldBe(Condition.enabled);
        int min = 0;
        int max = 100;
        double randomNumber = Math.random()*(max-min+1)+min;
        String randomString = String.valueOf(randomNumber).substring(0,2);

        File loginButtonFile = CommonUtils.takePngScreenShot(randomString+"_loginButton.png");

        String imageBaseID = pCloudyUtils.getBaseImageId(loginButtonFile);

        log.info("loginButton.getText() = "+loginButton.getText());
        Object getCoordinate = pCloudyUtils.getCoordinate(imageBaseID, loginButton.getText());
        log.info("getCoordinate = "+ getCoordinate);

        return getCoordinate;
    }
}
