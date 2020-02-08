package com.events.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewEventPage {

	public static String FRONT_URL = "http://localhost:4200/add-event";

	private WebDriver driver;

	@FindBy(name = "name")
	private WebElement nameInput;

	@FindBy(name = "description")
	private WebElement descriptionInput;

	@FindBy(name = "eventType")
	private WebElement eventTypeInput;

	@FindBy(name = "fromDate")
	private WebElement fromDateInput;

	@FindBy(name = "toDate")
	private WebElement toDateInput;

	@FindBy(id = "add")
	private WebElement submitButton;

	public NewEventPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getNameInput() {
		return nameInput;
	}

	public void setNameInput(String value) {
		WebElement el = getNameInput();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getDescriptionInput() {
		return descriptionInput;
	}

	public void setDescriptionInput(String value) {
		WebElement el = getDescriptionInput();
		el.clear();
		el.sendKeys(value);
	}

	public void setEventTypeInput(String value) {
		driver.findElement(By.name("eventType")).click();
		driver.findElement(By.xpath("//mat-option[contains(.,'" + value + "')]")).click();
	}

	public WebElement getFromDateInput() {
		return fromDateInput;
	}

	public void setFromDateInput(String value) {
		WebElement el = getFromDateInput();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getToDateInput() {
		return toDateInput;
	}

	public void setToDateInput(String value) {
		WebElement el = getToDateInput();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getSubmitButton() {
		return submitButton;
	}

	public void setFromHoursInput(String value) {
		driver.findElement(By.id("fromhours")).click();
		driver.findElement(By.xpath("//mat-option[contains(.,'" + value + "')]")).click();
	}

	public void setToHoursInput(String value) {
		driver.findElement(By.id("tohours")).click();
		driver.findElement(By.xpath("//mat-option[contains(.,'" + value + "')]")).click();
	}

	public void setPlaceInput(String value) {
		driver.findElement(By.id("place")).click();
		driver.findElement(By.xpath("//mat-option[contains(.,'" + value + "')]")).click();
	}

	public void setHallSectorInput(String value) {
		driver.findElement(By.xpath("//mat-checkbox[@id='" + value + "']/label")).click();
	}

	public void ensureHallsAreDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("halls")));
	}

	public void ensureSectorsAreDisplayed() {
		(new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.className("sectorslist")));
	}

	public void ensureNewEventIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("add")));
	}

	public void ensureSubmitButtonIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(submitButton));
	}
	
	public void ensureTicketPriceIsDisplayed(String value) {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id(value)));
	}

	public void setTicketPrice(String id, String value) {
		WebElement el = driver.findElement(By.id(id));
		el.clear();
		el.sendKeys(value);
	}

}
