package ro.pub.cs.paj.discussionforum.db;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int id;
	private String description;
	private Date dateTime;
	private String clientUsername;
	private int postId;
	public int banned;
	
	public Comment() {}

	public Comment(int id, String description, Date dateTime, String clientUsername, int postId, int banned) {
		super();
		this.id = id;
		this.description = description;
		this.dateTime = dateTime;
		this.clientUsername = clientUsername;
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

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getClientUsername() {
		return clientUsername;
	}

	public void setClientUsername(String clientUsername) {
		this.clientUsername = clientUsername;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int isBanned() {
		return banned;
	}

	public void setBanned(int banned) {
		this.banned = banned;
	}
}
