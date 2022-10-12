package com.roytuts.java.read.large.excel.file.apache.poi;

import java.io.File;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class LargeExcelReaderApp {

	public static void main(String[] args) throws Exception {
		// String fileName = "ARMTEK_MAIN_40006905_202210041806.xlsx";
		String fileName = "Книга1.xlsx";
		// readLargeExcelFile(fileName);
		System.out.println("started");
        long startTime = System.nanoTime();
		
		SaxEventUserModel saxEventUserModel = new SaxEventUserModel();
		
		saxEventUserModel.processSheets(fileName);
		long estimatedTime = System.nanoTime() - startTime;
       
        System.out.println("estimatedTime: " + estimatedTime / 1_000_000_000.);
		// System.out.println("started V2");
		// startTime = System.nanoTime();
		// readLargeExcelFile(fileName);
		// estimatedTime = System.nanoTime() - startTime;
       
        // System.out.println("estimatedTime: " + estimatedTime / 1_000_000_000.);
	}

	// The following method will give error - OutOfMemoryError
	public static void readLargeExcelFile(final String fileName)
			throws EncryptedDocumentException, IOException, InvalidFormatException {
		Workbook wb = WorkbookFactory.create(new File(fileName));

		XSSFSheet sheet = (XSSFSheet) wb.getSheetAt(0);
		int rowNum = 0;
		for (Row r : sheet) {
			rowNum++;
			try {

				for (Cell c : r) {
					CellType cellType = c.getCellType();
					if (CellType.STRING.equals(cellType)) {
						// System.out.println(c.getStringCellValue());
					} else if (CellType.NUMERIC.equals(cellType)) {
						// System.out.println(String.valueOf(c.getNumericCellValue()));
					} else if (DateUtil.isCellDateFormatted(c)) {
						// System.out.println(c.getDateCellValue());
					}
				}
			} catch (Exception e) {
				System.out.println(rowNum);
			}
		}
	}
}
