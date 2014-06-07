package cmu.deloittecap.tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Account {

	public boolean verifyAppID(String app_id, Connection con)
			throws SQLException {
		Statement statement = con.createStatement();
		ResultSet result = statement
				.executeQuery("SELECT * FROM account WHERE app_id='" + app_id
						+ "';");
		if (!result.next())
			return true;
		else
			return false;
	}

	public boolean login(String app_id, String password,
			Connection con) throws SQLException {
		Statement statement = con.createStatement();
		ResultSet result = statement
				.executeQuery("SELECT password FROM account WHERE app_id='"
						+ app_id + "';");
		boolean empty = result.next();
		if(!empty) return false;
		String psw = result.getString("password");
		if (psw == null || !psw.equals(password))
			return false;
		else
			return true;
	}

	public boolean createAccount(String app_id, String password, String firstName, String middleName, String lastName, String dob,	String email, String sq1, String sa1, String sq2, String sa2, Connection con) throws SQLException {
		Statement statement = con.createStatement();
		int result = statement
				.executeUpdate("INSERT INTO account (app_id, password, fname, mname, lname, dob, email, sq1, sa1, sq2, sa2) VALUE ('" + app_id + "', '" + password + "', '" + firstName + "', '" + middleName + "', '" + lastName + "', '" + dob + "', '" + email + "', '" + sq1 + "', '" + sa1 + "', '" + sq2 + "', '" + sa2 + "');");
		if(result == 1){
			con.commit();
			return true;
		}else
			con.rollback();
		return false;
	}
	
	public boolean resetPassword(String app_id, String password, Connection con) throws SQLException{
		Statement statement = con.createStatement();
		int result = statement.executeUpdate("UPDATE account SET password='" + password + "' WHERE app_id='" + app_id + "';");
		if(result == 1){
			con.commit();
			return true;
		}else{
			con.rollback();
			return false;
		}
	}
	
	public boolean validateSecurityQuestion(String app_id, String dob, String sq_num, String sa, Connection con) throws SQLException{
		Statement statement = con.createStatement();
		ResultSet result = statement.executeQuery("SELECT * FROM account WHERE app_id='" + app_id + "';");
		if(result.next() && dob.equals(result.getString("dob")) && sa.equals(result.getString("sa" + sq_num))){
			return true;
		}else{
			return false;
		}
	}
	
	public String getRandomSecurityQuestion(String app_id, String dob, Connection con) throws SQLException{
		Statement statement = con.createStatement();
		ResultSet result = statement.executeQuery("SELECT sq1, sa1 FROM account WHERE app_id='" + app_id + "' AND dob='" + dob + "';");
		String res = null;
		if(result.next()){
			res = result.getString("sq1") + result.getString("sa1");
			return res;
		}
		return "incorrect id or dob";
	}
}
