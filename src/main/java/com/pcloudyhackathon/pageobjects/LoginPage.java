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

import static com.codeborne.selenide.Selenide.screenshot;

public class LoginPage {

    final CustomLogger log = new CustomLogger(LoginPage.class);
    private PcloudyUtils pCloudyUtils =  new PcloudyUtils();

    @AndroidFindBy(xpath= "//android.widget.Button[@resource-id='com.pcloudyhackathon:id/login']")
    private SelenideElement loginButton;

    public void clickOnLoginButton()  {
        loginButton.shouldBe(Condition.enabled).click();
    }

    public void getLoginCoordinate() throws IOException, ConnectError {
        log.info("Inside clickOnLoginButton");
        loginButton.shouldBe(Condition.enabled);
        log.info("loginButton is enabled");
        File loginButtonFile = CommonUtils.takePngScreenShot("loginButton.png");
        log.info("loginButtonFile = "+ loginButtonFile);
        String imageBaseID = pCloudyUtils.getBaseImageId(loginButtonFile);
        log.info("imageBaseID = "+ imageBaseID);
        log.info("loginButton.getText() = "+loginButton.getText());
        Object getCoordinate = pCloudyUtils.getCoordinate(imageBaseID, loginButton.getText());
        log.info("getCoordinate = "+ getCoordinate);
    }
}
