package com.events.test;

import com.events.config.BrowserFactory;
import com.events.pages.HomePage;
import com.events.pages.LoginPage;
import com.events.pages.ReservationTicketPage;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchEventsTest {

    private WebDriver browser;

    HomePage homePage;

    @Before
    public void setupSelenium() throws Exception {
        browser = BrowserFactory.getBrowser();
        homePage = PageFactory.initElements(browser, HomePage.class);
        browser.navigate().to(HomePage.FRONT_URL);
    }

    @Test
    public void searchWithBedEventName() throws Exception {
        homePage.ensureSeachResulstPresent(); // ono inicialno sto se ucita
        homePage.ensureSearchBoxIsDisplayed();
        homePage.getSearchBoxHeader().click();
        homePage.ensureSearchFiledsAreDisplayed();
        homePage.setSearchEventName("afasdfasdfsadfasd");
        homePage.getSearchBtn().click();
        int resultSize = homePage.getSearchResults().size();
        assertEquals(0, resultSize);
    }

    @Test
    public void searchWithValidEventName() throws Exception {
        homePage.ensureSeachResulstPresent(); // ono inicialno sto se ucita
        homePage.ensureSearchBoxIsDisplayed();
        homePage.getSearchBoxHeader().click();
        homePage.ensureSearchFiledsAreDisplayed();
        homePage.setSearchEventName("UTAKMICA2");
        homePage.getSearchBtn().click();
        int resultSize = homePage.getSearchResults().size();
        assertEquals(1, resultSize);
    }

    @AfterClass
    public static void closeSelenium() {
        BrowserFactory.quitBrowser();
    }
}