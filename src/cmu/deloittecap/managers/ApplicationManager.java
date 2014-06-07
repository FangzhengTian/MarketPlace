package cmu.deloittecap.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

import cmu.deloittecap.tables.Application;
import cmu.deloittecap.tables.Family;
import cmu.deloittecap.tables.Household;
import cmu.deloittecap.tables.Job;
import cmu.deloittecap.tables.Plan;

public class ApplicationManager {
	private Household household;
	private Family family;
	private Job job;
	private Plan plan;
	private Application application;
	Connection con;
	private String address = "jdbc:mysql://localhost/deloitte?user=root&password=deloitte";

	public String storeInfo(String h, String fa, String ja) throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection(address);
		con.setAutoCommit(false);

		household = new Household();
		family = new Family();
		job = new Job();

		JSONObject house = new JSONObject(h);

		String appid = house.getString("appid");
		if (!household.verifyAppid(appid, con)) {
			boolean b1 = job.delete(appid, con);
			boolean b2 = family.delete(appid, con);
			boolean b3 = household.delete(appid, con);
			if(b1 && b2 && b3) con.commit();
			else con.rollback();
		}
		con.close();
		return insertinfo(h, fa, ja);
	}

	private String insertinfo(String h, String fa, String ja)
			throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection(address);
		con.setAutoCommit(false);
		
		household = new Household();
		family = new Family();
		job = new Job();

		JSONArray results = new JSONArray();
		int totalincome = 0;

		JSONObject house;
		boolean s1 = false;
		house = new JSONObject(h);
		String appid = house.getString("appid");
		int num = house.getInt("num");
		String address1 = house.getString("address1");
		String address2 = house.getString("address2");
		String city = house.getString("city");
		String state = house.getString("state");
		int zipcode = house.getInt("zipcode");
		s1 = household.createHousehold(appid, num, address1, address2, city,
				state, zipcode, con);
		
		JSONArray members;
		boolean s2 = true;
		members = new JSONArray(fa);
		int len1 = members.length();
		for (int i = 0; i < len1; i++) {
			JSONObject one = members.getJSONObject(i);
			String apid = one.getString("appid");
			int personid = one.getInt("personid");
			String fname = one.getString("fname");
			String mname = one.getString("mname");
			String lname = one.getString("lname");
			String dob = one.getString("dob");
			boolean insured = one.getBoolean("insured");
			char gender = (char) one.getInt("gender");
			boolean pregnancy = one.getBoolean("pregnant");
			boolean disability = one.getBoolean("disability");
			boolean esrd_als = one.getBoolean("esrd_als");
			boolean medicare_medicaid = one.getBoolean("medicare_medicaid");
			boolean taxpayer = one.getBoolean("taxpayer");

			Calendar c = Calendar.getInstance();
			int thisyear = c.get(Calendar.YEAR);
			int birthyear = Integer.valueOf(dob.split("/")[2]);
			int age = thisyear - birthyear;

			if (age > 65 || insured || disability || esrd_als
					|| medicare_medicaid) {
				JSONObject j = new JSONObject();
				j.put("personid", personid);
				j.put("name", fname + " " + lname);
				j.put("plan", "medicare/medicaid");
				j.put("category", "medicare/medicaid");
				results.put(j);
			} else {
				JSONObject j = new JSONObject();
				j.put("personid", personid);
				j.put("name", fname + " " + lname);
				j.put("plan", "");
				j.put("category", "");
				results.put(j);
			}

			s2 = s2
					&& family.createFamilyMember(apid, personid, fname, mname,
							lname, dob, insured, gender, pregnancy, disability,
							esrd_als, medicare_medicaid, taxpayer, con);
		}

		JSONArray jobs = new JSONArray(ja);
		int len2 = jobs.length();
		boolean s3 = true;
		for (int i = 0; i < len2; i++) {
			JSONObject one = jobs.getJSONObject(i);
			String apid = one.getString("appid");
			int personid = one.getInt("personid");
			int incomeid = one.getInt("incomeid");
			String incometype = one.getString("incometype");
			int income = one.getInt("income");
			totalincome += income;
			String jobname = one.getString("jobname");
			s3 = s3
					&& job.createJob(apid, personid, incomeid, incometype,
							income, jobname, con);
		}

		plan = new Plan();
		String presult = plan.getContentByIncome(totalincome, con);
		String[] pp = presult.split("\t");
		for (int i = 0; i < results.length(); i++) {
			String tmp = results.getJSONObject(i).getString("plan");
			if (tmp.equals(""))
				results.getJSONObject(i).put("plan", pp[0]);
			tmp = results.getJSONObject(i).getString("category");
			if (tmp.equals(""))
				results.getJSONObject(i).put("category", pp[1]);
		}
		JSONObject js = new JSONObject();
		js.put("result", results);
		js.put("planid", pp[2]);

		if (s1 && s2 && s3) {
			con.commit();
			con.close();
			return js.toString();
		} else {
			con.rollback();
			con.close();
			return null;
		}
	}

	public String finishApplicaiton(String appid, int planid, String results) throws Exception{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection(address);
		con.setAutoCommit(false);
		
		application = new Application();
		boolean re = true;
		JSONArray jarray = new JSONArray(results);
		for(int i=0;i<jarray.length();i++){
			JSONObject js = jarray.getJSONObject(i);
			int personid = js.getInt("personid");
			String p = js.getString("plan");
			int pid = planid;
			if(p.equalsIgnoreCase("Medicare/Medicaid")) pid = 4;
			re = re && application.insertApplication(appid, personid, pid, con);
		}
		if(re){
			con.close();
			return "Application finished!";
		}
		con.close();
		return "Application failed!";
		
	}
	
	public JSONArray checkStatus(String appid) throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection(address);

		application = new Application();
		JSONArray re = application.checkStatus(appid, con);
		con.close();
		return re;
	}
	
	public boolean verifyApplication(String appid) throws Exception{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection(address);

		application = new Application();
		boolean re = application.verifyAppid(appid, con);
		con.close();
		return re;
	}
}
