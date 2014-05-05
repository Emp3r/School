package Class08;

public class Activity {
	
	private String date, hours, text;
	
	public Activity(String d, String h, String n) {
		this.date = d;
		this.hours = h;
		this.text = n;
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

	public String getText() {
		return text;
	}
	public void setText(String n) {
		this.text = n;
	}
	
	@Override
	public String toString() {
		return date + ", " + hours + " hours, " + text;
	}

}
