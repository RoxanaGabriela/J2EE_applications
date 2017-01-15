package ro.pub.cs.paj.discussionforum.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Post implements Serializable {

	public Post(){}
	
	public Post(int id, String title, String description, Date dateTime, String clientUsername, int banned) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.post_Time = dateTime;
		this.clientUsername = clientUsername;
		this.banned = banned;
		this.topics = new ArrayList<Topic>();
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
	public Date getDateTime() {
		return post_Time;
	}
	public void setDateTime(Date dateTime) {
		this.post_Time = dateTime;
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
	
	public Collection<Topic> getTopics() {
		return topics;
	}
	
	public void setTopics(Collection<Topic> topics) {
		this.topics = topics;
	}
	
	private int id;
	private String title;
	private String description;	 
	private Date post_Time;
	private String clientUsername;
	private int banned;
	private Collection<Topic> topics;
	private Collection<Comment> comments = new ArrayList<Comment>();
}
