package ro.pub.cs.paj.discussionforum.businesslogic;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import ro.pub.cs.paj.discussionforum.helper.Record;
import ro.pub.cs.paj.discussionforum.util.HibernateUtil;
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
	
	public int getCliedId( String  titleP){
	
		int idC = -1;
		
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
						
			try {	        		
			
				Query query = session.createQuery("from " + table + " where title = :title  ");
				
		        query.setParameter("title", titleP);
		        //query.setParameter("password", password);
		        
		        
				//String queryString = "Select * from client where username="+"'"+ username + "'" + " and password='123'";
				//Query query = session.createQuery(queryString);
		        List<String> att = new ArrayList<String>();
		        att.add("id");
		        att.add("title");
		        att.add("description");
		        att.add("post_date");
		        att.add("post_time");
		        att.add("cliend_id");
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
				session.getTransaction().commit();
			}
		
		
		return idC;
		
		
		
	}
	
	
}
