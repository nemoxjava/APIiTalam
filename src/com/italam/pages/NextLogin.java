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

public class NextLogin extends BasePage {

	static final Logger log = LogManager.getLogger(NextLogin.class.getName());

	
	@FindBy(id = "userid")
	WebElement iLogin;

	@FindBy(id = "pwd")
	WebElement iPass;
	
	
	@FindBy(name = "Submit")
	WebElement bSubmit;
	
	
	
	public NextLogin(WebDriver driver) throws IOException {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public boolean login(String sUserId, String sPassword) throws InterruptedException,
			IOException {
		boolean bLogin = false;

		// Begin flow of the login
		log.info("Begin the flow of the login");
		try {
			fillText(iLogin, sUserId);
			//iLogin.sendKeys("025204116");
			fillText(iPass, sPassword);
			GeneralUtility.Sleep(1);
			clickJS(bSubmit);
			bLogin = true;
			//bLogin = IsObjectExist(By.id("pthdr2logimg"));
		} catch (TimeoutException te) {
			log.fatal("Exception received: ");
			te.printStackTrace();
			return false;
		}
		catch (Exception e) {
			log.fatal("Exception received: ");
			e.printStackTrace();
			return false;
		}

		GeneralUtility.Sleep(1);

		if (bLogin) {
			GeneralUtility.Sleep(2);
			bLogin = driver.getCurrentUrl().toLowerCase().contains("tab=default");
		}
		else
			return false;
		
		return bLogin;
	}

}
