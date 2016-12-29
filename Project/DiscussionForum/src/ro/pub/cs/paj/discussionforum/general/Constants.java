package ro.pub.cs.paj.discussionforum.general;

import java.sql.Date;
import java.sql.Time;

public class Constants {
	public final static String APPLICATION_NAME = "DiscussionForum";

	public final static String DATABASE_CONNECTION = "jdbc:mysql://localhost:8080/forumDatabase";
	public final static String DATABASE_USERNAME = "root";
	public final static String DATABASE_PASSWORD = "root";
	public final static String DATABASE_NAME = "forumDatabase";

	public final static boolean DEBUG = false;

	public final static long SEED = 20152015L;

	public final static String LOGIN_SERVLET_PAGE_CONTEXT = "LoginServlet";
	public final static String ADMINISTRATOR_SERVLET_PAGE_CONTEXT = "AdministratorServlet";
	public final static String CLIENT_SERVLET_PAGE_CONTEXT = "ClientServlet";
	public final static String USERFARACONT_SERVLET_PAGE_CONTEXT = "UserFaraContServlet";
	
	public final static String LOGIN_SERVLET_PAGE_NAME = "Authentication";
	public final static String ADMINISTRATOR_SERVLET_PAGE_NAME = "Administration Page";
	public final static String CLIENT_SERVLET_PAGE_NAME = "Client page";
	public final static String USERFARACONT_SERVLET_PAGE_NAME = "UserFaraCont page";
	
	public final static String LOGIN_FORM = "login_form";
	public final static String ADMINISTRATOR_FORM = "administrator_form";
	public final static String CLIENT_FORM = "client_form";
	public final static String USERFARACONT_FORM = "USERFARACONT_form";
	
	public final static String SIGNIN = "Sign in";
	public final static String SIGNOUT = "Sign out";
	public final static String WELCOME_MESSAGE = "Welcome, ";
	public final static String DISPLAY = "display";
	public final static String ERROR_USERNAME_PASSWORD = "Either username or password are incorrect!";

	public final static String USER_TYPE = "type";
	public final static String ADMINISTRATOR_TYPE = "administrator";
	public final static String CLIENT_TYPE = "client";
	public final static String USERFARACONT_TYPE = "client_faracont";
	public final static String USERNAME = "username";
	public final static String PASSWORD = "password";

	public final static int USER_NONE = 0;
	public final static int USER_ADMINISTRATOR = 0;		
	public final static int USER_FARACONT = 1;
	public final static int USER_CLIENT = 2;
	
	
	
	public final static int OPERATION_NONE = 0;
	public final static int OPERATION_TOPIC_POST = 0;
	public final static int OPERATION_COMMENT_POST = 0;
	public final static int OPERATION_DELETE = 0;
	public final static int OPERATION_LOGOUT = 0;

	public final static String CREATETOPIC_BUTTON_NAME = "Insert";
	public final static String POSTREPLY_BUTTON_NAME = "Update";	

	//public final static String OPERATION_TABLE_HEADER = "operation";

	public final static String INVALID_VALUE = "invalid";

	//public final static String CURRENT_TABLE = "currentTable";

	public final static String RECORDS_PER_PAGE = "Records per Page ";
	public final static int RECORDS_PER_PAGE_VALUES[] = { 1, 5, 10, 15, 20, 25, 40, 50, 60, 75, 80, 100 };
	public final static String PAGE = "Page ";

	public final static String FORMAT = "Format: ";
	public final static String CURRENT_FORMAT = "currentFormat";
	public final static String FORMAT_ATTRIBUTE = "value";
	public final static String FORMATS_FILTER = "formatsFilter";
	//public final static String LANGUAGE = "Language: ";
	//public final static String CURRENT_LANGUAGE = "currentLanguage";
	//public final static String LANGUAGE_ATTRIBUTE = "name";
	//public final static String LANGUAGES_FILTER = "languagesFilter";
	//public final static String CATEGORY = "Category: ";
	//public final static String CURRENT_CATEGORY = "currentCategory";
	//public final static String CATEGORY_ATTRIBUTE = "name";
	//public final static String CATEGORIES_FILTER = "categoriesFilter";

	//public final static String FORMAT_TABLE = "format";
	//public final static String LANGUAGE_TABLE = "language";
	//public final static String CATEGORY_TABLE = "category";

	public final static int ID_INDEX = 0;
	public final static String ID_ATTRIBUTE = "id";
	
	// topic
	public final static int TITLE_INDEX = ID_INDEX + 1; // 1
	
	public final static String TITLE_ATTRIBUTE = "title";
	public final static String SUBTITLE_ATTRIBUTE = "description";
	
	
	// post
	public final static int POST_INDEX = ID_INDEX + 1; // 1
	
	public final static String TITLE_POST = "title";
	public final static String POST_DESCRIPTION = "description";
	public final static String POST_DATE= "date";
	public final static String POST_TIME = "time";
	public final static String CLIENT_ID = "clietId";
	public final static String BANNED = "banned";

	//comment
	public final static int COMMENT_INDEX = ID_INDEX + 1; // 1
	
	public final static String TITLE_COMMENT = "title";
	public final static String COMMENT_DESCRIPTION = "description";
	public final static String COMMENT_DATE= "date";
	public final static String COMMENT_TIME = "time";



	public final static String[] SYSTEM_FUNCTION = { "CURDATE()" };
}
