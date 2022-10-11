package com.roytuts.java.read.large.excel.file.apache.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
// import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import com.github.pjfanning.xlsx.StreamingReader;



    static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File("D:\\java\\java_POI_MAVEN\\ARMTEK_MAIN_40006905_202210041806.xlsx");
        long startTime = System.nanoTime();
        try (
            InputStream is = new FileInputStream(new File(file));
            Workbook workbook = StreamingReader.builder()
              .rowCacheSize(100)
              .bufferSize(4096)
              .open(is)
    ){
      for (Sheet sheet : workbook){
        System.out.println(sheet.getSheetName());
        for (Row r : sheet) {
          for (Cell c : r) {
            System.out.println(c.getStringCellValue());
          }
        }
      }
    }
        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("Wbread Time: " + estimatedTime / 1_000_000_000.);
    }
