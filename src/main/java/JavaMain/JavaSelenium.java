package JavaMain;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.*;
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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.testng.Assert;

import SourcePages.OpenPage;


public class JavaSelenium {
	/*
	 * 1. create selenium object code
	 * 2. use configuration file
	 * 3. convert to page object model
	 * 4. apply testng
	 * 5. apply extent report
	 * 6. add log files
	 */
	static WebDriver driver;
	
	public static void InitializeDriver() {
		try {
			String projectPath = System.getProperty("user.dir");
			System.out.println(projectPath);
			
			Properties prop = new Properties();
			InputStream input;

			input = new FileInputStream("./Config/config.property");
			prop.load(input);
			
			System.setProperty("webdriver.chrome.driver", prop.getProperty("ChromeDriver"));
			
			ChromeOptions options = new ChromeOptions();
//			options.addArguments("start-maximized");
//			options.addArguments("start-fullscreen");
			
		    driver = new ChromeDriver(options); 
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public static void CloseAndQuitBrowser() {
		try {
			Thread.sleep(3000);
			driver.quit();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void HandleInputandRadio() {
		System.out.println("-- HandleInputandRadio --");
		
		String url = "https://demoqa.com/automation-practice-form";
		driver.get(url);
		
		// ----- How to handle Inputbox & RadioButtons -----
		WebElement firstnameinput = driver.findElement(By.xpath("//input[@id='firstName']"));
		WebElement lastnameinput = driver.findElement(By.xpath("//input[@id='lastName']"));
		
		System.out.println(firstnameinput.isDisplayed());
		System.out.println(firstnameinput.isEnabled());
		System.out.println("Current value: " + firstnameinput.getAttribute("value"));
		
		firstnameinput.sendKeys("John");
		lastnameinput.sendKeys("Doe");
		
		System.out.println("Updated value: " + firstnameinput.getAttribute("value"));
		
		WebElement gendermalelabel = driver.findElement(By.xpath("//label[text() = 'Male']"));
		WebElement genderfemalelabel = driver.findElement(By.xpath("//label[text() = 'Female']"));
		WebElement gendermaleradiobutton = driver.findElement(By.xpath("//input[@id='gender-radio-1']"));
		WebElement genderfemaleradiobutton = driver.findElement(By.xpath("//input[@id='gender-radio-2']"));
		
		System.out.println("Radiobutton " + gendermaleradiobutton.isSelected());
		gendermalelabel.click();
		System.out.println(gendermaleradiobutton.isSelected());
		System.out.println(genderfemaleradiobutton.isSelected());
		genderfemalelabel.click();
		System.out.println(genderfemaleradiobutton.isSelected());
		
		// ----- How to handle Checkboxes -----
		WebElement hobbiessportslabel = driver.findElement(By.xpath("//label[text() = 'Sports']"));
		WebElement hobbiessportscheckbox = driver.findElement(By.xpath("//input[@id='hobbies-checkbox-1']"));
		WebElement hobbiesreadinglabel = driver.findElement(By.xpath("//label[text() = 'Reading']"));
		WebElement hobbiesreadingcheckbox = driver.findElement(By.xpath("//input[@id='hobbies-checkbox-2']"));
		
		System.out.println("Checkbox " + hobbiessportscheckbox.isSelected());
		hobbiessportslabel.click();
		System.out.println(hobbiessportscheckbox.isSelected());
		System.out.println(hobbiesreadingcheckbox.isSelected());
		hobbiesreadinglabel.click();
		System.out.println(hobbiesreadingcheckbox.isSelected());
		
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
	
	public static void HandleDropdown() {
		System.out.println("-- HandleDropdown --");
		
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

	    System.out.println(countryDropdown.getAttribute("value"));

	    Assert.assertEquals(countryDropdown.getAttribute("value"),"india");

	    driver.findElement(By.cssSelector("#country > option:nth-child(6)")).click();
	    System.out.println(countryDropdown.getAttribute("value"));
	}
	
	public static void HandleMultiDropdown() {
		System.out.println("-- HandleMultiDropdown --");
		
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
			
			System.out.println("Numer of options: " + colorsdropdownobject.getOptions().size());
			
			for (WebElement colors : colorsdropdownobject.getAllSelectedOptions())
			    System.out.println("Value: " + colors.getText());		

		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
	public static void HandleBootstrapDropdown() {
		System.out.println("-- HandleBootstrapDropdown --");
		
	    String url = "https://www.jquery-az.com/boots/demo.php?ex=63.0_2";
	    driver.get(url);

	    WebElement outputbutton = driver.findElement(By.xpath("//button"));
	    List<WebElement> outputoption = driver.findElements(By.xpath("//ul//li//label//input"));

	    System.out.println("Numer of options: " + outputoption.size());
	    outputbutton.click();

	    for (WebElement item : outputoption) {
	        String value = item.getAttribute("value");
	        if (value.equals("Angular") || value.equals("Java")) {
	        		System.out.println(item.getAttribute("value"));
		            item.click();
		            System.out.println(item.isSelected());	        		
	        }

	        if (value.equals("HTML") || value.equals("CSS")) {
	        		System.out.println(item.getAttribute("value"));
		            item.click();
		            System.out.println(item.isSelected());	        			        		
	        }
	    }	    
	}
	
	public static void HandleAutoSuggestion() {
		System.out.println("-- HandleAutoSuggestion --");
		
		String url = "https://www.redbus.in/";
	    driver.get(url);

	    WebElement frominput = driver.findElement(By.xpath("//input[@id='src']"));
	    frominput.sendKeys("Delhi");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@class,'sc-iwsKbI')]/div/text[1]")));
		
	    List<WebElement> autodropdownoptions = driver.findElements(By.xpath("//li[contains(@class,'sc-iwsKbI')]/div/text[1]"));

	    System.out.println(("Number of options:" + autodropdownoptions.size()));

	    for (WebElement option : autodropdownoptions) {
	        String value = option.getText();
	        System.out.println(value);

	        if (value.equals("RK Ashram")) {
	            option.click();
	            break;		
	        }	    	
	    }
	}

	public static void HandleHiddenItems() {
		System.out.println("-- HandleHiddenItems --");
		
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

	public static void HandleDialogAlerts() {
		System.out.println("-- HandleDialogAlerts --");
		
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
		    System.out.println(alert.getText());
		    alert.accept();

		    confirmbutton.click();
		    Thread.sleep(2000);
		    alert = driver.switchTo().alert();
		    System.out.println(alert.getText());
		    alert.dismiss();

		    promptbutton.click();
		    Thread.sleep(2000);
		    alert = driver.switchTo().alert();
		    System.out.println(alert.getText());
		    alert.sendKeys("JavaSE");
		    alert.accept();
		    Thread.sleep(2000);
		    String value = promptmessage.getAttribute("textContent");
		    System.out.println(value);

		    //----- manual alert trigger
		    System.out.println("1. Click Alert button!!");
		    		    
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    alert = wait.until(ExpectedConditions.alertIsPresent());
		    
		    alert = driver.switchTo().alert();
		    System.out.println(alert.getText());
		    alert.accept();
		    Thread.sleep(2000);

		    
		    System.out.println("2. Click Confirm button!!");
		    
		    alert = wait.until(ExpectedConditions.alertIsPresent());
		    
		    alert = driver.switchTo().alert();
		    System.out.println(alert.getText());
		    alert.dismiss();
		    Thread.sleep(2000);
		    	    
		} catch (InterruptedException | TimeoutException e) {
			e.printStackTrace();
		}		
	}
	
	public static void HandleFramesiFrames() {
		System.out.println("-- HandleFramesiFrames --");
		
	    String url = "https://ui.vision/demo/webtest/frames/";
	    driver.get(url);

	    List<WebElement> framewindow = driver.findElements(By.xpath("//frame"));
	    System.out.println("Number of frames: " + framewindow.size());

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
	
	public static void HandleWebTablePagination() {
		System.out.println("-- HandleWebTablePagination --");
		
		String url = "https://testautomationpractice.blogspot.com/";
	    driver.get(url);

	    WebElement tablelabel = driver.findElement(By.xpath("//h2[text()='Pagination Table']"));
	    JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", tablelabel);

	    WebElement paginationtable = driver.findElement(By.xpath("//table[@id='productTable']"));
	    List<WebElement> columns = paginationtable.findElements(By.cssSelector("thead tr th"));
	    List<WebElement> rows = paginationtable.findElements(By.cssSelector("tbody tr"));

	    List<WebElement> rowscells = paginationtable.findElements(By.cssSelector("tbody tr td"));

	    System.out.println("Number of columns: " + columns.size());
	    System.out.println("Number of rows: " + rows.size());

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
	            System.out.println(rowarray);
	            index = 0;
			}
		}

	    List<WebElement> pages = driver.findElements(By.xpath("//ul[@id='pagination']//li//a"));
	    for (WebElement page : pages) {
	        System.out.println(page.getText());
	        page.click();

	        rows = paginationtable.findElements(By.cssSelector("tbody tr"));
	        for (WebElement elements : rows) {
	            System.out.println(elements.getText());
			}
		}
	}
	
	public static void HandleDatePickers() {
		System.out.println("-- HandleDatePickers --");
		
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
	
	public static void HandleMouseActions() {
		System.out.println("-- HandleMouseActions --");
		
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
		    System.out.println("Text is: " + field1.getAttribute("value"));
	
		    WebElement field2 = driver.findElement(By.xpath("//input[@id='field2']"));
		    System.out.println("Text is: " + field2.getAttribute("value"));
	
		    // -----How to handle Mouse Drag and Drop-----
		    WebElement draggable = driver.findElement(By.xpath("//div[@id='draggable']"));
		    WebElement droppable = driver.findElement(By.xpath("//div[@id='droppable']"));
	
		    action.dragAndDrop(draggable, droppable).perform();
		} catch (InterruptedException | TimeoutException e) {
			e.printStackTrace();
		}
	}
	
	public static void HandleKeyboardActions() {
		System.out.println("-- HandleKeyboardActions --");
		
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
	
	public static void HandleUploadFiles() {
		System.out.println("-- HandleUploadFiles --");
		
	    String url = "https://www.foundit.in/";
	    driver.get(url);

	    try {
	    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	    	
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[@class='mqfihd-upload']")));
			
		} catch (TimeoutException e) {
			System.out.println("-- [TIMEOUT] --");
			e.printStackTrace();
		}

	    url = "https://davidwalsh.name/demo/multiple-file-upload.php";
	    driver.get(url);

	    WebElement nofileslabel = driver.findElement(By.xpath("//ul[@id='fileList']/li"));
	    System.out.println("\"No Files Selected\" = " + nofileslabel.getText());

	    WebElement filestouploadbutton = driver.findElement(By.xpath("//input[@id='filesToUpload']"));
	    filestouploadbutton.sendKeys("C:/Temp/sample1.txt \n C:/Temp/sample2.txt");

	    WebElement filename1 = driver.findElement(By.xpath("//ul[@id='fileList']/li[1]"));
	    WebElement  filename2 = driver.findElement(By.xpath("//ul[@id='fileList']/li[2]"));

	    System.out.println("\"sample1.txt\" = " + filename1.getText());
	    System.out.println("\"sample2.txt\" = " + filename2.getText());	
	}
	
	public static void HandlePagesWindows() {
		System.out.println("-- HandlePagesWindows --");
		
	    String url = "https://opensource-demo.orangehrmlive.com/";
	    driver.get(url);

	    String originalwindow = driver.getWindowHandle();
	    System.out.println(driver.getTitle());

	    driver.switchTo().newWindow(WindowType.TAB);
	    driver.get("https://www.orangehrm.com/");
	    String tabwindow = driver.getWindowHandle();
	    System.out.println(driver.getTitle());

	    driver.switchTo().newWindow(WindowType.WINDOW);
	    driver.get("https://www.google.com/");
	    String newwindow = driver.getWindowHandle();
	    System.out.println(driver.getTitle());
	    Threadsleep(2000);
	    driver.close();

	    System.out.println("Original Window: " + originalwindow);
	    System.out.println("Tab Window: " + tabwindow);
	    System.out.println("New Window: " + newwindow);

	    Set<String> totalwindows = driver.getWindowHandles();
	    System.out.println("Number of Window(s): " + totalwindows.size());

	    driver.switchTo().window(tabwindow);
	    Threadsleep(1000);
	    driver.close();

	    driver.switchTo().window(originalwindow);
	    Threadsleep(2000);	
	}
	
	public static void HandleMultiplePagesWindows() {
		System.out.println("-- HandleMultiplePagesWindows --");
		
		String url = "https://opensource-demo.orangehrmlive.com/";
	    driver.get(url);

	    // Original Window
	    driver.get("https://opensource-demo.orangehrmlive.com/");
	    String originalwindow = driver.getWindowHandle();
	    System.out.println(driver.getTitle());

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='OrangeHRM, Inc']")));

	    WebElement orangelink = driver.findElement(By.xpath("//a[text()='OrangeHRM, Inc']"));
	    orangelink.click();

	    // New Tab
	    Set<String> openwindows = driver.getWindowHandles();
	    String openwindowsIndex = openwindows.toArray()[1].toString();
	    driver.switchTo().window(openwindowsIndex);

	    String tabwindow = driver.getWindowHandle();
	    System.out.println(driver.getTitle());

	    // New Window
	    driver.switchTo().newWindow(WindowType.WINDOW);
	    driver.get("https://www.google.com");
	    String newwindow = driver.getWindowHandle();
	    System.out.println(driver.getTitle());
	    Threadsleep(2);

	    System.out.println("Original Window: " + originalwindow);
	    System.out.println("Tab Window: " + tabwindow);
	    System.out.println("New Window: " + newwindow);

	    Set<String> totalwindows = driver.getWindowHandles();
	    System.out.println("Number of Window(s): " + totalwindows.size());

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
	
	public static void HandleCaptureScreen() {
		System.out.println("-- HandleCaptureScreen --");
		
		String url = "https://testautomationpractice.blogspot.com/";
	    driver.get(url);

	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss"); 
		LocalDateTime now = LocalDateTime.now();  			

		String pathString = "./captures";
		File file = new File(pathString);
		
	    if (!file.exists()) {
	    	System.out.println("Directory created");
	    	file.mkdir();
	    } else {
	    	System.out.println("Directory already exists");
	    }

	    //Capture screen
	    String savefile = "./captures/window_" + dtf.format(now) + ".png";
	    System.out.println(savefile);
	    File sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);   
	    try {
			FileUtils.copyFile(sourceFile, new File(savefile));
		} catch (IOException e) {
			e.printStackTrace();
		}

	    //Capture element
	    savefile = "./captures/element_" + dtf.format(now) + ".png";
	    System.out.println(savefile);
	    WebElement droppablelement = driver.findElement(By.xpath("//div[@id='droppable']"));
	    sourceFile = ((TakesScreenshot)droppablelement).getScreenshotAs(OutputType.FILE);
	    try {
			FileUtils.copyFile(sourceFile, new File(savefile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    //Capture fullscreen
//	    savefile = "./captures/full_" + dtf.format(now) + ".png";
//	    System.out.println(savefile);
//	    WebElement fullscreen = driver.findElement(By.tagName("body"));
//	    sourceFile = ((TakesScreenshot)fullscreen).getScreenshotAs(OutputType.FILE);
//	    try {
//			FileUtils.copyFile(sourceFile, new File(savefile));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
}	
	
	public static void HandleUsingClass() {
		System.out.println("-- HandleUsingClass --");
		
		// class within same file
	    MyClass myclassinstance = new MyClass();
	    System.out.println(myclassinstance.x);
	    myclassinstance.loopPrint();
	    
	    //class from external file
	    OpenPage classGoogleInstancePage = new OpenPage(driver);
	    classGoogleInstancePage.openPage("https://www.google.com");
	    classGoogleInstancePage.enterSearchInput("java selenium");
	    Threadsleep(2000);
	}
	
	public static void Threadsleep(int inMilliSeconds) {
		try {
			Thread.sleep(inMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void SelectProduct(String productname) {
		WebElement matchedrow = driver.findElement(By.xpath("//td[text()='" + productname + "']//following-sibling::td//input"));
    	matchedrow.click();
	}
		
	public static void main(String[] args) {
		StopWatch watch = new StopWatch();
		watch.start();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
		LocalDateTime now = LocalDateTime.now();  			
		System.out.println("Start Execution: " + dtf.format(now));
		
	    InitializeDriver();
	    
	    HandleInputandRadio();
	    HandleDropdown();
	    HandleMultiDropdown();
	    HandleBootstrapDropdown();
	    HandleAutoSuggestion();
	    HandleHiddenItems();
	    HandleDialogAlerts();
	    HandleFramesiFrames();
	    HandleWebTablePagination();
	    HandleDatePickers();
	    HandleMouseActions();
	    HandleKeyboardActions();
	    HandleUploadFiles();
	    HandlePagesWindows();
	    HandleMultiplePagesWindows();
	    HandleCaptureScreen();
	    HandleUsingClass();
	    
	    CloseAndQuitBrowser();
	    
	    watch.stop();
	    DateFormat df = new SimpleDateFormat("HH 'hours', mm 'mins', ss 'seconds', SSS 'ms'");
		df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
		String elapsedTime = df.format(watch.getTime());			
		System.out.println("Time Elapsed: " + elapsedTime);
	}
}


class MyClass {
	int x;
	
	public MyClass() {
		this.x = 55;
	}
	
	public void loopPrint() {
		for (int i=0; i < 5; i++) {
			System.out.println(i);
		}
	}
}
