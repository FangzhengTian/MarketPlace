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
 * Servlet implementation class ValidateSecurityQuestionServlet
 */
@WebServlet(name = "PickSecurityQuestion", urlPatterns = {"/PickSecurityQuestion"})
public class PickSecurityQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PickSecurityQuestionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String appid = request.getParameter("app_id");
		String dob = request.getParameter("dob");
		
		try {
			RegistrationManager remanager = new RegistrationManager();
			String result = remanager.pickQuestion(appid, dob);
			PrintWriter output = response.getWriter();
			JSONObject req = new JSONObject();
			req.put("question", result);
			output.write(req.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
