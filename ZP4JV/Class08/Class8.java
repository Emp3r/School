package Class08;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Class8 {
	
	private static Connection con;
	
	public static void main(String[] args) throws SQLException {
		prepareDB();
		doSomething();
	}
	
	private static void doSomething() throws SQLException {
		addPerson("Alan Turing", "mathematician, cryptanalyst");
		addPerson("Charles Babbage", "mathematician, mechanical engineer");
		addActivity("Alan Turing", "14. 1. 1942", "42", "cryptanalysing");
	
		printAll();
		
		editActivity("Alan Turing", "14. 1. 1942", "14. 1. 1942", "3", "more computing");
		editPerson("Alan Turing", "A. Turing", "logican, cryptanalyst");

		printAll();
		
		addPerson("Donald Knuth", "father of the analysis of algorithms");
		addActivity("Donald Knuth", "30. 5. 1858", "4", "writing");
		addActivity("Donald Knuth", "11. 1. 1862", "2", "publishing");
		
		printAll();
		
		deletePerson("Charles Babbage");
		editActivity("Donald Knuth", "11. 1. 1862", "11. 1. 1871", "5", "dying");
		deleteActivity("Donald Knuth", "30. 5. 1858");
		
		printAll();
	}
	

	// ADD Person
	private static void addPerson(String name, String occupation) throws SQLException {
		try (PersonManager personManager = new PersonManager(con)) {
			personManager.addPerson(name, occupation);
		}
	}

	// EDIT Person
	private static void editPerson(String name, String newName, String newOccupation) throws SQLException {
		try (PersonManager personManager = new PersonManager(con)) {
			Person p = new Person(newName, newOccupation);
			personManager.changePerson(p, name);
			try (ActivityManager activityManager = new ActivityManager(con)) {
				activityManager.changeNames(name, newName);
			}
		}
	}

	// DEL Person
	private static void deletePerson(String name) throws SQLException {
		try (PersonManager personManager = new PersonManager(con)) {
			Person beingRemoved = personManager.getByName(name);
			personManager.deletePerson(beingRemoved);
			removeAllActivities(name);
		}
	}
	
	// ADD Activity
	private static void addActivity(String name, String date, String hours, String text) throws SQLException {
		try (ActivityManager activityManager = new ActivityManager(con)){
			activityManager.addActivity(name, date, hours, text);
		}
	}

	// EDIT Activity
	private static void editActivity(String name, String date, String nDate, String nHours, String nText) throws SQLException {
		try (ActivityManager activityManager = new ActivityManager(con)){
			Activity a = new Activity(nDate, nHours, nText);
			activityManager.editActivity(a, name, date);
		}
	}
	
	// DELETE Activity
	private static void deleteActivity(String name, String date) throws SQLException {
		try (ActivityManager activityManager = new ActivityManager(con)) {
			Activity a = activityManager.getActivityByDate(name, date);
			activityManager.deleteActivity(name, a);
		}
	}
	
	private static void removeAllActivities(String name) throws SQLException {
		try (ActivityManager activityManager = new ActivityManager(con)){
			ArrayList<Activity> activities = (ArrayList<Activity>) activityManager.getActivities(name);
			for (Activity a : activities) {
				activityManager.deleteActivity(name, a);
			}
		}
	}
	
	
	// INIT
	private static void prepareDB() throws SQLException {
		con = DriverManager.getConnection("jdbc:derby:MyDataBase;create=true");
		
		try (Statement stmt = con.createStatement()) {
			if (!isReady("EMPLOYEES") && !isReady("ACTIVITIES")) {
				stmt.executeUpdate("CREATE TABLE employees (name VARCHAR(32) PRIMARY KEY, occupation VARCHAR(64))");
				stmt.executeUpdate("CREATE TABLE activities (name VARCHAR(32), date VARCHAR(16), hours VARCHAR(8), text VARCHAR(128))");
			} else {
				stmt.executeUpdate("DELETE FROM employees");
				stmt.executeUpdate("DELETE FROM activities");
			}
		}
	}
	
	private static boolean isReady(String table) throws SQLException{
		DatabaseMetaData mdb = con.getMetaData();
		try (ResultSet tables = mdb.getTables(null, null, table, null)) {
			return tables.next();
		}
	}
	
	private static void printActivities(String name) throws SQLException{
		try (ActivityManager activityManager = new ActivityManager(con)) {
			for (Activity a : activityManager.getActivities(name))
				System.out.println("\t- " + a.toString());
		}
	}
	
	private static void printAll() throws SQLException{
		try (PersonManager personManager = new PersonManager(con)) {
			for (Person p : personManager.getPersons()) {
				System.out.println(p.toString());
				printActivities(p.getName());
			}
			System.out.println("\n");
		}
	}
	
}