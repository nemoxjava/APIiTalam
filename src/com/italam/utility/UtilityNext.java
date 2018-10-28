package com.italam.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.SkipException;

import com.italam.pages.BasePage;


public class UtilityNext {

	static final Logger log = LogManager.getLogger(BasePage.class.getName());

	//
	// 
	// Getting value from config.properties file
	public static String getProperty(String key) throws IOException {
		Properties prop = new Properties();

		// If want to get the data properties from project environmet
		// String workDir = System.getProperty("user.dir");
		// InputStream input = new
		// FileInputStream(workDir+"\\src\\com\\hotportal\\data\\datafile.properties");
		// (GeneralUtility.getUserDir(System.getProperty("user.dir"))

		InputStream input = null; 
		try {
		input = new FileInputStream(GeneralUtility.getUserDir(System.getProperty("user.dir")) + "\\datafile.properties");
		}
		catch(IOException ex) {
			log.error("Could not find the utility file " + "hotnet.datafile.properties");
			ex.printStackTrace();
		}
		
		prop.load(input);
		return prop.getProperty(key);
	}

	public static String[] returnLoginParams() throws IOException, InterruptedException {

		String sUser, sPass, baseUrl;

		switch (getProperty("environment")) {


		case "test": {
			sUser = ""; // UtilityNext.getProperty("hotnet_crm_intuser");
			baseUrl = UtilityNext.getProperty("urlTest");
			break;
		}
		case "production": {
			sUser = ""; // UtilityNext.getProperty("hotnet_crm_intuser");
			baseUrl = UtilityNext.getProperty("urlProd");
			break;
		}
		case "other": {
			sUser = ""; // UtilityNext.getProperty("hotnet_crm_intuser");
			baseUrl = UtilityNext.getProperty("urlOther");
			break;
		}

		default: {
			sUser = "";
			log.error("No proper environment was found");
			throw new SkipException("No proper Environment in crm xml...");
		}

		}

		sPass = "123456";
		String output[] = { sUser, sPass, baseUrl };
		return output;
	}

	public static String getToken(String string) {
		//String string = "I'm a ' 123 & 'test'";
		String token = "NO_TOKEN";
		String[] str = string.split("token\":\"");
		
		token = str[1].replaceAll("\"}}", "").trim();
		
		return token;
	}
	
	public static String organization_id(String string) {
		//String string = "I'm a ' 123 & 'test'";
		String organization_id = "NO_organization_id";
		String[] str = string.split("organization_id\":");
		
		organization_id = str[1].replaceAll("}}}", "").trim();
		
		return organization_id;
	}
	
	
	public static String getPassword(String string) {
		//String string = "I'm a ' 123 & 'test'";
		String password = "NO_organization_id";
		String[] str = string.split("password\":\"");
		int index =str[1].indexOf("\"");
		
		password = str[1].substring(0, index);
		
		return password;
	}
	
	public static String getData(String string) {
		//String string = "I'm a ' 123 & 'test'";
		String data = "NO_data";
		String[] str = string.split("data\":");
		
		data = str[1].replaceAll("\"}}", "").trim();
		
		return data;
	}

	
}
