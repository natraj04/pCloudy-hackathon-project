package com.pcloudyhackathon.pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.pcloudyhackathon.utils.CustomLogger;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

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

    // elements inside doubleTapRow
    @AndroidFindBy(xpath= "//android.widget.Button[@resource-id='com.pcloudyhackathon:id/click_here']")
    private SelenideElement clickHere;



    // drag and drop
    //android.widget.Button[@resource-id='com.pcloudyhackathon:id/button']
    //android.widget.LinearLayout[@resource-id='com.pcloudyhackathon:id/left_layout']

    public void clickOnDoubleTapButton () {
        doubleTapRow.shouldBe(Condition.enabled).click();
    }

    public void doubleCLick() {
        clickHere.shouldBe(Condition.enabled).isDisplayed();
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

    public void clickOnBaseImageRow () {
        baseImageRow.shouldBe(Condition.enabled).click();
    }

    public void clickOnSecondayImageRow () {
        secondayImageRow.shouldBe(Condition.enabled).click();
    }
}
