package cmu.deloittecap.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import cmu.deloittecap.managers.ApplicationManager;

/**
 * Servlet implementation class CheckStatusServlet
 */
@WebServlet(name = "CheckStatus", urlPatterns = { "/CheckStatus" })
public class CheckStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckStatusServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String appid = request.getParameter("appid");

		try {
			ApplicationManager appmanager = new ApplicationManager();
			boolean verify = appmanager.verifyApplication(appid);
			PrintWriter output = response.getWriter();
			JSONObject req = new JSONObject();
			if (verify) {
				req.put("message", "No application found");
			}else{
				JSONArray re = appmanager.checkStatus(appid);
				req.put("message", "You have already applied!");
				req.put("result", re);
			}
			output.write(req.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
