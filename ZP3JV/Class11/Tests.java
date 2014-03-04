import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import RPN.*;

public class Tests {

	@Test
	public void numbers() throws Exception {
		Object result = null;
		
		result = RPN.rpnCalc("1.1 2 +");
		assertEquals(3.1, result);
		result = RPN.rpnCalc("1 2 + ");
		assertEquals(3, result);
		result = RPN.rpnCalc("666 808 -");
		assertEquals(-142, result);
		result = RPN.rpnCalc("42 21 /");
		assertEquals(2, result);
		result = RPN.rpnCalc("3 3 * 3 *");
		assertEquals(27, result);
		result = RPN.rpnCalc("2 3 1 * 4 + 42 - 42 + + 6 /");
		assertEquals(1.5, result);
		result = RPN.rpnCalc("1 2 3 4 < ?");
		assertEquals(2, result);
	}

	@Test
	public void booleans() throws Exception {
		Object result = null;

		result = RPN.rpnCalc("1.1 2 <");
		assertEquals(true, result);
		result = RPN.rpnCalc("#f #t <");
		assertEquals(true, result);
		result = RPN.rpnCalc("#f #t >");
		assertEquals(false, result);
		result = RPN.rpnCalc("#f false >=");
		assertEquals(true, result);
		result = RPN.rpnCalc("false true !=");
		assertEquals(true, result);
		result = RPN.rpnCalc("#f #t 1.1 2.2 < ?");
		assertEquals(true, result);
	}


	@Test
	public void hashs() throws Exception {
		Object result = null;
		
		Map<String, Double> hm = new HashMap<>(); 
        hm.put("foo", 32.0); 
        hm.put("bar", 66.0); 
        
        result = RPN.rpnCalc("1 foo + 42 * 5 + bar -", hm);
        assertEquals(1325, result);
        result = RPN.rpnCalc("foo bar -", hm);
        assertEquals(-34, result);
        result = RPN.rpnCalc("foo 2 * 2 + bar /", hm);
        assertEquals(1, result);
	}

	@Test (expected = IncomparableValuesException.class)
	public void incomparableValues() throws DivideByZeroException, IncomparableValuesException, NotBooleanException, WrongInputException {
		RPN.rpnCalc("1 #f >");
	}
	
	@Test (expected = WrongInputException.class)
	public void wrongInput() throws Exception {
		RPN.rpnCalc("blabla");
	}

	@Test (expected = NotBooleanException.class)
	public void notBoolean() throws Exception {
		RPN.rpnCalc("1 2 3 ?");
	}
}
