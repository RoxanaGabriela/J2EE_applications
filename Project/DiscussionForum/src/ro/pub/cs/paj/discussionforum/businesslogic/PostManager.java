package ro.pub.cs.paj.discussionforum.businesslogic;

import java.sql.SQLException;
import java.util.ArrayList;
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
			att.add("post.title");
			att.add("post.description");
			att.add("post_date");
			att.add("post_time");
			att.add("username");

			String whereClause = "client_id=client.id and post.banned=1";
			List<List<String> > entries = databaseOperations.getTableContent(table + ", client", att, whereClause, null, null, null);		

			for(int i = 0;i < entries.size(); i++) {
				List<String> current = entries.get(i);
				Post entry = new Post();
				entry.setId(Integer.parseInt(current.get(0)));
				entry.setTitle(current.get(1));
				entry.setDescription(current.get(2));
				entry.setDate(current.get(3));
				entry.setTime(current.get(4));
				entry.setClientUsername(current.get(5));

				att.clear();
				att.add("topic.title");

				whereClause = "post.id=\'" + entry.getId() + 
						"\' and post.id=post_id and topic.id=topic_id";
				List<List<String>> topics = databaseOperations.getTableContent(table + ", topic, topic_post", att, whereClause, null, null, null);
				String stopics = "";
				if (topics != null && !topics.isEmpty()) {
					for (List<String> topic : topics) {
						stopics += topic.get(0) + ", ";
					}
					stopics = stopics.substring(0, stopics.length() - 2);
				}
				entry.setTopics(stopics);
				
				att.clear();
				att.add("COUNT(post_comment.id)");

				whereClause = "post.id=\'" + entry.getId() + "\' and post.id=post_id";
				List<List<String>> comments = databaseOperations.getTableContent(table + ", post_comment", att, whereClause, null, null, null);
				entry.setCommentsNo(0);
				if (comments != null && !comments.isEmpty()) {
					entry.setCommentsNo(Integer.parseInt(comments.get(0).get(0)));
				}
				entry.setTopics(stopics);
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
	// posts that have new comments that need approval
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

			String whereClause = "post.client_id=client.id and post.banned=0";
			List<List<String> > entries = databaseOperations.getTableContent(table + ", client", att, whereClause, null, null, null);		

			for(int i = 0;i < entries.size(); i++) {
				List<String> current = entries.get(i);

				Post entry = new Post();
				entry.setId(Integer.parseInt(current.get(0)));
				entry.setTitle(current.get(1));
				entry.setDescription(current.get(2));
				entry.setDate(current.get(3));
				entry.setTime(current.get(4));
				entry.setClientUsername(current.get(5));
				entry.setBanned(Integer.parseInt(current.get(6)));
				res.add(entry);			
			}

			whereClause = "post.client_id=client.id and " + 
					"post_comment.post_id=post.id and post_comment.banned=0";
			entries = databaseOperations.getTableContent(table + ", post_comment, client", att, whereClause, null, null, null);		

			for(int i = 0;i < entries.size(); i++) {
				List<String> current = entries.get(i);

				Post entry = new Post();
				entry.setId(Integer.parseInt(current.get(0)));
				entry.setTitle(current.get(1));
				entry.setDescription(current.get(2));
				entry.setDate(current.get(3));
				entry.setTime(current.get(4));
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

	public Post getPostDetails(String postId) {
		Post post = new Post();
		DatabaseOperations databaseOperations = null;

		try {
			databaseOperations = DatabaseOperationsImplementation.getInstance();

			List<String> att = new ArrayList<String>();
			att.add("post.id");
			att.add("post.title");
			att.add("post.description");
			att.add("post_date");
			att.add("post_time");
			att.add("username");

			String whereClause = "post.id=\'" + postId + "\' and client_id=client.id and post.banned=1";
			List<List<String> > entries = databaseOperations.getTableContent(table + ", client", att, whereClause, null, null, null);		

			if (entries != null && !entries.isEmpty()) {
				post.setId(Integer.parseInt(entries.get(0).get(0)));
				post.setTitle(entries.get(0).get(1));
				post.setDescription(entries.get(0).get(2));
				post.setDate(entries.get(0).get(3));
				post.setTime(entries.get(0).get(4));
				post.setClientUsername(entries.get(0).get(5));

				att.clear();
				att.add("topic.title");

				whereClause = "post.id=\'" + post.getId() + 
						"\' and post.id=post_id and topic.id=topic_id";
				List<List<String>> topics = databaseOperations.getTableContent(table + ", topic, topic_post", att, whereClause, null, null, null);
				String stopics = "";
				if (topics != null && !topics.isEmpty()) {
					for (List<String> topic : topics) {
						stopics += topic.get(0) + ", ";
					}
					stopics = stopics.substring(0, stopics.length() - 2);
				}
				post.setTopics(stopics);
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
