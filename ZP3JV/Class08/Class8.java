package Class08;

import java.util.List;
import java.util.ArrayList;

public class Class8 {
	
	// 1.
	public static List<Object> map(List<Object> list, Mapping m) {
		List<Object> result = new ArrayList<>();
		for (Object o : list)
			result.add(m.map(o));
		return result;
	}
	private static class Expt implements Mapping {
		@Override
		public Object map(Object o) {
			if (o instanceof Number)
				return ((int)o * (int)o);
			else
				return o;
		}
	}
	
	// 2.
	public static List<Object> filter(List<Object> list, Condition c) {
		List<Object> result = new ArrayList<>();
		for (Object o : list)
			if (c.cond(o))
				result.add(o);
		return result;
	}
	private static class Even implements Condition {
		@Override
		public boolean cond(Object o) {
			return (o instanceof Number) ? ((int)o % 2 == 0) : false;
		}
	}
	
	
	// TESTS
	public static void main(String[] args) {
		
		List<Object> list = new ArrayList<>();
		list.add(42);
		list.add(1);
		list.add(9);
		list.add(-666);
		list.add(-25);
		list.add(0);
		
		// 1.
		List<Object> list1 = map(list, new Expt());
		for (Object o : list1)
			System.out.print(o + ", ");
		
		// 2.
		List<Object> list2 = filter(list, new Even());
		System.out.print("\n\n");
		for (Object o : list2)
			System.out.print(o + ", ");
		//
		List<Object> list3 = filter(list, new Condition() {
			@Override
			public boolean cond(Object o) {
				if (o instanceof Number) {
					if ((int)o % 2 == 0)
						return true;
				}
				return false;
			}
		});
		System.out.print("\n");
		for (Object o : list3)
			System.out.print(o + ", ");
	}
}
