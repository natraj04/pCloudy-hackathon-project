package com.pcloudyhackathon;

import com.pcloudyhackathon.library.BaseTest;
import com.pcloudyhackathon.pageobjects.HomePage;
import com.pcloudyhackathon.pageobjects.LoginPage;
import com.pcloudyhackathon.utils.CustomLogger;
import com.ssts.pcloudy.exception.ConnectError;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.page;

public class Scenario_1 extends BaseTest {

    final CustomLogger log = new CustomLogger(Scenario_1.class, true);
    LoginPage loginPage = null;
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
    public void Perform_Double_Click_Option() {
        HomePage homePage = page(HomePage.class);
        log.info("Step 3a: Click on double click row");
        homePage.clickOnDoubleTapButton();
        log.info("Step 3b: Click on double click row");
        homePage.doubleCLick();
        log.info("Scenario 1 passed");
    }
}
