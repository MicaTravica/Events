package com.events.test;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.events.config.BrowserFactory;
import com.events.pages.HomePage;
import com.events.pages.RegisterPage;

public class RegisterTest {

	private WebDriver browser;

	RegisterPage registerPage;
	HomePage homePage;
//	SingInPage singInPage;

	@Before
	public void setupSelenium() throws Exception{
		
		browser = BrowserFactory.getBrowser();

		// navigate
		browser.navigate().to("http://localhost:4200/");

		homePage = PageFactory.initElements(browser, HomePage.class);
		registerPage = PageFactory.initElements(browser, RegisterPage.class);
//		singInPage = PageFactory.initElements(browser, SingInPage.class);
	}

	@Test
	public void singInTest() {
		homePage.ensureRegisterIsDisplayed();
		homePage.getRegisterLink().click();

		assertEquals("http://localhost:4200/register", browser.getCurrentUrl());

		registerPage.setNameInput("Olga");
		registerPage.setSurnameInput("Danilovic");
		registerPage.setEmailInput("email@gmail.com");
		registerPage.setPhoneInput("123-456789");
		registerPage.setUsernameInput("olgica");
		registerPage.setPasswordInput("12345678");

		registerPage.ensureSubmitButtonIsDisplayed();
		registerPage.getSubmitButton().click();
		
		browser.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
//		browser.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
//		new WebDriverWait(browser, 10).until(ExpectedConditions.elementToBeClickable(submitButton))
		assertEquals("http://localhost:4200/login", browser.getCurrentUrl());
	}

	@After
	public void closeSelenium() {
		BrowserFactory.quitBrowser();
	}
}
