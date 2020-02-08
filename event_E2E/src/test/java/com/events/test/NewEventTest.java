package com.events.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.events.config.BrowserFactory;
import com.events.constants.EventConstants;
import com.events.constants.UserConstants;
import com.events.pages.HomePage;
import com.events.pages.LoginPage;
import com.events.pages.NewEventPage;

public class NewEventTest {

	private WebDriver browser;

	NewEventPage newEventPage;
	HomePage homePage;
	LoginPage loginPage;

	@Before
	public void setupSelenium() throws Exception {
		browser = BrowserFactory.getBrowser();

		homePage = PageFactory.initElements(browser, HomePage.class);
		newEventPage = PageFactory.initElements(browser, NewEventPage.class);
		loginPage = PageFactory.initElements(browser, LoginPage.class);
	}

	@Test
	public void testNewEvent() {

		browser.navigate().to(HomePage.FRONT_URL);

		homePage.ensureLoginIsDisplayed();
		homePage.getLoginLink().click();

		loginPage.ensureLoginDisplayed();
		loginPage.setUsernameField(UserConstants.VALID_USERNAME_ADMIN);
		loginPage.setPasswordField(UserConstants.VALID_PASSWORD_ADMIN);
		loginPage.getLogginBtn().click();

		homePage.ensureNewEventIsDisplayed();
		homePage.getNewEventButton().click();

		assertEquals(NewEventPage.FRONT_URL, browser.getCurrentUrl());

		newEventPage.ensureNewEventIsDisplayed();
		newEventPage.setNameInput(EventConstants.NEW_NAME);
		newEventPage.setDescriptionInput(EventConstants.NEW_DESCRIPTION);
		newEventPage.setEventTypeInput(EventConstants.NEW_EVENT_TYPE);
		newEventPage.setFromDateInput(EventConstants.NEW_FROM_DATE);
		newEventPage.setToDateInput(EventConstants.NEW_TO_DATE);
		newEventPage.setFromHoursInput(EventConstants.NEW_FROM_HOURS);
		newEventPage.setPlaceInput(EventConstants.NEW_PLACE);

		newEventPage.ensureHallsAreDisplayed();
		newEventPage.setHallSectorInput(EventConstants.NEW_HALL);

		newEventPage.ensureSectorsAreDisplayed();
		newEventPage.setHallSectorInput(EventConstants.NEW_SECTOR);

		newEventPage.ensureTicketPriceIsDisplayed(EventConstants.NEW_SECTOR_PRICE);
		newEventPage.setTicketPrice(EventConstants.NEW_SECTOR_PRICE, EventConstants.NEW_PRICE);

		homePage.ensureToasterIsNotDisplayed();

		newEventPage.ensureSubmitButtonIsDisplayed();
		newEventPage.getSubmitButton().click();

		homePage.ensureToasterIsDisplayed();

		assertEquals("Dates can not be null and to date must be after from date", homePage.getToastr().getText());

		newEventPage.setToHoursInput(EventConstants.NEW_TO_HOURS);

		homePage.ensureToasterIsNotDisplayed();

		newEventPage.ensureSubmitButtonIsDisplayed();
		newEventPage.getSubmitButton().click();

		homePage.ensureToasterIsDisplayed();

		assertEquals("You add event!", homePage.getToastr().getText());

	}

	@After
	public void closeSelenium() {
		BrowserFactory.quitBrowser();
	}

}
