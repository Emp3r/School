package Stuff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Trida Database - databaze instanci tridy Contact.
 * @author Josef Podstata
 * @version 1.0
 */
public class Database {
	
	private List<Contact> contactList;
	private String filePath;
	private int count;
	
	// Konstruktor
	public Database(String file) {
		this.contactList = new ArrayList<>();
		this.count = 0;
		
		if (file.length() == 0){
			file = "untitled.txt";
		}
		if (!isNameOfFile(file)) {
			file += ".txt";
		}
		this.filePath = file;
		this.loadFile();
	}
	
	// Get/Set
	public String getPath() {
		return filePath;
	}
	public void setPath(String file) {
		if (!isNameOfFile(file)) {
			file += ".txt";
		}
		this.filePath = file;
	}
	
	public List<Contact> getList() {
		return contactList;
	}
	
	public int getCount() {
		return count;
	}
	
	// Pridat
	public void addContact(Contact c) {
		for (Contact cont : contactList) {
			if (cont.sameAs(c)) {
				return;
			}
		}
		contactList.add(c);
		count++;
		
		this.sortByName();
		this.saveFile();
	}
	
	public void addContact(String name, String number, String adress) {
		this.addContact(new Contact(name, number, adress));
	}
	
	// Odebrat
	public boolean deleteContact(String name) {
		for (Contact c : contactList) {
			if (c.getName().equals(name)) {
				contactList.remove(c);
				count--;
				this.saveFile();
				
				return true;
			}
		}
		return false;
	}
	
	// Smazat vse
	public void deleteAllContacts() {
		contactList.removeAll(contactList);
		count = 0;
		this.deleteFile();
	}
	
	// Smazat soubor
	private void deleteFile() {
		File f = new File(filePath);
		f.delete();
	}
	
	// Vypis
	private String header = "jmeno                          |          cislo | "
					+ "                adresa\n--------------------------------"
					+ "----------------------------------------\n";
	
	public String tablePrint() {
		if (contactList.isEmpty()) {
			return "Databaze je prazdna.\n";
		}
		
		StringBuilder result = new StringBuilder();
		result.append(header);
		
		for (Contact c : contactList) {
			result.append(c.toLine());
			result.append("\n");
		}
		result.append("Celkove kontaktu: " + count + "\n");
		
		return result.toString();
	}
	
	// Hledat
	public String searchPrint(String s) {
		StringBuilder result = new StringBuilder();
		boolean found = false;
		
		for (Contact c : contactList) {
			if (c.getName().toLowerCase().contains(s.toLowerCase())) {
				if (!found) {
					result.append(header);
					found = true;
				}
				result.append(c.toLine());
				result.append("\n");
			}
		}
		if (!found) {
			result.append("Zadny kontakt nenalezen.\n");
		}
		return result.toString();
	}
	
	// Setridit
	private void sortByName() {
		Collections.sort(contactList, new Comparator<Contact>() {
		    public int compare(Contact c1, Contact c2) {
		        return c1.getName().compareToIgnoreCase(c2.getName());
		    }
		});
	}
	
	// Zapis
	private void saveFile() {
		try (FileWriter file = new FileWriter(filePath)) {
			file.write(this.toString());
		}
		catch (Exception e) {
			System.out.print("Nastala chyba pri zapisovani do souboru.\n");
		}
	}
	
	// Nacteni
	private void loadFile() {
		String databaseString = fileToText(filePath);
		
		if (databaseString.length() > 5) {
			String[] lines = databaseString.split("\n");
			
			for (String line : lines) {
				String[] parts = line.split(" - ");
				
				contactList.add(new Contact(parts[0], parts[1], parts[2]));
				count++;
			}
		}
	}
	
	// Soubor do textu
	private static String fileToText(String path) {
		StringBuilder result = new StringBuilder();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = null;
			
			while ((line = br.readLine()) != null) {
				result.append(line);
				result.append("\n");
			}
			return result.toString();
		} 
		catch (FileNotFoundException e) {
			return "";
		} 
		catch (IOException e) {
			return "";
		}
	}
	
	private static boolean isNameOfFile (String name) {
		if (name.length() > 4) {
			if ((name.charAt(name.length() - 1) == 't') && 
				(name.charAt(name.length() - 2) == 'x') && 
				(name.charAt(name.length() - 3) == 't') && 
				(name.charAt(name.length() - 4) == '.')) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (Contact c : contactList) {
			result.append(c.toString());
			result.append("\n");
		}
		return result.toString();
	}
}
