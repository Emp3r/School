import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


public class DOMExampleR {
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		
		InputStream input = new ByteArrayInputStream("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><foo>Lorem ipsum</foo><foo bar=\"qux\"/></root>".getBytes());
		
		// vytvori objekty pro sestaveni dokument
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		
		// rozparsuje dokument
		Document doc = documentBuilder.parse(input);
		
		// korenovy element
		Element root = doc.getDocumentElement();
		System.out.println("Korenovy element:" + root.getNodeName());
		
		for (int i = 0; i < root.getChildNodes().getLength(); i++) {
			Node node = root.getChildNodes().item(i);
			
			if (node.getTextContent().length() > 0) {
				System.out.println("Potomek c. " + i + " obsahuje text: " + node.getTextContent());
			}
			
			if (node.hasAttributes()) {
				// atribut je taky uzel
				Node attr = node.getAttributes().getNamedItem("bar");
				String value = attr.getTextContent();
				System.out.println("Potomek c. " + i + " ma attribut bar='" + value + "'");
			}
		}
	}
}
