package Class03;

import java.util.ArrayList;
import java.util.List;

public class Class3 {

	// 1. œkol
	public static int[] odd(int[] foo) {
		int size = 0;
		for (int i : foo) {
			if (i % 2 == 1) {
				size++;
			}
		}
		int[] result = new int[size];
		int j = 0;
		for (int i : foo) {
			if (i % 2 == 1) {
				result[j] = i;
				j++;
			}
		}
		return result;
	}

	// 2. œkol
	public static List<Integer> odd(List<Object> foo) {
		List<Integer> result = new ArrayList<Integer>();

		for (Object i : foo) {
			if (i instanceof Integer) {
				int j = (Integer) i;
				if (j % 2 == 1) {
					result.add(j);
				}
			}
		}
		return result;
	}

	// 3. œkol - Point

	// 4. œkol
	public static int[] merge(int[] pole1, int[] pole2) {
		int[] result = new int[pole1.length + pole2.length];
		int pole1i = 0;
		int pole2i = 0;

		for (int i = 0; i <= (pole1.length + pole2.length); i++) {
			if ((pole1i < pole1.length) && (pole2i < pole2.length)) {
				if (pole1[pole1i] <= pole2[pole2i]) {
					result[i] = pole1[pole1i];
					pole1i++;
				} else {
					result[i] = pole2[pole2i];
					pole2i++;
				}
			} else if (pole2i < pole2.length) {
				result[i] = pole2[pole2i];
				pole2i++;
			} else if (pole1i < pole1.length) {
				result[i] = pole1[pole1i];
				pole1i++;
			}
		}
		return result;
	}

	public static List<Integer> merge(List<Integer> l1, List<Integer> l2) {
		List<Integer> result = new ArrayList<Integer>();
		
		for (int i = 0, j = 0; (i < l1.size()) || (j < l2.size());) {
			if (i == l1.size()) {
				result.add((Integer) l2.get(j));
				j++;
			} else if (j == l2.size()) {
				result.add((Integer) l1.get(i));
				i++;
			} else if (((Integer) l1.get(i)).compareTo((Integer) l2.get(j)) < 0) {
				result.add((Integer) l1.get(i));
				i++;
			} else {
				result.add((Integer) l2.get(j));
				j++;
			}
		}
		return result;
	}

	// 5. œkol
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

	/* TESTS
	public static void main(String[] args) {
		int[] pole1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 40, 30, 21, 42, 44, 43, 51 };
		int[] pole2 = odd(pole1);
		for (int i : pole2) {
			System.out.print(i + ", ");
		}
		int[] pole3 = merge(pole1, pole2);
		System.out.print("\n\n");
		for (int i : pole3) {
			System.out.print(i + ", ");
		}

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

		System.out.print("\n\n");
		for (int i = 0; i < 7; i++) {
			System.out.print(list1.get(i) + ", ");
		}
		System.out.print("\n");
		for (int i = 0; i < 7; i++) {
			System.out.print(list2.get(i) + ", ");
		}
	}
	*/
}
