package Stuff;

/**
 * Trida Contact - kontakt v databazi
 * @author Josef Podstata
 * @version 1.0
 */
public class Contact {
	
	private String name, number, adress;
	
	// Konstruktor
	public Contact(String nam, String num, String adr) {
		this.name = nam;
		if (num.length() == 0) {
			this.number = "n/a";
		} else {
			this.number = num;
		}
		if (adr.length() == 0) {
			this.adress = "n/a";
		} else {
			this.adress = adr;
		}
	}

	// Get/Set
	public String getName() {
		return name;
	}
	public void setName(String j) {
		this.name = j;
	}

	public String getNumber() {
		return number;
	}
	public void setNumber(String c) {
		this.number = c;
	}

	public String getAdress() {
		return adress;
	}
	public void setAdress(String a) {
		this.adress = a;
	}
	
	public boolean sameAs(Contact c) {
		if (name.equals(c.getName()) && 
			number.equals(c.getNumber()) && 
			adress.equals(c.getAdress())) {
			return true;
		}
		return false;
	}
	
	// Vypis
	public String toLine() {
		StringBuilder result = new StringBuilder();
		
		result.append(name);
		for (int i = 0; i < 30 - name.length(); i++)
			result.append(" ");
		result.append(" | ");
		for (int i = 0; i < 14 - number.length(); i++)
			result.append(" ");
		result.append(number);
		result.append(" | ");
		for (int i = 0; i < 22 - adress.length(); i++)
			result.append(" ");
		result.append(adress);
		
		return result.toString();
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append(name);
		result.append(" - ");
		result.append(number);
		result.append(" - ");
		result.append(adress);
		
		return result.toString();
	}
	
}
