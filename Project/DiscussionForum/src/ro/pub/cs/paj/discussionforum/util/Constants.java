package ro.pub.cs.paj.discussionforum.util;

public class Constants
{
	public final static String APPLICATION_NAME = "Discussion Forum";
	
	public final static String DATABASE_CONNECTION = "jdbc:mysql://localhost:3306/forumdatabase";
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
	public final static String ADMINISTRATOR_SERVLET_PAGE_CONTEXT = "AdministratorServlet";
	public final static String CLIENT_SERVLET_TOPICHOME_PAGE_CONTEXT = "PostServlet";
	public final static String CLIENT_SERVLET_TOPICDISCUSSION_PAGE_CONTEXT = "PostDiscussionServlet";
	
	public final static String LOGIN_SERVLET_PAGE_NAME = "Authentication";
	public final static String ADMINISTRATOR_SERVLET_PAGE_NAME = "Administration Page";
	public final static String CLIENT_SERVLET_PAGE_NAME = "Forum Page";
	
	public final static String SIGNIN = "Sign in";
	public final static String SIGNOUT = "Sign out";
	public final static String WELCOME_MESSAGE = "Welcome, ";
	public final static String DISPLAY = "display";
	public final static String ERROR_USERNAME_PASSWORD = "Either username or password are incorrect!";
	
	public final static String USERNAME = "username";
	public final static String PASSWORD = "password";
}
