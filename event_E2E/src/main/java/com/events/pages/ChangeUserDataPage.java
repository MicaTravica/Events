package com.events.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChangeUserDataPage {


	public static String FRONT_URL = "http://localhost:4200/profile";

	private WebDriver driver;
	
	@FindBy(id = "name")
	private WebElement nameInput;

	@FindBy(id = "surname")
	private WebElement surnameInput;

	@FindBy(id = "email")
	private WebElement emailInput;

	@FindBy(id = "phone")
	private WebElement phoneInput;

	@FindBy(id = "username")
	private WebElement usernameInput;

	@FindBy(id = "change")
	private WebElement submitButton;
	
	public ChangeUserDataPage(WebDriver driver) {
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

	public WebElement getSurnameInput() {
		return surnameInput;
	}

	public void setSurnameInput(String value) {
		WebElement el = getSurnameInput();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getEmailInput() {
		return emailInput;
	}

	public void setEmailInput(String value) {
		WebElement el = getEmailInput();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getPhoneInput() {
		return phoneInput;
	}

	public void setPhoneInput(String value) {
		WebElement el = getPhoneInput();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getUsernameInput() {
		return usernameInput;
	}

	public void setUsernameInput(String value) {
		WebElement el = getUsernameInput();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getSubmitButton() {
		return submitButton;
	}

	public void ensureSubmitButtonIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(submitButton));
	}
}
