package ro.pub.cs.paj.discussionforum.util;

public class Constants
{
	public final static String APPLICATION_NAME = "Discussion Forum";
	
	public final static String DATABASE_CONNECTION = "jdbc:mysql://localhost:3306/forumdatabase?verifyServerCertificate=false&useSSL=true";
	public final static String DATABASE_USERNAME = "root";
	public final static String DATABASE_PASSWORD = "root123";
	public final static String DATABASE_NAME = "forumdatabase";
	
	
	public final static int USER_NONE = -1;
	public final static int USER_NOACCOUNT = 0;
	public final static int USER_ADMINISTRATOR = 1;
	public final static int USER_CLIENT = 2;
	
	public final static boolean DEBUG = false;

	public final static long SEED = 20152015L;
	
	public final static String LOGIN_SERVLET_PAGE_CONTEXT = "LoginServlet";
	public final static String REGISTER_SERVLET_PAGE_CONTEXT = "RegisterServlet";
	public final static String ADMINISTRATOR_SERVLET_PAGE_CONTEXT = "AdministratorServlet";
	public final static String POST_SERVLET_PAGE_CONTEXT = "PostServlet";
	public final static String POSTDISCUSSION_SERVLET_PAGE_CONTEXT = "PostDiscussionServlet";
	public final static String POST_LOGGEDIN_SERVLET_PAGE_CONTEXT = "PostLoggedInServlet";
	public final static String POSTDISCUSSION_LOGGEDIN_SERVLET_PAGE_CONTEXT = "PostDiscussionLoggedInServlet";
	
	public final static String LOGIN_SERVLET_PAGE_NAME = "Authentication";
	public final static String ADMINISTRATOR_SERVLET_PAGE_NAME = "Administration Page";
	public final static String CLIENT_SERVLET_PAGE_NAME = "Forum Page";
	
	public final static String POST_FORM = "PostForm";
	public final static String POSTDISCUSSION_FORM = "PostDiscussionForm";
	
	public final static String HOME = "Home";
	
	public final static String SIGNIN = "Sign in";
	public final static String SIGNUP = "Sign up";
	public final static String SIGNOUT = "Sign out";
	public final static String INSERT_BUTTON_NAME = "Insert";
	public final static String VIEW_BUTTON_NAME = "View";
	public final static String WELCOME_MESSAGE = "Welcome, ";
	public final static String DISPLAY = "display";
	public final static String ERROR_USERNAME_PASSWORD = "Either username or password are incorrect!";
	
	public final static String USERNAME = "username";
	public final static String PASSWORD = "password";
	
	public final static String RECORDS_PER_PAGE = "Records per Page ";
	public final static int RECORDS_PER_PAGE_VALUES[] = { 5, 10, 15, 20, 25, 40, 50, 60, 75, 80, 100 };
	public final static String PAGE = "Page ";
}
