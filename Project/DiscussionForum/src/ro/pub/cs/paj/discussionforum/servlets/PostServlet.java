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
import ro.pub.cs.paj.discussionforum.businesslogic.PostManager;
import ro.pub.cs.paj.discussionforum.graphicuserinterfaces.PostGraphicUserInterface;
import ro.pub.cs.paj.discussionforum.util.Constants;
import ro.pub.cs.paj.discussionforum.util.Utilities;
import ro.pub.cs.paj.discussionforum.db.Post;

@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {

	private static final long serialVersionUID = -1864122641544054598L;
	private PostManager postManager;
	private ClientManager clientManager;
	private List<Post> posts;
	private String previousRecordsPerPage;
	private String currentRecordsPerPage;
	private String currentPage;
	
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

		posts = null;
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
			String newPostTitle = null;
			String newPostDescription = null;
			
			if (session.getAttribute("username") != null) {
				username = (String) session.getAttribute("username");
			}
			if (session.getAttribute("loggedIn") != null) {
				loggedIn = (boolean) session.getAttribute("loggedIn");
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
				
				if (parameter.equals("newPostTitle")) {
					newPostTitle = request.getParameter(parameter);
				}

				if (parameter.equals("newPostDescription")) {
					newPostDescription = request.getParameter(parameter);
				}
				
				if (parameter.startsWith(Constants.VIEW_BUTTON_NAME.toLowerCase()) &&
						parameter.endsWith(".x")) {
					String id = parameter.substring(parameter.lastIndexOf("_") + 1, parameter.indexOf(".x"));
					session.setAttribute("postId", id);
					RequestDispatcher dispatcher = null;
					dispatcher = getServletContext()
							.getRequestDispatcher("/" + Constants.POSTDISCUSSION_SERVLET_PAGE_CONTEXT);
					if (dispatcher != null) {
						dispatcher.forward(request, response);
					}
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
					if(parameter.startsWith(Constants.INSERT_BUTTON_NAME.toLowerCase()) &&
							parameter.endsWith(".x")) {
						if(newPostTitle != null && newPostDescription != null) {
							List<String> val = new ArrayList<String>();

							val.add(newPostTitle);
							val.add(newPostDescription);

							java.sql.Date currdate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
							val.add(currdate.toString());

							Calendar rightNow = Calendar.getInstance();					
							int hour = rightNow.get(Calendar.HOUR_OF_DAY);
							int minutes = rightNow.get(Calendar.MINUTE);
							int seconds = rightNow.get(Calendar.SECOND);

							String time = hour+":"+minutes+":"+seconds;

							val.add(time);

							Integer idc = clientManager.getUserId(username);
							val.add(idc.toString());

							val.add("0");

							postManager.create(val);
						}
					}
					
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
		
			if (posts == null)
				posts = postManager.getElements();

			PostGraphicUserInterface.displayPostGraphicUserInterface(username, posts, loggedIn,
					(currentRecordsPerPage != null) ? Integer.parseInt(currentRecordsPerPage)
							: Constants.RECORDS_PER_PAGE_VALUES[0],
							(currentPage != null && currentRecordsPerPage != null
							&& currentRecordsPerPage.equals(previousRecordsPerPage)) ? Integer.parseInt(currentPage)
									: 1, printWriter);
		}
	}
}
