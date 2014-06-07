package cmu.deloittecap.tables;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Job {

	public boolean createJob(String appid, int personid, int incomeid,
			String incometype, int income, String jobname, Connection con)
			throws SQLException {
		Statement statement = con.createStatement();
		int r = statement.executeUpdate("INSERT INTO job VALUES ('" + appid
				+ "', " + personid + ", " + incomeid + ", '" + incometype
				+ "', " + income + ", '" + jobname + "');");
		if (r == 1) {
			statement.close();
			return true;
		} else {
			statement.close();
			return false;
		}
	}
	
	public boolean delete(String appid, Connection con) throws SQLException{
		Statement statement = con.createStatement();
		int result = statement.executeUpdate("DELETE FROM job WHERE app_id='" + appid + "';");
		if (result >= 1) {
			statement.close();
			return true;
		} else {
			statement.close();
			return false;
		}
	}
}
