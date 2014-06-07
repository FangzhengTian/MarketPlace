package cmu.deloittecap.managers;

import java.sql.Connection;
import java.sql.DriverManager;

import cmu.deloittecap.tables.Account;

public class RegistrationManager {

	private Account account;
	Connection con;
	private String address = "jdbc:mysql://localhost/deloitte?user=root&password=deloitte";

	public RegistrationManager() throws Exception {

	}

	public String register(String app_id, String password, String firstName,
			String middleName, String lastName, String dob, String email,
			String sq1, String sa1, String sq2, String sa2) throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection(address);
		con.setAutoCommit(false);
		account = new Account();
		String result;
		if (account.verifyAppID(app_id, con)) {
			boolean s = account.createAccount(app_id, password, firstName,
					middleName, lastName, dob, email, sq1, sa1, sq2, sa2, con);
			if (s)
				result = "success";
			else
				result = "failed";
		} else {
			result = "Id has been used";
		}
		con.close();
		return result;
	}

	public boolean login(String app_id, String password) throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection(address);
		account = new Account();
		boolean success = account.login(app_id, password, con);
		con.close();
		return success;
	}

	public boolean validateSecurityQuestion(String app_id, String dob, String sq_num, String sa) throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection(address);
		account = new Account();
		boolean success = account.validateSecurityQuestion(app_id, dob,
				sq_num, sa, con);
		con.close();
		return success;
	}

	public boolean resetPassword(String app_id, String password) throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection(address);
		con.setAutoCommit(false);
		account = new Account();
		boolean success = account.resetPassword(app_id, password, con);
		con.close();
		return success;
	}
	
	public String pickQuestion(String app_id, String dob) throws Exception{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection(address);
		account = new Account();
		String question = account.getRandomSecurityQuestion(app_id, dob, con);
		con.close();
		return question;
	}
}
