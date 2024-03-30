package StepDefinition;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import PageObject.AddNewCustomerPage;
import PageObject.LoginPage;
import Utilities.ReadConfig;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.Assert;
public class StepDef extends BaseClass{

	@Before
	public void setup() {
		
		
		readConfig = new ReadConfig();
		
		String browser = readConfig.getBrowser();
		log=LogManager.getLogger("StepDef");
		
		
		 switch(browser.toLowerCase())
			{
			case "chrome":
				
				ChromeOptions option = new ChromeOptions();
	            option.addArguments("--remote-allow-origins=*");
				System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\eclipse-workspace\\DemoStore\\Driver\\chromedriver.exe");        
				 driver = new ChromeDriver(option); 
				 break;

			case "msedge":
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				break;

			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				break;
			default:
				driver = null;
				break;

			}
		 
		 
		 log.info("chrome browser launched succesfully");
	}

@Given("User Launch Chrome browser")
public void user_launch_chrome_browser() {

	   
	loginpage = new LoginPage(driver);
	
	customerpage =new AddNewCustomerPage(driver);
}

@When("User opens URL {string}")
public void user_opens_url(String url) {
    // Write code here that turns the phrase above into concrete actions
	driver.get(url);
	 log.info("Navigated to url");
	
}

@When("User enters Email as {string} and Password as {string}")
public void user_enters_email_as_and_password_as(String stremail, String strpassword) {
   
	 loginpage.enterEmail(stremail);
	    loginpage.enterPassword(strpassword);
	    log.info("entered password and username");
}

@When("Click on Login")
public void click_on_login() {
	  loginpage.clickOnLoginButton();
	  log.info("click on login button");
}

@Then("Page Title should be {string}")
public void page_title_should_be(String expectedTitle) {
	
	String actualTitle = driver.getTitle();
	
	if(actualTitle.equals(expectedTitle)) {
		
		Assert.assertTrue(true);
		log.warn("page title matched");
	}else
		
		Assert.assertTrue(false);
	log.warn("page title not matched");
	
   
}

@When("User click on customers Menu")
public void user_click_on_customers_menu() {
	 customerpage.clickOnCustomersMenu();
	 log.info("clicked on customers menu");
	
}
@When("click on customers Menu Item")
public void click_on_customers_menu_item() {
	customerpage.clickOnCustomersMenuItem();
	 log.info("clicked on customers menu item");
}

@When("click on Add new button")
public void click_on_add_new_button() {
	customerpage.clickOnAddnew();
	 log.info("clicked on Add new");
}

@Then("User can view Add new customer page")
public void user_can_view_add_new_customer_page() {
	 String actualTitle =customerpage.getPageTitle();
	 
	 String expectedTitle="Add a new customer / nopCommerce administration";
	 
	 if(actualTitle.equals(expectedTitle)) {
		 
			Assert.assertTrue(true);
			log.error("titles matched");
			
		}else {
			 
			Assert.assertTrue(false);
			log.error("titles not matched");
		}
}

@When("User enter customer info")
public void user_enter_customer_info() {
	customerpage.enterEmail(generateEmailid()+"@gmail.com");
	customerpage.enterPassword("test1");
	customerpage.enterFirstName("amjath");
	customerpage.enterLastName("Khan");
	customerpage.enterGender("male");
	customerpage.enterDob("6/10/2000");
	customerpage.enterCompanyName("mindteck");
	customerpage.enterAdminContent("IT proffesion");
	customerpage.enterManagerOfVendor("Vendor 1");
	
	log.info("customer details are added");
}

@When("click on Save button")
public void click_on_save_button() {
	customerpage.clickOnSave();

	log.info("click on save button");
    
    try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
	
}

@Then("User can view confirmation message {string}")
public void user_can_view_confirmation_message(String expectedconfirmationmsg) {
	String bodyText = driver.findElement(By.tagName("Body")).getText();
	
	if(bodyText.contains(expectedconfirmationmsg)) {

		Assert.assertTrue(true);
		log.warn("page title not matched");
	}else {
		
		
		Assert.assertFalse(false);
		log.warn("page title not matched");
	}
	
	
}


@When("User click on Log out link")
public void user_click_on_log_out_link() {
	loginpage.clickOnLogOutButton();
	log.info("clicked on logout button");
	
}

@Then("close browser")
public void close_browser() {
	driver.close();
	
}
@After
public void teardown(Scenario sc) {
	
	if(sc.isFailed()==true)
	{
		//Convert web driver object to TakeScreenshot

		String fileWithPath = "C:\\Users\\Admin\\eclipse-workspace\\DemoStore\\Screenshot\\failedScreenshot.png";
		TakesScreenshot scrShot =((TakesScreenshot)driver);

		//Call getScreenshotAs method to create image file
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

		//Move image file to new destination
		File DestFile=new File(fileWithPath);

		//Copy file at destination

		try {
			FileUtils.copyFile(SrcFile, DestFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
  driver.quit();	
}
/*
@BeforeStep
public void beforestepDemo() {
	System.out.println("it is executed before every step");
}

@AfterStep
public void afterstepDemo() {
	System.out.println("it is executed after every step");
}
*/
@AfterStep
public void addScreenshot(Scenario scenario) {
	
	if(scenario.isFailed()==true)
	{
		//Convert web driver object to TakeScreenshot

		final byte[] screenshot= ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
        
		scenario.attach(screenshot, "image/png", scenario.getName());
		//Call getScreenshotAs method to create image file
	
		
	}
}



	
}
