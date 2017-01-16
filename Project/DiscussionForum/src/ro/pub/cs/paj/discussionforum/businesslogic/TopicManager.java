package ro.pub.cs.paj.discussionforum.businesslogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ro.pub.cs.paj.discussionforum.dataaccess.DatabaseOperations;
import ro.pub.cs.paj.discussionforum.dataaccess.DatabaseOperationsImplementation;
import ro.pub.cs.paj.discussionforum.util.Constants;

public class TopicManager extends EntityManager {
	public TopicManager() {
		table = "topic";
	}
	
	public int getId(String title) {
		int id = -1;
		DatabaseOperations databaseOperations = null;

		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();

			List<String> att = new ArrayList<String>();
			att.add("id");

			String whereClause = "title=\'" + title + "\'";
			List<List<String> > entries = databaseOperations.getTableContent(table, att, whereClause, null, null, null);		

			if (entries != null && entries.get(0) != null && entries.get(0).get(0) != null) {
				id = Integer.parseInt(entries.get(0).get(0));
			}
		} catch (SQLException sqlException) {
			System.out.println("An exception has occurred while handling database records: " + sqlException.getMessage());
			if (Constants.DEBUG) {
				sqlException.printStackTrace();
			}
		} finally {
			databaseOperations.releaseResources();
		}
		
		return id;
	}
}
