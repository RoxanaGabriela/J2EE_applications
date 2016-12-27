package ro.pub.cs.paj.discussionforum.db;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;


public class Post implements Serializable{

	/**
	 * 
	 */
	Post(){}
	
	public Post(int id, String title, String description, Date postDate,
			Time postTime, int clientId, boolean banned) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.postDate = postDate;
		this.postTime = postTime;
		this.clientId = clientId;
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
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public Time getPostTime() {
		return postTime;
	}
	public void setPostTime(Time postTime) {
		this.postTime = postTime;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public boolean isBanned() {
		return banned;
	}
	public void setBanned(boolean banned) {
		this.banned = banned;
	}
	
	private int id;
	private String title;
	private String description;
	private Date postDate;
	private Time postTime;
	private int clientId;
	private boolean banned;
	
}
