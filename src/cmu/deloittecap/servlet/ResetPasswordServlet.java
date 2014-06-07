package cmu.deloittecap.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import cmu.deloittecap.managers.RegistrationManager;

/**
 * Servlet implementation class ForgetPasswordServlet
 */
@WebServlet(name = "ResetPassword", urlPatterns = {"/ResetPassword"})
public class ResetPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ResetPasswordServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String password = request.getParameter("password");
		String appid = request.getParameter("app_id");
		
		try {
			RegistrationManager remanager = new RegistrationManager();
			boolean success = remanager.resetPassword(appid, password);	
			PrintWriter output = response.getWriter();
			JSONObject req = new JSONObject();
			req.put("success", Boolean.toString(success));
			output.write(req.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
