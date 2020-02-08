package com.events.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChangePasswordPage {

    private WebDriver driver;

    public static String FRONT_URL = "http://localhost:4200/changePassword";

    @FindBy(id = "oldPassword")
    private WebElement oldPassword;

    @FindBy(id = "password1")
    private WebElement password1;

    @FindBy(id = "password2")
    private WebElement password2;

    @FindBy(id = "submitBtn")
    private WebElement submitBtn;

    @FindBy(className = "ngx-toastr")
    private WebElement toaster;

    public ChangePasswordPage(WebDriver webDriver) {
        this.driver = webDriver;
    }

    public void ensureChangePasswordDisplayed() {
        //wait for card number input field to be present
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.id("password1")));
    }

    public void getToaster() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.className("ngx-toastr")
                ));
    }

    public String getToasterTest() {
        return toaster.getText();
    }

    public WebElement getOldPassword() {
        return this.oldPassword;
    }

    public WebElement getPassword1() {
        return this.password1;
    }

    public WebElement getPassword2() {
        return this.password2;
    }

    public WebElement getSubmitBtn() {
        return this.submitBtn;
    }

    public void setPassword1(String value) {
        WebElement el = getPassword1();
        el.clear();
        el.sendKeys(value);
    }

    public void setPassword2(String value) {
        WebElement el = getPassword2();
        el.clear();
        el.sendKeys(value);
    }

    public void setOldPassword(String value) {
        WebElement el = getOldPassword();
        el.clear();
        el.sendKeys(value);
    }
}
