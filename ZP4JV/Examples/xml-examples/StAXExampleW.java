import java.io.StringWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;


public class StAXExampleW {
	
	public static void main(String[] args) throws XMLStreamException {
		
		// pomocny buffer -- muze byt libovolny Writer/Output stream
		StringWriter buf = new StringWriter();
		
		// inicializace writeru
		XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
		XMLStreamWriter xmlWriter = xmlOutputFactory.createXMLStreamWriter(buf);
		
		xmlWriter.writeStartDocument();
		
		// korenovy element
		xmlWriter.writeStartElement("root");
		
		xmlWriter.writeStartElement("foo");
		xmlWriter.writeCharacters("Lorem ipsum");
		xmlWriter.writeEndElement();
		
		xmlWriter.writeStartElement("foo");
		xmlWriter.writeAttribute("bar", "qux");
		xmlWriter.writeEndElement();
		
		// konec korenoveho element
		xmlWriter.writeEndElement();
		
		xmlWriter.writeEndDocument();
		
		// vypsani bufferu
		System.out.println(buf.toString());
	}

}
