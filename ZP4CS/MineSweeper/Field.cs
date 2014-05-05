using System;

namespace MineSweeper
{
	public class Field
	{
		private bool mine;
		private int minesAround;
		private bool clicked;
		private bool flag;

		public Field ()
		{
			this.mine = false;
			this.minesAround = 0;
			this.clicked = false;
			this.flag = false;
		}

		public bool IsMine () 
		{
			return mine;
		}

		// pro snadnější výpočet min kolem pole
		public int IsMineInt ()
		{	
			if (mine)
				return 1;
			else
				return 0;
		}

		public void SetMine (bool m) 
		{
			this.mine = m;
		}

		public int GetMines () 
		{
			return minesAround;
		}

		public void SetMines (int b)
		{
			this.minesAround = b;
		}

		public bool GetClicked ()
		{
			return clicked;
		}

		public int Click ()
		{
			// pokud je pole označené vlajkou, nemůže se na něj kliknout
			if (!flag) {
				clicked = true;
				// pokud se stouplo na minu, prohra se signalizuje číslem -1, jinak vrací počet min kolem pole
				if (mine)
					return -1;
				else
					return minesAround;
			} else {
				return -2;
			}
		}
		
		// nastaví vlajku - pokud není pole odkliknuté, neguje se ovlajkování
		public void SetFlag ()
		{
			if (!clicked) {
				flag = !flag;
			}
		}

		public override string ToString ()
		{
			if (flag)
				return "F";
			if (clicked) {
				if (mine) {
					return "X";
				} else if (minesAround == 0) {
					return " ";
				} else {
					return minesAround.ToString ();
				}
			} else {
				return "-";
			}
		}
	}
}

