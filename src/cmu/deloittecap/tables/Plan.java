package cmu.deloittecap.tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Plan {
	private int povertyline = 3000;

	public String getContentByIncome(int income, Connection con) throws SQLException {
		double poverty133 = povertyline * 1.33;
		int poverty500 = povertyline * 500;
		int id = 0;
		if(income < poverty133){
			return "Medicare/Medicaid\tMedicare/Medicaid\t4"; 
		}else if(income < poverty500 || income > poverty133){
			id = 1;
		}else{
			id = 3;
		}
		Statement statement = con.createStatement();
		ResultSet result = statement
				.executeQuery("SELECT plan_name,plan_type FROM plan WHERE plan_id=" + id
						+ ";");
		result.next();
		String r = result.getString("plan_name") + "\t" + result.getString("plan_type") + "\t" + id;
		statement.close();
		result.close();
		return r;
	}
}
