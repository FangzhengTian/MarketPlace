package cmu.deloittecap.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import cmu.deloittecap.managers.ApplicationManager;

/**
 * Servlet implementation class ApplyForBenefitsServlet
 */
@WebServlet(name = "ApplyForBenefits", urlPatterns = { "/ApplyForBenefits" })
public class ApplyForBenefitsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ApplicationManager aplmanager = new ApplicationManager();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ApplyForBenefitsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String h = request.getParameter("household");
		String fa = request.getParameter("family");
		String ja = request.getParameter("job");
		try {
			String result = aplmanager.storeInfo(h, fa, ja);
			PrintWriter output = response.getWriter();
			output.write(result);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
