package com.qa.highlevel.pages;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.highlevel.base.TestBase;

public class LoginPage extends TestBase {

	
	
	@FindBy(id="email")
	WebElement username;
	
	@FindBy(id="password")
	WebElement password;
	
	@FindBy(xpath="//button[contains(text(),'Sign in')]")
	WebElement loginbutton;
	
	public LoginPage() throws IOException {
		PageFactory.initElements(driver, this);
	}
	
    public HomePage login(String un, String pwd) throws IOException, InterruptedException {
		
		username.sendKeys(un);
		password.sendKeys(pwd);
		Thread.sleep(3000);
		loginbutton.click(); 
		return new HomePage() ;
	}
	
	

}
