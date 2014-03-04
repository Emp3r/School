package Class02;

import java.util.*;

public class Timesheet {
	
	private List<Person> persons;
	
	public Timesheet() {
		this.persons = new ArrayList<>();
	}
	
	public List<Person> getPersons() {
		return persons;
	}
	public void setPersons(List<Person> p) {
		this.persons = p;
	}

	// pridej osobu
	public void addPerson(Person p) {
		persons.add(p);
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		int i = 1;
		for (Person p : persons) {
			result.append(i + ".\n");
			result.append(p.toString());
			result.append("\n");
			i++;
		}
		return result.toString();
	}
}
