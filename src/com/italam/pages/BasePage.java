package com.italam.pages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.italam.utility.GeneralUtility;

public class BasePage {

	static final Logger log = LogManager.getLogger(BasePage.class.getName());

	// For adding image -------
	private static final String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";

	public WebDriver driver;
	JavascriptExecutor js;
	WebDriverWait wait;
	Actions action;
	private static String highlight = "false";

	// Constructor for driver
	public BasePage(WebDriver driver) throws IOException {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, 15);
		System.setProperty(ESCAPE_PROPERTY, "false");
		action = new Actions(driver);
	}

	public void moveToElement(WebElement we) {
		action = new Actions(driver);
		action.moveToElement(we).build().perform();

	}

	public void click(WebElement el) throws Exception {
		wait.until(ExpectedConditions.elementToBeClickable(el));
		highlight(el);
		el.click();
	}

	public void click(By by) throws Exception {
		WebElement el = driver.findElement(by);
		wait.until(ExpectedConditions.elementToBeClickable(el));
		highlight(el);
		el.click();
	}

	public void click(By by, boolean focus) throws Exception {

		WebElement el = driver.findElement(by);
		if (focus) {

		}

		wait.until(ExpectedConditions.elementToBeClickable(el));
		highlight(el);
		el.click();
	}

	public void submit(WebElement el) throws Exception {
		wait.until(ExpectedConditions.elementToBeClickable(el));
		highlight(el);
		el.submit();
	}

	public void submit(By by) throws Exception {
		WebElement el = driver.findElement(by);
		wait.until(ExpectedConditions.elementToBeClickable(el));
		highlight(el);
		el.submit();
	}

	// focus element
	public void FocusElement(String elementid) {
		js.executeScript("document.getElementById('" + elementid + "').focus();");
	}

	
	// focus element
		public void FocusElement(By by) {
			String elementid = driver.findElement(by).getAttribute("id");
			js.executeScript("document.getElementById('" + elementid + "').focus();");
		}
	public void FocusElement(WebElement element) {
		action = new Actions(driver);
		action.sendKeys(Keys.PAGE_DOWN).build().perform();
		action.moveToElement(element).build().perform();
	}

	// focus Window
	public void FocusWindow() {
		this.js = (JavascriptExecutor) driver;
		((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	public void sendKeys(Keys tab) {
		action = new Actions(driver);
		action.sendKeys(tab).build().perform();
		log.info("end send key");
	}

	// Return Element after wait for be clickable
	public WebElement returnSlowElement(By by) throws Exception {
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(by)));
		return driver.findElement(by);

	}

	// Return Element after wait for be clickable
	public WebElement returnSlowElement(By by, boolean Wait) throws Exception {

		if (Wait)
			wait.until(ExpectedConditions.elementToBeClickable(by));
		highlight(driver.findElement(by));
		return driver.findElement(by);

	}

	// Return Element after wait for be clickable
	public List<WebElement> returnSlowElements(By by) throws Exception {
//		GeneralUtility.Sleep(3);
		//wait.until(ExpectedConditions.elementToBeClickable(by));
		// highlight(driver.findElement(by));
		return driver.findElements(by);
	}

	// Refresh driver page
	public void refresh() throws Exception {

		driver.navigate().refresh();
		action.sendKeys(Keys.ESCAPE);
	}

	// Return true if WebElement exit in driver page
	public boolean IsObjectExist(WebElement el) throws Exception {

		wait = new WebDriverWait(driver, 3);
		wait.until(ExpectedConditions.elementToBeClickable(el));
		// el.click();

		highlight(el);
		return true;

	}

	// Return true if web element exist, find using by locator
	public boolean IsObjectExist(By by) throws Exception {
		//highlight(driver.findElement(by));
		//wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(by)));
		List<WebElement> appearances = returnSlowElements(by);
		if (appearances.size()>0)		
		return true;
		else
			return false;

	}
	
	// Return true if web element exist, find using by locator
		public int countHowManyApperancesOfWebElement(By by) throws Exception {

			List<WebElement> appearances = driver.findElements(by);
			return appearances.size();

		}

	// Return true if web element exist, find using by locator
	public boolean IsObjectExist(By by, int timeToWait) throws Exception {

		wait = new WebDriverWait(driver, timeToWait);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(by)));
		highlight(driver.findElement(by));
		return true;

	}

	// Clear and send keys to web element
	public void fillText(WebElement el, String text) throws Exception {
		 GeneralUtility.Sleep(1);
		wait.until(ExpectedConditions.visibilityOf(el));
		highlight(el);
		//el.click();
		el.clear();
		//GeneralUtility.Sleep(1);
		el.sendKeys(text);
	}

	// Clear and send keys to web element
	public void fillText(By by, String text) throws Exception {
		WebElement el = returnSlowElement(by);
		el.clear();
		specifichighlight(el);
		click(el);
		GeneralUtility.Sleep(1);
		el.sendKeys(text);
	}
	
	// Clear and send keys to web element
		public void fillTextIe(By by, String text) throws Exception {
			WebElement el = returnSlowElement(by);
			GeneralUtility.Sleep(1);
			Actions act = new Actions(driver);
			act.click(el).sendKeys(text).sendKeys(Keys.TAB).build().perform();
		}

	
	public void fillTextNoWait(By by, String text) throws Exception {
		WebElement el = driver.findElement(by);

		specifichighlight(el);
		//click(el);
		el.click();
		GeneralUtility.Sleep(1);
		el.sendKeys(text);
	}

	
	public void ClearAndfillText(By by, String text) throws Exception {
		WebElement el = returnSlowElement(by);

		highlight(el);
		//click(el);
		el.clear();
		GeneralUtility.Sleep(1);
		el.sendKeys(text);
	}

	public void clearText(WebElement el) throws Exception {
		// GeneralUtility.Sleep(1);
		wait.until(ExpectedConditions.visibilityOf(el));
		highlight(el);
		el.clear();
		GeneralUtility.Sleep(1);
	}

	// Select drop down option (dropdown) by text of the option item
	public boolean selectText(WebElement weSelect, String sText) throws Exception {
		boolean boSelectText = false;

		highlight(weSelect);
		Select newSelect = new Select(weSelect);
		GeneralUtility.Sleep(2);
		newSelect.selectByVisibleText(sText);
		boSelectText = true;

		return boSelectText;

	}

	public boolean selectText(By by, String sText) throws Exception {
		boolean boSelectText = false;
		WebElement weSelect = returnSlowElement(by);
		GeneralUtility.Sleep(1);
		highlight(weSelect);
		Select newSelect = new Select(weSelect);
		GeneralUtility.Sleep(1);
		newSelect.selectByVisibleText(sText);
		boSelectText = true;

		return boSelectText;

	}

	// Select drop down option (dropdown) by text of the option item
	public boolean selectTextBy(WebElement weSelect, String typeOfSelect, String sText) throws Exception {
		boolean boSelectText = false;

		highlight(weSelect);
		Select newSelect = new Select(weSelect);
		GeneralUtility.Sleep(1);

		switch (typeOfSelect.toLowerCase()) {
		case "selectbyvisibletext":
			newSelect.selectByVisibleText(sText);
			break;
		case "selectbyvalue":
			newSelect.selectByValue(sText);
			break;

		case "index":
			newSelect.selectByIndex(Integer.parseInt(sText));
			break;
		default:
			newSelect.selectByVisibleText(sText);
			break;
		}

		boSelectText = true;

		return boSelectText;

	}

	public boolean selectIndex(WebElement weSelect, int iSelection) throws Exception {
		boolean boSelectText = false;

		highlight(weSelect);
		Select newSelect = new Select(weSelect);
		GeneralUtility.Sleep(1);
		newSelect.selectByIndex(iSelection);
		;
		boSelectText = true;

		return boSelectText;

	}

	public boolean selectIndex(By by, int iSelection) throws Exception {
		boolean boSelectText = false;
		WebElement weSelect = driver.findElement(by);

		highlight(weSelect);
		Select newSelect = new Select(weSelect);
		GeneralUtility.Sleep(1);
		newSelect.selectByIndex(iSelection);
		;
		boSelectText = true;

		return boSelectText;

	}

	// Select default search item offered after type text
	public void selectTextSpan(WebElement weSelect, WebElement weTypeIn2, String sText) throws Exception {

		// wait.until(ExpectedConditions.visibilityOf(weSelect));
		highlight(weSelect);
		weSelect.click();
		weTypeIn2.sendKeys(sText);
		weTypeIn2.sendKeys(Keys.ENTER);
	}

	// Select default search item offered after type text
	public void selectTextAfterFiltering(WebElement weSelect, String sText) throws Exception {

		// wait.until(ExpectedConditions.visibilityOf(weSelect));
		highlight(weSelect);
		GeneralUtility.Sleep(1);
		weSelect.click();
		GeneralUtility.Sleep(1);
		action.sendKeys(sText).build().perform();
		GeneralUtility.Sleep(1);
		action.sendKeys(Keys.ENTER).build().perform();
		GeneralUtility.Sleep(1);
		action.sendKeys(Keys.TAB).build().perform();
	}

	
	// Select default search item offered after type text
		public void selectTextAfterFiltering(By by, String sText) throws Exception {

			// wait.until(ExpectedConditions.visibilityOf(weSelect));
			WebElement weSelect = returnSlowElement(by);
			highlight(weSelect);
			GeneralUtility.Sleep(1);
			weSelect.click();
			GeneralUtility.Sleep(1);
			action.sendKeys(sText).build().perform();
			GeneralUtility.Sleep(1);
			action.sendKeys(Keys.ENTER).build().perform();
			GeneralUtility.Sleep(1);
			action.sendKeys(Keys.TAB).build().perform();
		}

	// Click on WebElement located by id
	public boolean clickElementById(String id) throws Exception {

		WebElement element = (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id(id)));

		highlight(element);

		wait.until(ExpectedConditions.elementToBeClickable(element));

		click(element);
		// log.info("Clicked element by id " + id);
		return true;

	}

	// Click on web element located by css selector
	public boolean clickElementByCss(String css) throws Exception {

		// WebElement element = driver.findElement(By.id(id));
		WebElement element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector(css)));

		highlight(element);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(element));

		click(element);

		// log.info("Clicked element by CSS " + css);
		return true;

	}

	// Click on element located by xpath
	public boolean clickElementByxPath(String xpath) throws Exception {

		// WebElement element = driver.findElement(By.id(id));
		WebElement element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		highlight(element);
		click(element);
		// log.info("Clicked element by xpath " + xpath);
		return true;

	}

	public boolean actionClick(WebElement element) throws Exception {

		highlight(element);
		action.click(element).build().perform();
		// log.info("Clicked element " );
		return true;

	}

	public boolean actionClick(By by) throws Exception {

		// WebElement element = driver.findElement(By.id(id));
		WebElement element = (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(by));
		highlight(element);
		action.click(element).build().perform();
		// log.info("Clicked element by " + by.toString());
		return true;

	}

	public boolean sendkeysElementById(String id, String text) throws Exception {

		// WebElement element = driver.findElement(By.id(id));
		WebElement element = (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id(id)));

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(element));

		highlight(element);

		element.clear();
		element.sendKeys(text);
		log.info("Send keys: " + text + " to element: " + id);
		return true;

	}

	// Send keys type into field web element located by css selector
	public boolean sendkeysElementByCss(String css, String text) throws Exception {

		// WebElement element = driver.findElement(By.id(id));
		WebElement element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector(css)));

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(element));

		highlight(element);
		element.clear();
		element.sendKeys(text);
		log.info("Send keys: " + text + " to element: " + css);
		return true;

	}

	// Type into element located by xpath locator
	public boolean sendkeysElementByXpath(String xpath, String text) throws Exception {

		WebElement element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(element));

		highlight(element);
		element.clear();
		element.sendKeys(text);
		log.info("Send keys: " + text + " to element: " + xpath);
		return true;

	}

	// int iframe =
	// returnFrameNumberAccordingToElement(By.xpath(".//*[@id='HT_SO_DRV_HT_SHOW_CALENDAR']"));
	public int returnFrameNumberAccordingToElement(By by) {
		driver.switchTo().defaultContent();

		int iCountFrames = driver.findElements(By.tagName("iframe")).size();
		log.info("count of frames " + iCountFrames);

		for (int i = 0; i < iCountFrames; i++) {
			if (i > iCountFrames) {
				break;
			}
			driver.switchTo().frame(i);
			if (driver.findElements(by).size() > 0) {
				log.info("Element found in frame no. " + i);
				// log.info("size of find elements: " + driver.findElements(by).size());
				return i;
			} else {
				driver.switchTo().defaultContent();
				log.info("no found attempt: " + i);
			}
		}

		return 0;
	}

	public int returnFrameNumberAccordingToElement(By by, int startFrame) {
		driver.switchTo().defaultContent();

		int iCountFrames = driver.findElements(By.tagName("iframe")).size();
		log.info("count of frames " + iCountFrames);

		for (int i = startFrame; i < iCountFrames; i++) {
			if (i > 5) {
				break;
			}
			driver.switchTo().frame(i);
			if (driver.findElements(by).size() > 0) {
				log.info("Element found in frame no. " + i);
				// log.info("size of find elements: " + driver.findElements(by).size());
				return i;
			} else {
				driver.switchTo().defaultContent();
				log.info("no found attempt: " + i);
			}
		}
		driver.switchTo().defaultContent();
		return 0;
	}

	// Do submit on element located by id
	public boolean submitElementById(String id) throws Exception {

		WebElement element = (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id(id)));
		highlight(element);
		element.submit();
		return true;

	}

	// Verify return true, if text appear in driver title
	public boolean isTextApperInTitle(String text) {
		String siteTitle = driver.getTitle();
		if (siteTitle.toLowerCase().contains(text))
			return true;
		else
			return false;
	}

	// Verify that the URL was modified after click a link
	public boolean isUrlChanged(String text) throws Exception {
		boolean blChanged = false;
		int iCount = 0;

		String siteUrl = driver.getCurrentUrl();
		while ((!blChanged) && (iCount < 12)) {
			if (siteUrl.toLowerCase().contains(text.toLowerCase())) {
				blChanged = true;
				break;
			} else {
				GeneralUtility.Sleep(1);
				iCount += 1;
				siteUrl = driver.getCurrentUrl();
				log.info(" my hot " + iCount);

			}
		}

		return blChanged;
	}

	// Return true if the URL is as expected
	public boolean isCorrectURL(String URL) {
		String siteURL = driver.getCurrentUrl();
		log.info("page url verification for: " + driver.getCurrentUrl());
		if (siteURL.contains(URL))
			return true;
		else
			return false;
	}

	// Scan page text and return true if found the text searched
	public boolean isTextApperInPage(String text) {
		String siteText = driver.getPageSource();
		if (siteText.contains(text))
			return true;
		else
			return false;
	}

	// Return true if the text exist in the URL content
	public boolean isPageExist(String URL, String text) throws Exception {
		driver.navigate().to(URL);
		GeneralUtility.Sleep(1);

		String siteText = driver.getPageSource();
		if (siteText.toLowerCase().contains(text))
			return true;
		else
			return false;
	}

	// mark web element like highlight by change the borders
	public void highlight(WebElement el) throws Exception {
		if (highlight.equalsIgnoreCase("true")) {
			for (int i = 0; i < 3; i++) {
				js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid yellow;');",
						el);
				Thread.sleep(250);
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", el, "");
				Thread.sleep(250);
			}
		}
	}

	public void highlight(By by) throws Exception {
		if (highlight.equalsIgnoreCase("true")) {
			WebElement el = driver.findElement(by);
			for (int i = 0; i < 3; i++) {
				js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid yellow;');",
						el);
				Thread.sleep(250);
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", el, "");
				Thread.sleep(250);
			}
		}
	}

	public void highlightSiblin(WebElement el) throws Exception {
		if (highlight.equalsIgnoreCase("true")) {
			el = el.findElement(By.xpath("./following-sibling::label"));
			for (int i = 0; i < 3; i++) {
				js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid yellow;');",
						el);
				Thread.sleep(250);
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", el, "");
				Thread.sleep(250);
			}
		}
	}

	public void specifichighlight(WebElement el) throws Exception {
		for (int i = 0; i < 3;) {
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid yellow;');",
					el);
			Thread.sleep(250);
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", el, "");
			Thread.sleep(250);
			break;
		}
	}

	// Scroll using java script js into web element
	public void scrollIntoElement(WebElement el) throws Exception {
		// Scroll Element Into View
		// WebElement element = driver.findElement(By.id("mousehover"));
		js.executeScript("arguments[0].scrollIntoView(true);", el);
		GeneralUtility.Sleep(2);
		js.executeScript("window.scrollBy(0, -190);");

	}

	public void scrollDown(int y) throws Exception {
		// Scroll Element Into View

		js.executeScript("window.scrollBy(0,-200);");

	}

	public void getURL(String sUrl) throws Exception {
		driver.get(sUrl);
		Thread.sleep(3000);
	}

	// Switch to tab by his name and close the tab
	public void SwitchTabandClose(String tabName) throws Exception {
		Set<String> windows = driver.getWindowHandles();
		String mainwindow = driver.getWindowHandle();
		log.info("main window: " + mainwindow);

		for (String handle : windows) {
			driver.switchTo().window(handle);
			log.info("switched to " + driver.getTitle() + " Window");
			String pagetitle = driver.getTitle();
			log.info("page Title:: " + pagetitle);
			if (pagetitle.equalsIgnoreCase(tabName)) {
				// driver.close();
				GeneralUtility.Sleep(1);
				driver.findElement(By.tagName("body")).sendKeys(Keys.CONTROL + "w");
				log.info("Closed the  '" + pagetitle + "' Tab now ...");
				GeneralUtility.Sleep(2);
			}
		}

	}

	// Switch to tab / window by the title of the window
	public void SwitchTabByTitle(String tabName) {
		Set<String> windows = driver.getWindowHandles();
		// String mainwindow = driver.getWindowHandle();

		for (String handle : windows) {
			driver.switchTo().window(handle);
			// log.info("switched to "+ driver.getTitle()+" Window");
			String pagetitle = driver.getTitle();
			if (pagetitle.equalsIgnoreCase(tabName)) {
				// driver.close();
				log.info("Switch to  '" + pagetitle + "' Tab now ...");
				return;
			}
		}
	}

	// Return true if text exist in Web Element
	public boolean isTextAppearInWL(WebElement el, String text) throws Exception {

		highlight(el);
		GeneralUtility.Sleep(1);

		// Assert.assertTrue("Text not found!", bodyText.contains(text));

		String ActualText = el.getText();
		if (ActualText.contains(text)) {
			// log.info(el + ": " + text + " - Text in web element successfully
			// found");
			return true;
		} else {
			log.info("Text in web element was Not found");
			return false;
		}

	}

	
	public boolean isTextAppearInWL(By by, String text) throws Exception {

		highlight(by);
		GeneralUtility.Sleep(1);
		boolean bTextAppearInWL = false;
		
		
		// Assert.assertTrue("Text not found!", bodyText.contains(text));
		WebElement el = driver.findElement(by);
		String ActualText = el.getText();
		if (!ActualText.isEmpty()) {
			if (ActualText.contains(text)) {
				// log.info(el + ": " + text + " - Text in web element successfully
				// found");
				bTextAppearInWL=true;
			} else {
				log.info("Text in web element was Not found");
				bTextAppearInWL=false;
			}
		}
			
		return bTextAppearInWL;
	}

	public boolean verifyTextAppearInWL(WebElement el, String text) throws Exception {

		highlight(el);
		GeneralUtility.Sleep(1);
		String ActualText = el.getText();
		if (ActualText == "" || ActualText.isEmpty())
			ActualText = el.getAttribute("value");
		// Assert.assertTrue("Text not found!", bodyText.contains(text));
		
			if (ActualText.equals(text)) {
				//log.info(text + " - Text in web element successfully found");
				return true;
			} else {
				log.info("Text in web element was Not found");
				return false;
			}
	
	}
	
	public boolean verifyWLisEmpty(WebElement el) throws Exception {

		highlight(el);
		GeneralUtility.Sleep(1);
		String ActualText = el.getText();
		if (ActualText == "" || ActualText.isEmpty())
			ActualText = el.getAttribute("value");
		// Assert.assertTrue("Text not found!", bodyText.contains(text));
		
			if (ActualText.isEmpty()) {
				//log.info(text + " - Text in web element successfully found");
				return true;
			} else {
				log.info("Text in web element was Not found");
				return false;
			}
	
	}
	public boolean verifyWLisEmpty(By by) throws Exception {

		highlight(by);
		WebElement el = driver.findElement(by);
		GeneralUtility.Sleep(1);
		String ActualText = el.getText();
		if (ActualText == "" || ActualText.isEmpty())
			ActualText = el.getAttribute("value");
		// Assert.assertTrue("Text not found!", bodyText.contains(text));
		
			if (ActualText.isEmpty()) {
				//log.info(text + " - Text in web element successfully found");
				return true;
			} else {
				log.info("Text in web element was Not found");
				return false;
			}
	
	}

	public boolean verifyWLContainsText(By by, String text) throws Exception {

		WebElement el = driver.findElement(by);
		highlight(el);
		GeneralUtility.Sleep(1);
		String ActualText = el.getText();
		if (ActualText == "" || ActualText.isEmpty())
			ActualText = el.getAttribute("value");
		// Assert.assertTrue("Text not found!", bodyText.contains(text));
	
			if (ActualText.contains(text)) {
				log.info(text + " - Text in web element successfully found");
				return true;
			} else {
				log.info("Text in web element was Not found");
				return false;
			}
	
	}

	public boolean verifyTextAppearInWL(WebElement el, String text, boolean ignorCase) throws Exception {

		highlight(el);
		GeneralUtility.Sleep(1);
		String ActualText = el.getText();
		if (ActualText == "" || ActualText.isEmpty())
			ActualText = el.getAttribute("value");
		// Assert.assertTrue("Text not found!", bodyText.contains(text));
	
			if (ignorCase) {
				if (ActualText.toLowerCase().equals(text.toLowerCase())) {
					// log.info(el + ": " + text + " - Text in web element successfully
					// found");
					return true;
				} else {
					log.info("Text in web element was Not found");
					return false;
				}
			}

		return false;
	}

	public String getTextFromElementInWL(WebElement el) throws Exception {
		// WebElement el = driver.findElement(by);
		highlight(el);
		GeneralUtility.Sleep(1);
		String ActualText = el.getText();
		if (ActualText == "" || ActualText.isEmpty())
			ActualText = el.getAttribute("value");
		return ActualText;
	}

	public String getTextFromElementInWL(By byVar) throws Exception {
		WebElement el = driver.findElement(byVar);
		String actualText;
		highlight(el);
		GeneralUtility.Sleep(1);
		if (el.getTagName().equals("select"))
			actualText = el.findElement(By.xpath("./option[@selected]")).getText();
		else {
			actualText = el.getText();
			if (actualText == "" || actualText.isEmpty())
				actualText = el.getAttribute("value");
		}
		return actualText;
	}

	// Verify the text not exist in getText of web element
	public boolean VerifyTextNotAppearInWL(WebElement el, String text) throws Exception {

		String ActualText = el.getText();
		// Assert.assertTrue("Text not found!", bodyText.contains(text));
		
			if (ActualText.contains(text)) {
				log.info(el + ": " + text + " - Text in web element unsuccessfully found");
				return false;
			} else {
				log.info("text in web element successfully Not found");
				return true;
			}
		
	
	}

	// Write text to external file in the file system
	public void writeTextToTargetFile(String input, List<WebElement> List) throws IOException {
		// Create File In D: Driver.
		String TestFile = input; // "D:\\temp.txt";
		File FC = new File(TestFile);// Created object of java File class.

	
			FC.createNewFile();// Create file.

			// Writing In to file.
			// Create Object of java FileWriter and BufferedWriter class.
			FileWriter FW = new FileWriter(TestFile);
			BufferedWriter BW = new BufferedWriter(FW);

			int i = 1;
			for (WebElement results : List) {
				BW.write(i + ": " + results.getText());
				BW.newLine();
				i += 1;
			}
			BW.close();

		
	}

	// Wait for element
	public void WaitForElement(WebElement we, int time) {
	
			WebDriverWait sWait = new WebDriverWait(driver, time);
			sWait.until(ExpectedConditions.elementToBeClickable(we));
	
	}

	public boolean WaitForElement(By by, int time) {
		
			WebDriverWait sWait = new WebDriverWait(driver, time);
			sWait.until(ExpectedConditions.elementToBeClickable(by));
			return true;
	
	}

	public boolean WaitForElement(By by) {
	
			WebDriverWait sWait = new WebDriverWait(driver, 20);
			sWait.until(ExpectedConditions.elementToBeClickable(by));
			return true;
		
	}

	public static void WaitByRequest(int TimeInSeconds) throws Exception {
		if (TimeInSeconds < 1000)
			TimeInSeconds = TimeInSeconds * 1000;
		Thread.sleep(TimeInSeconds);
	}

	// Navigation using navigate
	public void Navigate(String nav) {
		switch (nav.toLowerCase()) {
		case "back":
			driver.navigate().back();
		default:
			driver.navigate().back();

		}
	}

	// //////////////

	public void catchExceptions(ITestResult result) throws Exception {
		log.info("result" + result);
		String methodName = result.getName();
		log.info(methodName);
		log.info("TEST RESULT WAS MADE");

		if (!result.isSuccess()) {

			
				String userDirector = GeneralUtility.getUserDir(System.getProperty("user.dir")) + "/";
				String TimeStamp = GeneralUtility.TimeStamp();

				String failureImageFileName = TimeStamp + ".png";
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(failureImageFileName));

				Reporter.log(
						"<a href=\"" + userDirector + failureImageFileName + "\"><img src=\"file:///" + userDirector
								+ failureImageFileName + "\" alt=\"\"" + "height='100' width='100'/> " + "<br />");
				Reporter.setCurrentTestResult(null);
			
		}

	}

	// try clicking on element with in while
	public boolean retryingFindClick(By by) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 5) {
			try {
				driver.findElement(by).click();
				result = true;
				break;
			} catch (StaleElementReferenceException ser) {
			}
			attempts++;
		}
		return result;
	}

	// Click web element using JavaScritp (js)
	public boolean clickJS(WebElement we) throws Exception {
		boolean result = false;
		int attempts = 0;
		while (attempts < 10) {

			// GeneralUtility.Sleep(1);
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid yellow;');",
					we);
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", we, "");

			// action.click(we).build().perform();
			GeneralUtility.Sleep(1);

			js.executeScript("arguments[0].click();", we);
			// GeneralUtility.Sleep(1);
			// act.sendKeys(Keys.ENTER);
			result = true;
			break;
		}
		attempts++;

		return result;

	}

	public boolean clickJS(By by) throws Exception {
		WebElement we = driver.findElement(by);
		boolean result = false;
		int attempts = 0;
		while (attempts < 10) {
			try {
				// GeneralUtility.Sleep(1);
				js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid yellow;');",
						we);
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", we, "");

				// action.click(we).build().perform();
				GeneralUtility.Sleep(1);

				js.executeScript("arguments[0].click();", we);
				// GeneralUtility.Sleep(1);
				// act.sendKeys(Keys.ENTER);
				result = true;
				break;

			} catch (StaleElementReferenceException ser) {
				
			} catch (Exception e1) {
			}
			attempts++;
		}
		return result;
	}

	// Verify the loading object disappear: getting wait obejct
	public boolean VerifyPageIsReady(By by) throws Exception {
		boolean result = false;
		int attempts = 0;
		GeneralUtility.Sleep(1);
		while (attempts < 30) {
			try {
				if (driver.findElement(by).isDisplayed())
					GeneralUtility.Sleep(2);
				else
					return true;
			} catch (StaleElementReferenceException ser) {
			} catch (NotFoundException nf) {
				return true;
			}

			catch (Exception e1) {
			}
			attempts++;
		}
		if (driver.findElements(by).size() > 0)
			return result;
		else
			GeneralUtility.Sleep(1);
		return true;
	}

	public void closePopupCRM() throws Exception {
		WaitByRequest(1);
		Actions act = new Actions(driver);
		// act.sendKeys(Keys.ENTER).build().perform();
		act.sendKeys(Keys.ENTER).build().perform();
		// act.sendKeys(Keys.ENTER).build().perform();
		WaitByRequest(1);
	}

	public boolean verifySelection(By by, String sSelection) throws Exception {
		boolean boVerifySelection = false;
		WebElement we = driver.findElement(by);
		List<WebElement> lOptions = we.findElements(By.tagName("option"));

		for (WebElement w : lOptions) {
			try {
				if ((w.getText().contains(sSelection)) && (w.getAttribute("selected").contains("true"))) {
					highlight(w);
					log.info("Successfully verify selected value: " + sSelection);
					boVerifySelection = true;
					break;
				}

			} catch (Exception exp) {
				boVerifySelection = false;
				log.info("Selection failed in checking default value or not found selected attribute: " + sSelection);
			}

		}
		if ((!boVerifySelection) && (sSelection == ""))
			boVerifySelection = true;

		return boVerifySelection;
	}
	
	public boolean verifySelection(WebElement we, String sSelection) throws Exception {
		boolean boVerifySelection = false;
		
		List<WebElement> lOptions = we.findElements(By.tagName("option"));

		for (WebElement w : lOptions) {
			try {
				if ((w.getText().contains(sSelection)) && (w.getAttribute("selected").contains("true"))) {
					highlight(w);
					log.info("Successfully verify selected value: " + sSelection);
					boVerifySelection = true;
					break;
				}

			} catch (Exception exp) {
				boVerifySelection = false;
				log.info("Selection failed in checking default value or not found selected attribute: " + sSelection);
			}

		}
		if ((!boVerifySelection) && (sSelection == ""))
			boVerifySelection = true;

		return boVerifySelection;
	}

	public boolean verifyElementSelected(WebElement selectionElement) throws Exception {
		boolean boVerifySelection = false;

		highlightSiblin(selectionElement);

	
			if (selectionElement.isSelected()) {
				boVerifySelection = true;
			}
	
		return boVerifySelection;
	}

	public boolean verifyElementSelected(WebElement selectionElement, boolean selected) throws Exception {
		boolean boVerifySelection = false;

		highlightSiblin(selectionElement);

		
			if (selected) {
				if (selectionElement.isSelected()) {
					boVerifySelection = true;
				}
			} else {
				if (!selectionElement.isSelected()) {
					boVerifySelection = true;
				}
			}

		

		return boVerifySelection;
	}

	public boolean isRadioButtonCheckedByIndex(WebElement container, int index) throws Exception {
		GeneralUtility.Sleep(1);
		List<WebElement> radioButtonList = container.findElements(By.xpath("//*[@type='radio']"));
		if (radioButtonList.get(index - 1).isSelected()) {
			return true;
		}

		else
			return false;
	}

	public WebDriver switchWindows() {
		String firstGuid = driver.getWindowHandle();
		log.info("First guid: " + driver.getWindowHandle());
		Set<String> allGuids = driver.getWindowHandles();

		for (String tmp : allGuids) {
			if (!tmp.equalsIgnoreCase(firstGuid))
				driver.switchTo().window(tmp);
		}

		log.info("current guid: " + driver.getWindowHandle());
		return driver;

	}

	public boolean isAttributeCorrect(WebElement we, String attribute, String attribute_value) throws Exception {
		
			highlight(we);
			String attValue = we.getAttribute(attribute);
			if (attValue.equals(attribute_value))
				return true;
			else
				return false;
		
	}
	
	public String getTableValue(WebElement table, int tr, int td) throws Exception {
		log.info("Going to scan table and return row number");
		
		String cellValue = table.findElement(By.xpath(".//tr["+tr+"]/td["+td+"]")).getText();

		return cellValue;
	}

	public int getRowNumberByColValue(WebElement table, int colNum, String txtEqual) throws Exception {
		log.info("Going to scan table and return row number");
		int totalRow = table.findElements(By.tagName("tr")).size();

		for (int i = 2; i < totalRow + 1; i++) {
			WebElement we = table.findElement(By.xpath(".//tr[" + i + "]/td[" + colNum + "]"));
			if (txtEqual.equals(getText(we)))
				return i;
			else
				log.info("Not in row: " + (i - 1));
		}

		log.info("Data was not found");

		return 0;
	}

	public String getText(WebElement we) throws Exception {
		highlight(we);
		return we.getText();
	}

	public String getText(By by) throws Exception {
		WebElement we = driver.findElement(by);
		highlight(we);
		return we.getText();
	}

	public void insertandclick(WebElement el, String text) throws Exception {
		// GeneralUtility.Sleep(1);

		
			// wait.until(ExpectedConditions.elementToBeClickable(el));
			highlight(el);
			wait.until(ExpectedConditions.elementToBeClickable(el));
			specifichighlight(el);
			action = new Actions(driver);
			click(el);
			// el.sendKeys(Keys.RETURN);
			action.sendKeys(text).build().perform();
			action.sendKeys(Keys.RETURN).build().perform();

		
	}

	public void insertandclickSpecial(WebElement el, String text) throws Exception {
		// GeneralUtility.Sleep(1);

		
			// wait.until(ExpectedConditions.elementToBeClickable(el));
			highlight(el);
			wait.until(ExpectedConditions.elementToBeClickable(el));
			specifichighlight(el);
			action = new Actions(driver);
			click(el);
			// el.sendKeys(Keys.RETURN);
			clickJS(el);
			el.clear();
			fillText(el, text);
			GeneralUtility.Sleep(1);
			
			clickJS(el);
			action.sendKeys(Keys.BACK_SPACE).build().perform();
			GeneralUtility.Sleep(1);
			action.sendKeys(Keys.ARROW_DOWN).build().perform();
			action.sendKeys(Keys.RETURN).build().perform();

		log.info("");
	}
	
	public void insertandclick(By by, String text) throws Exception {
		GeneralUtility.Sleep(1);

		
			WebElement el = driver.findElement(by);
			wait.until(ExpectedConditions.elementToBeClickable(el));
			highlight(el);
			action = new Actions(driver);
			click(el);
			// el.sendKeys(Keys.RETURN);
			action.sendKeys(text).build().perform();
			action.sendKeys(Keys.RETURN).build().perform();
			// click(el);

		
	}

	public boolean isErrorMsgAppear(By byID) throws Exception {
		boolean boolErrorMsgAppear = false;
		WebElement error_msg;

		GeneralUtility.Sleep(1);
		driver.switchTo().defaultContent();
		error_msg = returnSlowElement(By.className("PSMODALTABLE"));
		if (error_msg.isDisplayed()) {
			boolErrorMsgAppear = true;
			log.info("����� ������ ������...");
			GeneralUtility.sendReporter(" ����� ������ ������ ");
			// action.sendKeys(Keys.ENTER).build().perform();
			clickJS(error_msg.findElement(By.xpath(".//input[@tabindex='1']")));
		} else {
			log.info("����� �� ������ ������...");
			GeneralUtility.sendReporter(" ����� �� ������ ������ ����� �.� ");
		}

		return boolErrorMsgAppear;
	}

	// quit the driver object
	public static void tearDown(WebDriver driver) throws Exception {
		GeneralUtility.Sleep(2);
		driver.quit();
	}

}
