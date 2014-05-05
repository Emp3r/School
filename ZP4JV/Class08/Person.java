package Class08;

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
	
	public void addActivity(Activity a) {
		activities.add(a);
	}
	
	@Override
	public String toString() {
		return name + "\t-\t" + occupation;
	}
}
