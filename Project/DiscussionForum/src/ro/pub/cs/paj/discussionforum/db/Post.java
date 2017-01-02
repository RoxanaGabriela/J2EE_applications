package ro.pub.cs.paj.discussionforum.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="post")
public class Post implements Serializable {

	public Post(){}
	
	public Post(int id, String title, String description, Date dateTime,
			Client client, int banned) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.dateTime = dateTime;
		this.client = client;
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
	
	@Id  
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Column(name="datetime")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date dateTime;
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name = "client_id")
	private Client client;
	
	@Column(name="banned")
	private int banned;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "post_topic", joinColumns = {
			@JoinColumn(name = "post_id", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "topic_id",
					nullable = false, updatable = false) })
	private Collection<Topic> topics;
	
	@OneToMany (mappedBy="post")
	private Collection<Comment> comments = new ArrayList<Comment>();
}
