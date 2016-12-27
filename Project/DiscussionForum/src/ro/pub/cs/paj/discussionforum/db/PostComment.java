package ro.pub.cs.paj.discussionforum.db;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class PostComment implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int id;
	private String description;
	private Date postDate;
	private Time postTime;
	private int clientId;
	private int postId;
	public boolean banned;
	
	public PostComment() {}

	public PostComment(int id, String description, Date postDate,
			Time postTime, int clientId, int postId, boolean banned) {
		super();
		this.id = id;
		this.description = description;
		this.postDate = postDate;
		this.postTime = postTime;
		this.clientId = clientId;
		this.postId = postId;
		this.banned = banned;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public boolean isBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
	}
	
	
	
	
}
