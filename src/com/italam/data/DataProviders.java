package com.italam.data;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;

import com.italam.utility.ExcelUtils;
import com.italam.utility.GeneralUtility;
import com.italam.utility.UtilityNext;


public class DataProviders {

	private static Logger log ;//= LogManager.getLogger(DataProviders.class.getName());

	
static String workDir = GeneralUtility.getUserDir(System.getProperty("user.dir"));
	
	
	public static String[] returnConnectionParams() throws IOException {
		init();
		
		String environment = UtilityNext.getProperty("environment");
		String[] connectionDetails = new String[15];
		switch (environment) {
		
		case "int": {
			
			
			break;
		}
		case "test": {
			
			connectionDetails[0] = UtilityNext.getProperty("osbNextConnStringTest"); // OSB DB
			connectionDetails[1] = UtilityNext.getProperty("osbNextConnStringUserTest");
			connectionDetails[2] = UtilityNext.getProperty("osbNextConnStringPassTest");
			
//			connectionDetails[3] = UtilityNext.getProperty("crmNextConnStringTest");  // CRM DB
//			connectionDetails[4] = UtilityNext.getProperty("crmNextConnStringUserTest");
//			connectionDetails[5] = UtilityNext.getProperty("crmNextConnStringPassTest");
//			
//			connectionDetails[6] = UtilityNext.getProperty("wizNextConnStringTest"); // WIZ DB
//			connectionDetails[7] = UtilityNext.getProperty("wizNextConnStringUserTest");
//			connectionDetails[8] = UtilityNext.getProperty("wizNextConnStringPassTest");
//			
//			connectionDetails[9] = UtilityNext.getProperty("irbNextConnStringTest"); // IRB DB
//			connectionDetails[10] = UtilityNext.getProperty("irbNextConnStringUserTest");
//			connectionDetails[11] = UtilityNext.getProperty("irbNextConnStringPassTest");
//			
//			connectionDetails[12] = UtilityNext.getProperty("hotwizNextConnStringTest"); // HotWiz DB
//			connectionDetails[13] = UtilityNext.getProperty("hotwizNextConnStringUserTest");
//			connectionDetails[14] = UtilityNext.getProperty("hotwizNextConnStringPassTest");
			
			break;
		}
		case "production": {
			
			
			break;
		}
		default:
			log.error("No proper environment was found");
			throw new SkipException("No proper Environment in crm xml...");
			

		}
		return connectionDetails;
	}
	
	
	private static void init() {
		String propertiesPath = "C:\\APItalm\\StarterProject\\conf\\log4j.properties";
		if (!new File(propertiesPath).exists())
			System.err.println("log4j.properties is missing under : " + propertiesPath);
		else
			PropertyConfigurator.configure(propertiesPath);
		
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		Class<?> cls = null;
		try {
			cls = Class.forName(ste[1].getClassName());
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
		}
		log = Logger.getLogger(cls);
		
	}


	@DataProvider(name = "GetToken_DataXls")
	public Object[][] getNextCustomerDataXls() throws Exception {
		String sExcelPath = GeneralUtility.getUserDir(System.getProperty("user.dir")) + UtilityNext.getProperty("excelDoc");
		String sExcelSheet = UtilityNext.getProperty("excelDocsheet1");
		log.info("Provide data from Next Excel to the test");
		GeneralUtility.sendReporter("Provide data from Next Excel to the test");
		//Will provide data from Excel sheet to the test
		return ExcelUtils.getTableArray(sExcelPath, sExcelSheet);
		
	}
		
	
	@DataProvider(name = "iteration")
	public Object[][] iteration() {
		// return new Object[][] { { 1 }, { 2 }, { 3 }, { 4 }, { 5 }, { 6 }, { 7 }, { 8
		// }, { 9 }, { 10 }, { 11 }, { 12 }, { 13 }, { 14 }, { 15 }, { 16 }, { 17 }, {
		// 18 }, { 19 } };
		// return new Object[][] { { 1 }, { 2 }, { 3 }, { 4 }, { 5 }, { 6 }, { 7 }, { 8
		// }, { 9 } };
		//return new Object[][] { { 1 }, { 2 }, { 3 } };
		 return new Object[][] { { 1 } };
	}

	@DataProvider(name = "iteration_2")
	public Object[][] iteration_2() {
		return new Object[][] { { 1 }, { 2 } };
	}

	@DataProvider(name = "iteration_3")
	public Object[][] iteration_3() {
		return new Object[][] { { 1 }, { 2 }, { 3 } };
	}

	
	
	
	
	
	// // ��� ���� ����� ������
	// @DataProvider(name = "getDataFromDB_AdvancedSearchClients")
	// public Object[][] getDataFromDB_AdvancedSearchClients() throws Exception {
	// String[][] tabArray = null;
	//
	// String sSqlQueryCustomer = Constant.getsSqlQueryCustomer();
	// tabArray = DBUtils.getDataFromDB_AdvancedSearchUtil(sSqlQueryCustomer,
	// GeneralUtility.getProperty("crmconnectionstringsolqa2"),
	// GeneralUtility.getProperty("crmconnectionusersolqa2"),
	// GeneralUtility.getProperty("crmconnectionpasssolqa2"));
	// return tabArray;
	// }
}
