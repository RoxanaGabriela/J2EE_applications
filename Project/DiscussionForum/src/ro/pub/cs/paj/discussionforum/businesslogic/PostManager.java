package ro.pub.cs.paj.discussionforum.businesslogic;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ro.pub.cs.paj.discussionforum.businesslogic.EntityManager;
import ro.pub.cs.paj.discussionforum.dataaccess.DatabaseOperations;
import ro.pub.cs.paj.discussionforum.dataaccess.DatabaseOperationsImplementation;
import ro.pub.cs.paj.discussionforum.db.Post;
import ro.pub.cs.paj.discussionforum.util.Constants;

public class PostManager extends EntityManager {

	public PostManager(){
		table = "post";
	}

	public  List<Post>  getElements() {
		List<Post> res = new ArrayList<Post>();
		DatabaseOperations databaseOperations = null;

		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();

			List<String> att = new ArrayList<String>();
			att.add("post.id");
			att.add("title");
			att.add("description");
			att.add("post_date");
			att.add("post_time");
			att.add("username");						

			String whereClause = "client_id=client.id and post.banned=1";
			List<List<String> > entries = databaseOperations.getTableContent(table + ", client", att, whereClause, null, null, null);		

			for(int i = 0;i < entries.size(); i++) {
				List<String> current = entries.get(i);
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				Date parsed;
				try {
					parsed = format.parse(current.get(3));
					java.sql.Date date = new java.sql.Date(parsed.getTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Post entry = new Post();
				entry.setId(Integer.parseInt(current.get(0)));
				entry.setTitle(current.get(1));
				entry.setDescription(current.get(2));
				entry.setClientUsername(current.get(5));
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
	
	// new posts that need approval
	public  List<Post>  getAdminPosts() {
		List<Post> res = new ArrayList<Post>();
		DatabaseOperations databaseOperations = null;

		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();

			List<String> att = new ArrayList<String>();
			att.add("post.id");
			att.add("post.title");
			att.add("post.description");
			att.add("post.post_date");
			att.add("post.post_time");
			att.add("client.username");
			att.add("post.banned");

			String whereClause = "(post.client_id=client.id and post.banned=0) or " +
					 			 "(post.client_id=client.id and " + 
					 			 "post_comment.post_id=post.id and post_comment.banned=0)";
			List<List<String> > entries = databaseOperations.getTableContent(table + ", post_comment, client", att, whereClause, null, null, null);		

			for(int i = 0;i < entries.size(); i++) {
				List<String> current = entries.get(i);
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				Date parsed;
				try {
					parsed = format.parse(current.get(3));
					java.sql.Date date = new java.sql.Date(parsed.getTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Post entry = new Post();
				entry.setId(Integer.parseInt(current.get(0)));
				entry.setTitle(current.get(1));
				entry.setDescription(current.get(2));
				entry.setClientUsername(current.get(5));
				entry.setBanned(Integer.parseInt(current.get(6)));
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
	
	
	// posts that have new comments that need approval
	
	public Post getPostDetails(String postId) {
		Post post = new Post();
		DatabaseOperations databaseOperations = null;

		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();

			List<String> att = new ArrayList<String>();
			att.add("title");
			att.add("description");
			att.add("post_date");
			att.add("post_time");
			att.add("username");						

			String whereClause = "post.id=\'" + postId + "\' and client_id=client.id";
			List<List<String> > entries = databaseOperations.getTableContent(table + ", client", att, whereClause, null, null, null);		

			if (entries != null && !entries.isEmpty()) {
				post.setTitle(entries.get(0).get(0));
				post.setDescription(entries.get(0).get(1));
				post.setClientUsername(entries.get(0).get(4));
			}
		} catch (SQLException sqlException) {
			System.out.println("An exception has occurred while handling database records: " + sqlException.getMessage());
			if (Constants.DEBUG) {
				sqlException.printStackTrace();
			}
		} finally {
			databaseOperations.releaseResources();
		}
		
		return post;
	}
}
