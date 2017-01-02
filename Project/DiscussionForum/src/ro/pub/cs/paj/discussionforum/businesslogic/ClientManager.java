package ro.pub.cs.paj.discussionforum.businesslogic;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transaction;

import org.eclipse.jdt.internal.compiler.flow.FinallyFlowContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import ro.pub.cs.paj.discussionforum.dataaccess.DatabaseOperationsImplementation;
import ro.pub.cs.paj.discussionforum.db.Client;
import ro.pub.cs.paj.discussionforum.util.HibernateUtil;
import ro.pub.cs.paj.discussionforum.util.Constants;

public class ClientManager {
	
	private String table;
	
	public ClientManager() {
		table = "Client";
	}
	
	public int getType(String username, String password) {
		int userType = Constants.USER_NONE;
		
		Session session=HibernateUtil.openSession();
		session.beginTransaction();
		try {	        		
			
			Query query = session.createQuery("from " + table + " where username = :username and password = :password ");
			
	        query.setParameter("username", username);
	        query.setParameter("password", password);
	        
	        
			//String queryString = "Select * from client where username="+"'"+ username + "'" + " and password='123'";
			//Query query = session.createQuery(queryString);
	        List<String> att = new ArrayList<String>();
	        att.add("id");
	        att.add("username");
	        att.add("password");
	        att.add("type");
	        att.add("banned");
	        
	        String whereClause = "username='" +username + "' and password='" + password + "'";
	        
	        List<List < String > > results = new ArrayList<List<String>>();
	        
	        results = DatabaseOperationsImplementation.getInstance().getTableContent(table,att,whereClause,null, null, null);
	        
	        
	        String type = null;
	        if (results != null){
	        	List<String> res = results.get(0);
	        	type =  res.get(3);
	        }
	        		        													           	          
	        if (type != null || type != ""){	        		        	
	        	if (type.equals("client"))
	        		userType = Constants.USER_CLIENT;
	        	else
	        		userType = Constants.USER_ADMINISTRATOR;
	        }
	        	
	      
	        
		} catch (Exception exception) {
				// Ignore: OK for this implementation
		} finally {
			session.getTransaction().commit();
		}
		
		return userType;
	}

	public String getDisplay(String username, String password) {
		return "";
	}
}
