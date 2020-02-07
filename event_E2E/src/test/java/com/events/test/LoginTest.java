package com.events.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.events.pages.LoginPage;
import org.junit.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.events.config.BrowserFactory;
import com.events.pages.HomePage;
import com.events.pages.SingInPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
