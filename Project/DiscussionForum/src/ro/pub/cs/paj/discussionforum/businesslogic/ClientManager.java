package ro.pub.cs.paj.discussionforum.businesslogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ro.pub.cs.paj.discussionforum.dataaccess.DatabaseOperations;
import ro.pub.cs.paj.discussionforum.dataaccess.DatabaseOperationsImplementation;
import ro.pub.cs.paj.discussionforum.util.Constants;

public class ClientManager extends EntityManager {
	private String table;
	
	public ClientManager() {
		table = "client";
	}
	
	public int getType(String username, String password) {
		int userType = Constants.USER_NONE;
		DatabaseOperations databaseOperations = null;
		
		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			
	        List<String> att = new ArrayList<String>();
	        att.add("id");
	        att.add("username");
	        att.add("password");
	        att.add("type");
	        
	        String whereClause = "username='" +username + "' and password='" + password + "'";
	        
	        List<List < String > > results = new ArrayList<List<String>>();
	        
	        results = databaseOperations.getTableContent(table,att,whereClause,null, null, null);
	        if (results != null && !results.isEmpty() && results.get(0) != null && results.get(0).get(0) != null) {
		        String type = results.get(0).get(3);
		        if (type != null || type != "") {	        		        	
		        	if (type.equals("client"))
		        		userType = Constants.USER_CLIENT;
		        	else
		        		userType = Constants.USER_ADMINISTRATOR;
		        }
	        }	        	
		} catch (SQLException sqlException) {
			System.out.println("An exception has occurred while handling database records: " + sqlException.getMessage());
			if (Constants.DEBUG) {
				sqlException.printStackTrace();
			}
		} finally {
			databaseOperations.releaseResources();
		}
		
		return userType;
	}

	public int getUserId(String username) {
		int id = -1;

		DatabaseOperations databaseOperations = null;

		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();
			List<String> att = new ArrayList<String>();
			att.add("id");
			att.add("username");
			att.add("password");
			att.add("type");

			String whereClause = "username='" +username + "'";

			List<List < String > > results = new ArrayList<List<String>>();

			results = databaseOperations.getTableContent(table,att,whereClause,null, null, null);


			int idt = -1;
			if (results != null){
				List<String> res = results.get(0);
				idt = Integer.parseInt(res.get(0));
			}

			if (idt != -1) {	        		        	
				id = idt;
			}		        			     

		} catch (SQLException sqlException) {
			System.out
			.println("An exception has occurred while handling database records: " + sqlException.getMessage());
			if (Constants.DEBUG) {
				sqlException.printStackTrace();
			}
		} finally {
			databaseOperations.releaseResources();
		}

		return id;
	}
}
