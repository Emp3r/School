package Class02;

import java.io.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class SAXTimesheetReaderWriter implements TimesheetReaderWriter {

	@Override
	public Timesheet loadTimesheet(InputStream input) throws Exception {
		final Timesheet result = new Timesheet();
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		
		DefaultHandler dh = new DefaultHandler() {
			String pName, pOccupation;
			String aDate, aHours, aName;
			Person actualPerson;
			Activity actualActivity;
			boolean inName = false, inOccupation = false;
			boolean inActivities = false, inActivity = false;
			
			@Override
			public void startDocument() throws SAXException {
			}
			@Override
			public void endDocument() throws SAXException {
			}
			
			@Override
			public void startElement(String uri, String localName,
					String qName, Attributes attributes) throws SAXException {
				if (qName.equals("name"))
					inName = true;
				else if (qName.equals("occupation"))
					inOccupation = true;
				else if (qName.equals("activities"))
					inActivities = true;
				else if (qName.equals("activity") && inActivities) {
					inActivity = true;
					aDate = attributes.getValue("date");
					aHours = attributes.getValue("hours");
				}
			}
			@Override
			public void endElement(String uri, String localName, String qName)
					throws SAXException {
				if (qName.equals("name"))
					inName = false;
				else if (qName.equals("occupation")) {
					actualPerson = new Person(pName, pOccupation);
					inOccupation = false;
				}
				else if (qName.equals("activities")) {
					result.addPerson(actualPerson);
					inActivities = false;
				}
				else if (qName.equals("activity")) {
					actualActivity = new Activity(aDate, aHours, aName);
					actualPerson.addActivity(actualActivity);
					inActivity = false;
				}
			}
			
			@Override
			public void characters(char[] ch, int start, int length)
					throws SAXException {
				if (inName)
					pName = new String(ch, start, length);
				else if (inOccupation)
					pOccupation = new String(ch, start, length);
				else if (inActivity && inActivities)
					aName = new String(ch, start, length);
			}
		};
		
		sp.parse(input, dh);
		
		return result;
	}

	@Override
	public void storeTimesheet(OutputStream output, Timesheet timesheet)
			throws Exception {
		new Exception();
	}
}
