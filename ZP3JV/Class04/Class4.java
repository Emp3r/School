package Class04;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class Class4 {

	// 1.
	public static List<Integer> odd(List<Integer> numbers) {
		List<Integer> result = new ArrayList<Integer>();

		for (Integer i : numbers) {
			if (i % 2 == 1) {
				result.add(i);
			}
		}
		return result;
	}
	
	
	// 2.
	public static List<Integer> merge(List<Integer> l1, List<Integer> l2) {
		List<Integer> result = new ArrayList<Integer>();
		
		for (int i = 0, j = 0; (i < l1.size()) || (j < l2.size());) {
			if (i == l1.size()) {
				result.add(l2.get(j));
				j++;
			} else if (j == l2.size()) {
				result.add(l1.get(i));
				i++;
			} else if ((l1.get(i)).compareTo(l2.get(j)) < 0) {
				result.add(l1.get(i));
				i++;
			} else {
				result.add(l2.get(j));
				j++;
			}
		}
		return result;
	}

	public static List<Integer> mergeSort(List<Integer> seznam) {
		if (seznam.size() <= 1) {
			return seznam;
		}
		List<Integer> left = new ArrayList<Integer>();
		List<Integer> right = new ArrayList<Integer>();
		int middle = seznam.size() / 2;

		for (int i = 0; i < middle; i++) {
			left.add(seznam.get(i));
		}
		for (int i = middle; i < seznam.size(); i++) {
			right.add(seznam.get(i));
		}

		left = mergeSort(left);
		right = mergeSort(right);

		return merge(left, right);
	}
	
	// 3.
	public static Set<Integer> intersect(Set<Integer> set1, Set<Integer> set2) {
		Set<Integer> result = new HashSet<Integer>(set1);
		
		result.retainAll(set2);
		
		return result;
	}

	
	
	public static void main(String[] args) {
		// 2.
		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new ArrayList<Integer>();

		list1.add(2);
		list1.add(7);
		list1.add(1);
		list1.add(3);
		list1.add(9);
		list1.add(6);
		list1.add(8);
		list2 = mergeSort(list1);

		for (int i = 0; i < 7; i++) {
			System.out.print(list1.get(i) + ", ");
		}
		System.out.print("\n");
		for (int i = 0; i < 7; i++) {
			System.out.print(list2.get(i) + ", ");
		}
		
		// 3.
		Set<Integer> set1 = new HashSet<Integer>();
		Set<Integer> set2 = new HashSet<Integer>();
		set1.add(1);
		set1.add(3);
		set1.add(5);
		set1.add(7);
		set1.add(9);
		set1.add(11);
		set1.add(12);
		set2.add(3);
		set2.add(6);
		set2.add(9);
		set2.add(12);
		
		Object[] set4 = intersect(set1, set2).toArray();
		
		System.out.print("\n\n");
		for (int i = 0; i < 3; i++) {
			System.out.print(set4[i] + ", ");
		}
		
		// 4.
		Set<Point> setP1 = new HashSet<Point>();
		setP1.add(new Point(0, 1));
		setP1.add(new Point(3, 4));
		setP1.add(new Point(0, 1));
		setP1.add(new Point(2, 5));
        System.out.print("\n\nVelikost: " + setP1.size()); 

		Set<NewPoint> setP2 = new HashSet<NewPoint>();
		setP2.add(new NewPoint(0, 1));
		setP2.add(new NewPoint(3, 4));
		setP2.add(new NewPoint(0, 1));
		setP2.add(new NewPoint(2, 5));
        System.out.print("\nVelikost: " + setP2.size()); 
		
	}
}
