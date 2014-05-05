package Class10;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

public class Serializer {

	private String path;
	
	public Serializer(String filePath) {
		this.path = filePath;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public void setPath(String newPath) {
		this.path = newPath;
	}
	
	public void serialize(Object obj) throws Exception {
		// DOM init
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		
		Class<? extends Object> clazz = obj.getClass();
		Annotation[] annotations = clazz.getAnnotations();

		for (Annotation a : annotations) {
			if (a instanceof Serialize) {
				Element classElement = doc.createElement(((Serialize) a).as());
				doc.appendChild(classElement);

				for (Method m : clazz.getDeclaredMethods()) {
					for (Annotation an : m.getAnnotations()) {
						if (an instanceof Serialize) {
							Element methodElement = doc.createElement(((Serialize) an).as());
							methodElement.setTextContent(m.invoke(obj).toString());
							classElement.appendChild(methodElement);
						}
					}
				}
			}
		}
		
		// DOM serialize
		DOMImplementationRegistry r = DOMImplementationRegistry.newInstance();
		DOMImplementationLS ls = (DOMImplementationLS)r.getDOMImplementation("LS");
		LSSerializer serializer = ls.createLSSerializer();
		String allText = serializer.writeToString(doc);
		
		// save file
		OutputStream op = new FileOutputStream(new File(path));
		try (DataOutputStream dos = new DataOutputStream(op)) {
			dos.writeUTF(allText);
		} catch (Exception e) {
			System.out.println(e);
		}
		op.close();
	}
}
