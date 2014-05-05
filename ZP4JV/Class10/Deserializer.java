package Class10;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Deserializer {

	private String path;
	
	public Deserializer(String filePath) {
		this.path = filePath;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public void setPath(String newPath) {
		this.path = newPath;
	}
	
	public Object deserialize() throws Exception{
		Object result;
		InputStream is = new FileInputStream(new File(path));
		// DOM init
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(is);
		Element all = doc.getDocumentElement();
 
		System.out.println(all.getNodeName());	// "emp"
		// záhadný způsob jak z "emp" zjistit, že jde o Employee.class
		
		// nejde!?
		// Set<Class<? extends Object>> allClasses = new Reflections().getSubTypesOf(Object.class);
		
		Class<?> clazz = Employee.class;
		result = clazz.newInstance();
		
		Method[] methods = clazz.getDeclaredMethods();
		
		for (int i = 0; i < all.getChildNodes().getLength(); i++) {
			Node n = all.getChildNodes().item(i);
			// System.out.println(n.getNodeName() + ": " + n.getTextContent());
			
			for (Method m : methods) {
				if (m.getAnnotation(Deserialize.class) != null) {
					String an = m.getAnnotation(Deserialize.class).as();
					
					if (an.equals(n.getNodeName())) {
						
						Class<?>[] parameters = m.getParameterTypes();

						if (parameters[0] == int.class)
							m.invoke(result, Integer.parseInt(n.getTextContent()));
						else if (parameters[0] == double.class)
							m.invoke(result, Double.parseDouble(n.getTextContent()));
						else
							m.invoke(result, n.getTextContent());
						
						// System.out.println(m.getName() + " " + m.getAnnotation(Deserialize.class) + " - " + an);
					} 
				}
			} 
		} 
		is.close();
		return result;
	}
}
