package ro.pub.cs.paj.discussionforum.businesslogic;

import java.sql.SQLException;

import javax.persistence.Query;

import org.eclipse.jdt.internal.compiler.flow.FinallyFlowContext;
import org.hibernate.Session;

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
	        
			String type = (String) query.getSingleResult();
	        
	        if (type != null || type != "")
	        	if (type == "client")
	        		userType = Constants.USER_CLIENT;
	        	else
	        		userType = Constants.USER_ADMINISTRATOR;
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
