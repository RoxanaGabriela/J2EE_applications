package ro.pub.cs.paj.discussionforum.businesslogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ro.pub.cs.paj.discussionforum.dataaccess.DatabaseOperations;
import ro.pub.cs.paj.discussionforum.dataaccess.DatabaseOperationsImplementation;
import ro.pub.cs.paj.discussionforum.db.Comment;
import ro.pub.cs.paj.discussionforum.util.Constants;

public class PostDiscussionManager extends EntityManager{
	public PostDiscussionManager(){
		table = "post_comment";
	}

	public  List<Comment>  getElements(int postId) {
		List<Comment> res = new ArrayList<Comment>();
		
		DatabaseOperations databaseOperations = null;

		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();

			List<String> att = new ArrayList<String>();
			att.add("post_comment.id");
			att.add("description");
			att.add("post_date");
			att.add("post_time");
			att.add("username");	

			String where = "post_id=\'" + postId + "\' and client_id=client.id and post_comment.banned=0";
			List<List<String> > entries = databaseOperations.getTableContent(table + ", client", att, where, null, null, null);

			for(int i = 0;i < entries.size(); i++) {
				List<String> current = entries.get(i);
				Comment entry = new Comment();

				entry.setId(Integer.parseInt(current.get(0)));
				entry.setDescription(current.get(1));
				entry.setClientUsername(current.get(4));

				res.add(entry);
			}
		} catch (SQLException sqlException) {
			System.out.println("An exception has occurred while handling database records: " + sqlException.getMessage());
			if (Constants.DEBUG) {
				sqlException.printStackTrace();
			}
		} finally {
			databaseOperations.releaseResources();
		}
		
		return res;
	}
}
