package ro.pub.cs.paj.discussionforum.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ro.pub.cs.paj.discussionforum.businesslogic.ClientManager;
import ro.pub.cs.paj.discussionforum.businesslogic.PostDiscussionManager;
import ro.pub.cs.paj.discussionforum.businesslogic.PostManager;
import ro.pub.cs.paj.discussionforum.db.Comment;
import ro.pub.cs.paj.discussionforum.db.Post;
import ro.pub.cs.paj.discussionforum.graphicuserinterfaces.TopicDiscussionGraphicInterface;
import ro.pub.cs.paj.discussionforum.graphicuserinterfaces.TopicGraphicInterface;
import ro.pub.cs.paj.discussionforum.util.Constants;

@WebServlet("/PostDiscussionServlet")
public class PostDiscussionServlet extends HttpServlet{
	
	
	private List<Comment> Comments;
	
	@Override
    public void init(ServletConfig config) throws ServletException {
		super.init(config);
					
		
	}
	
	@Override
	public void destroy() {
	}
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		
		boolean foundLogedIn = false;		
		
		String topicToNavigate;
		
		try (PrintWriter printWriter = new PrintWriter(response.getWriter())) {
		
			Enumeration<String> parameters = request.getParameterNames();
			while (parameters.hasMoreElements()) {
				
				PostDiscussionManager tm = new PostDiscussionManager();
				try {
					Comments = tm.getElements();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
				String parameter = (String) parameters.nextElement();
				try {
					for(int i = 0 ;i < tm.getElements().size(); i++)
						if (parameter.equals(tm.getElements().get(i).getDescription())){
							topicToNavigate = tm.getElements().get(i).getDescription();
						}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			//TODO la request se trimite si daca userul este logat sau nu
			if(parameters.equals(true))
				foundLogedIn = true;
			
			response.setContentType("text/html");
			
			RequestDispatcher dispatcher = null;
			
			dispatcher = getServletContext()
						.getRequestDispatcher("/" + Constants.CLIENT_SERVLET_TOPICDISCUSSION_PAGE_CONTEXT);
						
			
			PostDiscussionManager tm = new PostDiscussionManager();
			try {
				Comments = tm.getElements();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		
			TopicDiscussionGraphicInterface.displayLoginGraphicUserInterface(false, Comments, printWriter);	
		}
			
	}
	
	
}
