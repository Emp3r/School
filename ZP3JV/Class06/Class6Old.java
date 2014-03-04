package Class06;

import java.util.Stack;
import java.util.Map;
//import java.util.HashMap;
//import java.lang.Exception;;

/**
 * Java - ukol 06. Vylepsena RPN kalkulacka, vyjimky a dokumentace.
 * @author Josef Podstata
 * @since 1.0
 */
public class Class6 {
	/**
	 * Metoda testuje, jestli parametr <i>e</i> znazornuje jeden z operatoru: 
	 * +, -, *, /, =, !=, <, >, <=, >=, nebo ternarni operator <b>?</b>.
	 * @param e
	 * @return true / false
	 */
	private static boolean isOperator(String e) {
		if (e.equals("+") || e.equals("-") || e.equals("*") || e.equals("/")
		 || e.equals("=") || e.equals("!=") || e.equals("<") || e.equals(">") 
		 || e.equals(">=") || e.equals("<=")|| e.equals("?")) {
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
				boolean b = true;
				if (s.equalsIgnoreCase("#t") || s.equalsIgnoreCase("true")) {
					b = true;
				} else {
					b = false;
				}
				stack.push(b);
			} else {
				if (stack.size() < 2)
                    throw new WrongInputException("No objects on stack."); 
				Object right = stack.pop();
				Object left = stack.pop();
				double number1;
				double number2;
				
				switch (s) {
				case "+":
					if (right instanceof Number && left instanceof Number) {
						number1 = (double)right;
						number2 = (double)left;
						stack.push(number2 + number1);
					} else {
						throw new WrongInputException("Trying to + with something else than numbers.");
					}
					break;
				case "-":
					if (right instanceof Number && left instanceof Number) {
						number1 = (double)right;
						number2 = (double)left;
						stack.push(number2 - number1); 
					} else {
						throw new WrongInputException("Trying to - with something else than numbers.");
					}
					break;
				case "*":
					if (right instanceof Number && left instanceof Number) {
						number1 = (double)right;
						number2 = (double)left;
						stack.push(number2 * number1);
					} else {
						throw new WrongInputException("Trying to * with something else than numbers.");
					}
					break;
				case "/":
					if (right instanceof Number && left instanceof Number) {
						number1 = (double)right;
						number2 = (double)left;
						if (number1 == 0) 
							throw new DivideByZeroException();
						stack.push(number2 / number1);
					} else {
						throw new WrongInputException("Trying to / with something else than numbers.");
					}
					break;
				case "<":
					if (right instanceof Number && left instanceof Number) {
						stack.push((double)left < (double)right);
					}
					else if (right instanceof Boolean && left instanceof Boolean) {
						if ((Boolean)left == false && (Boolean)right == true) 
							stack.push(new Boolean(true));
						else 
							stack.push(new Boolean(false));
					}
					else {
						throw new IncomparableValuesException();
					}
					break;
				case ">":
					if (right instanceof Number && left instanceof Number) {
						stack.push((double)left > (double)right);
					}
					else if (right instanceof Boolean && left instanceof Boolean) {
						if ((Boolean)left == true && (Boolean)right == false)
							stack.push(new Boolean(true));
						else
							stack.push(new Boolean(false));
					}
					else {
						throw new IncomparableValuesException();
					}
					break;
				case "=":
					if (right instanceof Number && left instanceof Number) {
						stack.push((double)left == (double)right);
					}
					else if (right instanceof Boolean && left instanceof Boolean) {
						if ((Boolean)left == (Boolean)right)
							stack.push(new Boolean(true));
						else
							stack.push(new Boolean(false));
					}
					else {
						throw new IncomparableValuesException();
					}
					break;
				case ">=":
					if (right instanceof Number && left instanceof Number) {
						stack.push((double)left >= (double)right);
					}
					else if (right instanceof Boolean && left instanceof Boolean) {
						if ((Boolean)left == (Boolean)right || 
							((Boolean)left == true && (Boolean)right == false))
							stack.push(new Boolean(true));
						else
							stack.push(new Boolean(false));
					}
					else {
						throw new IncomparableValuesException();
					}
					break;
				case "<=":
					if (right instanceof Number && left instanceof Number) {
						stack.push((double)left <= (double)right);
					}
					else if (right instanceof Boolean && left instanceof Boolean) {
						if ((Boolean)left == (Boolean)right || 
							((Boolean)left == false && (Boolean)right == true))
							stack.push(new Boolean(true));
						else
							stack.push(new Boolean(false));
					}
					else {
						throw new IncomparableValuesException();
					}
					break;
				case "!=":
					if (right instanceof Number && left instanceof Number) {
						stack.push((double)left != (double)right);
					}
					else if (right instanceof Boolean && left instanceof Boolean) {
						if ((Boolean)left != (Boolean)right)
							stack.push(new Boolean(true));
						else
							stack.push(new Boolean(false));
					}
					else {
						throw new IncomparableValuesException();
					}
					break;
				case "?":
					boolean bool = true;
					if (right instanceof Boolean) {
						if ((boolean)right == true)
							bool = true;
						else
							bool = false;
					} else {
						throw new NotBooleanException();
					}
					Object temp1 = left;
					Object temp2 = stack.pop();
					if (bool) {
						stack.push(temp1);
					} else {
						stack.push(temp2); 
					}
					break;
				}
			}
		}
		Object result = stack.pop();
		if (result instanceof Number) {
			if ((double)result % 1 == 0) {
				stack.push((int)((double)result - ((double)result % 1)));
			} else {
				stack.push(result);
			}
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
			throws NotVariableException, DivideByZeroException, IncomparableValuesException, NotBooleanException, WrongInputException {
		StringBuilder result = new StringBuilder();
		String[] exprs = expr.split(" ");
		
		for (String s : exprs) {
			if (variables.containsKey(s)) {
				result.append(variables.get(s));
				result.append(" ");
			} else if (isOperator(s)) {
				result.append(s);
				result.append(" ");
			} else if (isNumber(s)){
				result.append(s);
				result.append(" ");
			} else if (isBoolean(s)){
				result.append(s);
				result.append(" ");
			}
			else {
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
	} */
}
