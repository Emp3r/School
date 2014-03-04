package main;

import java.util.ArrayList;
import java.util.Scanner;
import simplegraphics.*;

public class Program {
	
    public static ArrayList<Object> objects = new ArrayList<>(); 

    // Bod
    public static Object makePoint() { 
        Scanner in = new Scanner(System.in);
        System.out.print("Zadej jmeno bodu:\n"); 
        String jmeno = in.nextLine();
        System.out.print("Zadej souradnice bodu na ose x:\n"); 
        int x = in.nextInt();
        System.out.print("Zadej souradnice bodu na ose y:\n"); 
        int y = in.nextInt();
        return new Point(jmeno, x, y); 
    }
    
    // Usecka
    public static Object makeLine() { 
        Scanner in = new Scanner(System.in);
        System.out.print("Zadej jmeno usecky: "); 
        String jmeno = in.nextLine();
        System.out.print("Zadej souradnice prvniho bodu na ose x: "); 
        int prvniBodX = in.nextInt();
        System.out.print("Zadej souradnice prvniho bodu na ose y: "); 
        int prvniBodY = in.nextInt();
        Point bodA = new simplegraphics.Point(prvniBodX, prvniBodY);
        System.out.print("Zadej souradnice druheho bodu na ose x: "); 
        int druhyBodX = in.nextInt();
        System.out.print("Zadej souradnice druheho bodu na ose y: "); 
        int druhyBodY = in.nextInt();
        Point bodB = new simplegraphics.Point(druhyBodX, druhyBodY);
        return new Line(jmeno, bodA, bodB);
    }
    
    // Kruznice
    public static Object makeCircle() { 
        Scanner in = new Scanner(System.in);
        System.out.print("Zadej jmeno kruznice: "); 
        String jmeno = in.nextLine();
        System.out.print("Zadej souradnice stredu na ose x: ");
        int stredX = in.nextInt();
        System.out.print("Zadej souradnice stredu na ose y: ");
        int stredY = in.nextInt();
        Point stred = new Point(stredX, stredY);
        System.out.print("Zadej polomer: ");
        double polomer = in.nextDouble();
        return new Circle(jmeno, stred, polomer);
    }
    
    // Ctverec
    public static Object makeSquare() { 
        Scanner in = new Scanner(System.in);
        System.out.print("Zadej jmeno ctverce:\n"); 
        String jmeno = in.nextLine();
        System.out.print("Zadej souradnici leveho spodniho rohu na ose x:\n");
        int x = in.nextInt();
        System.out.print("Zadej souradnici leveho spodniho rohu na ose y:\n");
        int y = in.nextInt();
        Point bod = new Point(x, y);
        System.out.print("Zadej delku strany:\n");
        int delka = in.nextInt();
        return new Square(jmeno, bod, delka);
    }
    
    // Obdelnik
    public static Object makeRectangle() { 
        Scanner in = new Scanner(System.in);
        System.out.print("Zadej jmeno obdelniku:\n"); 
        String jmeno = in.nextLine();
        System.out.print("Zadej souradnici leveho spodniho rohu na ose x:\n");
        int x = in.nextInt();
        System.out.print("Zadej souradnici leveho spodniho rohu na ose y:\n");
        int y = in.nextInt();
        Point bod = new Point(x, y);
        System.out.print("Zadej vysku obdelniku:\n"); 
        int vyska = in.nextInt();
        System.out.print("Zadej sirku obdelniku:\n"); 
        int sirka = in.nextInt();
        return new Rectangle(jmeno, bod, vyska, sirka); 
    }
    
    // Vzdalenost
    public static double getDistance(Object o, Point p) { 
        if (o instanceof Line)
            return ((Line) o).distance(p);
        else if (o instanceof Point)
            return ((Point) o).distance(p);
        else if (o instanceof Circle)
            return ((Circle) o).distance(p);
        else if (o instanceof Square)
            return ((Square) o).distance(p);
        else if (o instanceof Rectangle)
            return ((Rectangle) o).distance(p);
        System.out.print("Error?\n"); 
        return Double.MAX_VALUE; 
    }
    
    // Nejblizsi
    public static void closest() {
        Scanner in = new Scanner(System.in);
        if (objects.isEmpty()) { 
            System.out.print("Nic jste jeste neulozili, neni s cim porovnavat.\n");
            return;
        }
        System.out.print("Zadej souradnici na ose x:\n"); 
        int x = in.nextInt();
        System.out.print("Zadej souradnici na ose y:\n"); 
        int y = in.nextInt();
        Point bod = new Point(x, y);
        Object result = null; 
        double nejblizsi = Double.MAX_VALUE;
        
        for (Object o : objects) { 
            double aktualni = getDistance(o, bod); 
            if (aktualni < nejblizsi) { 
            	nejblizsi = aktualni; 
                result = o; 
            } 
        }
        System.out.print("Nejblizsi je: ");
        printObject(result);
    }
    
    // Tiskni udaje o objektu
    public static void printObject(Object o) { 
        if (o instanceof Line)
            System.out.print("Usecka jmenem " + ((Line)o).getName() + ".\n");
        else if (o instanceof Circle)
            System.out.print("Kruznice jmenem " + ((Circle)o).getName() + ".\n");
        else if (o instanceof Square)
            System.out.print("Ctverec jmenem " + ((Square)o).getName() + ".\n");
        else if (o instanceof Rectangle)
            System.out.print("Obdelnik jmenem " + ((Rectangle)o).getName() + ".\n");
        else if (o instanceof Point)
            System.out.print("Bod jmenem " +((Point)o).getName() + ".\n");
        else
            System.out.print("Error?\n");
    } 

    // Tiskni vsechno
	public static void print() {
		for (Object o : objects)
			printObject(o);
	}
    
    // pridat
    public static void add() {
        System.out.print("Jaky objekt chcete pridat? Mate na vyber \"bod\", " +
        			"\"usecka\", \"kruznice\", \"obdelnik\" a \"ctverec\".\n"); 
        Scanner in = new Scanner(System.in);
        String vstup = in.nextLine(); 
        
        switch (vstup) { 
            case "bod":
                objects.add(makePoint());
                break;
            case "usecka":
                objects.add(makeLine());
                break;
            case "kruznice":
                objects.add(makeCircle());
                break;
            case "obdelnik":
                objects.add(makeRectangle()); 
                break;
            case "ctverec":
                objects.add(makeSquare());
                break;
            default:
                System.out.println("Neznamy objekt."); 
                break;
        }
    }
    
	// ?
    public static void help() {
    	System.out.print("Program zna prikazy:\npridat\nvypis\nnejblizsi\n?\nkonec\n");
    }
    
    
    // MAIN
    public static void main(String[] args) { 
        System.out.print("Vitej v programu. Pro napovedu zadej \"?\"\n"); 
        Scanner in = new Scanner(System.in);
        
    	while(true) {
            String vstup = in.nextLine(); 
              
            switch (vstup) {
            	case "pridat":
            		add();
            		break;
                case "vypis":
                	print(); 
                    break;
                case "nejblizsi":
                    closest();
                    break;
                case "?":
                	help();
                    break;
                case "konec":
                    System.out.println("Ukoncuji program."); 
                    return;
                default:
                    System.out.println("Neznamy prikaz. Zkus zadat \"?\""); 
                    break;
            } 
    	}
    }
}
