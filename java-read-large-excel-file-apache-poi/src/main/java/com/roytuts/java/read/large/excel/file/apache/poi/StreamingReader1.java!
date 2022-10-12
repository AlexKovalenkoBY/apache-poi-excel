package com.roytuts.java.read.large.excel.file.apache.poi;

import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.github.pjfanning.xlsx.StreamingReader;
/**
 * Hello world!
 *
 */
public class  StreamingReader1
//непонятно поччему не работает 
{
    public static void main( String[] args ) throws FileNotFoundException, IOException
    {
        System.out.println( "Hello World!" );
        try (
        InputStream is = new FileInputStream(new File("D:\\java\\apache-poi-excel\\java-read-large-excel-file-apache-poi\\ARMTEK_MAIN_40006905_202210041806.xlsx"));
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
    }
}
