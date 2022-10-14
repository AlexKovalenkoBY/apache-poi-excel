package com.roytuts.java.read.large.excel.file.apache.poi;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

// import org.apache.poi.xssf.eventusermodel.XSSFReader;
// import org.apache.poi.xssf.model.SharedStringsTable;

// import org.xml.sax.ContentHandler;
// import lombok.extern.slf4j.Slf4j;

public class XLSprocessor {
    public ArrayList<String> elementObj;// = new ArrayList<String>();
    public ArrayList<ArrayList<String>> sheetObj = new ArrayList<ArrayList<String>>();

    public ArrayList<ArrayList<String>> processSheets(String filename) throws Exception {

        InputStream is = new FileInputStream(filename);
        POIFSFileSystem fs = new POIFSFileSystem(is);

        HSSFWorkbook wb = new HSSFWorkbook(fs.getRoot(), true);

        for (Sheet sheet : wb) {

            for (Row row : sheet) {
                elementObj = new ArrayList<String>();

                for (Cell cell : row) {
                    org.apache.poi.ss.usermodel.CellType ct = cell.getCellType();
                    switch (ct) {
                        case STRING: {
                            elementObj.add(cell.getStringCellValue());
                            break;
                        }
                        case NUMERIC: {
                            double dv = cell.getNumericCellValue();
                            elementObj.add(String.format("%.0f", dv));
                            break;
                        }
                        default:
                            break;
                    }

                }
                sheetObj.add(elementObj);
            }
        }
        fs.close();
        return sheetObj;
    }

}
