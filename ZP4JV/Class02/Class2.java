package Class02;

import java.io.*;

public class Class2 {

	public static void main(String[] args) throws Exception {

		InputStream is = new FileInputStream(new File("Timesheet.xml"));
		OutputStream op = new FileOutputStream(new File("test.xml"));
		
		// SAX test
		/*
		SAXTimesheetReaderWriter sax = new SAXTimesheetReaderWriter();
		System.out.print(sax.loadTimesheet(is).toString());
		*/
		
		// DOM test
		/* 
		DOMTimesheetReaderWriter dom = new DOMTimesheetReaderWriter();
		Timesheet t = dom.loadTimesheet(is);
		System.out.print(t.toString());
		dom.storeTimesheet(op, t);
		*/
		
		// StAX test
		StAXTimesheetReaderWriter stax = new StAXTimesheetReaderWriter();
		Timesheet t = stax.loadTimesheet(is);
		System.out.print(t.toString());
		stax.storeTimesheet(op, t);
		
	}
}
