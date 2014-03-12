package Class03;

import java.util.*;

public class Person {

	private String name, occupation;
	private List<Activity> activities;
	
	public Person(String n, String o) {
		this.name = n;
		this.occupation = o;
		this.activities = new ArrayList<Activity>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String n) {
		this.name = n;
	}

	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String o) {
		this.occupation = o;
	}

	public List<Activity> getActivities() {
		return activities;
	}
	public void setActivities(List<Activity> a) {
		this.activities = a;
	}
	
	// pridej aktivitu
	public void addActivity(Activity a) {
		activities.add(a);
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(name);
		result.append(" - ");
		result.append(occupation);
		return result.toString();
	}
}
