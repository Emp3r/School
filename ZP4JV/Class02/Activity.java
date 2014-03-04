package Class02;

public class Activity {
	
	private String date, hours, name;
	
	public Activity(String d, String h, String n) {
		this.date = d;
		this.hours = h;
		this.name = n;
	}

	public String getDate() {
		return date;
	}
	public void setDate(String d) {
		this.date = d;
	}

	public String getHours() {
		return hours;
	}
	public void setHours(String h) {
		this.hours = h;
	}

	public String getName() {
		return name;
	}
	public void setName(String n) {
		this.name = n;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(date);
		result.append(", hours: ");
		result.append(hours);
		result.append(" - ");
		result.append(name);
		return result.toString();
	}

}
