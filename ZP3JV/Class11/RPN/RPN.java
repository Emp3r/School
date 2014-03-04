package RPN;

import java.util.Stack;
import java.util.Map;
//import java.util.HashMap;
//import java.lang.Exception;;

public class RPN {
	
	private static boolean isOperator(String e) {
		return (e.equals("+") || e.equals("-") || e.equals("*") || e.equals("/") ||
			e.equals("<") || e.equals(">") || e.equals("<=") || e.equals(">=") ||
			e.equals("=") || e.equals("!=") || e.equals("?"));
	}
	
	
	private static boolean isBoolean(String e) {
		return (e.equalsIgnoreCase("#t") || e.equalsIgnoreCase("#f") || 
			e.equalsIgnoreCase("true") || e.equalsIgnoreCase("false"));
	}

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
	
	private static boolean isInteger(Object e) {
		if (e instanceof Number) {
			return ((double)e % 1 == 0);
		}
		return false;
	}

	private static Object add(Object o1, Object o2) throws WrongInputException {
		if (o1 instanceof Number && o2 instanceof Number) {
			return (double)o1 + (double)o2;
		} else {
			throw new WrongInputException();
		} 
	}

	private static Object subtract(Object o1, Object o2) throws WrongInputException {
		if (o1 instanceof Number && o2 instanceof Number) {
			return (double)o1 - (double)o2;
		} else {
			throw new WrongInputException();
		} 
	}

	private static Object multiply(Object o1, Object o2) throws WrongInputException {
		if (o1 instanceof Number && o2 instanceof Number) {
			return (double)o1 * (double)o2;
		} else {
			throw new WrongInputException();
		} 
	}

	private static Object divide(Object o1, Object o2) throws WrongInputException, DivideByZeroException {
		if (o1 instanceof Number && o2 instanceof Number) {
			if ((double)o2 == 0) 
				throw new DivideByZeroException();
			return (double)o1 / (double)o2;
		} else {
			throw new WrongInputException();
		} 
	}
	
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

	private static Object equals(Object o1, Object o2) throws IncomparableValuesException {
		if (o1 instanceof Number && o2 instanceof Number) {
			return ((double)o1 == (double)o2);
		} else if (o1 instanceof Boolean && o2 instanceof Boolean) {
			return ((Boolean)o1 == (Boolean)o2); 
		} else { 
			throw new IncomparableValuesException();
		}
	}


	private static Object equalsNot(Object o1, Object o2) throws IncomparableValuesException {
		if (o1 instanceof Number && o2 instanceof Number) {
			return ((double)o1 != (double)o2);
		} else if (o1 instanceof Boolean && o2 instanceof Boolean) {
			return ((Boolean)o1 != (Boolean)o2); 
		} else { 
			throw new IncomparableValuesException();
		}
	}

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

	public static void rpnPrint(String expr) 
			throws DivideByZeroException, IncomparableValuesException, 
			NotBooleanException, WrongInputException {
		System.out.print(expr + "   = " + rpnCalc(expr) + "\n");
	}

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
		rpnPrint("0 3 / 42 1 * 1 3 + 4 1 - >= ?");
	} */
}
