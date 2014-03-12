import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;


public class DOMExampleW {
	
	public static void main(String[] args) throws ParserConfigurationException, IOException, ClassCastException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		// vytvori objekty pro sestaveni dokumentu
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		
		// vytvori prazdny dokument
		Document doc = documentBuilder.newDocument();
		
		// vytvori korenovy element a priradi ho do dokumentu
		Element rootElement = doc.createElement("root");
		doc.appendChild(rootElement);
		
		// vytvori novy element "foo" a priradiho do korenoveho elementu
		Element childNode1 = doc.createElement("foo");
		rootElement.appendChild(childNode1);
		
		// vytvori druhy element "foo", nastavi mu atribut
		// a priradi do korenoveho elementu
		Element childNode2 = doc.createElement("foo");
		childNode2.setAttribute("bar", "qux");
		rootElement.appendChild(childNode2);
		
		// dodatecne priradi text k prvnimu elementu "foo"
		childNode1.appendChild(doc.createTextNode("Lorem ipsum"));
		
		// vypis pomoci DOM level 3 s Load & Store 
		DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
		DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");

		LSSerializer serializer = impl.createLSSerializer();
		String xml = serializer.writeToString(doc);
		System.out.println(xml);
	}

}
