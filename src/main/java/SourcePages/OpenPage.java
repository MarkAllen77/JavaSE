package SourcePages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class OpenPage {
		
	private WebDriver driver;
	private String initsearchinput;

	public OpenPage(WebDriver driver) {
		this.driver = driver;
		this.initsearchinput = "//textarea[@name='q']";
	}
	
	public void openPage(String url) {
		this.driver.get(url);
	}
	
	public void enterSearchInput(String searchKeyword) {
		this.driver.findElement(By.xpath(initsearchinput)).sendKeys(searchKeyword);;
		Threadsleep(1000);
		
	    Actions action = new Actions(driver);
		action.keyDown(Keys.END);	
		action.keyDown(Keys.ENTER);
		action.perform();
		
		Threadsleep(2000);
	}
	
	public void Threadsleep(int inMilliSeconds) {
		try {
			Thread.sleep(inMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
