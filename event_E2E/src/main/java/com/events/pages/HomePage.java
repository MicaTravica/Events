package com.events.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {

	private WebDriver driver;
	
	@FindBy(id = "registerlink")
	private WebElement registerLink;
	
	@FindBy(id = "profillink")
	private WebElement profilLink;

	@FindBy(id = "loginlink")
	private WebElement loginLink;
	
	@FindBy(id = "newevent")
	private WebElement newEventButton;

	@FindBy(className = "ngx-toastr")
	private WebElement toastr;

	@FindBy(id="searchBtn")
	private WebElement searchBtn;

	private static String searchPanel = "mat-expansion-panel-header";

	private static String searchNameFiledId = "searchName";

	private static String searchFromDateFiledId = "searchFromDate";

	private static String searchToDateFiledId = "searchToDate";

	private static String searchResultElement = "mat-grid-tile";

	public static String FRONT_URL = "http://localhost:4200/events";



	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebElement getRegisterLink() {
		return registerLink;
	}
	
	public void ensureRegisterIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(registerLink));
	}
	
	public WebElement getProfilLink() {
		return profilLink;
	}
	
	public void ensureProfilIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(profilLink));
	}

	public void ensureSearchBoxIsDisplayed() {
		(new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By.tagName(HomePage.searchPanel)));
	}

	public void ensureSearchFiledsAreDisplayed() {
		(new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.id(searchNameFiledId)));
	}

	public WebElement getSearchBoxHeader() {
		return driver.findElement(By.tagName(HomePage.searchPanel));
	}
	
	public WebElement getLoginLink() {
		return loginLink;
	}
	
	public void ensureLoginIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(loginLink));
	}
	
	public WebElement getToastr() {
		return toastr;
	}

	public WebElement getSearchBtn() {
		return searchBtn;
	}

	public List<WebElement> getSearchResults() {
		return driver.findElements(By.tagName(searchResultElement));
	}

	public void ensureSeachResulstPresent() {
		(new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By.tagName(searchResultElement)));
	}
	
	public void ensureToasterIsNotDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.numberOfElementsToBeLessThan(By.className("ngx-toastr"), 1));
	}
	
	public void ensureToasterIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.className("ngx-toastr")));
	}
	
	public WebElement getNewEventButton() {
		return newEventButton;
	}
	
	public void ensureNewEventIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(newEventButton));

	}
	
	public void setSearchEventName(String name) {
		WebElement e = driver.findElement(By.id(HomePage.searchNameFiledId));
		e.sendKeys(name);
	}
	
	public void setSearchEventFromDate(String value) {
		WebElement e = driver.findElement(By.id(HomePage.searchFromDateFiledId));
		e.sendKeys(value);
	}
	
	public void setSearchEventToDate(String value) {
		WebElement e = driver.findElement(By.id(HomePage.searchToDateFiledId));
		e.sendKeys(value);
	}
}
