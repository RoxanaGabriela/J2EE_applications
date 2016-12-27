package ro.pub.cs.paj.discussionforum.db;

import java.io.Serializable;

public class TopicPost implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	private int id;
	private int topicId;
	private int postId;
	
	
	public TopicPost() {}


	public TopicPost(int id, int topicId, int postId) {
		super();
		this.id = id;
		this.topicId = topicId;
		this.postId = postId;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getTopicId() {
		return topicId;
	}


	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}


	public int getPostId() {
		return postId;
	}


	public void setPostId(int postId) {
		this.postId = postId;
	}
	

	
	
}
