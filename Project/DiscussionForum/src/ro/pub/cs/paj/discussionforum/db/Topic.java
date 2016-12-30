package ro.pub.cs.paj.discussionforum.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="topic")
public class Topic implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id  
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "topics")
	private Collection<Post> posts;
	
	public Topic(){}
		
	
	public Topic(int id, String title, String description) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.posts = new ArrayList<Post>();
	}
	
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

	public Collection<Post> getPosts() {
		return posts;
	}

	public void setPosts(Collection<Post> posts) {
		this.posts = posts;
	}
}
