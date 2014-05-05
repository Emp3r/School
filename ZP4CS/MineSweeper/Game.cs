using System;

namespace MineSweeper
{
	public class Game
	{
		private int width;
		private int height;
		private Board board;
		private int clearFields;
		private int cleared;
		private bool win;
		private bool loose;

		public Game (int w, int h, int dificulty)
		{
			this.width = w;
			this.height = h;
			this.board = new Board(width, height, dificulty);
			this.clearFields = board.GetAllClearFields();
			this.cleared = 0;
			this.win = false;
			this.loose = false;
		}

		public int GetWidth ()
		{
			return width;
		}
		public int GetHeight ()
		{
			return height;
		}

		public bool GetWin () 
		{
			return win; 
		}
		public bool GetLoose () 
		{
			return loose; 
		}

		public Board GetBoard ()
		{	
			return board;
		}

		public int GetClearFields () 
		{
			return clearFields;
		}
		public int GetCleared () 
		{
			return cleared;
		}

		public void Flag (int x, int y)
		{
			board.Flag(x, y);
		}

		public void Click (int x, int y)
		{
			try {
				if (!board.GetFields()[x, y].GetClicked()) {
					int c = board.Click(x, y);
						
					if (c == -2) {
						// pole je ovlajkované
						return;
					} 
					else if (c == -1) {
						// pole je mina
						loose = true;
						return;
					}
					cleared++;
	
					// pokud má pole 0 min kolem sebe, automaticky se 
					// odklikají všechny pole kolem něho
					if (c == 0)
						AutoClick(x, y);

					// pokud je stejný počet odkliknutých polí s celkovým 
					// počtem polí bez min, hráč vyhrál
					if (cleared == clearFields)
						win = true;
				}
			} catch { }
		}
		
		private void AutoClick (int x, int y) {
			if (x == 0) {
				if (y == 0) {
					Click (x, y + 1);
					Click (x + 1, y);
					Click (x + 1, y + 1);
				} else if (y == width - 1) {
					Click (x, y - 1);
					Click (x + 1, y);
					Click (x + 1, y - 1);
				} else {
					Click (x, y + 1);
					Click (x, y - 1);
					Click (x + 1, y);
					Click (x + 1, y + 1);
					Click (x + 1, y - 1);
				}
			} else if (x == width - 1) {
				if (y == 0) {
					Click (x, y + 1);
					Click (x - 1, y);
					Click (x - 1, y + 1);
				} else if (y == width - 1) {
					Click (x, y - 1);
					Click (x - 1, y);
					Click (x - 1, y - 1);
				} else {
					Click (x, y + 1);
					Click (x, y - 1);
					Click (x - 1, y);
					Click (x - 1, y + 1);
					Click (x - 1, y - 1);
				}
			} else if (y == 0) {
				Click (x, y + 1);
				Click (x + 1, y);
				Click (x - 1, y);
				Click (x + 1, y + 1);
				Click (x - 1, y + 1);
			} else if (y == height - 1) {
				Click (x, y - 1);
				Click (x + 1, y);
				Click (x - 1, y);
				Click (x + 1, y - 1);
				Click (x - 1, y - 1);
			} else {
				Click (x, y + 1);
				Click (x, y - 1);
				Click (x + 1, y);
				Click (x - 1, y);
				Click (x + 1, y + 1);
				Click (x + 1, y - 1);
				Click (x - 1, y + 1);
				Click (x - 1, y - 1);
			}
		}

	}
}

