package com.italam.pages;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.italam.utility.GeneralUtility;

public class NextHomePage extends BasePage {

	static final Logger log = LogManager.getLogger(NextHomePage.class.getName());

	public NextHomePage(WebDriver driver) throws IOException {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	//WebElement progressSection, iDentifyType;

	@FindBy(css = "button.pushcrew-chrome-style-notification-btn.pushcrew-btn-close")
	WebElement closeOffer;
	
	@FindBy(xpath = "//ul[@id=\"headerMenu\"]//a[@ui-sref='packages']")
	WebElement ourPackeges;
	
	////ul[@id="headerMenu"]//a[@ui-sref='packages']

	public boolean closeUpdateOffer() throws InterruptedException, IOException {
		boolean closeUpdateOffer = false;
		//GeneralUtility.Sleep(1);

		log.info("Begin the flow of close offer");
		try {
			if (IsObjectExist(closeOffer))
				click(closeOffer);
			closeUpdateOffer = true;
		} catch (TimeoutException te) {
			log.info("Exception received: ");
			closeUpdateOffer = true;
			//return false;
		} catch (Exception e) {
			log.info("Exception received: ");
			closeUpdateOffer = true;
			//return false;
		}

		return closeUpdateOffer;
	}

	
	public boolean goTo(String Location) throws InterruptedException, IOException {
		boolean bGoTo = false;
		GeneralUtility.Sleep(1);

		log.info("Begin the flow of go to");
		try {
		
			switch (Location.toLowerCase()){
				case "packeges":
				{
					click(ourPackeges);
					log.info("Go to packegses page");
					GeneralUtility.sendReporter("Go to packegses page");
					bGoTo = true;
					break;
				}
				default:
				{
					log.info("Could not find the location specify, need to add case to switch...");
					bGoTo = true;
				}
			}
			
			
		} catch (TimeoutException te) {
			log.fatal("Exception received: ");
			te.printStackTrace();
			return false;
		} catch (Exception e) {
			log.fatal("Exception received: ");
			e.printStackTrace();
			return false;
		}

		return bGoTo;
	}

}
