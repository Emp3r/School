package Class02;

import java.io.*;
import javax.xml.stream.*;

public class StAXTimesheetReaderWriter implements TimesheetReaderWriter {

	@Override
	public Timesheet loadTimesheet(InputStream input) throws Exception {
		final Timesheet result = new Timesheet();
		XMLInputFactory xif = XMLInputFactory.newInstance();
		XMLStreamReader xsr = xif.createXMLStreamReader(input);

		Person actualPerson = null;
		String pName = null, pOccupation;
		String aDate, aHours, aName;
		
		while (xsr.hasNext()) {
			if (xsr.next() == XMLStreamReader.START_ELEMENT) {
				if (xsr.getName().toString().equals("name")) {
					xsr.next();
					pName = xsr.getText();
				}
				else if (xsr.getName().toString().equals("occupation")) {
					xsr.next();
					pOccupation = xsr.getText();
					actualPerson = new Person(pName, pOccupation);
					result.addPerson(actualPerson);
				}
				else if (xsr.getName().toString().equals("activity")) {
					aDate = xsr.getAttributeValue(0);
					aHours = xsr.getAttributeValue(1);
					xsr.next();
					aName = xsr.getText();
					actualPerson.addActivity(new Activity(aDate, aHours, aName));
				}
			}
		}
		return result;
	}

	@Override
	public void storeTimesheet(OutputStream output, Timesheet timesheet)
			throws Exception {
		
		StringWriter allText = new StringWriter();
		XMLOutputFactory xof = XMLOutputFactory.newInstance();
		XMLStreamWriter xsw = xof.createXMLStreamWriter(allText);
		xsw.writeStartDocument();
		xsw.writeStartElement("timesheet");
		
		for (Person p : timesheet.getPersons()) {
			xsw.writeStartElement("name");
			xsw.writeCharacters(p.getName());
			xsw.writeEndElement();
			xsw.writeStartElement("occupation");
			xsw.writeCharacters(p.getOccupation());
			xsw.writeEndElement();
			xsw.writeStartElement("activities");
			
			for (Activity a : p.getActivities()) {
				xsw.writeStartElement("activity");
				xsw.writeAttribute("date", a.getDate());
				xsw.writeAttribute("hours", a.getHours());
				xsw.writeCharacters(a.getName());
				xsw.writeEndElement();
			}
			xsw.writeEndElement();
		}
		xsw.writeEndElement();
		xsw.writeEndDocument();
		
		try (DataOutputStream dos = new DataOutputStream(output)) {
			dos.writeUTF(allText.toString());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
