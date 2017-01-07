package ro.pub.cs.paj.discussionforum.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

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
import ro.pub.cs.paj.discussionforum.graphicuserinterfaces.TopicDiscussionGraphicInterface;
import ro.pub.cs.paj.discussionforum.graphicuserinterfaces.TopicGraphicInterface;
import ro.pub.cs.paj.discussionforum.util.Constants;
import ro.pub.cs.paj.discussionforum.dataaccess.DatabaseOperationsImplementation;
import ro.pub.cs.paj.discussionforum.db.Comment;
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
		
		String topicToNavigate = null;
		
		String newPostName = null;
		String newPostDetails = null;
		
		boolean buttonPressed = false;
		
		HttpSession session = request.getSession(true);
		boolean notLoggedIn = (boolean) session.getAttribute("loggedin");
		String user = (String) session.getAttribute("userLogged");
		
		session.setAttribute("userLogged", user);
		
		logedIn = !notLoggedIn;
		
		Enumeration<String> parametersTopic = request.getParameterNames();
		boolean found = false;
		while (parametersTopic.hasMoreElements()) {
			String parameter = (String) parametersTopic.nextElement();
			if (parameter.equals("NewTopicName")) {
				newPostName = request.getParameter(parameter);
				
			}
			
			if (parameter.equals("NewTopicPost")) {
				newPostDetails = request.getParameter(parameter);
			}
			
			if(parameter.equals("NewTopicButton")){
				buttonPressed = true;
			}
			
		}
		
		
		
		response.setContentType("text/html");
		try (PrintWriter printWriter = new PrintWriter(response.getWriter())) {
		
			Enumeration<String> parameters = request.getParameterNames();
			int postId = -1;
			
			
			
			
			while (parameters.hasMoreElements()) {
				
				
				PostManager tm = new PostManager();
				try {
					Posts = tm.getElements();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
				boolean duplicate = false;
				for(Post p : Posts){
					if(p.getTitle().equals(newPostName)){
						duplicate = true;
						break;
					}						
					
				}
				
				
				if(newPostName != null && newPostDetails != null && !newPostName.equals("new topic name") 
						&& buttonPressed == true && duplicate == false){
					List<String> val = new ArrayList<String>();
						
					
					//Integer id = 10 + (int)(Math.random() * 5000); 
					//String idS = id.toString();
					//val.add(idS);
													
					val.add(newPostName);
					val.add(newPostDetails);
					java.sql.Date currdate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
					val.add(currdate.toString());
					
					Calendar rightNow = Calendar.getInstance();					
					int hour = rightNow.get(Calendar.HOUR_OF_DAY);
					int minutes = rightNow.get(Calendar.MINUTE);
					int seconds = rightNow.get(Calendar.SECOND);

					String time = hour+":"+minutes+":"+seconds;
					
					val.add(time);
					
					
					ClientManager cm = new ClientManager();
					Integer idc = cm.getUserId(user);
					val.add(idc.toString());
					
					val.add("0");
										
					tm.create(val);
					
					//use the description as the first comment of the post
					PostDiscussionManager pdm = new PostDiscussionManager();
					List<String> valComment = new ArrayList<String>();
					
					int idcomm = 0;
					//valComment.add("0");
					valComment.add(newPostDetails);
					valComment.add(currdate.toString());
					valComment.add(time);					
					valComment.add(idc.toString());
					//get post id
					
					 List<String> att = new ArrayList<String>();
					 att.add("id");
					 att.add("title");
					 att.add("description");
					 att.add("post_date");
					 att.add("post_time");
					 att.add("client_id");
					 att.add("banned");
					
					String whereClause = "title='" +newPostName + "'";
					List<List <String>> items = new ArrayList<List<String>>();
					try {
						 items = DatabaseOperationsImplementation.getInstance().getTableContent("post", att, whereClause, null, null, null);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Integer idP = Integer.parseInt(items.get(0).get(0).toString());
					valComment.add(idP.toString());
					valComment.add("0");
					
					pdm.create(valComment);
					
					PostManager pm = new PostManager();
					List<Post> listp;
					Integer postid = -1;
					try {
						listp = pm.getElements();
						
						for(int i = 0; i < listp.size(); i++){
							if(listp.get(i).getTitle().equals(newPostName))
								postid = listp.get(i).getId();						
							
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					session.setAttribute("postName", newPostName);
					session.setAttribute("clientId", postid);
					
					break;
					
					
				}
				
				
				
				//get post id to show just the comments from that post
				//also get the name of the post
				int clientIdC = -1;
				
				String parameter = (String) parameters.nextElement();
				try {
					for(int i = 0 ;i < tm.getElements().size(); i++)
						if (parameter.equals(tm.getElements().get(i).getTitle())){
							topicToNavigate = tm.getElements().get(i).getTitle();
							postId = tm.getElements().get(i).getId();
							
						}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int postIdC = -1;
				PostManager pm = new PostManager();
				postIdC =  pm.getCliedId(topicToNavigate);
				
				session.setAttribute("postName", topicToNavigate);
				session.setAttribute("clientId", postIdC);
				
			}
			
			//TODO la request se trimite si daca userul este logat sau nu
			if(parameters.equals(true))
				foundLogedIn = true;
			
			//response.setContentType("text/html");
			
			RequestDispatcher dispatcher = null;
			
			dispatcher = getServletContext()
						.getRequestDispatcher("/" + Constants.CLIENT_SERVLET_TOPICDISCUSSION_PAGE_CONTEXT);
						
			
			PostManager tm = new PostManager();
			try {
				Posts = tm.getElements();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
									
			
			
			
			
			
			
			PostDiscussionManager pdm = new PostDiscussionManager();
			List<Comment> comm = new ArrayList<>();
			try {
				comm = pdm.getElements(postId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if(topicToNavigate != null)
				TopicDiscussionGraphicInterface.displayTopicDiscussionGraphicUserInterface(logedIn, comm,printWriter);
			else
				TopicGraphicInterface.displayTopicGraphicUserInterface(logedIn, Posts, printWriter);
			
		}
			
	}
}
