package Class05;

import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Stack;

public class Class5 {

	// 1.
	public static String repeatChar1(char c, int n) {
		String result = new String();

		for (int i = 0; i < n; i++) {
			result += c;
		}
		return result;
	}

	public static String repeatChar2(char c, int n) {
		StringBuilder result = new StringBuilder(n);

		for (int i = 0; i < n; i++) {
			result.append(c);
		}
		return result.toString();
	}

	// 2.
	public static Map<String, Integer> freq(String s) {
		String[] words = s.split("[\\d+\\W]");
		Map<String, Integer> result = new TreeMap<String, Integer>();

		for (String w : words) {
			Integer temp = result.get(w);
			if (w.isEmpty() == false) {
				if (temp == null) {
					temp = 0;
				}
				temp++;
				result.put(w, temp);
			}
		}
		return result;
	}

	// 3.
	public static Map<String, Integer> freqIgnoreCase(String s) {
		return (freq(s.toLowerCase()));
	}

	// 4.
	public static int rpnCalc(String expr) {
		Stack<Integer> stack = new Stack<Integer>();
		String[] exprs = expr.split(" ");

		for (String s : exprs) {
			if (!(s.equals("+") || s.equals("-") || s.equals("*") || s .equals("/"))) {
				stack.push(Integer.valueOf(s));
			} else {
				int number1 = stack.pop();
				int number2 = stack.pop();
				switch (s) {
				case "+":
					stack.push(number1 + number2);
					break;
				case "-":
					stack.push(number2 - number1);
					break;
				case "*":
					stack.push(number1 * number2);
					break;
				case "/":
					stack.push(number2 / number1);
					break;
				}
			}
		}
		return stack.pop();
	}
	// 5.
	public static int rpnCalc(String expr, Map<String, Integer> variables) {
		StringBuilder result = new StringBuilder();
		String[] exprs = expr.split(" ");
		
		for (String s : exprs) {
			if (variables.containsKey(s)) {
				result.append(variables.get(s));
				result.append(" ");
			}
			else if (s.equals("+") || s.equals("-") || s.equals("*") || s .equals("/")) {
				result.append(s);
				result.append(" ");
			} 
			else {
				for (int i = 0; i < s.length(); i++) {
					if (Character.isDigit(s.charAt(i))) {
						if (i == s.length() - 1) { 
							result.append(s);
							result.append(" ");
						}
					}
					else { 
						System.out.print("\nBad syntax.");
						return 0;
					}
				}
			}
		}
		return rpnCalc(result.toString());
	}
	
	/* TESTS
	public static void main(String[] args) {
		// 1.
		double time1 = System.currentTimeMillis();
		// repeatChar1('c', 100000);
		double time2 = System.currentTimeMillis() - time1;
		System.out.print("repeatChar1: " + time2);

		time1 = System.currentTimeMillis();
		// repeatChar2('c', 100000);
		time2 = System.currentTimeMillis() - time1;
		System.out.print("\nrepeatChar2: " + time2);

		// 2. and 3.
		// Map<String, Integer> map1 = freq("Ahoj 42!! , . ahojahoj 42ahoj ahoj 42 AHOJ ah42oj.");
		// Map<String, Integer> map2 = freqIgnoreCase("Ahoj 42!! , . ahojahoj 42ahoj ahoj 42 AHOJ ah42oj.");
		System.out.print("\n\n");
		
		// 4.
		System.out.print("1 32 + 42 * 5 + 66 -  =  " + rpnCalc("1 32 + 42 * 5 + 66 -"));
		
		Map<String, Integer> hm = new HashMap<>(); 
        hm.put("foo", 32); 
        hm.put("bar", 66); 
		System.out.print("\nfoo = 32, bar = 66");
        System.out.print("\n1 foo + 42 * 5 + bar -  =  " + rpnCalc("1 foo + 42 * 5 + bar -", hm)); 
        
	}
	*/
}
