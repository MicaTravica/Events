package com.events.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginTest {

	private	WebDriver browser;

	HomePage homePage;
	LoginPage loginPage;

	@Before
	public void setupSelenium() throws Exception{
		browser = BrowserFactory.getBrowser();
		loginPage = PageFactory.initElements(browser, LoginPage.class);
		homePage = PageFactory.initElements(browser, HomePage.class);
	}

	@Test
	@Order(1)
	public void A_invalidLoginTest() {
		browser.navigate().to(LoginPage.FRONT_URL);
		loginPage.ensureLoginDisplayed();
		loginPage.setUsernameField("dusann");
		loginPage.setPasswordField("caoo");
		loginPage.getLogginBtn().click();

		assertNotEquals(HomePage.FRONT_URL,
				browser.getCurrentUrl());
	}

	@Test
	@Order(2)
	public void B_validLoginTest() {
		browser.navigate().to(LoginPage.FRONT_URL);
		loginPage.ensureLoginDisplayed();
		loginPage.setUsernameField("dusan");
		loginPage.setPasswordField("cao");
		loginPage.getLogginBtn().click();
		loginPage.ensureLoginNotDisplayed();

		assertEquals(HomePage.FRONT_URL,
				browser.getCurrentUrl());
	}



	@AfterClass
	public static void closeSelenium() {
		BrowserFactory.quitBrowser();
	}
}
