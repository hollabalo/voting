import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class GetAllQuestionsServlet extends HttpServlet {
	public void init() throws ServletException {}
	public void destroy() {}
	public void doGet(HttpServletRequest request, 
		    HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		VotingDatabase db = VotingDatabase.getInstance();
		out.println("<?xml version=\"1.0\" ?>");
		out.println("<questions>");
		for(String q : db.getQuestions()) {
			out.println("<question>" + q + "</question>");
		}
		out.println("</questions>");
	}
}