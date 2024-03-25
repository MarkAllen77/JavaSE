package JavaTest;

import org.openqa.selenium.WebDriver;

import JavaMain.JavaSelenium;
import SourcePages.TimersPage;

/**
 * 
 * DEFINITION:
 * @author markallen77
 * @version 20240325 
 * 
 */
public class JavaTestCycle {
	/*
	 * 1. create selenium object code
	 * 2. use configuration file
	 * 3. convert to page object model
	 * 4. apply testng
	 * 5. apply extent report
	 * 6. add log files
	 * 7. add java docs
	 */
	
	static WebDriver driver;
	
	public static void RunAsJava() {
		TimersPage TimersPageInstancePage = new TimersPage();
		TimersPageInstancePage.TimersPageStart();
		
		JavaSelenium JavaSeleniumInstance = new JavaSelenium(driver);
				
		driver = JavaSeleniumInstance.InitializeDriver("Chrome");
	    
		JavaSeleniumInstance.HandleInputandRadio(driver);
		JavaSeleniumInstance.HandleDropdown(driver);
		JavaSeleniumInstance.HandleMultiDropdown(driver);
		JavaSeleniumInstance.HandleBootstrapDropdown(driver);
		JavaSeleniumInstance.HandleAutoSuggestion(driver);
		JavaSeleniumInstance.HandleHiddenItems(driver);
		JavaSeleniumInstance.HandleDialogAlerts(driver);
		JavaSeleniumInstance.HandleFramesiFrames(driver);
		JavaSeleniumInstance.HandleWebTablePagination(driver);
		JavaSeleniumInstance.HandleDatePickers(driver);
		JavaSeleniumInstance.HandleMouseActions(driver);
		JavaSeleniumInstance.HandleKeyboardActions(driver);
		JavaSeleniumInstance.HandleUploadFiles(driver);
		JavaSeleniumInstance.HandlePagesWindows(driver);
		JavaSeleniumInstance.HandleMultiplePagesWindows(driver);
		JavaSeleniumInstance.HandleCaptureScreen(driver);
		JavaSeleniumInstance.HandleUsingClass(driver);
	    
		JavaSeleniumInstance.CloseAndQuitBrowser(driver);
	    
		TimersPageInstancePage.TimersPageStop();
	}

	/**
	 * 
	 * DEFINITION:
	 * @param args intended arguments
	 */
	public static void main(String[] args) {
		RunAsJava();
	}
}
