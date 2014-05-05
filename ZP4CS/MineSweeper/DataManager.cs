using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Globalization;

namespace MineSweeper
{
	// stárá se o práci se souborem - uložení highscore, načtení ze souboru, výpis tabulky do stringu
	public class DataManager
	{
		// cesta k textovému souboru, do kterého se ukládá highscore
		string path = @"HS.txt";
		// aktuálně nejhorší score, které je v tabulce
		float lowest;
		List<Record> list = new List<Record>();

		private struct Record
		{
			public float score;
			public string player;
			public string date;
			
			public Record(float s, string p, string d) {
				this.score = s;
				this.player = p;
				this.date = d;
			}
		}

		public DataManager ()
		{
			// tohle změní separátor desetinných míst z defaultní čárky na tečku
			// s čárkou to totiž dělá problém při parsování ze stringu
			System.Globalization.CultureInfo customCulture;
			customCulture = (System.Globalization.CultureInfo)System.Threading.Thread.CurrentThread.CurrentCulture.Clone();
			customCulture.NumberFormat.NumberDecimalSeparator = ".";
			System.Threading.Thread.CurrentThread.CurrentCulture = customCulture;

			LoadFile();
			SortTable();
		}

		public float GetLowest() {
			return this.lowest;
		}

		public void WriteScore(float newScore, string name) 
		{
			try {
				if (newScore < lowest) {
					// na poslední místo v tabulce se zapíše nové score s aktuálním časem
					list[9] = new Record(newScore, name, DateTime.Now.ToShortDateString());

					// poté se tabulka setřídí a uloží
					SortTable();
					SaveFile();
				}
			} catch {
				FillFile();
				LoadFile();
			}
		}

		// výpis highscore v úhledné tabulce
		public string HighscoreTable() 
		{
			StringBuilder stringTable = new StringBuilder();

			// první řádek
			stringTable.Append(String.Format("     {0,-5}     {1,-12} {2}\n", "čas", "hráč", "datum"));

			// záznamy
			for (int i = 0; i < this.list.Count; i++) {
				stringTable.Append(String.Format("{0,-5}{1:f2}     {2,-12} {3}\n", (i+1).ToString()+".", list[i].score, list[i].player, list[i].date));
			}
			return stringTable.ToString();
		}

		private void SortTable() 
		{
			// setřízení záznamů podle vlastního delegáta
			list.Sort(delegate(Record r1, Record r2) { return r1.score.CompareTo(r2.score); });
		}

		// uloží záznamy z kolekce list do souboru daného stringem path
		private void SaveFile() 
		{
			StreamWriter sw = new StreamWriter(path);

			for (int i = 0; i < this.list.Count; i++) {
				sw.WriteLine(String.Format("{0:f2}     {1,-12} {2}", list[i].score, list[i].player, list[i].date));
			}
			sw.Close();
		}

		// načte záznamy z textového souboru daného stringem path
		private void LoadFile() 
		{
			try {
				StreamReader sr = new StreamReader(path);
				string line;
				while ((line = sr.ReadLine()) != null) {
					string[] cells = line.Split();
					string[] results = new string[4];

					// line.Split() rozdělí řádek podle separátoru mezera, pokud je ale za sebou více mezer, 
					// dává do výsledku prázdné řetězce - tento for vybere pouze neprázdné řetězce
					for (int i = 0, j = 0; i < cells.Length; i++) {
						if (cells[i] != "") {
							results[j] = cells[i];
							j++;
						}
					}
					list.Add(new Record(float.Parse(results[0], CultureInfo.InvariantCulture.NumberFormat), results[1], results[2]));
				}
				lowest = list[9].score;
				sr.Close();
			} catch {
				FillFile();
				LoadFile();
			}
		}

		private void FillFile() {
			list = new List<Record>();
			DateTime dt = DateTime.Now;
			for (int i = 0; i < 10; i++)
				list.Add(new Record((float)99.99, "?", dt.ToShortDateString()));
			SaveFile();
		}
	}
}

