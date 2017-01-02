package ro.pub.cs.paj.discussionforum.businesslogic;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ro.pub.cs.paj.discussionforum.helper.Record;
import ro.pub.cs.paj.discussionforum.businesslogic.EntityManager;
import ro.pub.cs.paj.discussionforum.dataaccess.DatabaseOperationsImplementation;
import ro.pub.cs.paj.discussionforum.db.Post;
import ro.pub.cs.paj.discussionforum.db.Topic;

public class PostManager extends EntityManager{

	public PostManager(){
		table = "post";
	}
	
	public  List<Post>  getElements() throws SQLException{
		
		List<Post> res = new ArrayList();
		
		List<String> att = new ArrayList<String>();
		att.add("id");
		att.add("title");
		att.add("description");
		att.add("post_date");
		att.add("post_time");
		att.add("client_id");
		att.add("banned");							
									
		List<List<String> > entries = DatabaseOperationsImplementation.getInstance().getTableContent(table, att, null, null, null, null);
				
		 /*
		 List<String> attUser = new ArrayList<String>();
		 attUser.add("id");
		 attUser.add("username");
		 attUser.add("password");
		 attUser.add("type");
		 attUser.add("banned");
	        
	     String whereClause = "username='" +username + "' and password='" + password + "'";
	        
	     List<List < String > > results = new ArrayList<List<String>>();
	        
	     results = DatabaseOperationsImplementation.getInstance().getTableContent(table, att, whereClause,null, null, null);
		 */
		
				
		for(int i = 0;i < entries.size(); i++){
			
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
		   			
			//Post entry = new Post(Integer.parseInt(current.get(0)), current.get(1),current.get(2), date,  ,Integer.parseInt(current.get(4));
			Post entry = new Post();
			entry.setId(Integer.parseInt(current.get(0)));
		    entry.setTitle(current.get(1));
					    
		    
			res.add(entry);			
			
		}
					
		return res;
	}
}
