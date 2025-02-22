package com.roytuts.java.read.large.excel.file.apache.poi;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.Attributes;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

// import lombok.extern.slf4j.Slf4j;

public class SaxEventUserModel {

	public void processSheets(String filename) throws Exception {
		long startTime = System.nanoTime();
		OPCPackage pkg = OPCPackage.open(filename);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = (SharedStringsTable) r.getSharedStringsTable();
		// XMLReader parser = fetchSheetParser(sst);
		XMLReader parser = XMLHelper.newXMLReader();
		SheetHandler handler = new SheetHandler(sst);
		parser.setContentHandler(handler);
		Iterator<InputStream> sheets = r.getSheetsData();

		while (sheets.hasNext()) {
			InputStream sheet = sheets.next();
			System.out.println("Processing new sheet: ");
			InputSource sheetSource = new InputSource(sheet);
			parser.parse(sheetSource);
			sheet.close();
			long estimatedTime = System.nanoTime() - startTime;

			System.out.println("estimatedTime: " + estimatedTime / 1_000_000_000.);
			System.out.println("sheet proceed");
		}
		pkg.close();
	}

	public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException, ParserConfigurationException {
		XMLReader parser = XMLHelper.newXMLReader();
		SheetHandler handler = new SheetHandler(sst);
		parser.setContentHandler(handler);
		return parser;
	}

	/**
	 * See org.xml.sax.helpers.DefaultHandler javadocs
	 */
	private class SheetHandler extends DefaultHandler {
		public ArrayList<String> elementObj;// = new ArrayList<String>();
		public ArrayList<ArrayList<String>> sheetObj = new ArrayList<ArrayList<String>>();

		private SharedStringsTable sst;
		private String lastContents;
		private boolean nextIsString;

		private SheetHandler(SharedStringsTable sst) {
			this.sst = sst;
		}

		@Override
		public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
			// c => cell
			if (name.equals("row")) {
				elementObj = new ArrayList<String>();
			}
			if (name.equals("c")) {
				// Print the cell reference
				// System.out.print(attributes.getValue("r") + " - ");
				// Figure out if the value is an index in the SST
				String cellType = attributes.getValue("t");
				if (cellType != null && cellType.equals("s")) {
					nextIsString = true;
				} else {
					nextIsString = false;
				}
			}
			// Clear contents cache
			lastContents = "";
		}

		@Override
		public void endElement(String uri, String localName, String name) throws SAXException {
			// Process the last contents as required.
			// Do now, as characters() may be called more than once
			if (nextIsString) {
				int idx = Integer.parseInt(lastContents.trim());
				lastContents = sst.getItemAt(idx).getString();
				nextIsString = false;
			}
			// v => contents of a cell
			// Output after we've seen the string contents
			if (name.equals("v")) {
				// System.out.println(lastContents);
				elementObj.add(lastContents);
			}
			if (name.equals("row")) {
				sheetObj.add(elementObj);
				// System.out.println(lastContents);
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) {
			lastContents += new String(ch, start, length);
		}
	}

}
