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
 * DEFINITION:
 * @author markallen77
 * @version 20240325 
 * 
 */
public class JavaTestNG {
	TimersPage TimersPageInstancePage = new TimersPage();
	JavaSelenium JavaSeleniumInstance = new JavaSelenium(driver);

	static WebDriver driver;
	
	@Parameters("browserName")
	@BeforeTest
	public void Setup(String browserName) {		
		TimersPageInstancePage.TimersPageStart();
				
		driver = JavaSeleniumInstance.InitializeDriver(browserName);
	}
	
	@Test
	public void TestCase() {
		JavaSeleniumInstance.HandleUsingClass(driver);
	}
	
	@AfterTest
	public void TearDown() {
		JavaSeleniumInstance.CloseAndQuitBrowser(driver);
	    
		TimersPageInstancePage.TimersPageStop();		
	}
}
