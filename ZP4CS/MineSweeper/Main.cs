using System;
using System.Diagnostics;
using System.IO;

namespace MineSweeper
{
	class MainClass
	{
		private const int defaultSize = 8;
		
		private static Game g;
		private static DataManager dm;
		private static Stopwatch timer;
		private static bool isDefault;
		
		public static void Main (string[] args)
		{
			dm = new DataManager();
			Console.ForegroundColor = ConsoleColor.Red;
			Console.WriteLine ("TextMineSweeper");
			Console.ResetColor();
			PrintHelp();
			PrintStart();
			
			try {
				while (Start()) {
					Console.WriteLine("Znovu? (y/n)");
					string input = Console.ReadLine();
					switch (input) {
					case "y":
						continue;
					case "n":
						return;
					}
				}
			} catch (Exception e) {
				PrintGame();
				Console.WriteLine(e);
				Console.WriteLine("\nNastala fatální chyba. Program končí.");
			}
		}

		public static void Click (int[] input)
		{	
			g.Click(input[0], input[1]);
		}
		
		public static void Flag (int[] input)
		{	
			g.Flag(input[0], input[1]);
		}
		
		// zahájí hru a obsluhuje vstupy
		private static bool Start ()
		{
			if (WantCustom ()) {
				isDefault = false;
				InitializeGame ();
			} else {
				isDefault = true;
				g = new Game (defaultSize, defaultSize, 10);
				timer = new Stopwatch();
				timer.Start();
			}
			
			Console.Clear ();
			PrintGame();
			
			while (true) {
				string s = Console.ReadLine();
				switch (s) {
				case "k": 
					Console.WriteLine("Ukončuji program.");
					return false;
				case "n": 
					return true;
				case "s":
					Console.WriteLine(dm.HighscoreTable());
					break;
				case "?":
					PrintHelp();
					break;
				case "f":
					int[] inp = IsInput(Console.ReadLine());
					if (inp != null) {
						Flag (inp);
						Console.Clear();
						PrintGame();
					}
					break;
				default:
					if (IsInput(s) != null) {
						Click(IsInput(s));
						Console.Clear();
						PrintGame();
					}
					break;
				}
				
				if (CheckWinLoose())
					return true;
			}
		}
		
		// zkontroluje, jestli hra už neskončila, pokud ano, oznámí o tom a vrací true
		private static bool CheckWinLoose() {
			if (g.GetWin()) {
				if (isDefault) {
					timer.Stop();
					float score = (float)timer.ElapsedMilliseconds / 1000;
					Console.WriteLine("Gratuluji, vyhrál jste! Trvalo vám to {0} sekund.", score);
					
					if (score < dm.GetLowest()) {
						Console.WriteLine("Zadejte jméno hráče pro zápis do žebříčku nejlepších časů: ");
						dm.WriteScore(score, Console.ReadLine());
					}
				} else {
					Console.WriteLine("Gratuluji, vyhrál jste!");
				}
				return true;
			}
			else if (g.GetLoose()) {
				Console.WriteLine("BOOM! Konec hry.");
				return true;
			}
			return false;
		}
		
		private static bool WantCustom() {
			while (true) {
				Console.WriteLine ("Chcete vytvořit vlastní pole? (y/n)");
				string input = Console.ReadLine();
				
				switch (input) {
				case "y":
					PrintCustomHelp();
					return true;
				case "n":
					return false;
				default:
					Console.WriteLine ("Zadej pouze \"y\" pro souhlas nebo \"n\" pro nesouhlas.");
					continue;
				}
			}
		}
		
		// vypíše informace o stavu rozehrané hry
		private static void PrintHead () 
		{	
			Console.Write("odhaleno: ");
			Console.ForegroundColor = ConsoleColor.Yellow;
			Console.Write(g.GetCleared());
			Console.ResetColor();
			Console.Write(" \t zbývá odhalit: ");
			Console.ForegroundColor = ConsoleColor.Yellow;
			Console.WriteLine(g.GetClearFields() - g.GetCleared());
			Console.ResetColor();
		}
		
		// vypíše barevnou hrací desku
		private static void PrintGame ()
		{
			PrintHead();
			int i = 0;
			foreach (char c in g.GetBoard().ToString()) {
				if (i > 2) {
					switch (c) {
					case '1':
						Console.ForegroundColor = ConsoleColor.Blue;
						break;
					case '2':
						Console.ForegroundColor = ConsoleColor.Green;
						break;
					case '3':
						Console.ForegroundColor = ConsoleColor.Red;
						break;
					case '4':
						Console.ForegroundColor = ConsoleColor.Magenta;
						break;
					case '5':
						Console.ForegroundColor = ConsoleColor.Cyan;
						break;
					case '6':
						Console.ForegroundColor = ConsoleColor.DarkCyan;
						break;
					case '7':
						Console.ForegroundColor = ConsoleColor.DarkMagenta;
						break;
					case 'X':
						Console.ForegroundColor = ConsoleColor.Yellow;
						break;
					case '\n':
						i = -1;
						break;
					}
				}
				Console.Write(c);
				Console.ResetColor();
				i++;
			}
		}
		
