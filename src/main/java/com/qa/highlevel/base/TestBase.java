package com.qa.highlevel.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.highlevl.utils.testUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	public static WebDriver driver;

	public static WebDriverWait wait;

	public static Actions actions;

	public static Properties prop;// To use in child class as well

	public static EventFiringWebDriver e_driver;

	public TestBase() {
		prop = new Properties();

		try {

			FileInputStream input = new FileInputStream(
					"C:\\Users\\Thavamani Murugan\\eclipse-workspace\\Assessment_HighLevel4\\src\\main\\java\\com\\qa\\highlevel\\config\\config.properties");

			prop.load(input);
		}

		catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		catch (IOException e) {

			e.printStackTrace();
		}

	}

	public static void initilization() throws IOException {

		String browsername = prop.getProperty("browser");

		if (browsername.equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			options.addArguments("--disable-notifications");

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(options);
			wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		}

		/*
		 * else if (browsername.equals("firefox")) { driver = new FirefoxDriver(); }
		 */

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();

		driver.manage().timeouts().pageLoadTimeout(testUtil.page_load_timeout, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(testUtil.implicity_wait, TimeUnit.SECONDS);

		driver.get(prop.getProperty("url"));

	}


	public By dynamicLocator(String tag, String value) {
		return By.xpath("//*[contains(" + tag + ",'" + value + "')]");
	}

	public static void clickElement(WebDriver driver) {
		actions = new Actions(driver);
		actions.click();
	}

	public static String TimeZoneConverter(String selectedDate, String SelectedTimeZone, String selectedSlot) {

		LocalDate date = LocalDate.now();
		int monthValue = date.getMonthValue();
		int yearVal = date.getYear();
		int selectDate = Integer.parseInt(selectedDate);

		String[] split = SelectedTimeZone.split(" ");
		String country = split[1];

		String[] scheduleTime = selectedSlot.split(" - ");
		System.out.println("Schedule Time"+scheduleTime);

		String[] startTime = scheduleTime[0].split(" ");
		System.out.println(startTime[0]);
		System.out.println(startTime[1]);
		

		String amorPM = startTime[1].toString().toUpperCase();

		String DateString = monthValue + "/" + selectDate + "/" + yearVal + " " + startTime[0] + " " + amorPM;
		System.out.println("Enteered Date String" + DateString);

		DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
				.appendPattern("[M/dd/yyyy ][M/d/yyyy ][MM/dd/yyyy ][h.mm a][hh:mm a]")
				.toFormatter(Locale.ENGLISH);

		LocalDateTime localDateTime = LocalDateTime.parse(DateString, formatter);

		ZonedDateTime pacificMidwayDateTime = ZonedDateTime.of(localDateTime, ZoneId.of(country));// line1

		ZonedDateTime indiaDateTime = pacificMidwayDateTime.withZoneSameInstant(ZoneId.of("Asia/Kolkata"));

		String indiaTime = indiaDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

		System.out.println("India Standard Time: " + indiaTime);

		LocalDateTime datetime = LocalDateTime.parse(indiaTime);
		DateTimeFormatter formattedTime = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
		String output = datetime.format(formattedTime);

		System.out.println("CONVERTED FORMATTED TIME :" + output);

		return output;

	}

	public void convertDateFromUI(String bookedDate) {

		String inputDateStr = bookedDate;
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a", Locale.ENGLISH);

		LocalDateTime dateTime = LocalDateTime.parse(inputDateStr, inputFormatter);

		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
		String outputDateStr = dateTime.format(outputFormatter);

		System.out.println("Co" + outputDateStr);

	}

}
