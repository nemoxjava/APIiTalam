package com.italam.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


//From ToolsQA

public class ExcelUtils {

	private static final Logger log = LogManager.getLogger(ExcelUtils.class.getName());


	// Receive result set from Excel sheet data

	public static Object[][] getTableArray(String filePath, String sheetName) throws Exception {
		String[][] tabArray = null;
		try {
			
			XSSFSheet ExcelWSheet;
			XSSFWorkbook ExcelWBook;
			FileInputStream ExcelFile = new FileInputStream(filePath);
			// Access the required test data sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(sheetName);

			// String val =
			// formatter.formatCellValue(ExcelWSheet.getRow(col).getCell(row));

			int startRow = 1;
			int startCol = 1;
			int ci, cj;

			int totalRows = ExcelWSheet.getLastRowNum();
			Row r = ExcelWSheet.getRow(totalRows);

			int totalCols = r.getLastCellNum();
			totalCols = totalCols - 1;

			// System.out.println("-------total rows: " + totalRows);
			// System.out.println("-------total cols: " + totalCols);

			tabArray = new String[totalRows][totalCols];
			ci = 0;
			for (int i = startRow; i <= totalRows; i++, ci++) {
				cj = 0;
				for (int j = startCol; j <= totalCols; j++, cj++) {

					tabArray[ci][cj] = getCellData(i, j,filePath,sheetName);
					System.out.println(tabArray[ci][cj]);
				}
			}
			
			//ExcelWBook.close();
		}
		
		

		catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}
		
