package com.events.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	
	public WebElement getLoginLink() {
		return loginLink;
	}
	
	public void ensureLoginIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(loginLink));
	}
	
	public WebElement getToastr() {
		return toastr;
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
}
