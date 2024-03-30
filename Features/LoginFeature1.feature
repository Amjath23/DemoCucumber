Feature: Login 

Background: common steps for all scenarios
Given User Launch Chrome browser 
	When User opens URL "http://admin-demo.nopcommerce.com/login" 
	And User enters Email as "admin@yourstore.com" and Password as "admin" 
	And Click on Login 
	Then Page Title should be "Dashboard / nopCommerce administration" 
@Regression@smoke
Scenario: Successful Login with Valid Credentials 
	When User click on Log out link 
	Then Page Title should be "Your store. Login" 
	And close browser 
@Sanity	
Scenario Outline:Successful Login with Valid Credentials DDT
	
	When User click on Log out link 
	Then Page Title should be "Your store. Login" 
	And close browser 
	Examples:
	|email|password|
	|admin@yourstore.com|admin|
	