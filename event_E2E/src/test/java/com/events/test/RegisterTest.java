package com.events.test;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.events.config.BrowserFactory;
import com.events.pages.HomePage;
import com.events.pages.LoginPage;
import com.events.pages.RegisterPage;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
	@Order(1)
	public void A_registerTestInvalid() {
		browser.navigate().to(HomePage.FRONT_URL);
		homePage.ensureRegisterIsDisplayed();
		homePage.getRegisterLink().click();

		assertEquals(RegisterPage.FRONT_URL, browser.getCurrentUrl());

		registerPage.setNameInput("Olga");
		registerPage.setSurnameInput("Danilovic");
		registerPage.setEmailInput("email@gmail.com");
		registerPage.setPhoneInput("123-456789");
		registerPage.setUsernameInput("milovica");
		registerPage.setPasswordInput("12345678");
		
		registerPage.ensureSubmitButtonIsDisplayed();
		registerPage.getSubmitButton().click();
		
		loginPage.ensureLoginButtonNotDisplayed();
		
		assertEquals(RegisterPage.FRONT_URL, browser.getCurrentUrl());
	}

	@Test
	@Order(2)
	public void B_registerTestValid() {
		browser.navigate().to(HomePage.FRONT_URL);
		homePage.ensureRegisterIsDisplayed();
		homePage.getRegisterLink().click();

		assertEquals(RegisterPage.FRONT_URL, browser.getCurrentUrl());

		registerPage.setNameInput("Olga");
		registerPage.setSurnameInput("Danilovic");
		registerPage.setEmailInput("email@gmail.com");
		registerPage.setPhoneInput("123-456789");
		registerPage.setUsernameInput("olgica");
		registerPage.setPasswordInput("12345678");

		registerPage.ensureSubmitButtonIsDisplayed();
		registerPage.getSubmitButton().click();
		
		loginPage.ensureLoginDisplayed();
		
		assertEquals(LoginPage.FRONT_URL, browser.getCurrentUrl());
	}


	@AfterClass
	public static void closeSelenium() {
		BrowserFactory.quitBrowser();
	}
}
