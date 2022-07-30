package com.pcloudyhackathon;

import com.pcloudyhackathon.library.BaseTest;
import com.pcloudyhackathon.pageobjects.HomePage;
import com.pcloudyhackathon.pageobjects.LoginPage;
import com.pcloudyhackathon.utils.CommonUtils;
import com.pcloudyhackathon.utils.CustomLogger;
import com.pcloudyhackathon.utils.PcloudyUtils;
import com.ssts.pcloudy.exception.ConnectError;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.page;

/**
 * Perform Visual Testing using Pre - Build Commands for Below Scenarios
 *
 * Launch the app, get the coordinate of login button using pCloudy OCR and perform Click operation, click the base image and capture screenshot.Then go back and click on secondary image using pCloudy Visual AI commands and capture the difference image.
 * get all text present in the base image using pCloudy OCR
 * verify if “Titans” word exist in base image using pCloudy OCR
 */
public class Scenario_4 extends BaseTest {

    private String baseImage = null;
    private File baseImageFile = null;

    private String secondaryImage = null;
    private File secondaryImageFile = null;

    final CustomLogger log = new CustomLogger(Scenario_4.class, true);
    LoginPage loginPage = null;
    HomePage homePage = null;
    PcloudyUtils pcloudyUtils = null;
    @Test
    public void Get_The_Coordinates() throws IOException, ConnectError {
        loginPage = page(LoginPage.class);
        log.info("Step 1: Get The Coordinates of Login Button Using pCloudy’s OCR");
        loginPage.getLoginCoordinate();
    }

    @Test(dependsOnMethods = "Get_The_Coordinates")
    public void Click_Login_Button() {
        loginPage.clickOnLoginButton();
        log.info("Step 2: Click on login button");
    }

    @Test(dependsOnMethods = "Click_Login_Button")
    public void Click_And_Get_Base_Image() throws IOException, ConnectError {
        log.info("Step 3: Click on base image button");
        homePage = page(HomePage.class);
        pcloudyUtils = new PcloudyUtils();
        baseImageFile = homePage.clickOnBaseImageRowAndTakeScreenshot();
        baseImage = pcloudyUtils.getBaseImageId(baseImageFile);
        log.info("Step 3a: extract image");
        CommonUtils.navigateBack();
    }

    @Test(dependsOnMethods = "Click_And_Get_Base_Image")
    public void Click_And_Get_Secondary_Image() throws IOException, ConnectError {
        log.info("Step 4: Click on secondary image button");
        secondaryImageFile = homePage.clickOnSecondayImageRowAndTakeScreenshot();
        secondaryImage = pcloudyUtils.getBaseImageId(secondaryImageFile);
        log.info("Step 4a: extract image");
    }

    @Test(dependsOnMethods = "Click_And_Get_Base_Image")
    public void Validate_if_titans_text_is_Present_In_Image() {
        Object result = pcloudyUtils.isTextPresent(baseImage, "Titans");

        log.info("is text preset = "+result);
    }

    @Test(dependsOnMethods = "Click_And_Get_Secondary_Image")
    public void Check_diff_in_Image() throws IOException {
        File fileName = pcloudyUtils.diffInImage(baseImage, secondaryImage);
        log.info("fileName");
    }
}
