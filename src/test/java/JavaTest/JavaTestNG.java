package JavaTest;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
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

//	/**
//	 * 
//	 * Definition: 
//	 * 
//	 */
//	@Test
//	public void TestCase17() {
//		JavaSeleniumInstance.HandleMultiplePagesWindows(driver);
//	}
//	
//	/**
//	 * 
//	 * Definition: 
//	 * 
//	 */
//	@Test
//	public void TestCase19() {
//		JavaSeleniumInstance.HandleUsingClass(driver);
//	}
	
	/**
	 * 
	 * Definition: 
	 * 
	 */
	@DataProvider (name = "data-provider-local")
    public Object[][] DataProviderSource(){
		return new Object[][] {
			{"1","2"}, 
			{"3","4"}
		};
    }
	
	/**
	 * 
	 * Definition: 
	 * 
	 */
   @Test (dataProvider = "data-provider-local")
   public void MyTest (String val1, String val2) {
       System.out.println("Passed Parameter Is : " + val1 + " " + val2);
   }
   
	/**
	 * 
	 * Definition: 
	 * 
	 */
  @Test (dataProvider = "dataprovidersource", dataProviderClass = DataSets.DataSets.class)
  public void TestCase191 (String Name, String Email, String Phone, String Address) {
	  JavaSeleniumInstance.HandleTestDriven(driver, Name, Email, Phone, Address);
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
