package ro.pub.cs.paj.discussionforum.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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

import jdk.nashorn.internal.runtime.ListAdapter;
import ro.pub.cs.paj.discussionforum.businesslogic.ClientManager;
import ro.pub.cs.paj.discussionforum.businesslogic.PostDiscussionManager;
import ro.pub.cs.paj.discussionforum.businesslogic.PostManager;
import ro.pub.cs.paj.discussionforum.dataaccess.DatabaseOperationsImplementation;
import ro.pub.cs.paj.discussionforum.db.Client;
import ro.pub.cs.paj.discussionforum.db.Comment;
import ro.pub.cs.paj.discussionforum.db.Post;
import ro.pub.cs.paj.discussionforum.graphicuserinterfaces.TopicDiscussionGraphicInterface;
import ro.pub.cs.paj.discussionforum.graphicuserinterfaces.TopicGraphicInterface;
import ro.pub.cs.paj.discussionforum.util.Constants;

@WebServlet("/PostDiscussionServlet")
public class PostDiscussionServlet extends HttpServlet{
	
	
	private List<Comment> Comments;
	private boolean logedIn;
	
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
		
		String currentPost = null;
		Integer clientID = -1;
		
		String newComment = null;
		
		HttpSession session = request.getSession(true);
		boolean notLoggedIn = (boolean) session.getAttribute("loggedin");
		
		
		//boolean notLoggedIn = (boolean) session.getAttribute("loggedin");
		String user = (String) session.getAttribute("userLogged");
		
		currentPost = (String)session.getAttribute("postName");
		
		/*
		PostManager pm = new PostManager();
		List<Post> listp;
		Integer postid = -1;
		try {
			listp = pm.getElements();
			
			for(int i = 0; i < listp.size(); i++){
				if(listp.get(i).getTitle().equals(currentPost))
					postid = listp.get(i).getId();						
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		clientID = postid;
		*/
		
		
		int postID = -1;
		
		Enumeration<String> parametersTopic = request.getParameterNames();
		while (parametersTopic.hasMoreElements()) {
			String parameter = (String) parametersTopic.nextElement();
			if (parameter.equals("NewTopicComment")) {
				newComment = request.getParameter(parameter);
			}
		}
		
		try (PrintWriter printWriter = new PrintWriter(response.getWriter())) {
		
			Enumeration<String> parameters = request.getParameterNames();
			while (parameters.hasMoreElements()) {
																							
				if(newComment != null && !newComment.equals("new topic comment")){
					List<String> val = new ArrayList<String>();
					//val.add("id");
					
					val.add(newComment);
					
					java.sql.Date currdate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
					val.add(currdate.toString());
					
					Calendar rightNow = Calendar.getInstance();					
					int hour = rightNow.get(Calendar.HOUR_OF_DAY);
					int minutes = rightNow.get(Calendar.MINUTE);
					int seconds = rightNow.get(Calendar.SECOND);

					String time = hour+":"+minutes+":"+seconds;
					
					val.add(time);
															
					PostManager pm = new PostManager();
					List<Post> listp;
					Integer postid = -1;
					Client cl = new Client();
					
					try {
						listp = pm.getElements();
						
						for(int i = 0; i < listp.size(); i++){
							if(listp.get(i).getTitle().equals(currentPost)){
								postid = listp.get(i).getId();
								//cl = listp.get(i).getClient();
							}
														
							
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					postID = postid;
					
					
					
					
					List<String> att = new ArrayList<String>();
				    att.add("id");
				   
				    att.add("username");
				    att.add("password");
				    att.add("time");
				    att.add("cliend_id");
				    att.add("banned");
					
				    String where = "username= '" +user + "'"; 
				    /*
					try {
						List<List<String> > users = DatabaseOperationsImplementation.getInstance().getTableContent("client", att, where, null, null, null);
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					
					ClientManager c = new ClientManager();
					clientID =  c.getUserId(user);
					
					//TODO get client ID
					//clientID = pm.getCliedId(currentPost);	
					
					
					
					
					
					val.add(clientID.toString());
					
					
					val.add(postid.toString());
					
					val.add("0");
					
					PostDiscussionManager pdm = new PostDiscussionManager();
					pdm.setTable("post_comment");
					pdm.create(val);
					break;
					
				}
				
				/*
				PostDiscussionManager tm = new PostDiscussionManager();
				try {
					Comments = tm.getElements(-1);
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
				String parameter = (String) parameters.nextElement();
				try {
					for(int i = 0 ;i < tm.getElements(-1).size(); i++)
						if (parameter.equals(tm.getElements(-1).get(i).getDescription())){
							topicToNavigate = tm.getElements(-1).get(i).getDescription();
						}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				
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
				Comments = tm.getElements(postID);
			} catch (SQLException e) {
				
				e.printStackTrace(); 
			}
		
			TopicDiscussionGraphicInterface.displayTopicDiscussionGraphicUserInterface(!notLoggedIn, Comments, printWriter);	
		}
			
	}
	
	
}
