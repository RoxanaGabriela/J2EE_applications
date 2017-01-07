package ro.pub.cs.paj.discussionforum.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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

import org.hibernate.SessionFactory;

import ro.pub.cs.paj.discussionforum.businesslogic.ClientManager;
import ro.pub.cs.paj.discussionforum.dataaccess.DatabaseException;
import ro.pub.cs.paj.discussionforum.dataaccess.DatabaseOperationsImplementation;
import ro.pub.cs.paj.discussionforum.db.Client;
import ro.pub.cs.paj.discussionforum.graphicuserinterfaces.LoginGraphicUserInterface;
import ro.pub.cs.paj.discussionforum.util.Constants;



/**
 * Servlet implementation class HelloWorld
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private String username;
    private String password;
    
    private ClientManager clientManager;
    
    @Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		username = null;
		password = null;
									
		clientManager = new ClientManager();
			
	}
    
    @Override
	public void destroy() {
	}
    
    public boolean isLoginError(String username, String password) {
		return (username != null && !username.isEmpty() && password != null && !password.isEmpty()
				&& clientManager.getType(username, password) == Constants.USER_NONE);
	}
 
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    }
 
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		Enumeration<String> parameters = request.getParameterNames();
		boolean found = false;
		boolean loggedIn = true;
		while (parameters.hasMoreElements()) {
			String parameter = (String) parameters.nextElement();
			if (parameter.equals(Constants.USERNAME)) {
				found = true;
				username = request.getParameter(parameter);
			}
			if (parameter.equals(Constants.PASSWORD)) {
				found = true;
				password = request.getParameter(parameter);
			}
			
			if (parameter.equals("UserNoAccountButton")) {
				loggedIn = false;
			}
			
		}
		
		if (!found) {
			username = "";
			password = "";
		}
		response.setContentType("text/html");
		try (PrintWriter printWriter = new PrintWriter(response.getWriter())) {
			int type = clientManager.getType(username, password);
			if(loggedIn == false)
				type = Constants.USER_NOACCOUNT;
			if (type != Constants.USER_NONE) {
				HttpSession session = request.getSession(true);
				session.setAttribute("display", clientManager.getDisplay(username, password));
				session.setAttribute("loggedin", isLoginError(username, password));
				
				session.setAttribute("userLogged", username);
				
				RequestDispatcher dispatcher = null;
				switch (type) {
				case Constants.USER_ADMINISTRATOR:
					dispatcher = getServletContext()
							.getRequestDispatcher("/" + Constants.ADMINISTRATOR_SERVLET_PAGE_CONTEXT);
					break;
				case Constants.USER_CLIENT:
					dispatcher = getServletContext().getRequestDispatcher("/" + Constants.CLIENT_SERVLET_TOPICHOME_PAGE_CONTEXT);					
					break;
				
				case Constants.USER_NOACCOUNT:
					dispatcher = getServletContext().getRequestDispatcher("/" + Constants.CLIENT_SERVLET_TOPICHOME_PAGE_CONTEXT);					
					break;
					
				}
				if (dispatcher != null) {
					dispatcher.forward(request, response);
				}
			}
			
			boolean loginError = isLoginError(username, password);
			
			LoginGraphicUserInterface.displayLoginGraphicUserInterface(loginError, printWriter);
			
		}
    }
}
