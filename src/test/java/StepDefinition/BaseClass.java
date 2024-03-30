package StepDefinition;

import java.util.Properties;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import PageObject.AddNewCustomerPage;
import PageObject.LoginPage;
import Utilities.ReadConfig;


public class BaseClass {
	
public WebDriver driver;
	
	public LoginPage loginpage;
	public AddNewCustomerPage customerpage;
	public static Logger log;
	public ReadConfig readConfig;
	

	
	public String generateEmailid() {
		return(RandomStringUtils.randomAlphabetic(5));
	}

}
