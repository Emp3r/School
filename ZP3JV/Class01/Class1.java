package Class01;

import java.util.Scanner;

public class Class1 {

	public void trojuhelnik(int strana) {
		System.out.print("*\n");
		if (strana > 1) {
			for (int i = 0; i < (strana - 2); i++) {
				System.out.print("*");

				for (int j = 0; j < i; j++) {
					System.out.print(" ");
				}
				System.out.print("*\n");
			}
			for (int i = 0; i < strana; i++) {
				System.out.print("*");
			}
			System.out.print("\n");
		}
	}

	public void dane(double mzda) {
		double dan;
		System.out.print("Zadana mzda: " + mzda + "\n");

		if (mzda < 10000) {
			dan = mzda * 0.1;
		} else if (mzda < 20000) {
			dan = 1000 + ((mzda - 10000) * 0.2);
		} else {
			dan = 3000 + ((mzda - 20000) * 0.3);
		}
		System.out.print("Odpovidajici dan: " + dan + "\n");
	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in, "Windows-1250");
		Class1 obj = new Class1();

		// 1. Trojúhelník
		System.out.print("Zadej hodnotu: ");
		int vstup = Integer.parseInt(sc.nextLine());
		obj.trojuhelnik(vstup);

		// 2. Progresivní daÀ
		System.out.print("\nZadej mzdu: ");
		vstup = Integer.parseInt(sc.nextLine());
		obj.dane(vstup);

		// 3. Pole (min a max)
		int[] pole = { 10, 2, 13, -4, 15, -6, 7 };
		int nejvetsi = pole[0];
		int nejmensi = pole[0];
		for (int i = 1; i < 7; i++) {
			if (nejmensi > pole[i]) {
				nejmensi = pole[i];
			}
			if (nejvetsi < pole[i]) {
				nejvetsi = pole[i];
			}
		}
		System.out.print("\nPole: ");
		for (int i = 0; i < 7; i++) {
			System.out.print(pole[i] + ", ");
		}
		System.out.print("\nNejmensi v poli: " + nejmensi + "\n");
		System.out.print("Nejvetsi v poli: " + nejvetsi + "\n");

		// 4. Prvočísla < 100
		System.out.print("\nPrvocisla mensi nez 100: 2");

		for (int i = 3; i < 100; i += 2) {
			int j;
			for (j = 3; j <= i; j++) {
				if (i % j == 0) {
					break;
				}
			}
			if (i == j) {
				System.out.print(", " + i);
			}
		}

	}
}
