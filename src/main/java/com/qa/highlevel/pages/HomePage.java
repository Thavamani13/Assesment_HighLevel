package com.qa.highlevel.pages;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.highlevel.base.TestBase;

public class HomePage extends TestBase {
	
   @FindBy(xpath = "//span[text()=' Calendars ']" )
   WebElement calendarLink;
   
   public HomePage() throws IOException {
	   PageFactory.initElements(driver, this);
	
	}
   
   public CalendarsPage clickCalendar() throws IOException {
	   calendarLink.click();
	   return new CalendarsPage();
   }

}
