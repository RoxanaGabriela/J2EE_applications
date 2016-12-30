package ro.pub.cs.paj.discussionforum.db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="comment")
public class Comment implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id  
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="description")
	private String description;
	
	@Column(name="datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;

	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name = "client_id")
	private Client client;
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;
	
	@Column(name="banned")
	public int banned;
	
	public Comment() {}

	public Comment(int id, String description, Date dateTime,
			Client client, Post post, int banned) {
		super();
		this.id = id;
		this.description = description;
		this.dateTime = dateTime;
		this.client = client;
		this.post = post;
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

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public int isBanned() {
		return banned;
	}

	public void setBanned(int banned) {
		this.banned = banned;
	}
	
	
	
	
}
