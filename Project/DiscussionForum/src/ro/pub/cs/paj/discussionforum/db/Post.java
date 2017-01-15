package ro.pub.cs.paj.discussionforum.db;

import java.io.Serializable;

public class Post implements Serializable {

	public Post(){}
	
	public Post(int id, String title, String description, String date, String time, String clientUsername, int banned) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.date = date;
		this.time = time;
		this.clientUsername = clientUsername;
		this.banned = banned;
	}
	
	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getClientUsername() {
		return clientUsername;
	}
	public void setClientUsername(String clientUsername) {
		this.clientUsername = clientUsername;
	}
	public int isBanned() {
		return banned;
	}
	public void setBanned(int banned) {
		this.banned = banned;
	}
	
	public String getTopics() {
		return topics;
	}
	
	public void setTopics(String topics) {
		this.topics = topics;
	}
	
	private int id;
	private String title;
	private String description;	 
	private String date;
	private String time;
	private String clientUsername;
	private int banned;
	private String topics;
}
