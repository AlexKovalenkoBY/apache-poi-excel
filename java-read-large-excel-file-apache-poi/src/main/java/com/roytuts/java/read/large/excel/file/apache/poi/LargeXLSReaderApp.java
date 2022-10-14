package com.roytuts.java.read.large.excel.file.apache.poi;

import java.util.ArrayList;

public class LargeXLSReaderApp {

	public static void main(String[] args) throws Exception {
		 String fileName = "bigFile.xls";

		XLSprocessor saxEventUserModel = new XLSprocessor();
		
		ArrayList<ArrayList<String>> sheetObj= saxEventUserModel.processSheets(fileName);
        int tt =0;
	
	}


}
