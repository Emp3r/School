package Program;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Projekt do ZP3JV - Databaze kontaktu
 * @author Josef Podstata
 * @version 0.1
 */
public class OldVersion {

	/**
	 * Cesta k souboru, do ktereho se databaze bude ukladat.
	 */
	private static final String FILE_PATH = "databaze.txt";
	
	/**
	 * Metoda testuje, zda je retezec slozen pouze ze znaku 0 az 9.
	 * @param s - vstupni retezec
	 */
	private static boolean isNumber(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (!(Character.isDigit(s.charAt(i)))) {
				return false;
			}
		}
		return true;
	}

	// SORT
	/**
	 * Metoda seradi vsechny zaznamy v databazi podle abecedy.
	 * Mela by byt zavolana po kazdem pridani kontaktu do databaze.
	 */
	private static void seraditDatabazi() {
		String all = textDatabaze();
		String[] parts = all.split("\n");
		
		Arrays.sort(parts);
		
		try (FileWriter file = new FileWriter(FILE_PATH)) {
			for (int i = 0; i < parts.length; i++) {
				file.write(parts[i]);
				file.write("\n");
			}
		} 
		catch (Exception e) {
			System.out.print("Nastala chyba pri zapisu do souboru.\n");
		}
	}
	
	// PRIDAT
	/**
	 * Metoda prida na konec souboru novy zaznam o kontaktu podle predanych informaci.
	 * @param jmeno - retezec o delce nejlepe do 32 znaku (kvuli tabulce na vypisu)
	 * @param cislo - retezec skladajici se pouze z cisel (0-9), nejlepe do 14 znaku
	 * @param adresa - retezec o delce nejlepe do 22 znaku
	 * @return <i>true</i> jestli metoda probehla uspesne, <i>false</i> jestli ne.
	 */
	public static boolean pridatKontakt(String jmeno, String cislo, String adresa) {
		try (FileWriter file = new FileWriter(FILE_PATH, true)) {
			file.write(jmeno + " - ");
			file.write(cislo + " - ");
			if (adresa.equals("")) {
				file.write("n/a\n");
			} else {
				file.write(adresa + "\n");
			}
			return true; 
		} 
		catch (Exception e) {
			System.out.print("Nastala chyba pri zapisovani do souboru.\n");
			return false;
		}
	}
	
	/**
	 * Metoda nacte ze vstupu informace (jmeno, cislo a adresu) a vola metodu 
	 * pridatKontakt, ktera podle ziskanych informaci vytvori novy zaznam v databazi.
	 */
	public static void pridat() {
		Scanner in = new Scanner(System.in);
		String jmeno, cislo, adresa;
		
		System.out.print("Zadej cele jmeno kontaktu: \n");
		jmeno = in.nextLine();
		System.out.print("Zadej telefoni cislo: \n");
		while (true) {
			cislo = in.nextLine();
			if (isNumber(cislo))
				break;
			else
				System.out.print("Chyba, cislo je ve spatnem formatu. " +
						"Jsou povolene pouze znaky 0-9. Zkuste to znovu:\n");
		} 
		System.out.print("Zadej adresu: \n");
		adresa = in.nextLine();
		if (pridatKontakt(jmeno, cislo, adresa)) {
			System.out.print("Kontakt byl ulozen.\n");
		} else {
			System.out.print("Kontakt se nepodarilo ulozit.\n");
		}
	}
	
	// ODEBRAT
	/**
	 * Metoda odebere kontakt z databaze podle predaneho retezce <b>jemno</b>. Predany 
	 * retezec musi odpovidat celemu jmenu kontaktu. Metoda funguje tak, ze do docasneho
	 * souboru prepise vsechny zaznamy krome toho, ktery chceme smazat. Pak puvodni 
	 * soubor smaze a novy prepise na puvodni jmeno.
	 * @param jmeno - retezec musi odpovidat jmenu kontaktu, vcetne malych/velkych pismen
	 * @return <i>true</i> jestli metoda probehla uspesne, <i>false</i> jestli ne.
	 */
	public static boolean odebratKontakt(String jmeno) {
		boolean result = false;
		try {
			File inFile = new File(FILE_PATH);
			File tempFile = new File("databaze_temp.txt");
			BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
			PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (!(line.split(" - ")[0].equals(jmeno))) {
					pw.println(line);
					pw.flush();
				} else {
					result = true;
				}
			}
			pw.close();
			br.close();
			if (!(inFile.delete())) {
				System.out.println("Nepodarilo se smazat puvodni soubor.\n");
				return false;
			}
			if (!(tempFile.renameTo(inFile))) {
				System.out.println("Nepodarilo se prejmenovat soubor.\n");
			}
		} catch (FileNotFoundException e) {
			System.out.print("Chyba. Soubor nebyl nalezen.\n");
		} catch (IOException e) {
			System.out.print("Nastala chyba pri odebirani kontaktu.\n");
		}
		return result;
	}
	
	// SMAZAT VSE
	/**
	 * Metoda smaze cely soubor, do ktereho se databaze uklada.
	 * @return <i>true</i> jestli metoda probehla uspesne, <i>false</i> jestli ne.
	 */
	public static boolean smazatVse() {
		try (FileWriter file = new FileWriter(FILE_PATH, false)) {
			return true;
		} 
		catch (IOException e) {
			System.out.print("Chyba. Nepodarilo se smazat databazi.\n");
			return false;
		} 
	}
	
	/**
	 * Metoda nacte ze vstupu cele jmeno kontaktu, ktery chceme smazat z databaze. 
	 * Pote zavola metodu odebratKontakt s predanym retezcem.
	 */
	public static void odebrat() {
		Scanner in = new Scanner(System.in);
		System.out.print("Zadej cele jmeno kontaktu: \n");
		String jmeno = in.nextLine();
		
		if (jmeno.equals("vse")) {
			System.out.print("Opravdu smazat celou databazi?\n");
			if (in.nextLine().equals("ano")) {
				if (smazatVse()) {
					System.out.print("Databaze byla smazana.\n");
				}
			}
		} else if (odebratKontakt(jmeno)) {
			System.out.print("Kontakt byl odebran.\n");
		} else {
			System.out.print("Kontakt se nepodarilo odebrat.\n");
		}
	}
	
	// HLEDAT
	/**
	 * Metoda najde a vypise hledany kontakt podle predaneho retezce <b>s</b>. 
	 * Staci zadat pouze cast jmena (krestni / prijmeni), metoda vypise vsechny 
	 * kontakty, ktere se s tim shoduji.
	 * @param s - hledane jmeno (staci krestni nebo prijmeni)
	 * @return <i>true</i> jestli metoda probehla uspesne, <i>false</i> jestli ne.
	 */
	public static boolean hledatKontakt(String s) {
		boolean result = false;
		try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
			StringBuilder out = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] part = line.split(" - ");
				String[] parts = part[0].split(" ");
				if (part[0].equalsIgnoreCase(s)) {
					out.append(line + "\n");
					result = true;
				} else if (parts.length > 1) {
					for (int i = 0; i < parts.length; i++) {
						if (parts[i].equalsIgnoreCase(s)) {
							out.append(line + "\n");
							result = true;
						}
					}
				}
			}
			if (result) {
				System.out.print("Nalezene kontakty:\n" + out.toString());
			}
		} catch (FileNotFoundException e) {
			System.out.print("Chyba. Soubor nebyl nalezen.\n");
		} catch (IOException e) {
			System.out.print("Chyba pri cteni ze souboru.\n");
		}
		return result;
	}
	
	/**
	 * Metoda nacte ze vstupu hledane jmeno a s timto argumentem zavola metodu 
	 * hledatKontakt. Ta vypise vsechny nalezena jmena. 
	 */
	public static void hledat() {
		Scanner in = new Scanner(System.in);

		System.out.print("Zadej jmeno kontaktu: \n");
		String jmeno = in.nextLine();
		
		if (!(hledatKontakt(jmeno))) {
			System.out.print("Kontakt se nepodarilo nalezt.\n");
		}
	}

	// VYPIS
	/**
	 * Metoda vypise do uhledne tabulky cely obsah databaze. Kvuli rozmerum tabulky je
	 * lepsi pri vkladani kontaktu dodrzovat maximalni pocet znaku. U jmena to je 32,
	 * u cisla 14 a u adresy 22.
	 */
	public static void vypisDatabaze() {
		String text = textDatabaze();
		
		if (text.length() < 3) {
			System.out.print("Databaze je prazdna.\n");
		}
		else {
			String[] lines = text.split("\n");
			
			System.out.printf("%-32s | %14s | %22s\n", "jmeno", "cislo", "adresa");
			System.out.print("--------------------------------------------------"
							+ "------------------------\n");

			for (int i = 0; i < lines.length; i++) {
				String[] parts = lines[i].split(" - ");
				System.out.printf("%-32s | %14s | %22s\n", parts[0], parts[1], parts[2]);
			}
		}
	}
	
	/**
	 * Metoda precte cely soubor, do ktereho se databaze uklada, a vrati ho jako retezec.
	 * @return - cely obsah databaze ve forme retezce
	 */
	public static String textDatabaze() {
		StringBuilder result = new StringBuilder();
		
		try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
			String line = null;
			
			while ((line = br.readLine()) != null) {
				result.append(line);
				result.append("\n");
			}
			
		} catch (FileNotFoundException e) {
			System.out.print("Chyba. Soubor nebyl nalezen.\n");
		} catch (IOException e) {
			System.out.print("Chyba pri cteni ze souboru.\n");
		}
		return result.toString();
	}

	/**
	 * Metoda vypise do konzole napovedu k ovladani programu.
	 */
	public static void vypisNapovedy() {
		System.out.print("Napoveda:\n\"pridat\" - prida kontakt podle zadanych informaci "
				+ "ze vstupu, adresa je nepovinny udaj (staci potvrdit prazdny radek)\n\""
				+ "odebrat\" - odebere kontakt podle zadaneho jmena ze vstupu, pro smazani"
				+ " cele databaze zadejte jako jmeno \"vse\"\n\"hledat\" - podle zadaneho "
				+ "jmena najde vsechny odpovidajici kontakty a vypise\n\"vypis\" - vypise"
				+ " celou databazi kontaktu v tabulce\n\"konec\" - ukonci program\n");
	}
	
	
	// MAIN
	public static void main(String[] args) {
		System.out.print("Vitej v databazi kontaktu.\nPro pridani kontaktu zadej prikaz \"pritdat\""
				+ ", pro odebrani \"odebrat\", pro vyhledani \"hledat\", pro vypis cele databaze "
				+ "\"vypis\", pro napovedu \"?\" a pro ukonceni programu \"konec\".\n");
		
		Scanner in = new Scanner(System.in);
		String vstup;

		while (true) {
			vstup = in.nextLine();

			switch (vstup) {
			case "?":
				vypisNapovedy();
				break;
				
			case "pridat":
				pridat();
				seraditDatabazi();
				break;
				
			case "odebrat":
				odebrat();
				break;
				
			case "hledat":
				hledat();
				break;
				
			case "vypis":
				vypisDatabaze();
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
