package Class10;

public class Class10 {

	public static void main(String[] args) throws Exception {
		Serializer s = new Serializer("serializer.xml");
		Deserializer d = new Deserializer("deserializer.xml");
		
		Employee e1 = new Employee();
		e1.setName("Petr Klíč");
		e1.setAge(40);
		e1.setSalary(24234);

		s.serialize(e1);
		
		Employee e2 = (Employee) d.deserialize();
		System.out.println(e2.toString());
		
	}
}