		private static void PrintStart() {
			String size = defaultSize.ToString() + "x" + defaultSize.ToString();
			Console.WriteLine ("Můžete hrát na přednastaveném poli {0} a nebo vytvořit vlastní.", size);
		}
		
		private static void PrintHelp() {
			Console.WriteLine ("Na políčka se kliká zadáváním souřadnic ve tvaru xy nebo xy, takže například \"f12\"" +
			                   "je to samé, co \"12f\". Pokud místo souřadnic zadáte jen \"f\", tak následující zadané" +
			                   " souřadnice označí políčko vlajkou (F), aby jste věděli, že tam je mina a omylem na ni" +
			                   " nestoupli. Příkaz \"s\" vypíše tabulku nejlepších výsledků z přednastaveného pole. " + 
			                   "Příkaz \"n\" ukončí hru. Příkaz \"k\" ukončí program.");
		}
		
		private static void PrintCustomHelp() {
			Console.WriteLine ("Vlastní desku vytvoř o šířce a výšce v rozmezí 4-26 políček. U obtížnosti je stejné " +
			                   "rozmezí. Nižší číslo znamená větši obtížnost a naopak větší číslo menší obtížnost.");
		}
		
		// pro správný vstup vrací pole se souřadnicemi x a y na prvním
		// a druhém prvku pole, pro špatný vstup vrací null
		private static int[] IsInput(string s) {
			if (s.Length == 2) {
				char first = s.ToCharArray () [0];
				char second = s.ToCharArray () [1];
				
				if (first >= 'a' && first < 'a' + g.GetWidth () &&
				    second >= '1' && second <= '9') {
					return new int[2] {first - 'a', second - '1'};
				} 
				else if (second >= 'a' && second < 'a' + g.GetWidth () &&
				         first >= '1' && first <= '9') {
					return new int[2] {second - 'a', first - '1'};
				}
			}
			else if (s.Length == 3) {
				char first = s.ToCharArray () [0];
				char second	= s.ToCharArray () [1];
				char third = s.ToCharArray () [2];
				
				if (first >= 'a' && first < 'a' + g.GetWidth ()) {
					if ((second >= '0' && second <= '0' + g.GetHeight() / 10) && 
					    (third >= '0' && third <= '9')) {
						int y = (second - '0') * 10 + (third - '1');
						if (y > g.GetHeight()) return null;
						return new int[2] {first - 'a', y};
					}
				}
				else if (third >= 'a' && third < 'a' + g.GetWidth ()) {
					if ((first >= '0' && first <= '0' + g.GetHeight() / 10) && 
					    (second >= '0' && second <= '9')) {
						int y = (first - '0') * 10 + (second - '1');
						if (y > g.GetHeight()) return null;
						return new int[2] {third - 'a', y};
					}
				}
			}
			return null;
		}
		
		// podle vstupů inicializuje vlastní hrací desku
		private static void InitializeGame() {
			int x, y, d;
			while (true) {
				Console.WriteLine("Zadej šířku: ");
				string s = Console.ReadLine ();
				if (IsSizeNumber (s)) {
					x = Convert.ToInt32 (s);
					break;
				} else {
					Console.WriteLine("Chyba. Zadávej pouze čísla v rozmezí 4-26.");
					continue;
				}
			}
			while (true) {
				Console.WriteLine("Zadej výšku: ");
				string s = Console.ReadLine ();
				if (IsSizeNumber (s)) {
					y = Convert.ToInt32 (s);
					break;
				} else {
					Console.WriteLine("Chyba. Zadávej pouze čísla v rozmezí 4-26.");
					continue;
				}
			}
			while (true) {
				Console.WriteLine("A obtížnost: ");
				string s = Console.ReadLine ();
				if (IsSizeNumber (s) ) {
					d = Convert.ToInt32 (s);
					break;
				} else {
					Console.WriteLine("Chyba. Zadávej pouze čísla v rozmezí 4-26.");
					continue;
				}
			}
			g = new Game(x, y, d);
		}
		
		// zjistí, jestli je číslo v rozmezí 4-26
		private static bool IsSizeNumber(string s) {
			return (s.Length == 1 && s[0] >= '4' && s[0] <= '9')
				|| (s.Length == 2 && s[0] >= '1' && s[0] <= '3' 
				    && s[1] >= '0' && s[1] <= '9' &&
				    ((s[0] - '0') * 10 + (s[1] - '0')) <= 26);
		}
	}
}
