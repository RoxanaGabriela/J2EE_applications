package ro.pub.cs.paj.discussionforum.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
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
import ro.pub.cs.paj.discussionforum.businesslogic.PostDiscussionManager;
import ro.pub.cs.paj.discussionforum.businesslogic.PostManager;
import ro.pub.cs.paj.discussionforum.db.Comment;
import ro.pub.cs.paj.discussionforum.db.Post;
import ro.pub.cs.paj.discussionforum.graphicuserinterfaces.PostDiscussionGraphicUserInterface;
import ro.pub.cs.paj.discussionforum.util.Constants;
import ro.pub.cs.paj.discussionforum.util.Utilities;

@WebServlet("/PostDiscussionServlet")
public class PostDiscussionServlet extends HttpServlet {
	private static final long serialVersionUID = -6934086532062597020L;

	private PostManager postManager;
	private ClientManager clientManager;
	private PostDiscussionManager postDiscussionManager = new PostDiscussionManager();
	private List<Comment> comments;
	private String previousRecordsPerPage;
	private String currentRecordsPerPage;
	private String currentPage;
	private String postId;
	private Post post;
	
	private boolean loggedIn;
	private String username;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		postManager = new PostManager();
		clientManager = new ClientManager();
		previousRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
		currentRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
		currentPage = String.valueOf(1);

		comments = null;
		postId = null;
		post = new Post();
		loggedIn = false;
		username = null;
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
			String newComment = null;

			if (session.getAttribute("username") != null) {
				username = (String) session.getAttribute("username");
			}
			
			if (session.getAttribute("loggedIn") != null) {
				loggedIn = (boolean) session.getAttribute("loggedIn");
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
				
				if (parameter.equals("newComment")) {
					newComment = request.getParameter(parameter);
				}

				if(parameter.startsWith(Constants.INSERT_BUTTON_NAME.toLowerCase()) &&
						parameter.endsWith(".x")) {
					if(newComment != null && !newComment.isEmpty()) {
						List<String> val = new ArrayList<String>();
						val.add(newComment);

						java.sql.Date currdate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
						val.add(currdate.toString());

						Calendar rightNow = Calendar.getInstance();					
						int hour = rightNow.get(Calendar.HOUR_OF_DAY);
						int minutes = rightNow.get(Calendar.MINUTE);
						int seconds = rightNow.get(Calendar.SECOND);

						String time = hour+":"+minutes+":"+seconds;

						val.add(time);

						int clientId = clientManager.getUserId(username);
						val.add(clientId + "");
						val.add(postId + "");
						val.add("0");

						postDiscussionManager.create(val);
					}
				}
				
				if (parameter.equals(Constants.HOME.toLowerCase() + ".x")) {
					Enumeration<String> requestParameters = request.getParameterNames();
					while (requestParameters.hasMoreElements()) {
						request.removeAttribute(requestParameters.nextElement());
					}
					RequestDispatcher dispatcher = getServletContext()
							.getRequestDispatcher("/" + Constants.POST_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
					break;
				}
				
				if (!loggedIn) {
					if (parameter.equals(Constants.SIGNIN.toLowerCase() + ".x")) {
						Enumeration<String> requestParameters = request.getParameterNames();
						while (requestParameters.hasMoreElements()) {
							request.removeAttribute(requestParameters.nextElement());
						}
						RequestDispatcher dispatcher = getServletContext()
								.getRequestDispatcher("/" + Constants.LOGIN_SERVLET_PAGE_CONTEXT);
						if (dispatcher != null) {
							dispatcher.forward(request, response);
						}
						break;
					}
					if (parameter.equals(Constants.SIGNUP.toLowerCase() + ".x")) {
						Enumeration<String> requestParameters = request.getParameterNames();
						while (requestParameters.hasMoreElements()) {
							request.removeAttribute(requestParameters.nextElement());
						}
						RequestDispatcher dispatcher = getServletContext()
								.getRequestDispatcher("/" + Constants.REGISTER_SERVLET_PAGE_CONTEXT);
						if (dispatcher != null) {
							dispatcher.forward(request, response);
						}
						break;
					}
				}
				
				if (loggedIn) {
					if (parameter.equals(Constants.SIGNOUT.toLowerCase() + ".x")) {
						Enumeration<String> requestParameters = request.getParameterNames();
						while (requestParameters.hasMoreElements()) {
							request.removeAttribute(requestParameters.nextElement());
						}
						previousRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
						currentRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
						currentPage = String.valueOf(1);
						loggedIn = false;
						session.invalidate();
						break;
					}
				}
			}

			if (comments == null)
				comments = postDiscussionManager.getElements(Integer.parseInt(postId));
			PostDiscussionGraphicUserInterface.displayPostDiscussionGraphicUserInterface(username, loggedIn,
					post, comments,
					(currentRecordsPerPage != null) ? Integer.parseInt(currentRecordsPerPage)
							: Constants.RECORDS_PER_PAGE_VALUES[0],
							(currentPage != null && currentRecordsPerPage != null
							&& currentRecordsPerPage.equals(previousRecordsPerPage)) ? Integer.parseInt(currentPage)
									: 1,
									printWriter);
		}
	}
}