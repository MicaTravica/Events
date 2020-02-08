package com.events.utill;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.Thread.sleep;

public class Utill {

    // da vidi da li ce da se promeni url
    // ako ne dobijes false pa ti bacaj exception ili sta vec hoces da radis
    public boolean ensureUrlChanged(String currentUrl, WebDriver driver, int time) throws Exception{
        int i = 0;
        while (i < time) {
            if(!driver.getCurrentUrl().equals(currentUrl)) {
                return true;
            }
            sleep(1000);
            i++;
        }
        return  false;
    }
}
