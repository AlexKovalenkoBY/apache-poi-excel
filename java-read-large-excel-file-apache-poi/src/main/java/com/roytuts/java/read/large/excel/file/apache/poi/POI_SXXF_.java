package com.roytuts.java.read.large.excel.file.apache.poi;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
/*этот код работает, но workbook  создается 10-15 сек. это очень много */
class POI_SXXF_ {
    public static void main(String[] args) throws Throwable {
        String fileName = "ARMTEK_MAIN_40006905_202210041806.xlsx";
        System.out.println("started");
        long startTime = System.nanoTime();
        Workbook wb2 = WorkbookFactory.create(new File(fileName));
        SXSSFWorkbook wb = new SXSSFWorkbook(-1); // keep 100 rows in memory, exceeding rows will be flushed to disk
        Sheet sh = wb.createSheet();
        
        Sheet sh2 = wb2.getSheetAt(0);
        int rownum =0;
        int celnum=0;
        long estimatedTime = System.nanoTime() - startTime;

        System.out.println("Workbook created: " + estimatedTime / 1_000_000_000.);
        for (Sheet sheet : wb2){
            System.out.println(sheet.getSheetName());
            for (Row r : sheet) {
                 Row newRow = sh.createRow(rownum); 
                celnum=0;
              for (Cell c : r) {
                 Cell newcell= newRow.createCell(celnum);
                 newcell.setCellValue(c.getStringCellValue());
                celnum++;
                // System.out.println(c.getStringCellValue());
              }
            rownum++;
            }
          }
        // Rows with rownum < 900 are flushed and not accessible
        estimatedTime = System.nanoTime() - startTime;

        System.out.println("new file Created: " + estimatedTime / 1_000_000_000.);
        FileOutputStream out = new FileOutputStream("sxssf.xlsx");
       // dispose of temporary files backing this workbook on disk
       wb.write(out);
       wb.dispose();
        out.close();
        wb.close();

        wb2.close();
         estimatedTime = System.nanoTime() - startTime;

        System.out.println("estimatedTime: " + estimatedTime / 1_000_000_000.);
    }
}