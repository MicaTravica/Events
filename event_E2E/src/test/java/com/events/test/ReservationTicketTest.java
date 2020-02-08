package com.events.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.events.config.BrowserFactory;
import com.events.constants.TicketReservationConstants;
import com.events.constants.UserConstants;
import com.events.pages.LoginPage;
import com.events.pages.ReservationTicketPage;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReservationTicketTest {
    private WebDriver browser;

    ReservationTicketPage reservationPage;
    LoginPage loginPage;

    @Before
    public void setupSelenium() throws Exception{
        browser = BrowserFactory.getBrowser();
        loginPage = PageFactory.initElements(browser, LoginPage.class);
        reservationPage = PageFactory.initElements(browser, ReservationTicketPage.class);

        browser.navigate().to(ReservationTicketPage.FRONT_URL);
        if (browser.getCurrentUrl().equals(LoginPage.FRONT_URL)) {
            loginPage.ensureLoginDisplayed();
            loginPage.setUsernameField(UserConstants.VALID_USERNAME);
            loginPage.setPasswordField(UserConstants.PASSWORD);
            loginPage.getLogginBtn().click();
            loginPage.ensureLoginNotDisplayed();
            browser.navigate().to(ReservationTicketPage.FRONT_URL);
        }
        reservationPage.ensureSelectHallDisplayed();
    }

    @Test
    public void A_validSearchSectorParter() throws Exception{
        reservationPage.enseureHallHasOptions();
        reservationPage.selectHall(TicketReservationConstants.hallName);
        reservationPage.ensureSelectSectorHasOptions();
        reservationPage.selectSector(TicketReservationConstants.parterSectorName);
        reservationPage.ensureSearchBtnEnabled();
        reservationPage.getSelectSearchSectorBtn().click();
        reservationPage.ensureParterFormDisplayed();
        reservationPage.setDesiredNumberOfTickets(TicketReservationConstants.validNumberOfTicketsParter);
        reservationPage.getSelectParterBtn().click();
        reservationPage.getReserveBtn().click();
        reservationPage.getToaster();
        String message = reservationPage.getToasterTest();
        assertEquals(TicketReservationConstants.validMessageReservation, message);
    }

    @Test
    public void B_validSearchSectorWithSeats() throws Exception{
        reservationPage.enseureHallHasOptions();
        reservationPage.selectHall(TicketReservationConstants.hallName);
        reservationPage.ensureSelectSectorHasOptions();
        reservationPage.selectSector(TicketReservationConstants.seatsSectorName);
        reservationPage.ensureSearchBtnEnabled();
        reservationPage.getSelectSearchSectorBtn().click();
        reservationPage.ensureTicketsGridDisplayed();
        reservationPage.selectTicktes(TicketReservationConstants.validNumberOfSeatTickets);
        reservationPage.getReserveBtn().click();
        reservationPage.getToaster();
        String message = reservationPage.getToasterTest();
        assertEquals(TicketReservationConstants.validMessageReservation, message);
    }

    @Test(expected = Exception.class)
    public void C_ivalidSearchSectorWithSeats() throws Exception{
        reservationPage.enseureHallHasOptions();
        reservationPage.selectHall(TicketReservationConstants.hallName);
        reservationPage.ensureSelectSectorHasOptions();
        reservationPage.selectSector(TicketReservationConstants.seatsSectorName);
        reservationPage.ensureSearchBtnEnabled();
        reservationPage.getSelectSearchSectorBtn().click();
        reservationPage.ensureTicketsGridDisplayed();
        reservationPage.selectTicktesBySeat(TicketReservationConstants.reservedSeat);
        reservationPage.getReserveBtn().click();
        reservationPage.getToaster();
        String message = reservationPage.getToasterTest();
        assertEquals(TicketReservationConstants.validMessageReservation, message);
    }
    // ne treba da uspe, jer nece ni da se pojavi dugme da moze
    // da selektuje sedista partera jer je broj veci od broja
    // slobodnih sedista
    @Test(expected = Exception.class)
    public void D_invalidSearchSectorParter() throws Exception{
        reservationPage.enseureHallHasOptions();
        reservationPage.selectHall(TicketReservationConstants.hallName);
        reservationPage.ensureSelectSectorHasOptions();
        reservationPage.selectSector(TicketReservationConstants.parterSectorName);
        reservationPage.ensureSearchBtnEnabled();
        reservationPage.getSelectSearchSectorBtn().click();
        reservationPage.ensureParterFormDisplayed();
        reservationPage.setDesiredNumberOfTickets(TicketReservationConstants.invalidNumberOfTicketsParter);
        reservationPage.getSelectParterBtn().click();
        reservationPage.getReserveBtn().click();
        reservationPage.getToaster();
        String message = reservationPage.getToasterTest();
        assertNotEquals(TicketReservationConstants.validMessageReservation, message);
    }

    @AfterClass
    public static void closeSelenium() {
        BrowserFactory.quitBrowser();
    }
}
