package Class07;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public class Class7 {
	
	// pomocne
	private static String readerToString(Reader file) {
		StringBuilder all = new StringBuilder();
		try (BufferedReader br = new BufferedReader(file)) {
			String line;
			while ((line = br.readLine()) != null) {
				all.append(line);
				all.append(" \n ");
			}
		} catch (Exception e) {
			System.out.print(e);
		}
		return all.toString();
	}
	private static void writeStringToMatrix(String s, int[][] matrix) {
		String[] parts = s.split(" ");
		for (int i = 0, j = 0, k = 0; i < parts.length; i++, k++) {
			if (!parts[i].equalsIgnoreCase("\n")) {
				matrix[j][k] = Integer.valueOf(parts[i]);
			} else {
				j++;
				k = -1;
			}
		}
	}
	
	// 1.
	public static int[][] readTextMatrix(String fileName) throws IOException {
		return readTextMatrix(new FileReader(fileName));
	}
	
	// 2.
	public static int[][] readTextMatrix(Reader file) throws IOException {	
		String all = readerToString(file);
		int columns = all.split("\n")[0].split(" ").length;
		int rows = all.split("\n").length - 1;
		int[][] result = new int[rows][columns];
		writeStringToMatrix(all, result);
		return result;
	}

	// 3.
	public static void writeTextMatrix(String fileName, int[][] matrix) throws IOException {
		writeTextMatrix(new FileWriter(fileName), matrix);
	}

	// 4.
	public static void writeTextMatrix(Writer file, int[][] matrix) throws IOException {
		try (BufferedWriter bw = new BufferedWriter(file)) {
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[0].length; j++) {
					bw.write(matrix[i][j] + " ");
				}
				bw.newLine();
			}
		} catch (Exception e) {
			System.out.print(e);
		}
	}
	
	// 5.	
	public static void writeBinaryMatrix(OutputStream s, int[][] matrix) throws IOException {
		try (DataOutputStream dos = new DataOutputStream(s)) {
			dos.writeInt(matrix.length);
			dos.writeInt(matrix[0].length);
			for (int i = 0; i < matrix.length; i++)
				for (int j = 0; j < matrix[i].length; j++)
					dos.writeInt(matrix[i][j]);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// 6.
	public static int[][] readBinaryMatrix(InputStream s) throws IOException {
		int[][] result = null;
		try (DataInputStream dis = new DataInputStream(s)) {
			result = new int[dis.readInt()][dis.readInt()];
			for (int i = 0; i < result.length; i++)
				for (int j = 0; j < result[0].length; j++)
					result[i][j] = dis.readInt();
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}
	
	
	// TESTS
	public static void main(String[] args) throws Exception {
		// 1.
		int[][] pole1 = readTextMatrix("src/Class07/matr.txt");
		for (int i = 0; i < pole1.length; i++) {
			for (int j = 0; j < 3; j++)
				System.out.print(pole1[i][j] + " ");
			System.out.print("\n");
		}
		System.out.print("\n");
		
		// 2.
		// test pro FileReader
		try (Reader rd = new FileReader("src/Class07/matr.txt")) {
			int[][] pole2 = readTextMatrix(rd);
			for (int i = 0; i < pole2.length; i++) {
				for (int j = 0; j < 3; j++)
					System.out.print(pole2[i][j] + " ");
				System.out.print("\n");
			}
		}
		System.out.print("\n");
		
		// test pro StringReader
		BufferedReader in = new BufferedReader(new FileReader("src/Class07/matr.txt"));
	    String s, s2 = new String();
	    while ((s = in.readLine()) != null)
	    	s2 += s + "\n";
	    in.close();
		try (StringReader rd = new StringReader(s2);) {
			int[][] pole2 = readTextMatrix(rd);
			for (int i = 0; i < pole2.length; i++) {
				for (int j = 0; j < 3; j++)
					System.out.print(pole2[i][j] + " ");
				System.out.print("\n");
			}
		}
		System.out.print("\n");
		
		// 3.
		writeTextMatrix("src/Class07/matr2.txt", pole1);
		
		// 4.
		FileWriter fw = new FileWriter("src/Class07/matr3.txt");
		writeTextMatrix(fw, pole1);
		StringWriter sw = new StringWriter();
		writeTextMatrix(sw, pole1);
		System.out.print("\nStringWriter:\n" + sw.toString());
		
		// 5.
		FileOutputStream fos = new FileOutputStream("src/Class07/matrByte.dat");
		writeBinaryMatrix(fos, pole1);
		
		// 6.
		System.out.print("\nDATA:\n");
		FileInputStream fis = new FileInputStream("src/Class07/matrByte.dat");
		int[][] pole5 = readBinaryMatrix(fis);
		for (int i = 0; i < pole5.length; i++) {
			for (int j = 0; j < pole5[0].length; j++)
				System.out.print(pole5[i][j] + " ");
			System.out.print("\n");
		}	
	}  
}
