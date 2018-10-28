package com.italam.utility;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import static java.nio.file.StandardCopyOption.*;

public class GeneralUtility {

	static final String jarPath = getWorkspaceDrive(GeneralUtility.getUserDir(System.getProperty("user.dir")))
			+ "jars\\drivers\\";
	static final Logger log = LogManager.getLogger(GeneralUtility.class.getName());
	static final String AB12 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static final String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static final String NU = "1234567890";
	static final String[] PP = { "050", "052", "053", "054", "055" };
	static SecureRandom rnd = new SecureRandom();
	static final String dataFolder = GeneralUtility.getUserDir(System.getProperty("user.dir"))
			+ "\\src\\com\\Framework\\data";

	// ===========================================================================================================
	// Get the web driver by browser type and get URL
	public static WebDriver getWebDriver(String sBrowser, String sUrl) throws IOException {

		System.setProperty("file.encoding", "UTF-8");

		WebDriver driver;
		log.info("Browser: " + sBrowser + ", Start page: " + sUrl);

		switch (sBrowser.toLowerCase()) {
		case "firefox":
			System.setProperty("webdriver.firefox.marionette", jarPath + "geckodriver.exe");
			driver = new FirefoxDriver();
			break;

		case "chrome":
			System.setProperty("webdriver.chrome.driver", jarPath + "chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("useAutomationExtension", false);

			driver = new ChromeDriver(options);
			break;

		case "ie":
			File file = new File(jarPath + "IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			//DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			/*
			 * capabilities.setCapability("nativeEvents", false);
			 * capabilities.setCapability("unexpectedAlertBehaviour", "accept");
			 * capabilities.setCapability("ignoreProtectedModeSettings", true);
			 * capabilities.setCapability("disable-popup-blocking", true);
			 * capabilities.setCapability("enablePersistentHover", true);
			 * capabilities.setCapability("ignoreZoomSetting", true);
			 */
			// capabilities.setCapability("requireWindowFocus", true);
			// driver = new InternetExplorerDriver(capabilities);
			driver = new InternetExplorerDriver();
			break;

		default:
			System.setProperty("webdriver.firefox.marionette", jarPath + "\\geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		}

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get(sUrl);
		driver.manage().window().maximize();

		return driver;

	}

	// ===========================================================================================================
	// Get the web driver by browser type and get URL
	public static WebDriver getWebDriver(String sBrowser, String sUrl, boolean maximizeWindow) throws IOException {

		WebDriver driver;

		switch (sBrowser.toLowerCase()) {
		case "firefox":
			System.setProperty("webdriver.firefox.marionette", jarPath + "geckodriver.exe");
			driver = new FirefoxDriver();
			break;

		case "chrome":
			System.setProperty("webdriver.chrome.driver", jarPath + "chromedriver.exe");
			driver = new ChromeDriver();
			break;

		case "ie":
			File file = new File(jarPath + "IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			driver = new InternetExplorerDriver(capabilities);
			break;

		default:
			System.setProperty("webdriver.firefox.marionette", jarPath + "\\geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		}

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get(sUrl);
		if (maximizeWindow)
			driver.manage().window().maximize();

		return driver;

	}

	// For Selenium Grid
	public static WebDriver getDriverInstance(String baseUrl, String nodeURL) throws MalformedURLException {

		WebDriver driver = null;
		DesiredCapabilities caps = new DesiredCapabilities();

		caps = DesiredCapabilities.chrome();
		caps.setCapability("version", "67.0");

		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);

		caps.setCapability(ChromeOptions.CAPABILITY, options);

		driver = new RemoteWebDriver(new URL(nodeURL), caps);

		driver.manage().window().maximize();

		driver.get(baseUrl);
		return driver;
	}

	public String getDataFolder() {
		return dataFolder;
	}


	// Quite driver
	public static void tearDown(WebDriver driver) throws InterruptedException, IOException {

		log.info("Going to tear down driver");
		// driver.manage().deleteAllCookies();
		driver.quit();

	}

	// Quite driver
	public static void closeDriver(WebDriver driver) throws InterruptedException, IOException {

		log.info("Run the batch to copy the testng-results.xml to the jenkins workspace");
		driver.manage().deleteAllCookies();
		// Runtime.getRuntime().exec(workingDir +
		// "\\resources\\SetXML4ALM.bat");
		Sleep(2);
		driver.close();

	}

	
	// Print screen and add to report
	public static File printScreen(WebDriver driver, String title) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy
		// somewhere

		String workingDir = GeneralUtility.getUserDir(System.getProperty("user.dir"));
		InetAddress address = InetAddress.getLocalHost();

		String hostIP = address.getHostAddress();

		workingDir = workingDir.toLowerCase().replaceAll("c:", hostIP);
		workingDir = workingDir.toLowerCase().replaceAll("d:", hostIP);
		workingDir = "\\" + "\\" + workingDir;
		String TimeStamp = TimeStamp();
		String SnapShotLocation = workingDir + "\\" + "images" + "\\" + title + "_" + TimeStamp + ".png";

		File snapLocation = new File(SnapShotLocation);
		FileUtils.copyFile(scrFile, snapLocation);

		String reportingIP = System.getProperty("reporting.ip");
		if (reportingIP != null) {
			File snapLocationForReport = new File(SnapShotLocation.replace(hostIP, reportingIP));
			return snapLocationForReport;
		}

		// reportLogScreenshot(snapLocation);
		log.info("Snap location: " + snapLocation);
		return snapLocation;
	}

	public static boolean copyFileFromTemplate(String sSource, String sTarget) throws IOException {
		try {
			Path source = Paths.get(sSource);
			Path target = Paths.get(sTarget);
			Files.copy(source, target, REPLACE_EXISTING);
			return true;
		} catch (Exception ex) {
			log.info("Some problem with copy file to new direction..." + sSource + " to: " + sTarget);
			return false;
		}
	}

	public static void sendReporter(String msg) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Reporter.log(timestamp + " : " + msg + "<br>");
	}

	public static void reportLogScreenshot(File file) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");

		String absolute = file.getAbsolutePath();
		int beginIndex = absolute.indexOf(".");
		String relative = absolute.substring(beginIndex).replace(".\\", "");
		String screenShot = relative.replace('\\', '/');

		log.info("add image to report line");

		Reporter.log("<a href=\"" + screenShot + "\"><p align=\"left\">Error screenshot at " + new Date() + "</p>");
		Reporter.log("<p><img width=\"1024\" src=\"" + file.getAbsoluteFile() + "\" alt=\"screenshot at " + new Date()
				+ "\"/></p></a><br />");
	}
	
	public static void reportLogScreenshot(File file, String status) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");

		String absolute = file.getAbsolutePath();
		int beginIndex = absolute.indexOf(".");
		String relative = absolute.substring(beginIndex).replace(".\\", "");
		String screenShot = relative.replace('\\', '/');

		log.info("add image to report line");
		if(status.toLowerCase().contains("pass"))
			Reporter.log("<a href=\"" + screenShot + "\"><p align=\"left\">successfully message to screenshot at " + new Date() + "</p>");
		else
			Reporter.log("<a href=\"" + screenShot + "\"><p align=\"left\">Error screenshot at " + new Date() + "</p>");
		Reporter.log("<p><img width=\"1024\" src=\"" + file.getAbsoluteFile() + "\" alt=\"screenshot at " + new Date()
				+ "\"/></p></a><br />");
	}

	public static String TimeStamp() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		String formattedDate = sdf.format(date);
		String replaced = formattedDate.replaceAll(Pattern.quote("/"), "");
		replaced = replaced.replaceAll(Pattern.quote(" "), "_");
		replaced = replaced.replaceAll(Pattern.quote(":"), "");
		log.info("TimeStamp was created: " + replaced);
		return replaced;
	}

	public static String getUserDir(String currentDir) {

		if (currentDir.contains("src")) { // running from batch{
			currentDir = currentDir.replace("\\src", "");
		}
		// log.info("system.dir is: " + currentDir);
		return currentDir;
	}

	public static String getWorkspaceDrive(String currentDir) {

		String currentDrive = "";
		if (currentDir.contains("D:")) { // running from batch{
			currentDrive = "D:\\";
		}

		if (currentDir.contains("C:")) { // running from batch{
			currentDrive = "C:\\";
		}
		// log.info("system.dir is: " + currentDir);

		return currentDrive;
	}

	public static void Sleep(int time) {
		try {
			if (time < 300)
				Thread.sleep(time * 1000);
			else
				Thread.sleep(time);
		} catch (Exception e) { // Sample for general exeption
			e.printStackTrace();
		}
	}

	public static void Sleep() {
		try {
			Thread.sleep(1);
		} catch (Exception e) { // Sample for general exeption
			e.printStackTrace();
		}
	}

	// Send mail
	public static void SendMail() throws IOException, InterruptedException {

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		String formattedDate = sdf.format(date);

		WebDriver driver = getWebDriver("chrome", "https://mail.google.com/");
		driver.findElement(By.xpath("//*[@id='identifierId']")).sendKeys("hagai1973@gmail.com");
		driver.findElement(By.cssSelector("#identifierNext > content > span")).click();
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("password"))))
				.sendKeys("Boaz1970");
		driver.findElement(By.cssSelector("#passwordNext > content > span")).click();

		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(".//div[text()='COMPOSE']"))))
				.click();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(".//*[@aria-label='To']"))))
				.sendKeys("Hagai.Tregerman@hot.net.il");
		driver.findElement(By.xpath(".//*[@placeholder='Subject']"))
				.sendKeys(formattedDate + ": Automation Report Was Updated After Exeuction Selenium TestSet");
		driver.findElement(By.xpath(".//*[@placeholder='Subject']")).sendKeys(Keys.TAB);
		Actions action = new Actions(driver);
		action.sendKeys("Review updated Automation Report: at: \\\\YKM-MIS-006\\test-output\\html\\index.html")
				.perform();

		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(".//*[text()='Send']"))))
				.click();

		Thread.sleep(4000);
		driver.quit();

	}

	// Create random valid ID number Generate ID
	public static String getValidID() {

		int n = 9999999 + (int) (Math.random() * 90000000);
		String n1 = Integer.toString(n);
		// log.info("random: " + n1);

		int checkDigit = getCheckDigit(n1);
		// log.info("required id number: " + n1+checkDigit );
		String n2 = n1 + checkDigit;
		log.info("the number id is: " + n2);
		return n2;

	}

	//Get control digit in ID
	public static int getCheckDigit(String randomID) {
		int[] iArray = { 1, 2, 1, 2, 1, 2, 1, 2 };
		int result, total = 0;

		String[] numbers = randomID.split("");
		for (int i = 0; i < iArray.length; i++) {

			result = Integer.parseInt(numbers[i]) * iArray[i];
			// log.info("the number " + result);
			if (Integer.valueOf(result).toString().length() > 1) {
				// log.info("NEED To SUM: " + result);
				int sum = 0;
				while (result > 0) {
					sum = sum + result % 10;
					result = result / 10;
				}
				// log.info("sum of the number is: "+ sum);
				result = sum;
			}

			total = total + result;
		}
		// log.info("the total is: " + total);
		// log.info("the required result is: " + (10-(total %10 )));
		int getCheckDigit = (10 - (total % 10));
		if (getCheckDigit == 10)
			getCheckDigit = 0;
		return getCheckDigit;
	}

	//Find replace in a file
	public static void editFile(String filePath, String txtToFind, String txtToChange) throws IOException {
		Path path = Paths.get(filePath);
		Charset charset = StandardCharsets.UTF_8;

		String content = new String(Files.readAllBytes(path), charset);
		content = content.replaceAll(txtToFind, txtToChange);
		Files.write(path, content.getBytes(charset));
		log.info("Complite update file");
	}

	// Scan value in input table
	public static boolean scanTableColumn(String[][] tabArray, int ColForVerify, String ToCheck) {
		boolean bScan = false;
		log.info("length of the tabArray: " + tabArray.length);
		for (int i = 0; i < tabArray.length; i++) {
			log.info("Result for Row: " + i);
			for (int j = 0; j < tabArray[i].length; j++) {
				// System.out.print("Result for col: " + j + ": ");
				// System.out.print(tabArray[i][j] + ", ");
				if (j == ColForVerify) {
					if (ToCheck.contains(tabArray[i][j]))
						bScan = true;
					else {
						bScan = false;
						log.fatal("Actual: " + tabArray[i][j] + " Expected: " + ToCheck);
						break;
					}

				}

			}
			if (bScan == false)
				break;
			log.info("-----------------");
		}

		log.info("Scanning row " + ColForVerify + " for " + ToCheck + " results: " + bScan);
		return bScan;
	}

	// Scan value in input table
	public static boolean scanTableColumn(String[][] tabArray, int ColForVerify, String[] ToCheck) {
		boolean bScan = false;
		log.info("length of the tabArray: " + tabArray.length);
		for (int i = 0; i < tabArray.length; i++) {
			log.info("Result for Row: " + i);
			for (int j = 0; j < tabArray[i].length; j++) {
				// System.out.print("Result for col: " + j + ": ");
				// System.out.print(tabArray[i][j] + ", ");
				if (j == ColForVerify) {
					if (ToCheck[i].contains(tabArray[i][j]))
						bScan = true;
					else {
						bScan = false;
						log.fatal("Actual: " + tabArray[i][j] + " Expected: " + ToCheck);
						break;
					}

				}

			}
			if (bScan == false)
				break;
			log.info("-----------------");
		}

		log.info("Scanning row " + ColForVerify + " for " + ToCheck + " results: " + bScan);
		return bScan;
	}

	public static boolean scanTableColumn(String[][] tabArray, int ColForVerify, String ToCheck, boolean Not) {
		boolean bScan = false;
		log.info("length of the tabArray: " + tabArray.length);
		for (int i = 0; i < tabArray.length; i++) {
			log.info("Result for Row: " + i);
			for (int j = 0; j < tabArray[i].length; j++) {
				// System.out.print("Result for col: " + j + ": ");
				// System.out.print(tabArray[i][j] + ", ");
				if (Not) {
					if (j == ColForVerify) {
						if (ToCheck.toLowerCase().contains(tabArray[i][j].toLowerCase())) {
							bScan = false;
							log.fatal("Actual: " + tabArray[i][j] + " Expected: " + ToCheck);
							break;

						}

						else {
							bScan = true;
						}

					}

				}
			}
			log.info("-----------------");
		}

		log.info("Scanning row " + ColForVerify + " for " + ToCheck + " results: " + bScan);
		return bScan;
	}

	// Print to console all the frames in page
	public static boolean printAllIframes(WebDriver driver) throws InterruptedException {

		boolean bResult = false;
		driver.switchTo().defaultContent();

		List<WebElement> iframesList = driver.findElements(By.tagName("iframe"));
		int listSize = iframesList.size();

		if (listSize == 3) {
			Thread.sleep(1000);
			log.info("iframes " + listSize);
			for (WebElement ifr : iframesList) {
				String frameid = ifr.getAttribute("id");
				log.info("frame number: " + frameid);
				bResult = true;
			}
			iframesList = driver.findElements(By.tagName("iframe"));
			listSize = iframesList.size();
		}
		if (listSize == 4) {
			log.info("iframes " + listSize);
			for (WebElement ifr : iframesList) {
				String frameid = ifr.getAttribute("id");
				log.info("frame number: " + frameid);
				bResult = true;
			}
			driver.switchTo().frame(iframesList.get(3));
		}
		return bResult;
	}

	// random string
	public static String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		log.info("Return random En string " + sb.toString());
		return sb.toString();
	}

	

	// Get random number: as String
	public static String randomNumber(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(NU.charAt(rnd.nextInt(NU.length())));
		log.info("Return random number " + sb.toString());
		return sb.toString();
	}

	// Get random number: as Int
	public static int intRrandomNumber(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(NU.charAt(rnd.nextInt(NU.length())));
		log.info("Return random number " + sb.toString());
		String number = sb.toString();
		int numberInt = Integer.parseInt(number);
		return numberInt;
	}

	public static int intRrandomMaxNumber(int max) {
		int random = (int) (Math.random() * max + 1);
		log.info("Return random number " + random);
		return random;
	}

	//Get random long number
	public static long randomLongNumber(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sb.append(NU.charAt(rnd.nextInt(NU.length())));
		}

		log.info("Return random number " + sb.toString());
		return Long.valueOf(sb.toString());
	}

	// Random phone prefix
	public static String randomPhonePrefix() {
		int rand = rnd.nextInt(PP.length);
		log.info("Return random phone prefix string " + PP[rand]);
		return PP[rand];

	}



	//Generate random mail
	public static String randomMail() {
		String mailPrefix = randomString(8);
		String mailSuffix = "@notRealMail.com";
		log.info("Return random mail " + mailPrefix + mailSuffix);
		return mailPrefix + mailSuffix;

	}

	// Get current date by pattern
	public static String getDate(String sPattern) {
		// example String sPattern = "dd/MM/yyyy";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(sPattern);
		LocalDate localDate = LocalDate.now();
		String sCurrentDate = "";
		try {
			sCurrentDate = dtf.format(localDate);
		} catch (Exception ex) {
			dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			sCurrentDate = dtf.format(localDate);
		}

		log.info("Return current date: " + sCurrentDate); // 2016/11/16
		return sCurrentDate;

	}

	//get date and time format dd-MM-yyyy HH:mm:ss
	public static String getDateWithTime() {
		// example String sPattern = "dd/MM/yyyy";
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String stringDate = dateFormat.format(new Date());

		log.info("Return current date: " + stringDate); // 2016/11/16
		return stringDate;

	}

	//Get current year
	public static String getYear() {
		Calendar c = Calendar.getInstance();
		String year = Integer.toString(c.get(Calendar.YEAR));
		return year;

	}

	//Get current month
	public static String getMonth() {
		Calendar c = Calendar.getInstance();
		String month = Integer.toString(c.get(Calendar.MONTH) + 1);
		return month;

	}
	
	//Get month with option to receive months in past according to int less
	public static String getMonth(int less) {
		Calendar c = Calendar.getInstance();
		int mm = (c.get(Calendar.MONTH) + 1) - less;
		String month = Integer.toString(mm);
		if (month.length() < 2)
			month = "0" + month;
		return month;

	}

	//Get current day of month
	public static String getDay() {
		Calendar c = Calendar.getInstance();
		String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
		return day;

	}

	//Get date according to format
	public static String getDateInSpecificFormat(int less, String format) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -less);
		Date result = cal.getTime();

		// SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		// SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMM");

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String string = dateFormat.format(result);

		return string;
	}

	// yyyyy-mm-dd
	public static String convertDateFormat(String sDate, String currentFormat, String newFormat) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(currentFormat);
		Date date = dateFormat.parse(sDate);
		SimpleDateFormat dt1 = new SimpleDateFormat(newFormat);
		String newDate = dt1.format(date);
		return newDate;
	}

	//convert the time to required format
	public static String convertTimeFormat(String sTime, String currentFormat, String newFormat) throws ParseException {

		DateFormat dt = new SimpleDateFormat(currentFormat);
		Date date = dt.parse(sTime);
		DateFormat dt1 = new SimpleDateFormat(newFormat);
		String formatted = dt1.format(date);
		if (formatted.charAt(0) == '0')
			formatted = formatted.substring(1);
		return formatted;
	}

	public static void main(String[] args) throws ParseException {
		String newDate1 = convertDateFormat("30/05/2018", "dd/mm/yyyy", "yyyy-mm-dd");
		System.out.println(newDate1);
		String newTime1 = convertTimeFormat("11:30", "HH:mm", "hhmm");
		System.out.println(newTime1);
	}

	//Getting string from time stamp
	public static String getTimeStampString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String string = dateFormat.format(new Date());
		string = string.replaceAll(" ", "_");
		string = string.replaceAll(":", "_");
		// System.out.println(string);
		return string;
	}

	//create new file in OS
	public static void createNewFile(String path) throws IOException {
		File file = new File(path);

		// Create the file
		if (file.createNewFile()) {
			log.info("File is created: " + path);
		} else {
			log.info("File already exists.");
		}
	}

	//Generate random phone number
	public static String generateRandomPhoneNum(String vendor_code) {
		// generate random Telephone num
		int n = (int) ((Math.random() * 9000000) + 1000000);
		String n1 = Integer.toString(n);
		log.info("random phone num generated " + n1);
		return vendor_code + n1;
	}

	public static String returnUniqTransactionID() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		String formattedDate = sdf.format(date);
		String replaced = formattedDate.replaceAll(Pattern.quote("/"), "");
		replaced = replaced.replaceAll(Pattern.quote(" "), "_");
		replaced = replaced.replaceAll(Pattern.quote(":"), "");

		return replaced;

		// vars.put("dummy_timeStamp",${__threadNum}+"_"+replaced);

	}

	//add days to current date
	public static String addDays(String sPattern, int days) {
		Date d = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, days);
		d.setTime(c.getTime().getTime());
		SimpleDateFormat sdf = new SimpleDateFormat(sPattern);
		String formattedDate = sdf.format(d);
		return formattedDate;
	}

	public static void endOfTestDelimiter() {
		

		log.info("\n");
		log.info("**************** END OF TEST ********************\n\n");

	}

	public static void endOfTestDelimiter(String message) {
	
		log.info("\n");
		log.info("**************** " + message.toUpperCase() + " ********************\n\n");

	}
	
	public static void startOfTestDelimiter(String message) {
		
		log.info("\n");
		log.info("**************** " + message.toUpperCase() + " ********************\n\n");

	}

	// extract a sub-string from a string using Regexp
	public static String extractRegExpFromString(String LB, String RB, String data, int count_num) {
		Pattern pattern = Pattern.compile(LB + "(.*?)" + RB);
		Matcher matcher = pattern.matcher(data);
		String ans;
		int count = 1;
		while (matcher.find()) {
			ans = matcher.group(1);
			if (count == count_num)
				return ans;

			count++;

		}
		return null;
	}

	public static String getEffectiveDateShort() { // yyyy-MM-ddThh:mm:ss.090+02:00

		Date date1 = new Date();
		Date date2 = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate1 = sdf1.format(date1);

		SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss");
		String formattedDate2 = sdf2.format(date2);

		String eff_date = formattedDate1 + "T" + formattedDate2 + ".090+02:00";

		return eff_date;
	}

	public static String getEffectiveDate() { // yyyy-MM-ddThh:mm:ss.00090+02:00

		Date date1 = new Date();
		Date date2 = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate1 = sdf1.format(date1);

		SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss");
		String formattedDate2 = sdf2.format(date2);

		String eff_date = formattedDate1 + "T" + formattedDate2 + ".00090+02:00";

		return eff_date;
	}

	public static String getEffectiveDateInXDays(int days) { // yyyy-MM-ddThh:mm:ss.00090+02:00

		Date date2 = new Date();

		String formattedDate1 = addDays("yyyy-MM-dd", 30);

		SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss");
		String formattedDate2 = sdf2.format(date2);

		String eff_date = formattedDate1 + "T" + formattedDate2 + ".00090+02:00";

		return eff_date;
	}
}