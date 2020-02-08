package com.events.pages;

import static java.lang.Thread.sleep;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReservationTicketPage {

    private WebDriver driver;

    public static String FRONT_URL = "http://localhost:4200/reservation/1";

    private static String selectHallId = "selectHall";
    private static String selectSectorId = "selectSector";
    private static String selectOptionsClass = "mat-option";
    private static String searchSectorBtn = "searchSectorBtn";
    private static String ParterForm = "form";
    private static String ParterFormDesiredFiel = "desiredCount";
    private static String ParterFormAvailableCountFiel = "availableCount";
    private static String SelectParterBtn = "selectParter";
    private static String ReserveBtn = "reserveBtn";
    private static String matGridTitle = "mat-grid-tile";


    @FindBy(className = "ngx-toastr")
    private WebElement toaster;

    public ReservationTicketPage(WebDriver webDriver) {
        this.driver = webDriver;
    }


    public void getToaster() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.className("ngx-toastr")
                ));
    }

    public String getToasterTest() {
        return toaster.getText();
    }

    public void ensureSearchBtnEnabled() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.not(ExpectedConditions.attributeToBe(
                        getSelectSearchSectorBtn(), "disabled", "false")));
    }

    public void ensureSelectHallDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.id(ReservationTicketPage.selectHallId)));
    }

    public void ensureParterFormDisplayed() {
        (new WebDriverWait(driver,10))
                .until(ExpectedConditions.presenceOfElementLocated(By.tagName(ReservationTicketPage.ParterForm)));
    }

    public void enseureHallHasOptions() throws Exception{
        int i = 0;
        while(i < 10) {
            WebElement hallSelect = getSelectHall();
            hallSelect.click();
            int l = driver.findElements(
                    By.className(ReservationTicketPage.selectOptionsClass)).size();
            if( l> 1) {
                return;
            }
            i++;
            sleep(1000);
        }
        throw new Exception("Select options are not present");
    }


    public void ensureSelectSectorHasOptions() throws Exception {
        int i = 0;
        while(i < 10) {
            WebElement sectorSelect = getSelectSector();
            sectorSelect.click();
            int l = driver.findElements(
                    By.className(ReservationTicketPage.selectOptionsClass)).size();
            if( l> 1) {
                return;
            }
            i++;
            sleep(1000);
        }
        throw new Exception("Select options are not present");
    }


    public void ensureTicketsGridDisplayed() {
        (new WebDriverWait(driver,10))
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.tagName(ReservationTicketPage.matGridTitle)));
    }

    public void selectTicktes(int numberOfTicktes) throws Exception{
        int selected = 0;
        List<WebElement> ts =
                driver.findElements(
                        By.tagName(ReservationTicketPage.matGridTitle));
        for(int i = 0; i< ts.size(); i++) {
            WebElement t = ts.get(i);
            String contents =
                    (String)((JavascriptExecutor)driver).executeScript("return arguments[0].outerHTML;", t);
            if(!contents.contains("reserved-ticket")) {
                t.click();
                selected++;
            }
            if(selected == numberOfTicktes) {
                break;
            }
        }
        if(selected < numberOfTicktes) {
            throw new Exception("Not enough free tickets");
        }
    }

    public void selectTicktesBySeat(String seat) throws Exception {
        List<WebElement> ts =
                driver.findElements(
                        By.tagName(ReservationTicketPage.matGridTitle));
        for (int i = 0; i < ts.size(); i++) {
            WebElement t = ts.get(i);
            System.out.println(t.getText());
            if (t.getText().trim().equals(seat)) {
                t.click();
            }
        }
    }


    public void selectHall(String hallName) throws Exception {
        List<WebElement> options =
        driver.findElements(
                By.className(ReservationTicketPage.selectOptionsClass));
        for(WebElement option : options) {
            if(option.getText().equals(hallName)) {
                option.click();
                return;
            }
        }
        throw new Exception("Hall option with name not found");
    }

    public void selectSector(String sectorName) throws Exception{
        List<WebElement> options =
                driver.findElements(
                        By.className(ReservationTicketPage.selectOptionsClass));
        for(WebElement option : options) {
            if(option.getText().equals(sectorName)) {
                option.click();
                return;
            }
        }
        throw new Exception("Hall option with name not found");
    }

    public void setDesiredNumberOfTickets(String numberOfTickets) {
        WebElement field  = driver.findElement(By.id(ReservationTicketPage.ParterFormDesiredFiel));
        field.clear();
        field.sendKeys(numberOfTickets);
        field = driver.findElement(By.id(ReservationTicketPage.ParterFormAvailableCountFiel));
        field.click();
    }

    public WebElement getSelectHall() {
        return driver.findElement(By.id(ReservationTicketPage.selectHallId));
    }

    public WebElement getSelectSector()
    {
        return driver.findElement(By.id(ReservationTicketPage.selectSectorId));
    }
    public WebElement getSelectSearchSectorBtn()
    {
        return driver.findElement(By.id(ReservationTicketPage.searchSectorBtn));
    }

    public WebElement getParterForm() {
        return driver.findElement(By.tagName(ReservationTicketPage.ParterForm));
    }

    public WebElement getSelectParterBtn() {
        (new WebDriverWait(driver,5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id(ReservationTicketPage.SelectParterBtn)));
        return  driver.findElement(By.id(ReservationTicketPage.SelectParterBtn));
    }

    public WebElement getReserveBtn() {
        (new WebDriverWait(driver,5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id(ReservationTicketPage.ReserveBtn)));
        return  driver.findElement(By.id(ReservationTicketPage.ReserveBtn));
    }

}
