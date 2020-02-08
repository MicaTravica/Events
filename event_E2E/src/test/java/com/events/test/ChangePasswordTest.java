package com.events.test;

import com.events.config.BrowserFactory;
import com.events.constants.UserConstants;
import com.events.pages.ChangePasswordPage;
import com.events.pages.HomePage;
import com.events.pages.LoginPage;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChangePasswordTest {

    private WebDriver browser;

    ChangePasswordPage changePasswordPage;
    LoginPage loginPage;

    @Before
    public void setupSelenium() throws Exception{
        browser = BrowserFactory.getBrowser();
        loginPage = PageFactory.initElements(browser, LoginPage.class);
        changePasswordPage = PageFactory.initElements(browser, ChangePasswordPage.class);

        if (browser.getCurrentUrl().equals("data:,")) {
            browser.navigate().to(LoginPage.FRONT_URL);
            loginPage.ensureLoginDisplayed();
            loginPage.setUsernameField(UserConstants.VALID_USERNAME);
            loginPage.setPasswordField(UserConstants.PASSWORD);
            loginPage.getLogginBtn().click();
            loginPage.ensureLoginNotDisplayed();
        }

    }

    @Test
    public void B_validChangePassword() {
        browser.navigate().to(ChangePasswordPage.FRONT_URL);
        changePasswordPage.ensureChangePasswordDisplayed();
        changePasswordPage.setOldPassword(UserConstants.PASSWORD);
        changePasswordPage.setPassword1(UserConstants.PASSWORD);
        changePasswordPage.setPassword2(UserConstants.PASSWORD);
        changePasswordPage.getSubmitBtn().click();
        changePasswordPage.getToaster();
        String message = changePasswordPage.getToasterTest();
        assertEquals("Successfully changed password", message);
    }

    @Test
    public void A_invalidChangePassword() {
        browser.navigate().to(ChangePasswordPage.FRONT_URL);
        changePasswordPage.ensureChangePasswordDisplayed();
        changePasswordPage.setOldPassword(UserConstants.INVALID_PASSWORD);
        changePasswordPage.setPassword1(UserConstants.PASSWORD);
        changePasswordPage.setPassword2(UserConstants.PASSWORD);
        changePasswordPage.getSubmitBtn().click();
        changePasswordPage.getToaster();
        String message = changePasswordPage.getToasterTest();
        assertNotEquals("Successfully changed password", message);
    }

    @AfterClass
    public static void closeSelenium() {
        BrowserFactory.quitBrowser();
    }
}
