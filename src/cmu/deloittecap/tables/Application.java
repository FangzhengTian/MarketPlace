package cmu.deloittecap.tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

public class Application {

	public boolean verifyAppid(String appid, Connection con)
			throws SQLException {
		Statement statement = con.createStatement();
		ResultSet result = statement
				.executeQuery("SELECT * FROM application WHERE app_id='"
						+ appid + "';");
		boolean re = true;
		if (result.next()) {
			re = false;
		}
		statement.close();
		result.close();
		return re;
	}

	public JSONArray checkStatus(String appid, Connection con) throws Exception {
		Statement statement = con.createStatement();
		ResultSet result = statement
				.executeQuery("SELECT * FROM application INNER JOIN family ON application.app_id=family.app_id AND application.person_id=family.person_id INNER JOIN plan ON application.plan_id=plan.plan_id WHERE application.app_id='"
						+ appid + "';");
		JSONArray jarray = new JSONArray();
		while (result.next()) {
			JSONObject js = new JSONObject();
			js.put("name",
					result.getString("fname") + " " + result.getString("lname"));
			String plan = result.getString("plan_name");
			js.put("plan", plan);
			if (!plan.equalsIgnoreCase("Medicare/Medicaid")) {
				js.put("status", "Processing");
			} else {
				js.put("status", "No status");
			}
			jarray.put(js);
		}
		statement.close();
		result.close();
		return jarray;
	}

	public boolean insertApplication(String appid, int personid, int planid,
			Connection con) throws Exception {
		Statement statement = con.createStatement();
		//Calendar c = Calendar.getInstance();	
		int result = statement.executeUpdate("INSERT INTO application VALUES('"
				+ appid + "', " + personid + ", " + planid + ", '1', '2');");
		if (result == 1) {
			con.commit();
			statement.close();
			return true;
		} else {
			con.rollback();
			statement.close();
			return false;
		}
	}
}
