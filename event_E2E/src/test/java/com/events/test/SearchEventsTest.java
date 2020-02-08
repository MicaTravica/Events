package com.events.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.events.config.BrowserFactory;
import com.events.pages.HomePage;

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
        homePage.ensureSeachResulstPresent();
        assertFalse(homePage.getSearchResults().isEmpty());
    }
    
    @Test
    public void searchWithValidEventNameFromDateToDate() throws Exception {
        homePage.ensureSeachResulstPresent(); // ono inicialno sto se ucita
        homePage.ensureSearchBoxIsDisplayed();
        homePage.getSearchBoxHeader().click();
        homePage.ensureSearchFiledsAreDisplayed();
        homePage.setSearchEventName("UTAKMICA2");
        homePage.setSearchEventFromDate("1/1/2020");
        homePage.setSearchEventToDate("7/7/2023");
        homePage.getSearchBtn().click(); 
        homePage.ensureSeachResulstPresent();
        assertFalse(homePage.getSearchResults().isEmpty());
    }

    @AfterClass
    public static void closeSelenium() {
        BrowserFactory.quitBrowser();
    }
}