package ro.pub.cs.paj.discussionforum.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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

import ro.pub.cs.paj.discussionforum.businesslogic.PostManager;
import ro.pub.cs.paj.discussionforum.db.Post;
import ro.pub.cs.paj.discussionforum.util.Constants;
import ro.pub.cs.paj.discussionforum.util.Utilities;
import ro.pub.cs.paj.discussionforum.graphicuserinterfaces.AdministratorPostGraphicUserInterface;

@WebServlet("/AdministratorPostServlet")
public class AdministratorPostServlet extends HttpServlet {

	public final static long serialVersionUID = 10011001L;

	private PostManager postManager;
	private String username;
	private List<Post> posts;
	
	private String previousRecordsPerPage;
	private String currentRecordsPerPage;
	private String currentPage;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		postManager = new PostManager();
		
		previousRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
		currentRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
		currentPage = String.valueOf(1);
		
		username = null;
		posts = null;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		response.setContentType("text/html");

		try (PrintWriter printWriter = new PrintWriter(response.getWriter())) {	
			boolean listChanged = false;
			
			if (session.getAttribute("username") != null) {
				username = (String) session.getAttribute("username");
			}
			
			Enumeration<String> parametersTopic = request.getParameterNames();
			while (parametersTopic.hasMoreElements()) {
				String parameter = (String) parametersTopic.nextElement();
				
				if (parameter.equals(Utilities.removeSpaces(Constants.RECORDS_PER_PAGE.toLowerCase().trim()))) {
					currentRecordsPerPage = request.getParameter(parameter);
				}
				if (parameter.equals(Utilities.removeSpaces(Constants.PAGE.toLowerCase().trim()))) {
					currentPage = request.getParameter(parameter);
				}
				
				if (parameter.startsWith(Constants.HOME.toLowerCase()) &&
						parameter.endsWith(".x")) {
					posts = null;
				}
				
				if (parameter.startsWith(Constants.VIEW_BUTTON_NAME.toLowerCase()) &&
						parameter.endsWith(".x")) {
					String id = parameter.substring(parameter.lastIndexOf("_") + 1, parameter.indexOf(".x"));
					session.setAttribute("postId", id);
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext()
							.getRequestDispatcher("/" + Constants.ADMINISTRATOR_POSTDISCUSSION_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
				}
				
				if(parameter.startsWith(Constants.ACCEPT_BUTTON_NAME.toLowerCase()) &&
						parameter.endsWith(".x")) {
						String id = parameter.substring(parameter.lastIndexOf("_") + 1, parameter.indexOf(".x"));

						List<String> attributes = new ArrayList<String>();
						attributes.add("banned");
						List<String> values = new ArrayList<String>();
						values.add("1");
						
						postManager.update(attributes, values, Integer.parseInt(id));
						listChanged = true;
				}
				
				if(parameter.startsWith(Constants.DELETE_BUTTON_NAME.toLowerCase()) &&
						parameter.endsWith(".x")) {
						String id = parameter.substring(parameter.lastIndexOf("_") + 1, parameter.indexOf(".x"));

						List<String> attributes = new ArrayList<String>();
						attributes.add("banned");
						List<String> values = new ArrayList<String>();
						values.add("-1");
						
						postManager.update(attributes, values, Integer.parseInt(id));
						listChanged = true;
				}
				
				if (parameter.equals(Constants.SIGNOUT.toLowerCase() + ".x")) {
					Enumeration<String> requestParameters = request.getParameterNames();
					while (requestParameters.hasMoreElements()) {
						request.removeAttribute(requestParameters.nextElement());
					}
					previousRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
					currentRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
					currentPage = String.valueOf(1);
					session.setAttribute("loggedIn", false);
					session.removeAttribute("username");
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext()
							.getRequestDispatcher("/" + Constants.POST_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
					//session.invalidate();
					break;
				}
			}
			
			if (posts == null || listChanged)
				posts = postManager.getAdminPosts();

			AdministratorPostGraphicUserInterface.displayAdministratorPostGraphicUserInterface(username, posts,
					(currentRecordsPerPage != null) ? Integer.parseInt(currentRecordsPerPage)
							: Constants.RECORDS_PER_PAGE_VALUES[0],
							(currentPage != null && currentRecordsPerPage != null
							&& currentRecordsPerPage.equals(previousRecordsPerPage)) ? Integer.parseInt(currentPage)
									: 1, printWriter);
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
