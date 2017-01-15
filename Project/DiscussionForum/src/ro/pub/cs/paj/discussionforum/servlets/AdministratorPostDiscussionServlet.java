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

import ro.pub.cs.paj.discussionforum.businesslogic.PostDiscussionManager;
import ro.pub.cs.paj.discussionforum.businesslogic.PostManager;
import ro.pub.cs.paj.discussionforum.db.Comment;
import ro.pub.cs.paj.discussionforum.db.Post;
import ro.pub.cs.paj.discussionforum.util.Constants;
import ro.pub.cs.paj.discussionforum.util.Utilities;
import ro.pub.cs.paj.discussionforum.graphicuserinterfaces.AdministratorPostDiscussionGraphicUserInterface;

@WebServlet("/AdministratorPostDiscussionServlet")
public class AdministratorPostDiscussionServlet extends HttpServlet {
	private static final long serialVersionUID = -907774573317683471L;

	private PostManager postManager;
	private PostDiscussionManager postDiscussionManager = new PostDiscussionManager();
	private List<Comment> comments;
	private String previousRecordsPerPage;
	private String currentRecordsPerPage;
	private String currentPage;
	private String postId;
	private Post post;
	
	private String username;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		postManager = new PostManager();
		previousRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
		currentRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
		currentPage = String.valueOf(1);

		comments = null;
		postId = null;
		post = new Post();
	}

	@Override
	public void destroy() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		HttpSession session = request.getSession(true);
		response.setContentType("text/html");

		try (PrintWriter printWriter = new PrintWriter(response.getWriter())) {
			boolean listChanged = false;
			
			username = null;

			if (session.getAttribute("username") != null) {
				username = (String) session.getAttribute("username");
			}
			
			postId = (String)session.getAttribute("postId");
			post = postManager.getPostDetails(postId);

			Enumeration<String> parameters = request.getParameterNames();
			while (parameters.hasMoreElements()) {
				String parameter = (String) parameters.nextElement();
				if (parameter.equals(Utilities.removeSpaces(Constants.RECORDS_PER_PAGE.toLowerCase().trim()))) {
					currentRecordsPerPage = request.getParameter(parameter);
				}
				if (parameter.equals(Utilities.removeSpaces(Constants.PAGE.toLowerCase().trim()))) {
					currentPage = request.getParameter(parameter);
				}
				
				if (parameter.equals(Constants.HOME.toLowerCase() + ".x")) {
					Enumeration<String> requestParameters = request.getParameterNames();
					while (requestParameters.hasMoreElements()) {
						request.removeAttribute(requestParameters.nextElement());
					}
					RequestDispatcher dispatcher = getServletContext()
							.getRequestDispatcher("/" + Constants.ADMINISTRATOR_POST_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
					break;
				}
				
				if(parameter.startsWith(Constants.ACCEPT_BUTTON_NAME.toLowerCase()) &&
						parameter.endsWith(".x")) {
						String id = parameter.substring(parameter.lastIndexOf("_") + 1, parameter.indexOf(".x"));

						List<String> attributes = new ArrayList<String>();
						attributes.add("banned");
						List<String> values = new ArrayList<String>();
						values.add("1");
						
						postDiscussionManager.update(attributes, values, Integer.parseInt(id));
						listChanged = true;
				}
				
				if(parameter.startsWith(Constants.DELETE_BUTTON_NAME.toLowerCase()) &&
						parameter.endsWith(".x")) {
						String id = parameter.substring(parameter.lastIndexOf("_") + 1, parameter.indexOf(".x"));

						List<String> attributes = new ArrayList<String>();
						attributes.add("banned");
						List<String> values = new ArrayList<String>();
						values.add("-1");
						
						postDiscussionManager.update(attributes, values, Integer.parseInt(id));
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
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext()
							.getRequestDispatcher("/" + Constants.POST_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
					session.invalidate();
					break;
				}
			}
			
			if (comments == null || listChanged)
				comments = postDiscussionManager.getAdminComments(Integer.parseInt(postId));
			AdministratorPostDiscussionGraphicUserInterface.displayAdministratorPostDiscussionGraphicUserInterface(
					username, post, comments,
					(currentRecordsPerPage != null) ? Integer.parseInt(currentRecordsPerPage)
							: Constants.RECORDS_PER_PAGE_VALUES[0],
							(currentPage != null && currentRecordsPerPage != null
							&& currentRecordsPerPage.equals(previousRecordsPerPage)) ? Integer.parseInt(currentPage)
									: 1,
									printWriter);
		}
	}
}
