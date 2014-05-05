package Class08;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ActivityManager implements AutoCloseable {

	private PreparedStatement getAllActivities;
	private PreparedStatement getActivities;
	private PreparedStatement getByNameStmt;
	private PreparedStatement getByDateStmt;
	private PreparedStatement insertStmt;
	private PreparedStatement updateStmt;
	private PreparedStatement updateNamesStmt;
	private PreparedStatement deleteStmt;
	
	public ActivityManager(Connection con) throws SQLException {
		try {
			getAllActivities = con.prepareStatement("SELECT text FROM activities");
			getActivities = con.prepareStatement("SELECT * FROM activities WHERE name = ?");
			getByNameStmt = con.prepareStatement("SELECT * FROM activities WHERE name = ? AND text = ?");
			getByDateStmt = con.prepareStatement("SELECT * FROM activities WHERE name = ? AND date = ?");
			updateNamesStmt = con.prepareStatement("UPDATE activities SET name = ? WHERE name = ?");
			
			insertStmt = con.prepareStatement("INSERT INTO activities (name, date, hours, text) VALUES (?, ?, ?, ?)");
			updateStmt = con.prepareStatement("UPDATE activities SET date = ?, hours = ?, text = ? WHERE name = ? AND date = ?");
			deleteStmt = con.prepareStatement("DELETE FROM activities WHERE name = ? AND date = ?");
		} catch (SQLException e) {
			throw new SQLException("Unable to initialize prepared statements.", e);
		}
	}
	
	public List<Activity> getActivities(String name) throws SQLException {
		List<Activity> activities = new ArrayList<Activity>();
		try {
			getActivities.setString(1, name);
			try (ResultSet results = getActivities.executeQuery()) {
				while (results.next()) {
					activities.add(new Activity(results.getString("date"), results.getString("hours"), results.getString("text")));
				}
			}
		} catch (Exception e) {
			throw new SQLException("Unable to list activities", e);
		}
		return activities;
	}
	
	public void editActivity(Activity a, String name, String date) throws SQLException {
		try {
			updateStmt.setString(1, a.getDate());
			updateStmt.setString(2, a.getHours());
			updateStmt.setString(3, a.getText());
			updateStmt.setString(4, name);
			updateStmt.setString(5, date);
			updateStmt.executeUpdate();
		} catch (Exception e) {
			throw new SQLException("Unable to edit activity", e);
		}
	}
	
	public Activity getActivityByDate(String name, String date) throws SQLException {
		Activity ac = null;
		try {
			getByDateStmt.setString(1, name);
			getByDateStmt.setString(2, date);
			
			try (ResultSet results = getByDateStmt.executeQuery()) { 
				if (results.next())
					ac = new Activity(results.getString("date"), results.getString("hours"), results.getString("text"));
			}
			
		} catch (Exception e) {
			throw new SQLException("Unable to list activities", e);
		}
		return ac;
	}
	
	// ADD
	public Activity addActivity(String name, String date, String hours, String text) throws SQLException {
		try {
			insertStmt.setString(1, name);
			insertStmt.setString(2, date);
			insertStmt.setString(3, hours);
			insertStmt.setString(4, text);
			insertStmt.executeUpdate();
			return new Activity(date, hours, text);
		} catch (SQLException e) {
			throw new SQLException("Unable to create new activity", e);
		}
	}
	
	// DELETE
	public void deleteActivity(String name, Activity a) throws SQLException {
		try {
			deleteStmt.setString(1, name);
			deleteStmt.setString(2, a.getDate());
			deleteStmt.executeUpdate();
		}
		catch (Exception e) {
			throw new SQLException("Unable to remove activity", e);
		}
		
	}
	
	public void changeNames(String name, String nameNew) throws SQLException {
		try {
			updateNamesStmt.setString(1, nameNew);
			updateNamesStmt.setString(2, name);
			updateNamesStmt.executeUpdate();
		}
		catch (Exception e) {
			throw new SQLException("Unable to edit all activities of person " + name, e);
		}
	}
	
	@Override
	public void close() {
		try {
			getAllActivities.close();
			getByNameStmt.close();
			insertStmt.close();
			updateStmt.close();
			deleteStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
