package com.events.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.events.config.BrowserFactory;
import com.events.pages.HomePage;
import com.events.pages.SingInPage;

public class LoginTest {

	private WebDriver browser;

	HomePage homePage;
	SingInPage singInPage;

	@Before
	public void setupSelenium() throws Exception{
		
		browser = BrowserFactory.getBrowser();

		// navigate
		browser.navigate().to("http://automationpractice.com/index.php");

		homePage = PageFactory.initElements(browser, HomePage.class);
		singInPage = PageFactory.initElements(browser, SingInPage.class);
	}

	@Test
	public void singInTest() {
		homePage.ensureSingInIsDisplayed();
		homePage.getSingInLink().click();

		assertEquals("http://automationpractice.com/index.php?controller=authentication&back=my-account",
				browser.getCurrentUrl());
	}

	@After
	public void closeSelenium() {
		BrowserFactory.quitBrowser();
	}
}
