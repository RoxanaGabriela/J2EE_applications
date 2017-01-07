package ro.pub.cs.paj.discussionforum.businesslogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ro.pub.cs.paj.discussionforum.dataaccess.DatabaseOperationsImplementation;
import ro.pub.cs.paj.discussionforum.db.Comment;
import ro.pub.cs.paj.discussionforum.db.Post;
import ro.pub.cs.paj.discussionforum.db.Topic;


public class PostDiscussionManager extends EntityManager{
	
	
	
	
	public PostDiscussionManager(){
		table = "post_comment";
		
		//TODO get the name of the topic
	}
	
	public  List<Comment>  getElements(int postId) throws SQLException{
			
		List<Comment> res = new ArrayList();
				
		
		List<String> att = new ArrayList<String>();
		att.add("id");
		//att.add("title");
		att.add("description");
		att.add("post_date");
		att.add("post_time");
		att.add("client_id");
		att.add("banned");		
		
		
		//TODO de vazut aici
		String where = null;
		if(postId != -1)
			where = "post_id=" + postId;
		
		List<List<String> > entries = DatabaseOperationsImplementation.getInstance().getTableContent(table, att, where, null, null, null);
				
		for(int i = 0;i < entries.size(); i++){
			
			List<String> current = entries.get(i);
			//Post entry = new Post(Integer.parseInt(current.get(0)),current.get(1),current.get(2));
			Comment entry = new Comment();
			
			entry.setId(Integer.parseInt(current.get(0)));
			entry.setDescription(current.get(1));
			
			
			res.add(entry);
			
			
		}
					
		return res;
	}
	
}
