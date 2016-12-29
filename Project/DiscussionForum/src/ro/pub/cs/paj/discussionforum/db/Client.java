package ro.pub.cs.paj.discussionforum.db;

import java.io.Serializable;

public class Client implements Serializable {
		
	public Client(){}	
	
	public Client(int id, String username, String password, userType type,
			boolean banned) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.type = type;
		this.banned = banned;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public userType getType() {
		return type;
	}
	public void setType(userType type) {
		this.type = type;
	}
	public boolean isBanned() {
		return banned;
	}
	public void setBanned(boolean banned) {
		this.banned = banned;
	}
	private static final long serialVersionUID = 1L;
	
	public enum userType{
		client,
		admin
	}
	
	private int id;
	private String username;
	private String password;
	private userType type;
	private boolean banned;
	
	
}
