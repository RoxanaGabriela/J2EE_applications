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
import ro.pub.cs.paj.discussionforum.businesslogic.TopicManager;
import ro.pub.cs.paj.discussionforum.businesslogic.TopicPostManager;
import ro.pub.cs.paj.discussionforum.graphicuserinterfaces.PostGraphicUserInterface;
import ro.pub.cs.paj.discussionforum.util.Constants;
import ro.pub.cs.paj.discussionforum.util.Utilities;
import ro.pub.cs.paj.discussionforum.db.Post;

@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {

	private static final long serialVersionUID = -1864122641544054598L;
	private PostManager postManager;
	private ClientManager clientManager;
	private TopicManager topicManager;
	private TopicPostManager topicPostManager;
	
	private List<Post> posts;
	private String previousRecordsPerPage;
	private String currentRecordsPerPage;
	private String currentPage;
	private List<String> topics;
	
	private boolean loggedIn;
	private String username;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		postManager = new PostManager();
		clientManager = new ClientManager();
		topicManager = new TopicManager();
		topicPostManager = new TopicPostManager();

		previousRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
		currentRecordsPerPage = String.valueOf(Constants.RECORDS_PER_PAGE_VALUES[0]);
		currentPage = String.valueOf(1);

		posts = null;
	}

	@Override
	public void destroy() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		HttpSession session = request.getSession(true);
		response.setContentType("text/html");
		
		try (PrintWriter printWriter = new PrintWriter(response.getWriter())) {
			boolean listChanged = false;
			loggedIn = false;
			username = null;
			
			String newPostTitle = null;
			String newPostDescription = null;
			
			if (session.getAttribute("username") != null) {
				username = (String) session.getAttribute("username");
			}
			if (session.getAttribute("loggedIn") != null) {
				loggedIn = (boolean) session.getAttribute("loggedIn");
			}
			
			topics = (List<String>) session.getAttribute(Constants.TOPIC);
			if (topics == null) {
				topics = new ArrayList<>();
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
				
				if (parameter.startsWith(Constants.HOME.toLowerCase()) &&
						parameter.endsWith(".x")) {
					posts = null;
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
						posts = null;
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
						posts = null;
						break;
					}
				}
				
				if (loggedIn) {
					if (parameter.equals(Constants.INSERT_BUTTON_NAME.toLowerCase() + "_" + Constants.TOPIC + ".x")) {
						String topic = request.getParameter(Constants.TOPIC.toLowerCase());
						if (!topics.contains(topic)) {
							topics.add(topic);
						}
					}
					if (parameter.startsWith(Constants.DELETE_BUTTON_NAME.toLowerCase() + "_" + Constants.TOPIC + "_")
							&& parameter.endsWith(".x")) {
						String topic = parameter.substring(parameter.lastIndexOf("_") + 1, parameter.indexOf(".x"));
						if (topics.contains(topic)) {
							topics.remove(topic);
						}
					}
					
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

							long post_id = postManager.create(val);
							
							for (String topic : topics) {
								if (topicManager.getId(topic) == -1) {
									val = new ArrayList<String>();
									val.add(topic);
									val.add("-");
									long topic_id = topicManager.create(val);
									
									val = new ArrayList<String>();
									val.add(topic_id + "");
									val.add(post_id + "");
									topicPostManager.create(val);
								}
								
							}
							topics.clear();
						}
					}
					
					session.setAttribute(Constants.TOPIC, topics);
					
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
						//session.invalidate();
						break;
					}
				}
			}
		
			if (posts == null || listChanged)
				posts = postManager.getElements();

			PostGraphicUserInterface.displayPostGraphicUserInterface(username, posts, topics, loggedIn,
					(currentRecordsPerPage != null) ? Integer.parseInt(currentRecordsPerPage)
							: Constants.RECORDS_PER_PAGE_VALUES[0],
							(currentPage != null && currentRecordsPerPage != null
							&& currentRecordsPerPage.equals(previousRecordsPerPage)) ? Integer.parseInt(currentPage)
									: 1, printWriter);
		}
	}
}
