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
 * Servlet implementation class FinishApplication
 */
@WebServlet(name = "FinishApplication", urlPatterns = { "/FinishApplication" })
public class FinishApplicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ApplicationManager aplmanager = new ApplicationManager();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FinishApplicationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String appid = request.getParameter("appid");
		int planid = Integer.valueOf(request.getParameter("planid"));
		String results = request.getParameter("result");
		try {
			String result = aplmanager.finishApplicaiton(appid, planid, results);
			PrintWriter output = response.getWriter();
			JSONObject req = new JSONObject();
			req.put("result", result);
			output.write(req.toString());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
