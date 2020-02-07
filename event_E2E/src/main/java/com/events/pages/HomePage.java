package com.events.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	private WebDriver driver;
	
	@FindBy(id = "registerlink")
	private WebElement registerLink;

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
}
