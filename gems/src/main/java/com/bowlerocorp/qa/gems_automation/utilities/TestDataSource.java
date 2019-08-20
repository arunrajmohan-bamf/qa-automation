package com.bowlerocorp.qa.gems_automation.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;

public class TestDataSource extends getProperties {
	public static int rowCount;
	public static int columnCount;
	final static Logger logger = Logger.getLogger("TestDataSource");

	public static HashMap<String, LinkedHashMap<Integer, List<String>>> loadExcelLines(File fileName) {
		HashMap<String, LinkedHashMap<Integer, List<String>>> outerMap = new LinkedHashMap<String, LinkedHashMap<Integer, List<String>>>();
		LinkedHashMap<Integer, List<String>> hashMap = new LinkedHashMap<Integer, List<String>>();
		String sheetName = null;
		FileInputStream fis = null;
		try {
			// File file=new File(filePath+"\\"+fileName);
			fis = new FileInputStream(fileName);
			Workbook Wb = null;
			String extn = fileName.toString().substring(fileName.toString().lastIndexOf("."));
			if (extn.equalsIgnoreCase(".xlsx"))
				Wb = new XSSFWorkbook(fis);
			else if (extn.equalsIgnoreCase(".xls"))
				Wb = new HSSFWorkbook(fis);
			else if (extn.equalsIgnoreCase(".xlsm"))
				Wb = new XSSFWorkbook(fis);

			int sc = Wb.getNumberOfSheets();
			for (int i = 0; i < sc; i++) {
				Sheet sheet = Wb.getSheetAt(i);
				sheetName = Wb.getSheetName(i);
				Iterator<Row> Rit = sheet.rowIterator();
				while (Rit.hasNext()) {

					XSSFRow row = (XSSFRow) Rit.next();
					// if(row.getLastCellNum()){}
					Iterator<Cell> Cit = row.cellIterator();
					List<String> data = new LinkedList<String>();
					while (Cit.hasNext()) {
						XSSFCell cell = (XSSFCell) Cit.next();
						//if (cell.getColumnIndex() != 1) {
							FormulaEvaluator evaluator = Wb.getCreationHelper().createFormulaEvaluator();
							switch (evaluator.evaluateInCell(cell).getCellType()) {
							case STRING:
								// System.out.println(cell.getStringCellValue());
								data.add(cell.getStringCellValue());
								break;
							case NUMERIC:
								DataFormatter df = new DataFormatter();
								String value = df.formatCellValue(cell);
								data.add(value);
								break;
							case ERROR:
								data.add(String.valueOf((cell.getErrorCellValue())));
								break;
							case BOOLEAN:
								data.add(String.valueOf((cell.getBooleanCellValue())));
								break;
							case FORMULA:
								data.add(cell.getStringCellValue());
								break;
							case BLANK:
								data.add("");

							default:
								break;

							}
						//}
						// else if (cell==null) {data.add(" ");}
					}
					hashMap.put(row.getRowNum(), data);
					for (Iterator<Map.Entry<Integer, List<String>>> HMItr = hashMap.entrySet().iterator(); HMItr
							.hasNext();) {
						Map.Entry<Integer, List<String>> HMentry = HMItr.next();
						if (HMentry.getKey() == 0) {
							HMItr.remove();
						}
					}
				}
				outerMap.put(sheetName, hashMap);
				hashMap = new LinkedHashMap<Integer, List<String>>();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return outerMap;
	}

	public static int testRunCounter(String name) {
		List<String> DataLS = new ArrayList<String>();
		List<Integer> CounterLS = new ArrayList<Integer>();
		// String
		// filepath="D:\\TestAutomation\\RemoteGigz\\WS\\RemoteGigz\\TestData\\TestDataMacro.xlsm";
		// loadProperties();
		HashMap<String, LinkedHashMap<Integer, List<String>>> resultMap = new LinkedHashMap<String, LinkedHashMap<Integer, List<String>>>(
				loadExcelLines(new File((getTestDataFile()))));
		@SuppressWarnings("unused")
		Set<String> KSS = resultMap.keySet();
		int Counter = 0;
		// String name = new Object(){}.getClass().getEnclosingMethod().getName();
		for (Entry<String, LinkedHashMap<Integer, List<String>>> ES : resultMap.entrySet()) {
			if (ES.getKey().equalsIgnoreCase(name)) {
				for (Entry<Integer, List<String>> ES2 : ES.getValue().entrySet()) {
					DataLS.addAll(ES2.getValue());
					CounterLS.add(ES2.getKey());
					Counter = Collections.max(CounterLS);
				}
			}
		}
		return Counter;
	}

	public static void WriteExcelData(String testfile, String SheetName, List<String> runtimedata) {
		// String sheetName = null;
		FileOutputStream fos = null;
		try {
			// File file=new File(testfile);
			fos = new FileOutputStream(testfile);
			Workbook Wb = new XSSFWorkbook();
			Sheet sheet = Wb.getSheet(SheetName);
			int lastrow = sheet.getLastRowNum();
			Row r = sheet.getRow(lastrow);
			for (int i = 0; i <= runtimedata.size(); i++) {
				Cell c = r.getCell(i);
				c.setCellValue(runtimedata.get(i));
				Wb.close();
				fos.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("resource")
	public static HashMap<String, LinkedHashMap<Integer, List<String>>> writeExcelLines(WebElement Table)
			throws IOException {

		HashMap<String, LinkedHashMap<Integer, List<String>>> outerMap = new LinkedHashMap<String, LinkedHashMap<Integer, List<String>>>();
		@SuppressWarnings("unused")
		LinkedHashMap<Integer, List<String>> hashMap = new LinkedHashMap<Integer, List<String>>();
		@SuppressWarnings("unused")
		String sheetName = null;
		FileOutputStream fos = null;
		try {
			String filePath = System.getProperty("user.dir") + File.separator + "TestData" + File.separator;
			String fileName = new java.util.Date().toString().replaceAll("[\\W+]", "") + ".xlsx";
			String sfile = filePath + fileName;
			File file = new File(sfile);
			fos = new FileOutputStream(file);
			Workbook Wb = new XSSFWorkbook();
			/*
			 * WebTBLtoXL xl=new WebTBLtoXL(); xl.getTableSize(); xl.test();
			 */

			fos = new FileOutputStream(file);
			Wb.write(fos);
			fos.close();
			Wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return outerMap;
	}

	public List<String> Login() {
		List<String> DataLS = new ArrayList<String>();
		HashMap<String, LinkedHashMap<Integer, List<String>>> resultMap = new LinkedHashMap<String, LinkedHashMap<Integer, List<String>>>(
				loadExcelLines(new File(getTestDataFile())));
		@SuppressWarnings("unused")
		Set<String> KSS = resultMap.keySet();
		String name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		for (Entry<String, LinkedHashMap<Integer, List<String>>> ES : resultMap.entrySet()) {
			if (ES.getKey().equalsIgnoreCase(name)) {
				for (Entry<Integer, List<String>> ES2 : ES.getValue().entrySet()) {
					DataLS.addAll(ES2.getValue());
				}
			}
		}
		return DataLS;
	}

	public List<String> UsersGroup_AddNew() {
		List<String> DataLS = new ArrayList<String>();
		HashMap<String, LinkedHashMap<Integer, List<String>>> resultMap = new LinkedHashMap<String, LinkedHashMap<Integer, List<String>>>(
				loadExcelLines(new File(getTestDataFile())));
		@SuppressWarnings("unused")
		Set<String> KSS = resultMap.keySet();
		String name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		for (Entry<String, LinkedHashMap<Integer, List<String>>> ES : resultMap.entrySet()) {
			if (ES.getKey().equalsIgnoreCase(name)) {
				for (Entry<Integer, List<String>> ES2 : ES.getValue().entrySet()) {
					DataLS.addAll(ES2.getValue());
				}
			}
		}
		return DataLS;
	}

	public static List<String> testData(String name) {
		List<String> DataLS = new ArrayList<String>();
		HashMap<String, LinkedHashMap<Integer, List<String>>> resultMap = new LinkedHashMap<String, LinkedHashMap<Integer, List<String>>>(
				loadExcelLines(new File(getTestDataFile())));
		@SuppressWarnings("unused")
		Set<String> KSS = resultMap.keySet();
		// String name = new Object(){}.getClass().getEnclosingMethod().getName();
		for (Entry<String, LinkedHashMap<Integer, List<String>>> ES : resultMap.entrySet()) {
			if (ES.getKey().equalsIgnoreCase(name)) {
				for (Entry<Integer, List<String>> ES2 : ES.getValue().entrySet()) {
					DataLS.addAll(ES2.getValue());
				}
			}
		}
		return DataLS;
	}

	public static List<String> writeData(String name) throws IOException {
		List<String> DataLS = new ArrayList<String>();
		HashMap<String, LinkedHashMap<Integer, List<String>>> writeResultMap = new LinkedHashMap<String, LinkedHashMap<Integer, List<String>>>(
				writeExcelLines(null));
		@SuppressWarnings("unused")
		Set<String> KSS = writeResultMap.keySet();
		// String name = new Object(){}.getClass().getEnclosingMethod().getName();
		for (Entry<String, LinkedHashMap<Integer, List<String>>> ES : writeResultMap.entrySet()) {
			if (ES.getKey().equalsIgnoreCase(name)) {
				for (Entry<Integer, List<String>> ES2 : ES.getValue().entrySet()) {
					DataLS.addAll(ES2.getValue());
				}
			}
		}
		return DataLS;
	}

	public static void setCellValue(String fileName, String sheetName, int Row, int Col, String Value)
			throws IOException, InvalidFormatException {
		// File file=new File(fileName);
		FileInputStream fis = new FileInputStream(fileName);
		// XSSFWorkbook Wb=new XSSFWorkbook(fis);
		Workbook Wb = null;
		String extn = fileName.toString().substring(fileName.toString().lastIndexOf("."));
		if (extn.equalsIgnoreCase(".xlsx"))
			Wb = new XSSFWorkbook(fis);
		else if (extn.equalsIgnoreCase(".xls"))
			Wb = new HSSFWorkbook(fis);
		else if (extn.equalsIgnoreCase(".xlsm"))
			Wb = new XSSFWorkbook(fis);
		int sheetindex = Wb.getSheetIndex(sheetName);
		System.out.println("sheetindex: " + sheetindex);
		Sheet sheet = Wb.getSheet(sheetName);
		org.apache.poi.ss.usermodel.Row row = null;
		Cell cell = null;
		row = sheet.getRow(Row);
		if (row == null) {
			row = sheet.createRow(Row);
		}
		cell = row.getCell(Col);
		if (cell == null) {
			cell = row.createCell(Col);
		}

		cell.setCellValue(Value);

		fis.close();
		FileOutputStream fos = new FileOutputStream(new File(fileName));
		Wb.write(fos);
		fos.close();

	}

	public static void ReadCellValue(String fileName, String sheetName) throws IOException {
		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);

		Workbook Wb = null;
		String extn = fileName.toString().substring(fileName.toString().lastIndexOf("."));
		if (extn.equalsIgnoreCase(".xlsx"))
			Wb = new XSSFWorkbook(fis);
		else if (extn.equalsIgnoreCase(".xls"))
			Wb = new HSSFWorkbook(fis);
		else if (extn.equalsIgnoreCase(".xlsm"))
			Wb = new XSSFWorkbook(fis);
		Sheet sheet = Wb.getSheet(sheetName);

		/*
		 * List<String> ARDataList=new ArrayList<String>(); ARDataList.add("Arun");
		 * ARDataList.add("ArthiMonika");
		 */

		System.out.println("getFirstRowNum: " + sheet.getFirstRowNum());
		System.out.println("getHeader: " + sheet.getHeader());
		System.out.println("getFooter: " + sheet.getFooter());
		System.out.println("getPhysicalNumberOfRows: " + sheet.getPhysicalNumberOfRows());

		int firstRowNumber = sheet.getFirstRowNum();
		Row Header = sheet.getRow(firstRowNumber);
		int HeaderCellCount = Header.getPhysicalNumberOfCells();
		System.out.println("HeaderCellCount: " + Header.getPhysicalNumberOfCells());
		HashMap<String, Integer> HdrIndexMap = new LinkedHashMap<String, Integer>();

		for (int k = 0; k <= HeaderCellCount; k++) {
			Cell hdr = Header.getCell(k);
			System.out.println("Header:" + hdr);
			if (hdr != null) {
				HdrIndexMap.put(hdr.toString(), hdr.getColumnIndex());
			}
		}
		for (Map.Entry<String, Integer> entry : HdrIndexMap.entrySet()) {
			System.out.println("Key: " + entry.getKey() + "; Value: " + entry.getValue());
		}
		System.out.println("***********Iterator******************");
		Iterator<Map.Entry<String, Integer>> itr = HdrIndexMap.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<String, Integer> entry = itr.next();
			System.out.println("Key: " + entry.getKey() + "; Value: " + entry.getValue());
		}

		/*
		 * int rowCount=sheet.getLastRowNum()-sheet.getFirstRowNum();
		 * System.out.println("RowCount: "+rowCount); for(int i=0;i<=rowCount;i++){ Row
		 * row=sheet.getRow(i); for(int j=0;j<=row.getLastCellNum();j++){ Cell
		 * cell=row.getCell(j); System.out.println("Cell: "+cell);}}
		 */

		Wb.close();
		fis.close();
	}

	public static void main(String args[]) throws IOException, InvalidFormatException {
		/*
		 * List<String> ARDataList=new ArrayList<String>(); ARDataList.add("Arun");
		 * ARDataList.add("ArthiMonika");
		 * System.out.println("ARDataList - "+ARDataList);
		 */
		setCellValue("D:\\SampleExcel.xlsx", "Test", 4, 4, "ArunArthi");

	}
}
