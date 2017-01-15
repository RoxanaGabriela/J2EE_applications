package ro.pub.cs.paj.discussionforum.db;

import java.io.Serializable;

public class Comment implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int id;
	private String description;
	private String date;
	private String time;
	private String clientUsername;
	private int postId;
	public int banned;
	
	public Comment() {}

	public Comment(int id, String description, String date, String time, String clientUsername, int postId, int banned) {
		super();
		this.id = id;
		this.description = description;
		this.date = date;
		this.time = time;
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
