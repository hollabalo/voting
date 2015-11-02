import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class VoteServlet extends HttpServlet {
	public void init() throws ServletException {}
	public void destroy() {}
	public void doGet(HttpServletRequest request, 
		    HttpServletResponse response) throws ServletException, IOException {
		VotingDatabase db = VotingDatabase.getInstance();
		String questionid = request.getParameter("questionid");
		String value = request.getParameter("value");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		out.println("<?xml version=\"1.0\" ?>");
		out.println("<result>");
		if(questionid != null && !questionid.equals("") &&
		   value != null && !value.equals("")) {
		   	try {
				if(db.addAnswer(Integer.parseInt(questionid), Integer.parseInt(value))) {
					out.println("<code>200</code>");
					out.println("<message>Success</message>");
				}
				else {
					out.println("<code>300</code>");
					out.println("<message>Failed</message>");
				}
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