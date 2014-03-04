package Class06;

import java.util.Stack;
import java.util.Map;
import java.util.HashMap;
//import java.lang.Exception;;

/**
 * Java - ukol 06. Vylepsena RPN kalkulacka, vyjimky a dokumentace.
 * @author Josef Podstata
 * @since 1.0
 */
public class Class6 {
	/**
	 * Metoda testuje, jestli parametr <i>e</i> znazornuje operator. Povolene: 
	 * +, -, *, /, <, >, <=, >=, =, != a ternarni operator ?.
	 * @param e
	 * @return true / false
	 */
	private static boolean isOperator(String e) {
		if (e.equals("+") || e.equals("-") || e.equals("*") || e.equals("/") ||
			e.equals("<") || e.equals(">") || e.equals("<=") || e.equals(">=") ||
			e.equals("=") || e.equals("!=") || e.equals("?")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Metoda testuje, jestli parametr <i>e</i> znazornuje pravdivostni hodnotu.
	 * Podporovane: #t, #f, true, false.
	 * @param e
	 * @return true / false
	 */
	private static boolean isBoolean(String e) {
		if (e.equalsIgnoreCase("#t") || e.equalsIgnoreCase("#f") || 
			e.equalsIgnoreCase("true") || e.equalsIgnoreCase("false")) {
			return true;
		}
		return false;
	}

	/**
	 * Metoda testuje, jestli je parametr <i>e</i> cislo. Napriklad 
	 * ve formatu 42, -42, 4.2, -44.532.
	 * @param e
	 * @return true / false
	 */
	private static boolean isNumber(String e) {
		boolean isDouble = false;
		if (Character.isDigit(e.charAt(0)) || (e.charAt(0) == '-' && (e.length() != 1))) {
			for (int i = 1; i < e.length(); i++) {
				if (e.charAt(i) == '.') {
					if (isDouble)
						return false;
					isDouble = true;
				} else if (!(Character.isDigit(e.charAt(i))))
					return false;
			}
		} else {
			return false;
		}
		return true;
	}
	
	/**
	 * Metoda testuje, jestli je parametr <i>e</i> cislo typu Integer.
	 * @param e
	 * @return true / false
	 */
	private static boolean isInteger(Object e) {
		if (e instanceof Number) {
			if ((double)e % 1 == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Metoda scita dva objekty typu Number.
	 * @throws WrongInputException
	 */
	private static Object add(Object o1, Object o2) throws WrongInputException {
		if (o1 instanceof Number && o2 instanceof Number) {
			return (double)o1 + (double)o2;
		} else {
			throw new WrongInputException();
		} 
	}

	/**
	 * Metoda odcita dva objekty typu Number.
	 * @throws WrongInputException
	 */
	private static Object subtract(Object o1, Object o2) throws WrongInputException {
		if (o1 instanceof Number && o2 instanceof Number) {
			return (double)o1 - (double)o2;
		} else {
			throw new WrongInputException();
		} 
	}

	/**
	 * Metoda nasobi dva objekty typu Number.
	 * @throws WrongInputException
	 */
	private static Object multiply(Object o1, Object o2) throws WrongInputException {
		if (o1 instanceof Number && o2 instanceof Number) {
			return (double)o1 * (double)o2;
		} else {
			throw new WrongInputException();
		} 
	}

	/**
	 * Metoda deli dva objekty typu Number.
	 * @throws WrongInputException
	 */
	private static Object divide(Object o1, Object o2) throws WrongInputException, DivideByZeroException {
		if (o1 instanceof Number && o2 instanceof Number) {
			if ((double)o2 == 0) 
				throw new DivideByZeroException();
			return (double)o1 / (double)o2;
		} else {
			throw new WrongInputException();
		} 
	}
	
	/**
	 * Metoda porovnava dva objekty pomoci operatoru mensi (<).
	 * @throws IncomparableValuesException
	 */
	private static Object less(Object o1, Object o2) throws IncomparableValuesException {
		if (o1 instanceof Number && o2 instanceof Number) {
			return ((double)o1 < (double)o2);
		} else if (o1 instanceof Boolean && o2 instanceof Boolean) {
			if ((Boolean)o1 == false && (Boolean)o2 == true) {
				return (new Boolean(true));
			} else {
				return (new Boolean(false));
			}
		} else {
			throw new IncomparableValuesException();
		}
	}

	/**
	 * Metoda porovnava dva objekty pomoci operatoru vetsi (>).
	 * @throws IncomparableValuesException
	 */
	private static Object greater(Object o1, Object o2) throws IncomparableValuesException {
		if (o1 instanceof Number && o2 instanceof Number) {
			return ((double)o1 > (double)o2);
		} else if (o1 instanceof Boolean && o2 instanceof Boolean) {
			if ((Boolean)o1 == true && (Boolean)o2 == false) {
				return (new Boolean(true));
			} else {
				return (new Boolean(false));
			}
		} else {
			throw new IncomparableValuesException();
		}
	}

	/**
	 * Metoda porovnava dva objekty pomoci operatoru mensi nebo rovna se (<=).
	 * @throws IncomparableValuesException
	 */
	private static Object lessEq(Object o1, Object o2) throws IncomparableValuesException {
		if (o1 instanceof Number && o2 instanceof Number) {
			return ((double)o1 <= (double)o2);
		} else if (o1 instanceof Boolean && o2 instanceof Boolean) {
			if (((Boolean)o1 == false && (Boolean)o2 == true) ||
					((Boolean)o1 == (Boolean)o2)) {
				return (new Boolean(true));
			} else {
				return (new Boolean(false));
			}
		} else {
			throw new IncomparableValuesException();
		}
	}

	/**
	 * Metoda porovnava dva objekty pomoci operatoru vetsi nebo rovna se (>=).
	 * @throws IncomparableValuesException
	 */
	private static Object greaterEq(Object o1, Object o2) throws IncomparableValuesException {
		if (o1 instanceof Number && o2 instanceof Number) {
			return ((double)o1 >= (double)o2);
		} else if (o1 instanceof Boolean && o2 instanceof Boolean) {
			if (((Boolean)o1 == true && (Boolean)o2 == false) ||
					((Boolean)o1 == (Boolean)o2)) {
				return (new Boolean(true));
			} else {
				return (new Boolean(false));
			}
		} else {
			throw new IncomparableValuesException();
		}
	}

	/**
	 * Metoda porovnava dva objekty pomoci operatoru rovna se (=).
	 * @throws IncomparableValuesException
	 */
	private static Object equals(Object o1, Object o2) throws IncomparableValuesException {
		if (o1 instanceof Number && o2 instanceof Number) {
			return ((double)o1 == (double)o2);
		} else if (o1 instanceof Boolean && o2 instanceof Boolean) {
			return ((Boolean)o1 == (Boolean)o2); 
		} else { 
			throw new IncomparableValuesException();
		}
	}

	/**
	 * Metoda porovnava dva objekty pomoci operatoru nerovna se (!=).
	 * @throws IncomparableValuesException
	 */
	private static Object equalsNot(Object o1, Object o2) throws IncomparableValuesException {
		if (o1 instanceof Number && o2 instanceof Number) {
			return ((double)o1 != (double)o2);
		} else if (o1 instanceof Boolean && o2 instanceof Boolean) {
			return ((Boolean)o1 != (Boolean)o2); 
		} else { 
			throw new IncomparableValuesException();
		}
	}
	
	/**
	 * Metoda vraci parametr <i>o2</i> pokud je parametr <i>o2</i> pravda. Jinak vraci <i>o1</i>
	 * @throws WrongInputException
	 * @throws NotBooleanException
	 */
	public static Object ternary(Object o1, Object o2, Object o3) 
			throws WrongInputException, NotBooleanException {
		boolean bool = true;
		if (o3 instanceof Boolean) {
			if ((boolean)o3 == false)
				bool = false;
		} else {
			throw new NotBooleanException();
		}
		return bool ? o2 : o1;
	}
	
	/**
	 * Metoda implementuje RPN kalkulacku. Parametr <i>expr</i> je vyraz v 
	 * postfixove notaci. Kalkulacka umi rozeznat operace +, -, *, /, =, 
	 * !=, <, >, <=, >= a ternarni operator ?.
	 * @param expr
	 * @return Objekt typu Double, Integer nebo Boolean.
	 */
	public static Object rpnCalc(String expr) 
			throws DivideByZeroException, IncomparableValuesException, 
			NotBooleanException, WrongInputException {
		Stack<Object> stack = new Stack<Object>();
		String[] exprs = expr.split(" ");
		for (String s : exprs) {
			if (isNumber(s)) {
				stack.push(Double.valueOf(s));
			} else if (isBoolean(s)) {
				boolean b = false;
				if (s.equalsIgnoreCase("#t") || s.equalsIgnoreCase("true"))
					b = true;
				stack.push(b);
			} else {
				if (stack.size() < 2) throw new WrongInputException("No objects on stack."); 
				Object right = stack.pop();
				Object left = stack.pop();
				if (isOperator(s)) { 
						switch (s) {
						case "+": 
							stack.push(add(left, right)); break;
						case "-": 
							stack.push(subtract(left, right)); break;
						case "*": 
							stack.push(multiply(left, right)); break;
						case "/":
							stack.push(divide(left, right)); break;
						case "<":
							stack.push(less(left, right)); break;
						case ">":
							stack.push(greater(left, right)); break;
						case "=":
							stack.push(equals(left, right)); break;
						case "!=":
							stack.push(equalsNot(left, right)); break;
						case ">=":
							stack.push(greaterEq(left, right)); break;
						case "<=":
							stack.push(lessEq(left, right)); break;
						case "?":
							stack.push(ternary(stack.pop(), left, right));
						}
				} else {
					throw new WrongInputException("Unknown operand."); 
				}
			}
		}
		Object result = stack.pop();
		if (isInteger(result)) {
				stack.push((int)((double)result - ((double)result % 1)));
		} else {
			stack.push(result);
		}
		return stack.pop();
	}
	
	/**
	 * Metoda implementuje RPN kalkulacku. Parametr <i>expr</i> je vyraz v 
	 * postfixove notaci, <i>variables</i> je mapa promennych. Kalkulacka umi 
	 * rozeznat operace +, -, *, /, =, !=, <, >, <=, >= a ternarni operator ?.
	 * @param expr
	 * @param variables
	 * @return Objekt typu Double, Integer nebo Boolean.
	 * @throws WrongInputException 
	 * @throws NotBooleanException 
	 * @throws IncomparableValuesException 
	 * @throws DivideByZeroException 
	 */
	public static Object rpnCalc(String expr, Map<String, Double> variables) 
			throws WrongInputException, NotVariableException, NotBooleanException, 
			IncomparableValuesException, DivideByZeroException {
		StringBuilder result = new StringBuilder();
		String[] exprs = expr.split(" ");
		
		for (String s : exprs) {
			if (variables.containsKey(s)) {
				result.append(variables.get(s));
				result.append(" ");
			} else if (isOperator(s) || isNumber(s) || isBoolean(s)) {
				result.append(s);
				result.append(" ");
			} else {
				throw new NotVariableException();
			}
		}
		return rpnCalc(result.toString());
	}

	/**
	 * Metoda pro vypocet a vypis RPN kalkulacky. Vyuziva metodu <i>rpnCalc</i>.
	 * Do konzole vypise predany vyraz a vysledek.
	 * @param expr
	 * @throws WrongInputException 
	 * @throws NotBooleanException 
	 * @throws IncomparableValuesException 
	 * @throws DivideByZeroException 
	 */
	public static void rpnPrint(String expr) 
			throws DivideByZeroException, IncomparableValuesException, 
			NotBooleanException, WrongInputException {
		System.out.print(expr + "   = " + rpnCalc(expr) + "\n");
	}
	/**
	 * Metoda pro vypocet a vypis RPN kalkulacky. Vyuziva metodu <i>rpnCalc</i>.
	 * Do konzole vypise predany vyraz a vysledek.
	 * @param expr
	 * @throws NotVariableException 
	 * @throws WrongInputException 
	 * @throws NotBooleanException 
	 * @throws IncomparableValuesException 
	 * @throws DivideByZeroException 
	 */
	public static void rpnPrint(String expr, Map<String, Double> vars) 
			throws NotVariableException, IncomparableValuesException, 
			DivideByZeroException, NotBooleanException, WrongInputException {
		System.out.print(expr + "   = " + rpnCalc(expr, vars) + "\n");
	}
	
	/* TESTS
	public static void main(String[] args) 
			throws NotVariableException, DivideByZeroException, 
			IncomparableValuesException, NotBooleanException, WrongInputException { 
		Map<String, Double> hm = new HashMap<>(); 
        hm.put("foo", 32.0); 
        hm.put("bar", 66.0); 
		System.out.print("foo = 32, bar = 66\n");
		rpnPrint("1 32 + 42 * 5 + 66 -");
		rpnPrint("1 foo + 42 * 5 + bar -", hm);
		rpnPrint("1.1 2 +");			// 3.1
		rpnPrint("1 2 + ");				// 3
		rpnPrint("1.1 2 <");			// #t
		rpnPrint("#f #t <");			// #t
		rpnPrint("#f #t >");			// #f
		rpnPrint("#f #f >=");			// #t
		//rpnPrint("1 #f =");			// IncomparableValuesException
		rpnPrint("1 2 3 4 < ?");		// 2
		//rpnPrint("1 2 3 4 + ? ");		// NotBooleanException
		rpnPrint("1.3 20 + 0.3 -");		// 21
		//rpnPrint("42 0 /");			// DivideByZeroException
		rpnPrint("42");					// 42
		//rpnPrint("foo 1 + xx -", hm);	// NotVariableException 
		//rpnPrint("3 #t +");			// WrongInputException
		rpnPrint("0 3 / 42 1 * 1 3 + 4 1 - < ?");
	} */
}
