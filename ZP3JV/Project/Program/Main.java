package Program;

import java.util.Scanner;
import Stuff.*;

/**
 * Trida Main - obsahuje funkci main se smyckou pro obsluhu programu pracujiciho s databazi kontaktu.
 * @author Josef Podstata
 * @version 1.0
 */
public class Main {
	
	private static Database currentDatabase;
	
	private static boolean isNumber(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (!(Character.isDigit(s.charAt(i)))) {
				return false;
			}
		}
		return true;
	}
	
	// Pridat
	public static void add() {
		Scanner in = new Scanner(System.in);
		String name, number, adress;
		
		System.out.print("Zadej cele jmeno kontaktu: \n");
		while (true) {
			name = in.nextLine();
			if (name.equalsIgnoreCase(""))
				System.out.print("Chyba, jmeno je povinne:\n");
			else
				break;
		} 
		System.out.print("Zadej telefoni cislo: \n");
		number = getNumber();
		System.out.print("Zadej adresu: \n");
		adress = in.nextLine();
		
		currentDatabase.addContact(name, number, adress);
		System.out.print("Kontakt byl ulozen.\n");
	}
	
	private static String getNumber() {
		Scanner in = new Scanner(System.in);
		String number;
		while (true) {
			number = in.nextLine();
			if (isNumber(number))
				break;
			else
				System.out.print("Chyba, cislo je ve spatnem formatu. " +
					"Jsou povolene pouze znaky 0-9. Zkuste to znovu:\n");
		}
		return number;
	}
	
	// Odebrat
	public static void remove() {
		Scanner in = new Scanner(System.in);
		System.out.print("Zadej cele jmeno kontaktu: \n");
		String name = in.nextLine();
		
		if (name.equalsIgnoreCase("vse")) {
			System.out.print("Opravdu smazat celou databazi? (ano/ne)\n");
			if (in.nextLine().equalsIgnoreCase("ano")) {
				currentDatabase.deleteAllContacts();
				System.out.print("Databaze byla smazana.\n");
			} else {
				System.out.print("Databaze nebyla smazana.\n");
			}
		} else if (currentDatabase.deleteContact(name)) {
			System.out.print("Kontakt byl odebran.\n");
		} else {
			System.out.print("Kontakt se nepodarilo odebrat.\n");
		}
	}
	
	// Upravit
	public static void edit() {
		Scanner in = new Scanner(System.in);
		System.out.print("Zadej cele jmeno kontaktu, ktery chcete upravit: \n");
		Contact c = findInDatabase(in.nextLine());
		
		if (c != null) {
			System.out.print("Stare cislo je " + c.getNumber() + ", zadej nove:\n");
			String number = getNumber();
			if (number.length() != 0) {
				c.setNumber(number);
			}

			System.out.print("Stara adresa je " + c.getAdress() + ", zadej novou:\n");
			String adress = in.nextLine();
			if (adress.length() != 0) {
				c.setAdress(adress);
			}
			System.out.print("Kontakt byl pozmenen.\n");
		}
		else {
			System.out.print("Kontakt nenalezen.\n");
		}
		
	}
	
	private static Contact findInDatabase(String name) {
		for (Contact c : currentDatabase.getList()) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	
	
	// Hledat
	public static void search() {
		Scanner in = new Scanner(System.in);
		System.out.print("Zadej jmeno kontaktu: \n");
		String name = in.nextLine();
		
		System.out.print(currentDatabase.searchPrint(name));
	}
	
	// Zmena
	public static void changeDatabase() {
		System.out.print("Zadej nazev nove databaze: \n");
		Scanner in = new Scanner(System.in);
		currentDatabase = new Database(in.nextLine());
		
		if (currentDatabase.getCount() > 0) {
			System.out.print("Databaze " + currentDatabase.getPath()
					+ " byla nactena.\n");
		}
		else {
			System.out.print("Zalozili jste novou databazi "
					+ currentDatabase.getPath() + ".\n");
		}
	}
	
	// Sloucit
	public static void addDatabase() {
		System.out.print("Zadej nazev databaze, kterou chcete pridat do te aktualni:\n");
		Scanner in = new Scanner(System.in);
		Database added = new Database(in.nextLine());
		int before = currentDatabase.getCount();
		
		if (added.getCount() == 0) {
			System.out.print("Takova databaze neexistuje.\n");
			return;
		}
		for (Contact c : added.getList()) {
			currentDatabase.addContact(c);
		}
		System.out.print("Bylo pridano " + (currentDatabase.getCount() - before) + 
				" kontaktu z databaze " + added.getPath() + ".\n");
	}
	
	// ?
	public static void help() {
		System.out.print("Napoveda:\n\"pridat\"  - prida kontakt podle zadanych informaci "
				+ ", cislo i adresa je nepovinny udaj (staci potvrdit prazdny radek)\n\""
				+ "odebrat\" - odebere kontakt podle zadaneho jmena ze vstupu, pro smazani"
				+ " cele databaze zadejte jako jmeno \"vse\"\n\"hledat\"  - podle zadaneho"
				+ " jmena najde vsechny odpovidajici kontakty a vypise\n\"vypis\"   - " 
				+ "vypise celou databazi kontaktu v tabulce\n\"zmena\"   - zmena databaze"
				+ "\n\"sloucit\" - slouci jiz existujici databazi s tou aktualni\n\"konec\""
				+ "   - ukonci program\n");
	}
	
	public static void start() {
		Scanner in = new Scanner(System.in);
		System.out.print("! Vitej v databazi kontaktu !\n");
		System.out.print("Zadej nazev nove nebo jiz existujici databaze:\n");
		
		currentDatabase = new Database(in.nextLine());
		System.out.print("Nyni muzes zadatavat prikazy. Pro napovedu zadej prikaz \"?\".\n");
	}
	
	// Main
	public static void main(String[] args) {
		start();
		Scanner in = new Scanner(System.in);
		String consoleIn;

		while (true) {
			consoleIn = in.nextLine();
			
			switch (consoleIn) {
			case "?":
				help();
				break;
			case "pridat":
				add();
				break;
			case "odebrat":
				remove();
				break;
			case "upravit":
				edit();
				break;
			case "hledat":
				search();
				break;
			case "vypis":
				System.out.print(currentDatabase.tablePrint());
				break;
			case "zmena":
				changeDatabase();
				break;
			case "sloucit":
				addDatabase();
				break;
			case "konec":
				System.out.print("Ukoncuji program.\n");
				return;
			default:
				System.out.print("Neznamy prikaz. Pro napovedu zadej \"?\".\n");
				break;
			}
		}
	}
}
