package com.pcloudyhackathon;

import com.pcloudyhackathon.library.BaseTest;
import com.pcloudyhackathon.pageobjects.HomePage;
import com.pcloudyhackathon.pageobjects.LoginPage;
import com.pcloudyhackathon.utils.CustomLogger;
import com.ssts.pcloudy.exception.ConnectError;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.page;

/**
 * Launch the app
 * Get the coordinates of login button using pCloudy’s OCR
 * Perform Click Operation
 * Perform drag and drop operations
 */
public class Scenario_3 extends BaseTest {

    final CustomLogger log = new CustomLogger(Scenario_3.class, true);
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
    public void Drag_And_drop() {
        HomePage homePage = page(HomePage.class);
        homePage.clickOnDragAndDrop();
        log.info("Step 3: Click on drag and drop button");
        homePage.performDragAndDrop();
        log.info("Step 3b: Drag and drop the element");
        log.info("Scenario 3 success");

    }
}