		return (tabArray);
	}


	// Received value of excel cell
	public static String getCellData(int RowNum, int ColNum,String filePath,String sheetName) throws Exception {
		try {
			
			XSSFSheet ExcelWSheet;
			XSSFWorkbook ExcelWBook;
			FileInputStream ExcelFile = new FileInputStream(filePath);
			// Access the required test data sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(sheetName);
			
			DataFormatter formatter = new DataFormatter();
			String myCell = formatter.formatCellValue(ExcelWSheet.getRow(RowNum).getCell(ColNum));
			//ExcelWBook.close();
			return myCell;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
		}

	}

	public static void main(String[] args) throws Exception {
		
		writeToCellInExcel("c:\\tmp\\File_01-01-2018_10_00_19.xlsx","compare",getLastRow("c:\\\\tmp\\\\File_01-01-2018_10_00_19.xlsx", "compare"),0,"rrrrrrrrrrrr");
		
		// String[] dataArray = {"first", "second", "third", "forth"};
		// writeExcel("c:\\test1.xlsx","data", dataArray);

		
		
		
		// if (compareRowsGardian_CRM("c:\\tmp\\File_01-01-2018_10_00_19.xlsx",
		// "compare"))
		// ;

		System.out.println("Last row in excel: " + getLastRow("c:\\tmp\\File_01-01-2018_10_00_19.xlsx", "compare"));
		int lastRow = getLastRow("c:\\tmp\\File_01-01-2018_10_00_19.xlsx", "compare");

		ArrayList<String> rowData = getRowArray("c:\\tmp\\File_01-01-2018_10_00_19.xlsx", "compare", lastRow - 1, 2);
		System.out.println("FIRST: " + rowData.toString());
		rowData = getRowArray("c:\\tmp\\File_01-01-2018_10_00_19.xlsx", "compare", lastRow, 2);

		System.out.println("AFTER: " + rowData.toString());

	}

	public static boolean compareRowsGardian_CRM(String filePath, String sheetName) throws Exception {

		int lastRow = ExcelUtils.getLastRow("c:\\tmp\\File_01-01-2018_10_00_19.xlsx", "compare");
		ArrayList<String> rowDataGardian = ExcelUtils.getRowArray("c:\\tmp\\File_01-01-2018_10_00_19.xlsx", "compare",
				lastRow - 1, 2);
		log.info("Row from Gardian: " + rowDataGardian.toString());
		ArrayList<String> rowDataCRM = ExcelUtils.getRowArray("c:\\tmp\\File_01-01-2018_10_00_19.xlsx", "compare",
				lastRow, 2);
		log.info("Row from CRM: " + rowDataCRM.toString());
		return rowDataCRM.equals(rowDataGardian);
	}

	public static ArrayList<String> getRowArray(String filePath, String sheetName, int rowNumber, int colNumber)
			throws Exception {
		try {
		
			FileInputStream ExcelFile = new FileInputStream(filePath);
			XSSFWorkbook ExcelWBook = new XSSFWorkbook(ExcelFile);
			XSSFSheet ExcelWSheet = ExcelWBook.getSheet(sheetName);

			Row r = ExcelWSheet.getRow(rowNumber);

			int totalCols = r.getLastCellNum();
			totalCols = totalCols - 1;

			ArrayList<String> rowData = new ArrayList<String>();

			for (int j = colNumber; j <= totalCols; j++) {
				rowData.add(getCellData(rowNumber, j,filePath,sheetName));
			}
			//ExcelWBook.close();
			return rowData;
		} catch (Exception ex) {
			log.error("Error getting data from Excel sheet, return empty array list..." + filePath);
			
		}
		return null;
	}

	public static int getLastRow(String filePath, String sheetName) throws Exception {
		int lastRow = 1;
		try {
			FileInputStream ExcelFile = new FileInputStream(filePath);
			XSSFWorkbook ExcelWBook = new XSSFWorkbook(ExcelFile);
			XSSFSheet ExcelWSheet = ExcelWBook.getSheet(sheetName);

			lastRow = ExcelWSheet.getLastRowNum();
			//ExcelWBook.close();
		} catch (Exception ex) {
			log.error("Error in getting data from Excel, last row was not found but return 1...." + filePath);
		}
		return lastRow;
	}

	public static void writeExcel(String filePath, String sheetName, String[] dataToWrite) throws IOException {

		// Create an object of File class to open xlsx file
		File file = new File(filePath);
		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(file);
		Workbook gWorkbook = null;
		// Find the file extension by splitting file name in substring and getting only
		// extension name
		String fileExtensionName = filePath.substring(filePath.indexOf("."));
		// Check condition if the file is xlsx file
		if (fileExtensionName.equals(".xlsx")) {
			// If it is xlsx file then create object of XSSFWorkbook class
			gWorkbook = new XSSFWorkbook(inputStream);
		}
		// Check condition if the file is xls file
		else if (fileExtensionName.equals(".xls")) {
			// If it is xls file then create object of XSSFWorkbook class
			gWorkbook = new HSSFWorkbook(inputStream);

		}

		// Read excel sheet by sheet name
		Sheet sheet = gWorkbook.getSheet(sheetName);
		// Get the current count of rows in excel file
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		// int startRow = 1;
		// int startCol = 1;
		// int ci, cj;

		int totalRows = sheet.getLastRowNum();
		Row r = sheet.getRow(totalRows);

		int totalCols = r.getLastCellNum();
		totalCols = totalCols - 1;

		// System.out.println("-------total rows: " + totalRows);
		// System.out.println("-------total cols: " + totalCols);

		// Get the first row from the sheet
		// Row row = sheet.getRow(0);

		// Create a new row and append it at last of sheet
		Row newRow = sheet.createRow(rowCount + 1);
		// Create a loop over the cell of newly created Row
		try {
			for (int j = 0; j < (dataToWrite.length); j++) {
				// Fill data in row
				Cell cell = newRow.createCell(j);
				cell.setCellValue(dataToWrite[j]);
			}

		} catch (Exception ex) {
			log.info(
					"Error in insert data to excel: verify the data items in array equal to the number of columns in Excel.");
		}

		// Close input stream

		inputStream.close();

		// Create an object of FileOutputStream class to create write data in excel file

		FileOutputStream outputStream = new FileOutputStream(file);

		// write data in the excel file

		gWorkbook.write(outputStream);

		// close output stream

		outputStream.close();

	}

	public static void writeToCellInExcel(String filePath, String sheetName, int iRow, int iCell, String dataToWrite)
			throws IOException {

		File file = new File(filePath);
		FileInputStream inputStream = new FileInputStream(file);
		Workbook gWorkbook = null;
		String fileExtensionName = filePath.substring(filePath.indexOf("."));

		if (fileExtensionName.equals(".xlsx")) {
			gWorkbook = new XSSFWorkbook(inputStream);
		} else if (fileExtensionName.equals(".xls")) {
			gWorkbook = new HSSFWorkbook(inputStream);

		}

		Sheet sheet = gWorkbook.getSheet(sheetName);
		Row newRow = sheet.getRow(iRow);
		try {
			Cell cell = newRow.createCell(iCell);
			cell.setCellValue(dataToWrite);

			inputStream.close();
			FileOutputStream outputStream = new FileOutputStream(file);

			gWorkbook.write(outputStream);

			// close output stream

			outputStream.close();
		} catch (Exception ex) {
			log.info(
					"Error in insert data to excel: verify the data items in array equal to the number of columns in Excel.");
		}
	}

}
