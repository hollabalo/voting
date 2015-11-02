import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddQuestionServlet extends HttpServlet {
	public void init() throws ServletException {}
	public void destroy() {}
	public void doGet(HttpServletRequest request, 
		    HttpServletResponse response) throws ServletException, IOException {
		VotingDatabase db = VotingDatabase.getInstance();
		String question = request.getParameter("text");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		out.println("<?xml version=\"1.0\" ?>");
		out.println("<result>");
		if(question != null && !question.equals("")) {
			if(db.addQuestion(question)) {
				out.println("<code>200</code>");
				out.println("<message>Success</message>");
			}
			else {
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