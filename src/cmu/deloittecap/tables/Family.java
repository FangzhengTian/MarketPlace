package cmu.deloittecap.tables;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Family {

	public boolean createFamilyMember(String appid, int personid, String fname, String mname,
			String lname, String dob, boolean insured, char gender, boolean pregnant,
			boolean disability, boolean esrd_als, boolean medicare_medicaid, boolean taxpayer, Connection con)
			throws SQLException {
		Statement statement = con.createStatement();
		int r = statement.executeUpdate("INSERT INTO family VALUES ('"
				+ appid + "', " + personid + ", '" + fname + "', '" + mname
				+ "', '" + lname + "', '" + dob + "', " + insured + ", '" + gender + "', "
				+ pregnant + ", " + disability + ", " + esrd_als + ", "
				+ medicare_medicaid + ", " + taxpayer + ");");
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
		int result = statement.executeUpdate("DELETE FROM family WHERE app_id='" + appid + "';");
		if (result >= 1) {
			statement.close();
			return true;
		} else {
			statement.close();
			return false;
		}
	}
}
