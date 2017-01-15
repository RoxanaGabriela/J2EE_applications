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

			String whereClause = "client_id=client.id and post.banned=0";
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

	public int getCliedId( String  titleP) {

		int idC = -1;

		try {	        		
			List<String> att = new ArrayList<String>();
			att.add("id");
			att.add("title");
			att.add("description");
			att.add("post_date");
			att.add("post_time");
			att.add("client_id");
			att.add("banned");

			String whereClause = "title='" +titleP + "'";

			List<List < String > > results = new ArrayList<List<String>>();

			results = DatabaseOperationsImplementation.getInstance().getTableContent(table,att,whereClause,null, null, null);


			int idt = -1;
			if (results != null){
				List<String> res = results.get(0);
				idt = Integer.parseInt(res.get(0));
			}

			if (idt != -1){	        		        	
				idC = idt;
			}		        			     

		} catch (Exception exception) {
			// Ignore: OK for this implementation
		} finally {
		}


		return idC;



	}
}
