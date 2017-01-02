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
import javax.servlet.http.HttpSession;

import ro.pub.cs.paj.discussionforum.businesslogic.ClientManager;
import ro.pub.cs.paj.discussionforum.businesslogic.PostManager;
import ro.pub.cs.paj.discussionforum.graphicuserinterfaces.TopicGraphicInterface;
import ro.pub.cs.paj.discussionforum.util.Constants;
import ro.pub.cs.paj.discussionforum.db.Post;
import ro.pub.cs.paj.discussionforum.db.Topic;

@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet{
	
	private ClientManager clientManager;
    private boolean logedIn;
	
    private List<Post> Posts;
    
	
	@Override
    public void init(ServletConfig config) throws ServletException {
		super.init(config);
					
		clientManager = new ClientManager();
		//TODO set variable after login/no login
		logedIn = false;
	}
    
    @Override
	public void destroy() {
	}
	
	 public boolean isLoginError(String username, String password) {
			return (username != null && !username.isEmpty() && password != null && !password.isEmpty()
					&& clientManager.getType(username, password) == Constants.USER_NONE);
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
				
				
				PostManager tm = new PostManager();
				try {
					Posts = tm.getElements();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
				String parameter = (String) parameters.nextElement();
				try {
					for(int i = 0 ;i < tm.getElements().size(); i++)
						if (parameter.equals(tm.getElements().get(i).getTitle())){
							topicToNavigate = tm.getElements().get(i).getTitle();
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
						
			
			PostManager tm = new PostManager();
			try {
				Posts = tm.getElements();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		
			TopicGraphicInterface.displayLoginGraphicUserInterface(false, Posts, printWriter);	
		}
			
	}
}
