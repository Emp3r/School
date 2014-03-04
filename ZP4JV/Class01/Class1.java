package Class01;

public class Class1 {

	// 2.
	public static double avg(double... list) {
		if (list.length == 0) {
			return Double.NaN;
		}
		else {
			double all = 0.0;
			for (int i = 0; i < list.length; i++)
				all += list[i];
			return all/list.length;
		}
	}
	
	private static boolean isNumber(char ch) {
		return (ch >= '0') && (ch <= '9');
	}
	// 3.
	public static String formatStr(String format, Object... args) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < format.length(); i++) {
			if (format.charAt(i) != '%') {
				result.append(format.charAt(i));
			} else if (i+1 < format.length() && isNumber(format.charAt(i+1))) {
				int j, index = 0;
				for (j = i+1; j < format.length() && isNumber(format.charAt(j)); j++) {
					index *= 10;
					index += Character.getNumericValue(format.charAt(j));
				}
				i = j-1;	
				if (index < args.length)
					result.append(args[index]);
				else
					result.append("<argument nenalezen>");
			} else {
				result.append("%");
			}
		}
		return result.toString();
	}
	
	
	public static void main(String[] args) {
		// 1.
		AnimalFarm af = new AnimalFarm();
		af.add("Al’k", AnimalKind.DOG, Gender.MALE);
		af.add("Bob’k", AnimalKind.DUCK, Gender.FEMALE);
		af.add("Chubaka", AnimalKind.DOG, Gender.FEMALE);
		af.add("Donald", AnimalKind.DUCK, Gender.MALE);
		af.list();
		
		// 2.
		System.out.println("\nPrumer 1, 2 a 6 je: " + avg(1, 2, 6));
		System.out.println("Prumer zadnych cisel je: " + avg());
		System.out.println("Prumer 90, 18 je: " + avg(90, 18));
		
		// 3.
		System.out.println("\n" + formatStr("A: %0; B: %1", 1, "XY"));
		System.out.println(formatStr("A: %0; B: %1; nezadano: %808; C: %0", "bla", 42));
		System.out.println(formatStr("Jmeno: %0 %1; procento:% %d %%", "A.", "Turing"));
	}
}
