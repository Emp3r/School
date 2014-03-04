package Class02;

import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.w3c.dom.bootstrap.*;
import org.w3c.dom.ls.*;

public class DOMTimesheetReaderWriter implements TimesheetReaderWriter {

	@Override
	public Timesheet loadTimesheet(InputStream input) throws Exception {
		final Timesheet result = new Timesheet();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(input);
		Element all = doc.getDocumentElement();
		
		Person actualPerson = null;
		String pName = null, pOccupation;
		String aDate, aHours, aName;
		
		for (int i = 0; i < all.getChildNodes().getLength(); i++) {
			Node n = all.getChildNodes().item(i);
			
			if (n.getNodeName().equals("name")) {
				pName = n.getTextContent();
			}
			else if (n.getNodeName().equals("occupation")) {
				pOccupation = n.getTextContent();
				actualPerson = new Person(pName, pOccupation);
			}
			else if (n.getNodeName().equals("activities")) {
				for (int j = 1; j < n.getChildNodes().getLength(); j += 2) {  
					// ? nevim proc jen licha cisla, ale jinak to hazi NullPointerException
					Node a = n.getChildNodes().item(j);
					aDate = a.getAttributes().getNamedItem("date").getNodeValue();
					aHours = a.getAttributes().getNamedItem("hours").getNodeValue();
					aName = a.getTextContent();
					actualPerson.addActivity(new Activity(aDate, aHours, aName));
				}	
				result.addPerson(actualPerson);
			}
		}
		return result;
	}

	@Override
	public void storeTimesheet(OutputStream output, Timesheet timesheet)
			throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		Element all = doc.createElement("timesheet");
		doc.appendChild(all);
		
		for (Person p : timesheet.getPersons()) {
			Element pName = doc.createElement("name");
			pName.setTextContent(p.getName());
			all.appendChild(pName);
			Element pOccupation = doc.createElement("occupation");
			pOccupation.setTextContent(p.getOccupation());
			all.appendChild(pOccupation);
			Element pActivities = doc.createElement("activities");
			Element pActivity;
			
			for (Activity a : p.getActivities()) {
				pActivity = doc.createElement("activity");
				pActivity.setAttribute("date", a.getDate());
				pActivity.setAttribute("hours", a.getHours());
				pActivity.setTextContent(a.getName());
				pActivities.appendChild(pActivity);
			}
			all.appendChild(pActivities);
		}
		
		DOMImplementationRegistry r = DOMImplementationRegistry.newInstance();
		DOMImplementationLS ls = (DOMImplementationLS)r.getDOMImplementation("LS");
		LSSerializer serializer = ls.createLSSerializer();
		String allText = serializer.writeToString(doc);
		
		try (DataOutputStream dos = new DataOutputStream(output)) {
			dos.writeUTF(allText);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
