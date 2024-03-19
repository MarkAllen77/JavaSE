package JavaTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.WebDriver;

import JavaMain.JavaSelenium;

public class JavaTestCycle {
	/*
	 * 1. create selenium object code
	 * 2. use configuration file
	 * 3. convert to page object model
	 * 4. apply testng
	 * 5. apply extent report
	 * 6. add log files
	 */
	
	static WebDriver driver;
	
	public static void main(String[] args) {
		StopWatch watch = new StopWatch();
		watch.start();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
		LocalDateTime now = LocalDateTime.now();  			
		System.out.println("Start Execution: " + dtf.format(now));
		
		JavaSelenium JavaSeleniumInstance = new JavaSelenium(driver);
				
		driver = JavaSeleniumInstance.InitializeDriver();
	    
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
	    
	    watch.stop();
	    DateFormat df = new SimpleDateFormat("HH 'hours', mm 'mins', ss 'seconds', SSS 'ms'");
		df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
		String elapsedTime = df.format(watch.getTime());			
		System.out.println("Time Elapsed: " + elapsedTime);
	}
}
