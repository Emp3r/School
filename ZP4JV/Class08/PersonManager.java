package Class08;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonManager implements AutoCloseable {

	private PreparedStatement getAllNames;
	private PreparedStatement getByNameStmt;
	private PreparedStatement insertStmt;
	private PreparedStatement updateStmt;
	private PreparedStatement deleteStmt;
	
	
	public PersonManager(Connection con) throws SQLException {
		try {
			getAllNames = con.prepareStatement("SELECT * FROM employees");
			getByNameStmt = con.prepareStatement("SELECT * FROM employees WHERE name = ?");
			insertStmt = con.prepareStatement("INSERT INTO employees (name, occupation) VALUES (?, ?)");
			updateStmt = con.prepareStatement("UPDATE employees SET name = ?, occupation = ? WHERE name = ?");
			deleteStmt = con.prepareStatement("DELETE FROM employees WHERE name = ?");
		} catch (SQLException e) {
			throw new SQLException("Unable to initialize prepared statements.", e);
		}
	}
	
	public List<Person> getPersons() throws SQLException {
		List<Person> names = new ArrayList<Person>();
		try {
			try (ResultSet results = getAllNames.executeQuery()) {
				while (results.next())
					names.add(new Person(results.getString(1), results.getString(2)));
			}
		} catch (Exception e) {
			throw new SQLException("Unable to list all employees", e);
		}
		return names;
	}
	
	public Person getByName(String name) throws SQLException {
		Person prsn = null;
		try {
			getByNameStmt.setString(1, name);
			try (ResultSet results = getByNameStmt.executeQuery()) {
				if (results.next())
					prsn = new Person(results.getString("name"), results.getString("occupation"));
			}
		} catch (SQLException e) {
			throw new SQLException("Unable to find an employee", e);
		}
		return prsn;
	}
	
	// ADD
	public Person addPerson(String name, String occupation) throws SQLException {
		try {
			insertStmt.setString(1, name);
			insertStmt.setString(2, occupation);
			insertStmt.executeUpdate();
			return new Person(name, occupation);
		} catch (SQLException e) {
			throw new SQLException("Unable to create new employee", e);
		}
	}
	
	// DELETE
	public void deletePerson(Person person) throws SQLException {
		try {
			deleteStmt.setString(1, person.getName());
			deleteStmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Unable to delete employee", e);
		}
	}
	
	// CHANGE
	public void changePerson(Person person, String name) throws SQLException {
		try {
			updateStmt.setString(1, person.getName());
			updateStmt.setString(2, person.getOccupation());
			updateStmt.setString(3, name);
			updateStmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Unable to update employee", e);
		}
	}
	
	@Override
	public void close() {
		try {
			getAllNames.close();
			getByNameStmt.close();
			insertStmt.close();
			updateStmt.close();
			deleteStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
