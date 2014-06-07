package cmu.deloittecap.tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Household {

	public boolean createHousehold(String appid, int num, String address1,
			String address2, String city, String state, int zipcode,
			Connection con) throws SQLException {
		Statement statement = con.createStatement();
		int result = statement.executeUpdate("INSERT INTO household VALUES ('"
				+ appid + "', " + num + ", '" + address1 + "', '" + address2
				+ "', '" + city + "', '" + state + "', " + zipcode + ");");
		if (result == 1) {
			statement.close();
			return true;
		} else {
			statement.close();
			return false;
		}
	}

	public boolean verifyAppid(String appid, Connection con)
			throws SQLException {
		Statement statement = con.createStatement();
		ResultSet result = statement
				.executeQuery("SELECT * FROM household WHERE app_id='" + appid
						+ "';");
		if (!result.next()) {
			statement.close();
			result.close();
			return true;
		} else {
			statement.close();
			result.close();
			return false;
		}
	}

	public boolean delete(String appid, Connection con) throws SQLException {
		Statement statement = con.createStatement();
		int result = statement
				.executeUpdate("DELETE FROM household WHERE app_id='" + appid
						+ "';");
		if (result == 1) {
			statement.close();
			return true;
		} else {
			statement.close();
			return false;
		}
	}
}
