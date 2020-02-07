package com.events.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * BrowserFactory
 */
public  class BrowserFactory {

    public static WebDriver browser;

    public BrowserFactory(){}

    public static WebDriver getBrowser() throws Exception {

        if(browser != null)
        {
            return browser;
        }

        String operatingSystem = System.getProperty("os.name").toUpperCase();

        if(operatingSystem.equals("LINUX"))
        {
            System.setProperty("webdriver.chrome.driver", "events_E2E_testing/chromedriver_linux");
        }
        else
        {
            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        }

		browser = new ChromeDriver();
        browser.manage().window().maximize();
        return browser;
    }

    public static void quitBrowser() {
        if (null != browser) {
            browser.quit();
        }
    }

}