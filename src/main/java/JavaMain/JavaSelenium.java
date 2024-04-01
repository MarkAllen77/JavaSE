package JavaMain;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;

import SourcePages.OpenPage;


/**
 * 
 * Definition: JavaSelenium class containing all web object functions and selenium interaction
 * @author markallen77
 * @version 20240325 
 * 
 */
public class JavaSelenium {
	/**
	 * 
	 * Definition: Logger class using the framework Log4j for creating text log files
	 * 
	 */
	public static Logger logger = LogManager.getLogger();
	ExtentSparkReporter htmlReporter;
	ExtentReports extent;
	ExtentTest test;
	WebDriver driver;
	
	/**
	 * 
	 * Definition: JavaSelenium class constructor
	 * @param driver selenium webdriver for specific web browser
	 */
	public JavaSelenium(WebDriver driver) {		
		this.driver = driver;
	}

	/**
	 * 
	 * Definition: 
	 * @return driver assign value to variable based on web browser
	 * @param browserName generated from TestNG parameters
	 */
	public WebDriver InitializeDriver(String browserName) {
		try {
		    //------------------- Extent Report ------------------------------
			//ExtentHtmlReporter and specified the file path for the Extent Report
			htmlReporter =  new ExtentSparkReporter("./Reports/extentReport.html");
			//initialize extentreports
			extent = new ExtentReports();
			extent.attachReporter(htmlReporter);
			
			//set the title of the extent report
			htmlReporter.config().setDocumentTitle("Automation Report");
			//set the report name
			htmlReporter.config().setReportName("Google Search");
			//specify the time format
			htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
			//theme
			//htmlReporter.config().setTheme(Theme.STANDARD);
			htmlReporter.config().setTheme(Theme.DARK);
			
		    test = extent.createTest("Test Case 1 Report","Initialize Extent report.");
		    			
		
			test = extent.createTest("Test Case 2 Driver","Initialize Chrome driver.");
			String projectPath = System.getProperty("user.dir");
			logger.info(projectPath);
			
			Properties prop = new Properties();
			InputStream input;

			input = new FileInputStream("./Config/config.property");
			prop.load(input);
			
			switch (browserName) {
				case "Chrome" :
					System.setProperty("webdriver.chrome.driver", prop.getProperty("ChromeDriver"));
					
					ChromeOptions options = new ChromeOptions();
					//options.addArguments("start-maximized");
					//options.addArguments("start-fullscreen");
					//options.addArguments("--headless", "--window-size=1920,1200");
					
				    driver = new ChromeDriver(options);
				    logger.info("Using Chrome browser");

					break;
				case "MSEdge" :
					System.setProperty("webdriver.edge.driver", prop.getProperty("MSEdgeDriver"));
					
					EdgeOptions optionsE = new EdgeOptions();
					//options.addArguments("start-maximized");
					//options.addArguments("start-fullscreen");
					//options.addArguments("--headless", "--window-size=1920,1200");
					
				    driver = new EdgeDriver(optionsE);
				    logger.info("Using MSEdge browser");
					break;
				case "FireFox" :
					System.setProperty("webdriver.gecko.driver", prop.getProperty("GeckoDriver"));
					
					FirefoxOptions optionsF = new FirefoxOptions();
					//options.addArguments("start-maximized");
					//options.addArguments("start-fullscreen");
					//options.addArguments("--headless", "--window-size=1920,1200");
					
				    driver = new FirefoxDriver(optionsF);
				    logger.info("Using FireFox browser");
					break;
				default :
					break;					
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return driver;
	}
		
	/**
	 * 
	 * Definition: 
	 * @param driver selenium webdriver for specific web browser
	 */
	public void HandleInputandRadio(WebDriver driver) {
		test = extent.createTest("Test Case 3 InputRadio","Handle Input and Radio objects.");
		test.log(Status.INFO,"-- HandleInputandRadio --");
		logger.info("-- HandleInputandRadio --");
		
		String url = "https://demoqa.com/automation-practice-form";
		driver.get(url);
		
		// ----- How to handle Inputbox & RadioButtons -----
		WebElement firstnameinput = driver.findElement(By.xpath("//input[@id='firstName']"));
		WebElement lastnameinput = driver.findElement(By.xpath("//input[@id='lastName']"));
		
		logger.info(firstnameinput.isDisplayed());
		logger.info(firstnameinput.isEnabled());
		logger.info("Current value: " + firstnameinput.getAttribute("value"));
		
		firstnameinput.sendKeys("John");
		lastnameinput.sendKeys("Doe");
		
		logger.info("Updated value: " + firstnameinput.getAttribute("value"));
		
		WebElement gendermalelabel = driver.findElement(By.xpath("//label[text() = 'Male']"));
		WebElement genderfemalelabel = driver.findElement(By.xpath("//label[text() = 'Female']"));
		WebElement gendermaleradiobutton = driver.findElement(By.xpath("//input[@id='gender-radio-1']"));
		WebElement genderfemaleradiobutton = driver.findElement(By.xpath("//input[@id='gender-radio-2']"));
		
		logger.info("Radiobutton " + gendermaleradiobutton.isSelected());
		gendermalelabel.click();
		logger.info(gendermaleradiobutton.isSelected());
		logger.info(genderfemaleradiobutton.isSelected());
		genderfemalelabel.click();
		logger.info(genderfemaleradiobutton.isSelected());
		
		// ----- How to handle Checkboxes -----
		WebElement hobbiessportslabel = driver.findElement(By.xpath("//label[text() = 'Sports']"));
		WebElement hobbiessportscheckbox = driver.findElement(By.xpath("//input[@id='hobbies-checkbox-1']"));
		WebElement hobbiesreadinglabel = driver.findElement(By.xpath("//label[text() = 'Reading']"));
		WebElement hobbiesreadingcheckbox = driver.findElement(By.xpath("//input[@id='hobbies-checkbox-2']"));
		
		logger.info("Checkbox " + hobbiessportscheckbox.isSelected());
		hobbiessportslabel.click();
		logger.info(hobbiessportscheckbox.isSelected());
		logger.info(hobbiesreadingcheckbox.isSelected());
		hobbiesreadinglabel.click();
		logger.info(hobbiesreadingcheckbox.isSelected());
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ArrayList<String> hobbiescheckboxes = new ArrayList<String>();
		
		hobbiescheckboxes.add("//label[text() = 'Sports']");
		hobbiescheckboxes.add("//label[text() = 'Reading']");
		hobbiescheckboxes.add("//label[text() = 'Music']");
		
		for (String box : hobbiescheckboxes)
		    driver.findElement(By.xpath(box)).click();		
	}
	
	/**
	 * 
	 * Definition: 
	 * @param driver selenium webdriver for specific web browser
	 */
	public void HandleDropdown(WebDriver driver) {
		test = extent.createTest("Test Case 4 Dropdown","Handle dropdown objects.");
		test.log(Status.INFO,"-- HandleDropdown --");
		logger.info("-- HandleDropdown --");
				
		String url = "https://testautomationpractice.blogspot.com/";
		driver.get(url);
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='country']")));

	    WebElement DropdownCountry = driver.findElement(By.id("country"));	    
	    DropdownCountry.sendKeys("Japan");

	    WebElement countryDropdown = driver.findElement(By.xpath("//select[@id='country']"));
	    countryDropdown.click();
	    WebElement countryOption = countryDropdown.findElement(By.xpath("//option[@value='india']"));
	    countryOption.click();

	    logger.info(countryDropdown.getAttribute("value"));

	    Assert.assertEquals(countryDropdown.getAttribute("value"),"india");

	    driver.findElement(By.cssSelector("#country > option:nth-child(6)")).click();
	    logger.info(countryDropdown.getAttribute("value"));
	}
	
	/**
	 * 
	 * Definition: 
	 * @param driver selenium webdriver for specific web browser
	 */
	public void HandleMultiDropdown(WebDriver driver) {
		test = extent.createTest("Test Case 5 MultiSelect","Handle multiple content dropdown objects.");
		test.log(Status.INFO,"-- HandleMultiDropdown --");
		logger.info("-- HandleMultiDropdown --");
		
		String url = "https://testautomationpractice.blogspot.com/";
		driver.get(url);
		
		WebElement colorslabel = driver.findElement(By.xpath("//label[contains(.,'Colors')]"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", colorslabel);
		
		WebElement colorsdropdown = driver.findElement(By.xpath("//select[@id='colors']/option[text()='Red']"));
		colorsdropdown.click();
		
		try {
			Select colorsdropdownobject = new Select(driver.findElement(By.xpath("//select[@id='colors']")));
			Thread.sleep(1000);
			colorsdropdownobject.selectByIndex(1);    // blue
			Thread.sleep(1000);
			colorsdropdownobject.selectByVisibleText("Green");
			Thread.sleep(1000);
			colorsdropdownobject.selectByValue("white");
			
			logger.info("Numer of options: " + colorsdropdownobject.getOptions().size());
			
			for (WebElement colors : colorsdropdownobject.getAllSelectedOptions())
			    logger.info("Value: " + colors.getText());		

		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 
	 * Definition: 
	 * @param driver selenium webdriver for specific web browser
	 */
	public void HandleBootstrapDropdown(WebDriver driver) {
		test = extent.createTest("Test Case 6 DropDownType","Handle multiple type dropdown objects.");
		test.log(Status.INFO,"-- HandleBootstrapDropdown --");
		logger.info("-- HandleBootstrapDropdown --");
		
	    String url = "https://www.jquery-az.com/boots/demo.php?ex=63.0_2";
	    driver.get(url);

	    WebElement outputbutton = driver.findElement(By.xpath("//button"));
	    List<WebElement> outputoption = driver.findElements(By.xpath("//ul//li//label//input"));

	    logger.info("Numer of options: " + outputoption.size());
	    outputbutton.click();

	    for (WebElement item : outputoption) {
	        String value = item.getAttribute("value");
	        if (value.equals("Angular") || value.equals("Java")) {
	        		logger.info(item.getAttribute("value"));
		            item.click();
		            logger.info(item.isSelected());	        		
	        }

	        if (value.equals("HTML") || value.equals("CSS")) {
	        		logger.info(item.getAttribute("value"));
		            item.click();
		            logger.info(item.isSelected());	        			        		
	        }
	    }	    
	}
	
	/**
	 * 
	 * Definition: 
	 * @param driver selenium webdriver for specific web browser
	 */
	public void HandleAutoSuggestion(WebDriver driver) {
		test = extent.createTest("Test Case 7 AutoSuggestion","Handle auto suggestion list objects.");
		test.log(Status.INFO,"-- HandleAutoSuggestion --");		
		logger.info("-- HandleAutoSuggestion --");
		
		String url = "https://www.redbus.in/";
	    driver.get(url);

	    WebElement frominput = driver.findElement(By.xpath("//input[@id='src']"));
	    frominput.sendKeys("Delhi");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@class,'sc-iwsKbI')]/div/text[1]")));
		
	    List<WebElement> autodropdownoptions = driver.findElements(By.xpath("//li[contains(@class,'sc-iwsKbI')]/div/text[1]"));

	    logger.info(("Number of options:" + autodropdownoptions.size()));

	    for (WebElement option : autodropdownoptions) {
	        String value = option.getText();
	        logger.info(value);

	        if (value.equals("RK Ashram")) {
	            option.click();
	            break;		
	        }	    	
	    }
	}
	
	/**
	 * 
	 * Definition: 
	 * @param driver selenium webdriver for specific web browser
	 */
	public void HandleHiddenItems(WebDriver driver) {
		test = extent.createTest("Test Case 8 BlurObjects","Handle objects that disappear during inspection.");
		test.log(Status.INFO,"-- HandleHiddenItems --");				
		logger.info("-- HandleHiddenItems --");
		
	    String url = "https://opensource-demo.orangehrmlive.com/";
	    driver.get(url);
	    
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='username']")));

	    WebElement usernameinput = driver.findElement(By.xpath("//input[@name='username']"));
	    WebElement passwordinput = driver.findElement(By.xpath("//input[@name='password']"));
	    //WebElement submitbutton = driver.findElement(By.xpath("//button[@type='submit']"));

	    usernameinput.sendKeys("Admin");
	    passwordinput.sendKeys("admin123");	
	}

	/**
	 * 
	 * Definition: 
	 * @param driver selenium webdriver for specific web browser
	 */
	public void HandleDialogAlerts(WebDriver driver) {
		test = extent.createTest("Test Case 9 Alerts","Handle pop-up alerts and dialog objects.");
		test.log(Status.INFO,"-- HandleDialogAlerts --");				
		logger.info("-- HandleDialogAlerts --");
		
		try {
		    String url = "https://testautomationpractice.blogspot.com/";
		    driver.get(url);

		    WebElement alertbutton = driver.findElement(By.xpath("//button[text()='Alert']"));
		    WebElement confirmbutton = driver.findElement(By.xpath("//button[text()='Confirm Box']"));
		    WebElement promptbutton = driver.findElement(By.xpath("//button[text()='Prompt']"));
		    WebElement promptmessage = driver.findElement(By.xpath("//p[@id='demo']"));

		    alertbutton.click();
		    Thread.sleep(2000);
		    Alert alert = driver.switchTo().alert();
		    logger.info(alert.getText());
		    alert.accept();

		    confirmbutton.click();
		    Thread.sleep(2000);
		    alert = driver.switchTo().alert();
		    logger.info(alert.getText());
		    alert.dismiss();

		    promptbutton.click();
		    Thread.sleep(2000);
		    alert = driver.switchTo().alert();
		    logger.info(alert.getText());
		    alert.sendKeys("JavaSE");
		    alert.accept();
		    Thread.sleep(2000);
		    String value = promptmessage.getAttribute("textContent");
		    logger.info(value);

		    //----- manual alert trigger
		    logger.info("1. Click Alert button!!");
		    		    
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    alert = wait.until(ExpectedConditions.alertIsPresent());
		    
		    alert = driver.switchTo().alert();
		    logger.info(alert.getText());
		    alert.accept();
		    Thread.sleep(2000);

		    
		    logger.info("2. Click Confirm button!!");
		    
		    alert = wait.until(ExpectedConditions.alertIsPresent());
		    
		    alert = driver.switchTo().alert();
		    logger.info(alert.getText());
		    alert.dismiss();
		    Thread.sleep(2000);
		    	    
		} catch (InterruptedException | TimeoutException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 
	 * Definition: 
	 * @param driver selenium webdriver for specific web browser
	 */
	public void HandleFramesiFrames(WebDriver driver) {
		test = extent.createTest("Test Case 10 Frames","Handle iFrames and Frames in web page.");
		test.log(Status.INFO,"-- HandleFramesiFrames --");						
		logger.info("-- HandleFramesiFrames --");
		
	    String url = "https://ui.vision/demo/webtest/frames/";
	    driver.get(url);

	    List<WebElement> framewindow = driver.findElements(By.xpath("//frame"));
	    logger.info("Number of frames: " + framewindow.size());

	    // approach 1: using locator
	    driver.switchTo().defaultContent();
	    WebElement frame1 = driver.findElement(By.xpath("//frame[@src='frame_1.html']"));
	    driver.switchTo().frame(frame1);
	    WebElement frameinput = driver.findElement(By.xpath("//input[@name='mytext1']"));
	    frameinput.sendKeys("This is frame 1");

	    // approach 2: using index
	    driver.switchTo().defaultContent();
	    driver.switchTo().frame(4);
	    WebElement frame5input = driver.findElement(By.xpath("//input[@name='mytext5']"));
	    frame5input.sendKeys("This is frame 5");

	    // nested
	    driver.switchTo().defaultContent();
	    WebElement frame3 = driver.findElement(By.xpath("//frame[@src='frame_3.html']"));
	    driver.switchTo().frame(frame3);
	    WebElement frame3input = driver.findElement(By.xpath("//input[@name='mytext3']"));
	    frame3input.sendKeys("This is frame 3");

	    WebElement iframe = driver.findElement(By.cssSelector("iframe"));
	    driver.switchTo().frame(iframe);
	    WebElement frame3radio = driver.findElement(By.xpath("//div[@id='i5']/div[3]/div"));
	    JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", frame3radio);

	    try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    
	    frame3radio.click();	
	}
	
	/**
	 * 
	 * Definition: 
	 * @param driver selenium webdriver for specific web browser
	 */
	public void HandleWebTablePagination(WebDriver driver) {
		test = extent.createTest("Test Case 11 Tables","Handle tables and pagination objects.");
		test.log(Status.INFO,"-- HandleWebTablePagination --");								
		logger.info("-- HandleWebTablePagination --");
		
		String url = "https://testautomationpractice.blogspot.com/";
	    driver.get(url);

	    WebElement tablelabel = driver.findElement(By.xpath("//h2[text()='Pagination Table']"));
	    JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", tablelabel);

	    WebElement paginationtable = driver.findElement(By.xpath("//table[@id='productTable']"));
	    List<WebElement> columns = paginationtable.findElements(By.cssSelector("thead tr th"));
	    List<WebElement> rows = paginationtable.findElements(By.cssSelector("tbody tr"));

	    List<WebElement> rowscells = paginationtable.findElements(By.cssSelector("tbody tr td"));

	    logger.info("Number of columns: " + columns.size());
	    logger.info("Number of rows: " + rows.size());

	    // select checkbox
	    WebElement matchedrow = driver.findElement(By.xpath("//td[text()='Product 2']//following-sibling::td//input"));
	    matchedrow.click();

	    SelectProduct("Product 4");
	    SelectProduct("Product 5");

	    List<String> rowarray = new ArrayList<String>();
	    int index = 0;

	    for (WebElement elements : rowscells) {
	        rowarray.add(elements.getText());
	        index ++;
	        if (index == 4) {
	            logger.info(rowarray);
	            index = 0;
			}
		}

	    List<WebElement> pages = driver.findElements(By.xpath("//ul[@id='pagination']//li//a"));
	    for (WebElement page : pages) {
	        logger.info(page.getText());
	        page.click();

	        rows = paginationtable.findElements(By.cssSelector("tbody tr"));
	        for (WebElement elements : rows) {
	            logger.info(elements.getText());
			}
		}
	}
	
	/**
	 * 
	 * Definition: 
	 * @param driver selenium webdriver for specific web browser
	 */
	public void HandleDatePickers(WebDriver driver) {
		test = extent.createTest("Test Case 12 Dates","Handle date selection for datepicker objects.");
		test.log(Status.INFO,"-- HandleDatePickers --");										
		logger.info("-- HandleDatePickers --");
		
		try {
		    String url = "https://testautomationpractice.blogspot.com/";
    	    driver.get(url);
    	    
    	    WebElement colorslabel = driver.findElement(By.xpath("//label[text()='Colors:']"));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();", colorslabel);
			Thread.sleep(2000);

    	    // direct type date
    	    WebElement datepicker = driver.findElement(By.xpath("//input[@id='datepicker']"));
    	    datepicker.click();
    	    datepicker.sendKeys("12/12/2023");
    	    Actions action = new Actions(driver);
			action.keyDown(Keys.TAB).perform();			
    	    Thread.sleep(2000);

    	    // using date picker
    	    String datestring = "3/15/2024";
    	    String[] datestringsplit = datestring.split("/");
    	    String[] monthname = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    	    String month = monthname[Integer.valueOf(datestringsplit[0]) - 1];
    	    String day = datestringsplit[1];
    	    String year = datestringsplit[2];

    	    datepicker.click();
    	    while (true) {
    	        String displayedyear = driver.findElement(By.xpath("//span[@class='ui-datepicker-year']")).getText();
    	        String displayedmonth = driver.findElement(By.xpath("//span[@class='ui-datepicker-month']")).getText();
    	        if (displayedyear.equals(year) && displayedmonth.equals(month))
    	            break;
    	        driver.findElement(By.xpath("//span[text()='Next']")).click();
			}

    	    driver.findElement(By.xpath("//a[text()='" + day + "']")).click();
    	    action.keyDown(Keys.TAB).perform();

    	    Thread.sleep(2000);						
		} catch (InterruptedException | TimeoutException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * 
	 * Definition: 
	 * @param driver selenium webdriver for specific web browser
	 */
	public void HandleMouseActions(WebDriver driver) {
		test = extent.createTest("Test Case 13 Mouse","Handle inputs from mouse buttons and actions.");
		test.log(Status.INFO,"-- HandleMouseActions --");												
		logger.info("-- HandleMouseActions --");
		
		try {
		    String url = "https://demo.opencart.com/";
		    driver.get(url);
	
		    // -----How to handle Mouse Hover-----
		    WebElement desktopslabel = driver.findElement(By.xpath("//a[text()='Desktops']"));
		    WebElement maclabel = driver.findElement(By.xpath("//a[text()='Mac (1)']"));
	
		    Actions action = new Actions(driver);
		    action.moveToElement(desktopslabel).perform();
		    Thread.sleep(2000);
		    
		    action.moveToElement(maclabel).perform();
		    Thread.sleep(2000);
	
		    // -----How to handle Mouse Right Click-----
		    url = "https://swisnl.github.io/jQuery-contextMenu/demo.html";
		    driver.get(url);
	
		    WebElement buttonrightclick = driver.findElement(By.xpath("//span[@class='context-menu-one btn btn-neutral']"));
		    action.contextClick(buttonrightclick).perform();
		    Thread.sleep(2000);
	
		    WebElement pastecontext = driver.findElement(By.xpath("//span[text()='Paste']"));
		    action.moveToElement(pastecontext).perform();
	
		    Thread.sleep(2000);
	
		    // -----How to handle Mouse Double Click-----
		    url = "https://testautomationpractice.blogspot.com/";
		    driver.get(url);
	
		    WebElement buttondoubleclick = driver.findElement(By.xpath("//button[text()='Copy Text']"));
		    action.doubleClick(buttondoubleclick).perform();
	
		    WebElement field1 = driver.findElement(By.xpath("//input[@id='field1']"));
		    logger.info("Text is: " + field1.getAttribute("value"));
	
		    WebElement field2 = driver.findElement(By.xpath("//input[@id='field2']"));
		    logger.info("Text is: " + field2.getAttribute("value"));
	
		    // -----How to handle Mouse Drag and Drop-----
		    WebElement draggable = driver.findElement(By.xpath("//div[@id='draggable']"));
		    WebElement droppable = driver.findElement(By.xpath("//div[@id='droppable']"));
	
		    action.dragAndDrop(draggable, droppable).perform();
		} catch (InterruptedException | TimeoutException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Definition: 
	 * @param driver selenium webdriver for specific web browser
	 */
	public void HandleKeyboardActions(WebDriver driver) {
		test = extent.createTest("Test Case 14 Keyboard","Handle keyboard key press and key combination.");
		test.log(Status.INFO,"-- HandleKeyboardActions --");														
		logger.info("-- HandleKeyboardActions --");
		
	    String url = "https://gotranscript.com/text-compare/";
	    driver.get(url);

	    WebElement fromtextarea = driver.findElement(By.xpath("//textarea[@name='text1']"));
	    WebElement totextarea = driver.findElement(By.xpath("//textarea[@name='text2']"));
	    WebElement buttoncompare = driver.findElement(By.xpath("//button[@id='recaptcha']"));

	    fromtextarea.sendKeys("Hello World was here");
	    fromtextarea.click();

	    Actions action = new Actions(driver);
	    action.keyDown(Keys.CONTROL);
	    action.sendKeys("a");
	    action.sendKeys("c");
	    action.perform();

	    totextarea.click();
	    action.keyDown(Keys.CONTROL);
	    action.sendKeys("v");
	    action.perform();

	    buttoncompare.click();

	    Threadsleep(2000);	
	}
	
	/**
	 * 
	 * Definition: 
	 * @param driver selenium webdriver for specific web browser
	 */
	public void HandleUploadFiles(WebDriver driver) {
		test = extent.createTest("Test Case 15 File","Handle uploading of file.");
		test.log(Status.INFO,"-- HandleUploadFiles --");																
		logger.info("-- HandleUploadFiles --");
		
	    String url = "https://www.foundit.in/";
	    driver.get(url);

	    try {
	    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	    	
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[@class='mqfihd-upload']")));
			
		} catch (TimeoutException e) {
			test.log(Status.FAIL, "-- [TIMEOUT] --");
			logger.warn("-- [TIMEOUT] --");
			e.printStackTrace();
		}

	    url = "https://davidwalsh.name/demo/multiple-file-upload.php";
	    driver.get(url);

	    WebElement nofileslabel = driver.findElement(By.xpath("//ul[@id='fileList']/li"));
	    logger.info("\"No Files Selected\" = " + nofileslabel.getText());

	    WebElement filestouploadbutton = driver.findElement(By.xpath("//input[@id='filesToUpload']"));
	    filestouploadbutton.sendKeys("C:/Temp/sample1.txt \n C:/Temp/sample2.txt");

	    WebElement filename1 = driver.findElement(By.xpath("//ul[@id='fileList']/li[1]"));
	    WebElement  filename2 = driver.findElement(By.xpath("//ul[@id='fileList']/li[2]"));

	    logger.info("\"sample1.txt\" = " + filename1.getText());
	    logger.info("\"sample2.txt\" = " + filename2.getText());	
	}
	
	/**
	 * 
	 * Definition: 
	 * @param driver selenium webdriver for specific web browser
	 */
	public void HandlePagesWindows(WebDriver driver) {
		test = extent.createTest("Test Case 16 Tabs","Handle creation of new tab or window.");
		test.log(Status.INFO,"-- HandlePagesWindows --");																		
		logger.info("-- HandlePagesWindows --");
		
	    String url = "https://opensource-demo.orangehrmlive.com/";
	    driver.get(url);

	    String originalwindow = driver.getWindowHandle();
	    logger.info(driver.getTitle());

	    driver.switchTo().newWindow(WindowType.TAB);
	    driver.get("https://www.orangehrm.com/");
	    String tabwindow = driver.getWindowHandle();
	    logger.info(driver.getTitle());

	    driver.switchTo().newWindow(WindowType.WINDOW);
	    driver.get("https://www.google.com/");
	    String newwindow = driver.getWindowHandle();
	    logger.info(driver.getTitle());
	    Threadsleep(2000);
	    driver.close();

	    logger.info("Original Window: " + originalwindow);
	    logger.info("Tab Window: " + tabwindow);
	    logger.info("New Window: " + newwindow);

	    Set<String> totalwindows = driver.getWindowHandles();
	    logger.info("Number of Window(s): " + totalwindows.size());

	    driver.switchTo().window(tabwindow);
	    Threadsleep(1000);
	    driver.close();

	    driver.switchTo().window(originalwindow);
	    Threadsleep(2000);	
	}
	
	/**
	 * 
	 * Definition: 
	 * @param driver selenium webdriver for specific web browser
	 */
	public void HandleMultiplePagesWindows(WebDriver driver) {
		test = extent.createTest("Test Case 17 Windows","Handle opening and switching to tab or window.");
		test.log(Status.INFO,"-- HandleMultiplePagesWindows --");																				
		logger.info("-- HandleMultiplePagesWindows --");
		
		String url = "https://opensource-demo.orangehrmlive.com/";
	    driver.get(url);

	    // Original Window
	    driver.get("https://opensource-demo.orangehrmlive.com/");
	    String originalwindow = driver.getWindowHandle();
	    logger.info(driver.getTitle());

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='OrangeHRM, Inc']")));

	    WebElement orangelink = driver.findElement(By.xpath("//a[text()='OrangeHRM, Inc']"));
	    orangelink.click();

	    // New Tab
	    Set<String> openwindows = driver.getWindowHandles();
	    String openwindowsIndex = openwindows.toArray()[1].toString();
	    driver.switchTo().window(openwindowsIndex);

	    String tabwindow = driver.getWindowHandle();
	    logger.info(driver.getTitle());

	    // New Window
	    driver.switchTo().newWindow(WindowType.WINDOW);
	    driver.get("https://www.google.com");
	    String newwindow = driver.getWindowHandle();
	    logger.info(driver.getTitle());
	    Threadsleep(2);

	    logger.info("Original Window: " + originalwindow);
	    logger.info("Tab Window: " + tabwindow);
	    logger.info("New Window: " + newwindow);

	    Set<String> totalwindows = driver.getWindowHandles();
	    logger.info("Number of Window(s): " + totalwindows.size());

	    driver.switchTo().window(originalwindow);
	    WebElement usernameinput = driver.findElement(By.xpath("//input[@name='username']"));
	    usernameinput.sendKeys("username123");
	    Threadsleep(3);

	    driver.switchTo().window(tabwindow);
	    usernameinput = driver.findElement(By.xpath("//input[@id='Form_submitForm_EmailHomePage']"));
	    usernameinput.sendKeys("username123@gmail.com");
	    Threadsleep(3);
	    driver.close();
	    Threadsleep(3);

	    driver.switchTo().window(newwindow);
	    usernameinput = driver.findElement(By.xpath("//textarea[@name='q']"));
	    usernameinput.sendKeys("python 3");
	    Threadsleep(3);
	    driver.close();

	    driver.switchTo().window(originalwindow);	
	}
	
	/**
	 * 
	 * Definition: 
	 * @param driver selenium webdriver for specific web browser
	 */
	public void HandleCaptureScreen(WebDriver driver) {
		test = extent.createTest("Test Case 18 Screenshot","Handle screenshot of window or an element.");
		test.log(Status.INFO,"-- HandleCaptureScreen --");																						
		logger.info("-- HandleCaptureScreen --");
		
		String url = "https://testautomationpractice.blogspot.com/";
	    driver.get(url);

	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss"); 
		LocalDateTime now = LocalDateTime.now();  			

		String pathString = "./captures";
		File file = new File(pathString);
		
	    if (!file.exists()) {
	    	logger.info("Directory created");
	    	file.mkdir();
	    } else {
	    	logger.info("Directory already exists");
	    }

	    //Capture screen
	    String savefile = "./captures/window_" + dtf.format(now) + ".png";
	    logger.info(savefile);
	    File sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);   
	    try {
			FileUtils.copyFile(sourceFile, new File(savefile));
		} catch (IOException e) {
			e.printStackTrace();
		}

	    //Capture element
	    savefile = "./captures/element_" + dtf.format(now) + ".png";
	    logger.info(savefile);
	    WebElement droppablelement = driver.findElement(By.xpath("//div[@id='droppable']"));
	    sourceFile = ((TakesScreenshot)droppablelement).getScreenshotAs(OutputType.FILE);
	    try {
			FileUtils.copyFile(sourceFile, new File(savefile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    //Capture fullscreen
		//	    savefile = "./captures/full_" + dtf.format(now) + ".png";
		//	    logger.info(savefile);
		//	    WebElement fullscreen = driver.findElement(By.tagName("body"));
		//	    sourceFile = ((TakesScreenshot)fullscreen).getScreenshotAs(OutputType.FILE);
		//	    try {
		//			FileUtils.copyFile(sourceFile, new File(savefile));
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}
}	
	
	/**
	 * 
	 * Definition: 
	 * @param driver selenium webdriver for specific web browser
	 */
	public void HandleUsingClass(WebDriver driver) {
		test = extent.createTest("Test Case 19 Classes","Demonstration of class inheritance.");
		test.log(Status.INFO,"-- HandleUsingClass --");																								
		logger.info("-- HandleUsingClass --");
		
		// class within same file
	    MyClass myclassinstance = new MyClass();
	    logger.info(myclassinstance.x);
	    myclassinstance.loopPrint();
	    
	    //class from external file
	    OpenPage classGoogleInstancePage = new OpenPage(driver);
	    classGoogleInstancePage.openPage("https://www.google.com");
	    classGoogleInstancePage.enterSearchInput("java selenium");
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
	
	/**
	 * 
	 * Definition: 
	 * @param productname retrieved from the website, use the table and click product
	 */
	public void SelectProduct(String productname) {
		WebElement matchedrow = driver.findElement(By.xpath("//td[text()='" + productname + "']//following-sibling::td//input"));
    	matchedrow.click();
	}
	
	/**
	 * 
	 * Definition: 
	 * @param driver selenium webdriver for specific web browser
	 */
	public void CloseAndQuitBrowser(WebDriver driver) {
		test = extent.createTest("Test Case 20 End","Close browser and end automation session.");
		test.log(Status.INFO,"-- CloseAndQuitBrowser --");																								
		logger.info("-- CloseAndQuitBrowser --");
		
		try {
			Thread.sleep(3000);
			driver.quit();
			
			extent.flush();
			logger.info("Thread: " + Thread.currentThread().getId());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
		
	//	public static void main(String[] args) {
	//		StopWatch watch = new StopWatch();
	//		watch.start();
	//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
	//		LocalDateTime now = LocalDateTime.now();  			
	//		logger.info("Start Execution: " + dtf.format(now));
	//		
	//	    InitializeDriver();
	//	    
	//	    HandleInputandRadio();
	//	    HandleDropdown();
	//	    HandleMultiDropdown();
	//	    HandleBootstrapDropdown();
	//	    HandleAutoSuggestion();
	//	    HandleHiddenItems();
	//	    HandleDialogAlerts();
	//	    HandleFramesiFrames();
	//	    HandleWebTablePagination();
	//	    HandleDatePickers();
	//	    HandleMouseActions();
	//	    HandleKeyboardActions();
	//	    HandleUploadFiles();
	//	    HandlePagesWindows();
	//	    HandleMultiplePagesWindows();
	//	    HandleCaptureScreen();
	//	    HandleUsingClass();
	//	    
	//	    CloseAndQuitBrowser();
	//	    
	//	    watch.stop();
	//	    DateFormat df = new SimpleDateFormat("HH 'hours', mm 'mins', ss 'seconds', SSS 'ms'");
	//		df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
	//		String elapsedTime = df.format(watch.getTime());			
	//		logger.info("Time Elapsed: " + elapsedTime);
	//	}
}

/**
 * 
 * Definition: 
 * 
 */
class MyClass {
	int x;
	
	public MyClass() {
		this.x = 55;
	}
	
	public void loopPrint() {
		for (int i=0; i < 5; i++) {
			JavaSelenium.logger.info(i);
		}
	}
}
