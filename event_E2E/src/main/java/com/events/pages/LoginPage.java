package com.events.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;

    public static String FRONT_URL = "http://localhost:4200/login";

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "loginBtn")
    private WebElement loginBtn;

    public LoginPage(WebDriver webDriver) {
        this.driver = webDriver;
    }

    public void ensureLoginDisplayed() {
        //wait for card number input field to be present
        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.id("password")));
    }

    public void ensureLoginNotDisplayed() {
        //wait for card number input field to be present
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.numberOfElementsToBeLessThan(By.id("password"), 1));
    }

    public void setUsernameField(String value) {
        WebElement el = getUsernameField();
        el.clear();
        el.sendKeys(value);
    }

    public void setPasswordField(String value) {
        WebElement el = getPasswordField();
        el.clear();
        el.sendKeys(value);
    }

    public WebElement getUsernameField() {
        return usernameField;
    }

    public WebElement getPasswordField() {
        return passwordField;
    }

    public WebElement getLogginBtn() {
        return loginBtn;
    }

}
