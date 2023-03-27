package com.qa.highlevel.testcases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.qa.highlevel.base.TestBase;
import com.qa.highlevel.pages.CalendarsPage;
import com.qa.highlevel.pages.HomePage;
import com.qa.highlevel.pages.LoginPage;

public class LoginPageTest extends TestBase {

	LoginPage loginPage;
	HomePage homePage;
	CalendarsPage calendarsPage;

	public LoginPageTest() throws IOException {
		super();
	}

	@BeforeSuite
	public void setUp() throws IOException {
		initilization();
		loginPage = new LoginPage();
	}

	@Test
	public void loginTest() throws IOException, InterruptedException {
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		calendarsPage = homePage.clickCalendar();
		calendarsPage.bookAppointMent();
		calendarsPage.selectTimezones();
		calendarsPage.selectDate();
		calendarsPage.selectSlot();
		calendarsPage.clickConfirmAppointment();
		
		String convertedDate =calendarsPage.verifyDate();
		String bookedDate=calendarsPage.veriFyBookingDetails();
		
		Assert.assertTrue(convertedDate.equalsIgnoreCase(bookedDate));
		
		
	}

}
