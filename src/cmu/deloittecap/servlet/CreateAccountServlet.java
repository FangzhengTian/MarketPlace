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

@WebServlet(name = "CreateAccount", urlPatterns = { "/CreateAccount" })
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateAccountServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String app_id = request.getParameter("app_id");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String middleName = request.getParameter("middleName");
		String lastName = request.getParameter("lastName");
		String dob = request.getParameter("dob");
		String email = request.getParameter("email");
		String sq1 = request.getParameter("sq1");
		String sa1 = request.getParameter("sa1");
		String sq2 = request.getParameter("sq2");
		String sa2 = request.getParameter("sa2");
		try {
			RegistrationManager remanager = new RegistrationManager();
			String result = remanager.register(app_id, password, firstName,
					middleName, lastName, dob, email, sq1, sa1,
					sq2, sa2);
			PrintWriter output = response.getWriter();
			JSONObject req = new JSONObject();
			req.put("success", result);
			output.write(req.toString());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
