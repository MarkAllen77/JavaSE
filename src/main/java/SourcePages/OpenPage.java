package SourcePages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

/**
 * 
 * Definition:
 * @author markallen77
 * @version 20240325 
 * 
 */
public class OpenPage {
	private WebDriver driver;
	private String initsearchinput;
	
	/**
	 * 
	 * Definition:
	 * @param driver selenium webdriver for specific web browser
	 */
	public OpenPage(WebDriver driver) {
		this.driver = driver;
		this.initsearchinput = "//textarea[@name='q']";
	}
	
	/**
	 * 
	 * Definition:
	 * @param url submitted url for interaction
	 */
	public void openPage(String url) {
		this.driver.get(url);
	}
	
	/**
	 * 
	 * Definition:
	 * @param searchKeyword submitted variable for browser search
	 */
	public void enterSearchInput(String searchKeyword) {
		this.driver.findElement(By.xpath(initsearchinput)).sendKeys(searchKeyword);;
		Threadsleep(1000);
		
	    Actions action = new Actions(driver);
		action.keyDown(Keys.END);	
		action.keyDown(Keys.ENTER);
		action.perform();
		
		Threadsleep(2000);
	}
	
	/**
	 * 
	 * Definition:
	 * @param inMilliSeconds the amount of time the process will hold until next line
	 */
	public void Threadsleep(int inMilliSeconds) {
		try {
			Thread.sleep(inMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
