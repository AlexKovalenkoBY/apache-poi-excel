package com.roytuts.java.read.large.excel.file.apache.poi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class BigXLSXgenerator {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        try (SXSSFWorkbook workbook = new SXSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();
            // CellStyle cellStyle = setExcelBodyCellStyle(workbook);
            int rowNum = 1048000;
            int collNum = 1000;
            for (int i = 0; i < rowNum; i++) {
                Row row = sheet.createRow(i);
                for (int cellNo = 0; cellNo < collNum; cellNo++) {
                    Cell newcell = row.createCell(cellNo);
                    int j = rowNum * cellNo;
                    newcell.setCellValue(j);
                    // createCell(row, cellNo, "lxfkglsdfkj");
                }

            }
            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) + "bigFile.xlsx";

            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            workbook.close();
            // dispose of temporary files backing this workbook on disk
            workbook.dispose();

        }
    }
}