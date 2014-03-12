import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;



public class SAXExampleR {
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		
		InputStream input = new ByteArrayInputStream("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><foo>Lorem ipsum</foo><foo bar=\"qux\"/></root>".getBytes());
		
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		SAXParser parser = parserFactory.newSAXParser();
		
		parser.parse(input, new DefaultHandler() {
			
			private int level = 0;

			@Override
			public void startDocument() throws SAXException {
				System.out.println("Zahajeno cteni dokumentu");
				level++;
			}

			@Override
			public void endDocument() throws SAXException {
				System.out.println("Konec");
				level--;
			}

			@Override
			public void startElement(String uri, String localName,
					String qName, Attributes attributes) throws SAXException {
				padding();
				System.out.println("Precten element: " + qName);
				level++;
			}

			@Override
			public void endElement(String uri, String localName, String qName)
					throws SAXException {
				level--;
				padding();
				System.out.println("Konec elementu: " + qName);
			}

			@Override
			public void characters(char[] ch, int start, int length)
					throws SAXException {
				padding();
				System.out.println("Text: " + new String(ch, start, length));
			}
			
			private void padding() {
				for (int i = 0; i < level; i++)
					System.out.print("   ");
			}
		});
	}

}
