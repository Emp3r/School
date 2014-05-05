using System;
using System.Text;

namespace MineSweeper
{
	public class Board
	{
		private int width;
		private int height;
		private int allFields;
		private int mineFields;
		private int clearFields;
		private Field[,] fields;

		public Board (int w, int h, int dificulty)
		{
			this.width = w;
			this.height = h;
			this.fields = new Field[w, h];
			this.allFields = w * h;
			// obtížnost - čím vetší čislo proměnné dificulty, tím méně bomb na desce
			this.mineFields = allFields / dificulty;
			this.clearFields = allFields - mineFields;

			FillFields();
			SetMines();
			SetMinesAround();
		}

		public int GetAllFields ()
		{
			return allFields;
		}
		public int GetAllMineFields ()
		{
			return mineFields;
		}
		public int GetAllClearFields ()
		{
			return clearFields;
		}

		public Field[,] GetFields ()
		{
			return fields;
		}

		public int Click (int x, int y)
		{
			return fields[x, y].Click();
		}

		public void Flag (int x, int y)
		{
			fields[x, y].SetFlag();
		}

		// inicializace polí
		private void FillFields()
		{
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					fields[x, y] = new Field();
				}
			}
		}

		// inicializace min - náhodné rozmístění po celé desce
		private void SetMines()
		{
			Random rnd = new Random ();
			for (int i = 0; i < mineFields; i++) {
				int x = rnd.Next(0, width);
				int y = rnd.Next(0, height);

				// pokud pole na nahodných souřadnicích už mina je, vygeneruje se nové náhodne pole
				if (!fields [x, y].IsMine()) {
					fields [x, y].SetMine(true);
				} else {
					i--;
				}
			}

		}

		// výpočet min kolem každého políčka
		private void SetMinesAround() 
		{
			for (int x = 0; x < width; x++) {
				if (x == 0) {
					// levý horní roh
					fields[0, 0].SetMines(fields[x+1, 0].IsMineInt() + fields[x, 1].IsMineInt() + fields[x+1, 1].IsMineInt());
					// levý spodní roh
					fields[0, height-1].SetMines(fields[x+1, height-1].IsMineInt() + fields[x, height-2].IsMineInt() + fields[x+1, height-2].IsMineInt());
					// všechny nerohové pole nalevo
					for (int y = 1; y < height - 1; y++) {
						fields[x, y].SetMines(XisZero(x, y));
					}
				}
				else if (x == width - 1) {
					// pravý horní roh
					fields[x, 0].SetMines(fields[x-1, 0].IsMineInt() + fields[x, 1].IsMineInt() + fields[x-1, 1].IsMineInt());
					// pravý spodní roh
					fields[x, height-1].SetMines(fields[x-1, height-1].IsMineInt() + fields[x, height-2].IsMineInt() + fields[x-1, height-2].IsMineInt());
					// všechny nerohové pole napravo
					for (int y = 1; y < height - 1; y++) {
						fields[x, y].SetMines(XisWidth(x, y));
					}
				}
				else {
					// horní pole
					fields[x, 0].SetMines(YisZero(x, 0));
					// spodní pole
					fields[x, height-1].SetMines(YisHeight(x, height-1));
					// ostatní pole mezi (uprostřed, nedotýkají se žádné hrany desky)
					for (int y = 1; y < height-1; y++) {
						fields[x, y].SetMines(CountAllAround(x, y));
					}
				}
			}
		}

		// pomocné funkce pro výpočet min kolem pole
		private int XisZero (int x, int y)
		{
			return fields[x+1, y].IsMineInt() + fields[x, y+1].IsMineInt() + fields[x, y-1].IsMineInt() + fields[x+1, y+1].IsMineInt() + fields[x+1, y-1].IsMineInt();
		}
		private int XisWidth (int x, int y)
		{
			return fields[x-1, y].IsMineInt() + fields[x, y+1].IsMineInt() + fields[x, y-1].IsMineInt() + fields[x-1, y+1].IsMineInt() + fields[x-1, y-1].IsMineInt();
		}

		private int YisZero (int x, int y)
		{
			return fields[x+1, y].IsMineInt() + fields[x-1, y].IsMineInt() + fields[x, y+1].IsMineInt() + fields[x+1, y+1].IsMineInt() + fields[x-1, y+1].IsMineInt();
		}
		private int YisHeight (int x, int y)
		{
			return fields[x+1, y].IsMineInt() + fields[x-1, y].IsMineInt() + fields[x, y-1].IsMineInt() + fields[x+1, y-1].IsMineInt() + fields[x-1, y-1].IsMineInt();
		}

		// pokud se pole ani z jedné strany nedotýká hrany desky
		private int CountAllAround (int x, int y)
		{
			return fields[x+1, y].IsMineInt() + fields[x-1, y].IsMineInt() + fields[x, y+1].IsMineInt() + fields[x, y-1].IsMineInt() + 
				   fields[x+1, y+1].IsMineInt() + fields[x+1, y-1].IsMineInt() + fields[x-1, y+1].IsMineInt() + fields[x-1, y-1].IsMineInt();
		}

		public override string ToString ()
		{
			StringBuilder result = new StringBuilder ("  | ");

			for (int i = 0; i < width; i++) {
				char ch = (char)('a' + i);
				result.Append(ch + " ");
			}

			result.Append("\n");
			result.Append('-', width*2 + 3);
			
			result.Append("\n");

			for (int y = 0; y < height; y++) {
				result.Append((y+1) + (y+1 > 9 ? "| " : " | "));
				for (int x = 0; x < width; x++) {
					result.Append(fields[x, y].ToString());
					result.Append(" ");
				}
				result.Append("\n");
			}
			return result.ToString();
		}

	}
}

