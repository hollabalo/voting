import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class VoteAverageServlet extends HttpServlet {
	public void init() throws ServletException {}
	public void destroy() {}
	public void doGet(HttpServletRequest request, 
		    HttpServletResponse response) throws ServletException, IOException {
		VotingDatabase db = VotingDatabase.getInstance();
		String questionid = request.getParameter("questionid");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		out.println("<?xml version=\"1.0\" ?>");
		out.println("<result>");
		if(questionid != null && !questionid.equals("")) {
			try {
				int ave = db.getAnswerAverage(Integer.parseInt(questionid));
				out.println("<average>");
				out.println(ave);
				out.println("</average>");
			}
			catch(Exception e) {
				out.println("<code>300</code>");
				out.println("<message>Failed</message>");
			}
		}
		else {
			out.println("<code>300</code>");
			out.println("<message>Failed</message>");
		}
		out.println("</result>");
	}
}