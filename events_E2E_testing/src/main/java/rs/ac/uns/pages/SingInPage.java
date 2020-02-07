package rs.ac.uns.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SingInPage {

	private WebDriver driver;

	@FindBy(id = "email")
	private WebElement emailInput;

	@FindBy(id = "passwd")
	private WebElement passwordInput;

	@FindBy(id = "SubmitLogin")
	private WebElement submitButton;

	@FindBy(xpath = "//*[@id='center_column']/div[1]/ol/li")
	private WebElement alertDivText;

	public SingInPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getEmailInput() {
		return emailInput;
	}

	public void setEmailInput(String value) {
		WebElement el = getEmailInput();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getPasswordInput() {
		return passwordInput;
	}

	public void setPasswordInput(String value) {
		WebElement el = getPasswordInput();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getSubmitButton() {
		return submitButton;
	}

	public WebElement getAlertDivText() {
		return alertDivText;
	}

	public void ensureSubmitButtonIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(submitButton));
	}
}
