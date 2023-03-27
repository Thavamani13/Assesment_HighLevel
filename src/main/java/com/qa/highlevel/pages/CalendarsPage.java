package com.qa.highlevel.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.lang.model.element.Element;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.qa.highlevel.base.TestBase;

public class CalendarsPage extends TestBase {

	public static String selecteddate;

	public static String SelectedTimeZone;

	public static String selectedSlot;
	
	public static String bookedDate;

	@FindBy(xpath = "//button[text()=' Book Appointment ']")
	WebElement bookAppointmentButton;

	@FindBy(xpath = "//input[@placeholder='Search by name, email, phone or company']")
	WebElement searchContactField;

	@FindBy(xpath = "//div[text()='thavamani-test ']") // *
	WebElement suggestedContact;

	@FindBy(xpath = "(//input[@class='n-base-selection-input'])[1]")
	WebElement CalendarSuggestion;

	@FindBy(xpath = "//span[text()=' Appointments ']")
	WebElement AppointmentsButon;

	@FindBy(xpath = "//div[@title='Calendar Default']/following-sibling::div")
	WebElement teamMember;

	@FindBy(xpath = "//div[@class='n-base-select-option__content'][text()='Thavamani SDET']") // *
	WebElement teamMemberSuggestion;

	

	@FindBy(xpath = "//div[@id='date-picker-standard']") // *
	WebElement dateField;

	@FindBy(xpath = "//div[@id='date-picker-standard']//td[@class='vdpCell selectable']") // *
	WebElement datePicker;

	// slot
	@FindBy(xpath = "//div[text()='Slot']//following-sibling::div//div[@class='n-base-selection-input__content']")
	WebElement slotField;

	// timezone
	@FindBy(xpath = "//div[contains(text(),'timezone')]/following-sibling::div//div[@class='n-base-loading n-base-suffix']")
	WebElement timeZoneField;

	@FindBy(xpath = "//span[text()='Book Appointment']")
	WebElement confirmBookAppointment;

	@FindBy(xpath = "//button[@title='Date Added (DESC)']")
	WebElement orderByButton;

	@FindBy(xpath = "//button[@title='Date Added (DESC)']//following-sibling::div//span[text()='Date Added (DESC)']//parent::a")
	WebElement sordtByDesc;

	@FindBy(xpath = "//td[@id='pg-appt__link-contact-detail']//following-sibling::td//div")
	WebElement dateFromUI;
	
	@FindBy(xpath = "//td[@id='pg-appt__link-contact-detail']")
	WebElement nameFromUI;

	public CalendarsPage() {
		PageFactory.initElements(driver, this);
	}

	public void bookAppointMent() throws InterruptedException {

		AppointmentsButon.click();
		System.out.println(driver.getTitle());// *
		
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(bookAppointmentButton));
		bookAppointmentButton.click();
		driver.switchTo().frame(0);
		searchContactField.sendKeys("Thavamani");
		suggestedContact.click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();

		

	}

	public void selectAppointmentDetails() throws InterruptedException {
		Thread.sleep(5000);
		enterCalenDarDetails();

		teamMember.click();
		teamMemberSuggestion.click();

		

	}

	public void enterCalenDarDetails() throws InterruptedException {
		Thread.sleep(5000);

		wait.until(ExpectedConditions.elementToBeClickable(CalendarSuggestion));
		// Click Calendar suggestion field
		CalendarSuggestion.click();
		CalendarSuggestion.sendKeys("Duplicate");
		CalendarSuggestion.sendKeys(Keys.TAB);
	}

	public String selectTimezones() throws InterruptedException {
		Thread.sleep(5000);
		driver.switchTo().frame(0);
		wait.until(ExpectedConditions.elementToBeClickable(timeZoneField));
		timeZoneField.click();

		List<WebElement> list = driver.findElements(By.xpath("//div[@class='n-base-select-option__content']"));
		List<String> timeZones = new ArrayList<String>();

		Random rand = new Random();
		int random = 0;
		while (true) {
			random = rand.nextInt(8);
			if (random != 0)
				break;
		}
		System.out.println(random);

		List<WebElement> lstElements = driver.findElements(By.xpath("//div[@class='n-base-select-option__content']"));
		System.out.println("New List " + lstElements.size());
		SelectedTimeZone = lstElements.get(random).getText().toString();
		System.out.println("Selected Time zone" + SelectedTimeZone);
		lstElements.get(random).click();
		return SelectedTimeZone;

	}

	public String selectDate() throws InterruptedException {
		dateField.click();
		List<WebElement> selectableDates = driver
				.findElements(By.xpath("//div[@id='date-picker-standard']//td[@class='vdpCell selectable']"));
		System.out.println("SelectableDates " + selectableDates.size());
		WebElement date = selectableDates.get(selectableDates.size() >= 2 ? 1 : 0);
		selecteddate = date.getText().toString();

		System.out.println("Selected Date" + selecteddate);
		date.click();
		

		Actions act = new Actions(driver);
		act.click();
		Thread.sleep(3000);

		return selecteddate;
	}

	public void selectSlot() throws InterruptedException {
		
		selectedSlot = slotField.getText();
		
	}

	public void clickConfirmAppointment() throws InterruptedException {
		Thread.sleep(15000);
		confirmBookAppointment = driver.findElement(By.xpath("//span[text()='Book Appointment']"));
		
		
		
		for(int i=0; i<=2;i++){
			  try{
			     driver.findElement(By.xpath("//span[text()='Book Appointment']")).click();
			     break;
			  }
			  catch(Exception e){
			     System.out.println(e.getMessage());
			  }
			}
		
		driver.switchTo().defaultContent();
		
	}

	public static String verifyDate() {
		String convertedDate =TimeZoneConverter(selecteddate, SelectedTimeZone, selectedSlot);
		return convertedDate;
	}
	
	
	public void sortAppointmentsByDesc() {
		wait.until(ExpectedConditions.visibilityOf(orderByButton));
		orderByButton.click();
		wait.until(ExpectedConditions.visibilityOf(sordtByDesc));
		sordtByDesc.click();
	}
	
	public String veriFyBookingDetails() {
		driver.navigate().refresh();
		wait.until(ExpectedConditions.visibilityOf(dateFromUI));
		bookedDate =dateFromUI.getText().toString();
		System.out.println("BOOKED DATE PRESENT IN UI"+bookedDate);
		
		nameFromUI.getText().toString();
		return bookedDate;
	}

}
