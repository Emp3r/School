import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;


public class StAXExampleR {

	public static void main(String[] args) throws XMLStreamException {
		InputStream input = new ByteArrayInputStream("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><foo>Lorem ipsum</foo><foo bar=\"qux\"/></root>".getBytes());
		
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(input);
		
		int level = 0;
		
		while (reader.hasNext()) {
			switch (reader.next()) {
			case XMLStreamReader.START_ELEMENT:
				padding(level++);
				System.out.println("Precten element: " + reader.getName());
				break;
				
			case XMLStreamReader.END_ELEMENT:
				padding(--level);
				System.out.println("Konec elementu: " + reader.getName());
				break;
			
			case XMLStreamReader.CHARACTERS:
				padding(level);
				System.out.println("Text: " + reader.getText());
				break;
				
			default:
				break;
			}
		}

	}
	
	private static void padding(int level) {
		for (int i = 0; i < level; i++)
			System.out.print("   ");
	}

}
