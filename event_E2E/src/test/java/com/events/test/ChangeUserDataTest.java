package com.events.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.events.config.BrowserFactory;
import com.events.constants.UserConstants;
import com.events.pages.ChangeUserDataPage;
import com.events.pages.HomePage;
import com.events.pages.LoginPage;

public class ChangeUserDataTest {

	private WebDriver browser;

	ChangeUserDataPage changeUserDataPage;
	HomePage homePage;
	LoginPage loginPage;

	@Before
	public void setupSelenium() throws Exception{
		browser = BrowserFactory.getBrowser();

		homePage = PageFactory.initElements(browser, HomePage.class);
		changeUserDataPage = PageFactory.initElements(browser, ChangeUserDataPage.class);
		loginPage = PageFactory.initElements(browser, LoginPage.class);
	}
	
	@Test
	public void testProfilEdit() {
		
		browser.navigate().to(HomePage.FRONT_URL);
		
		homePage.ensureLoginIsDisplayed();
		homePage.getLoginLink().click();
		
		loginPage.ensureLoginDisplayed();
		loginPage.setUsernameField(UserConstants.VALID_USERNAME_ADMIN);
		loginPage.setPasswordField(UserConstants.VALID_PASSWORD_ADMIN);
		loginPage.getLogginBtn().click();
		
		homePage.ensureProfilIsDisplayed();
		homePage.ensureToasterIsNotDisplayed();
		homePage.getProfilLink().click();
		
		changeUserDataPage.ensureSubmitButtonIsDisplayed();
		changeUserDataPage.setNameInput(UserConstants.NEW_NAME);
		changeUserDataPage.setUsernameInput(UserConstants.VALID_USERNAME);
		changeUserDataPage.getSubmitButton().click();
		
		homePage.ensureToasterIsDisplayed();

		assertEquals("\"Username already exists\"", homePage.getToastr().getText());
		
		changeUserDataPage.ensureSubmitButtonIsDisplayed();
		changeUserDataPage.setSurnameInput(UserConstants.NEW_SURNAME);
		changeUserDataPage.setUsernameInput(UserConstants.VALID_USERNAME_ADMIN);

		homePage.ensureToasterIsNotDisplayed();
		
		changeUserDataPage.getSubmitButton().click();
		
		homePage.ensureToasterIsDisplayed();

		assertEquals("successfully changed profile data", homePage.getToastr().getText());
		
	}



	@After
	public void closeSelenium() {
		BrowserFactory.quitBrowser();
	}

}
