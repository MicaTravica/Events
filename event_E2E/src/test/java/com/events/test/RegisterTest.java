package com.events.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.events.config.BrowserFactory;
import com.events.constants.UserConstants;
import com.events.pages.HomePage;
import com.events.pages.LoginPage;
import com.events.pages.RegisterPage;

public class RegisterTest {

	private WebDriver browser;

	RegisterPage registerPage;
	HomePage homePage;
	LoginPage loginPage;

	@Before
	public void setupSelenium() throws Exception{
		browser = BrowserFactory.getBrowser();

		homePage = PageFactory.initElements(browser, HomePage.class);
		registerPage = PageFactory.initElements(browser, RegisterPage.class);
		loginPage = PageFactory.initElements(browser, LoginPage.class);
	}
	
	@Test
	public void registerTest() {
		browser.navigate().to(HomePage.FRONT_URL);
		homePage.ensureRegisterIsDisplayed();
		homePage.getRegisterLink().click();

		assertEquals(RegisterPage.FRONT_URL, browser.getCurrentUrl());


		registerPage.setNameInput(UserConstants.NEW_NAME);
		registerPage.setSurnameInput(UserConstants.NEW_SURNAME);
		registerPage.setEmailInput(UserConstants.NEW_EMAIL);
		registerPage.setPhoneInput(UserConstants.NEW_PHONE);
		registerPage.setUsernameInput(UserConstants.VALID_USERNAME_ADMIN);
		registerPage.setPasswordInput(UserConstants.NEW_PASSWORD);
		
		registerPage.ensureSubmitButtonIsDisplayed();
		registerPage.getSubmitButton().click();
		
		homePage.ensureToasterIsDisplayed();
		
		assertEquals("\"Username already exists\"", homePage.getToastr().getText());
		
		registerPage.setUsernameInput(UserConstants.NEW_USERNAME);

		registerPage.ensureSubmitButtonIsDisplayed();
		registerPage.getSubmitButton().click();
		
		loginPage.ensureLoginDisplayed();
		
		assertEquals(LoginPage.FRONT_URL, browser.getCurrentUrl());
	}

	@After
	public void closeSelenium() {
		BrowserFactory.quitBrowser();
	}
}
