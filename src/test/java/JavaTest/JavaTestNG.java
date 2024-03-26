package JavaTest;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import JavaMain.JavaSelenium;
import SourcePages.TimersPage;

/**
 * 
 * Definition:
 * @author markallen77
 * @version 20240325 
 * 
 */
public class JavaTestNG {
	TimersPage TimersPageInstancePage = new TimersPage();
	JavaSelenium JavaSeleniumInstance = new JavaSelenium(driver);

	static WebDriver driver;
	
	/**
	 * 
	 * Definition: 
	 * @param browserName generated from TestNG pom.xml
	 * 
	 */
	@Parameters("browserName")
	@BeforeTest
	public void Setup(String browserName) {		
		TimersPageInstancePage.TimersPageStart();
				
		driver = JavaSeleniumInstance.InitializeDriver(browserName);
	}
	
	/**
	 * 
	 * Definition: 
	 * 
	 */
	@Test
	public void TestCase() {
		JavaSeleniumInstance.HandleUsingClass(driver);
	}
	
	/**
	 * 
	 * Definition: 
	 * 
	 */
	@AfterTest
	public void TearDown() {
		JavaSeleniumInstance.CloseAndQuitBrowser(driver);
	    
		TimersPageInstancePage.TimersPageStop();		
	}
}
