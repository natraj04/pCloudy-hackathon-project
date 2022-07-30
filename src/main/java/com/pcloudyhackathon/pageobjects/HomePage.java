package com.pcloudyhackathon.pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.pcloudyhackathon.utils.CommonUtils;
import com.pcloudyhackathon.utils.CustomLogger;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.actions;

public class HomePage {

    final CustomLogger log = new CustomLogger(HomePage.class);

    @AndroidFindBy(xpath= "//android.widget.TextView[@text='Double Tap']")
    private SelenideElement doubleTapRow;

    @AndroidFindBy(xpath= "//android.widget.TextView[@text='Draw']")
    private SelenideElement drawRow;

    @AndroidFindBy(xpath= "//android.widget.TextView[@text='Drag & Drop']")
    private SelenideElement dragAnddropRow;

    @AndroidFindBy(xpath= "//android.widget.TextView[@text='Base Image']")
    private SelenideElement baseImageRow;

    @AndroidFindBy(xpath= "//android.widget.TextView[@text='Seconday Image']")
    private SelenideElement secondayImageRow;

    final private String clickHereLoc = "//android.widget.Button[@resource-id='com.pcloudyhackathon:id/click_here']";
    final private String sourceElementToDrag = "//android.widget.Button[@resource-id='com.pcloudyhackathon:id/button']";
    final private String destinationLocation = "//android.widget.LinearLayout[@resource-id='com.pcloudyhackathon:id/left_layout']";



    public void clickOnDoubleTapButton () {
        doubleTapRow.shouldBe(Condition.enabled).click();
    }

    public void doubleCLick() {
        SelenideElement clickHere = $(By.xpath(clickHereLoc)).shouldBe(Condition.enabled);
        try {
            WebElement clickElement = clickHere;
            Actions action = new Actions(WebDriverRunner.getWebDriver());
            action.doubleClick(clickElement).perform();
        } catch (Exception e) {
            clickHere.click();
            clickHere.click();
        }
    }

    public void clickOnDrawRow () {
        drawRow.shouldBe(Condition.enabled).click();

    }

    public void clickOnDragAndDrop () {
        dragAnddropRow.shouldBe(Condition.enabled).click();
    }

    public void performDragAndDrop () {
        SelenideElement elementToDrag = $(By.xpath(sourceElementToDrag)).shouldBe(Condition.enabled);
        SelenideElement destinationLocationElement = $(By.xpath(destinationLocation)).shouldBe(Condition.enabled);
        actions().dragAndDrop(elementToDrag, destinationLocationElement).perform();

        actions().clickAndHold(elementToDrag).moveToElement(destinationLocationElement).release().perform();
    }

    public File clickOnBaseImageRowAndTakeScreenshot () throws IOException {
        baseImageRow.shouldBe(Condition.enabled).click();
        CommonUtils.waitTill(2000);
        return CommonUtils.takePngScreenShot("BaseImage.png");
    }

    public File clickOnSecondayImageRowAndTakeScreenshot () throws IOException {
        secondayImageRow.shouldBe(Condition.enabled).click();
        CommonUtils.waitTill(2000);
        return CommonUtils.takePngScreenShot("SecondaryImage.png");
    }
}
