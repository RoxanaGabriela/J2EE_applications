package ro.pub.cs.paj.discussionforum.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="client")
public class Client implements Serializable {
		
	public Client(){}	
	
	public Client(int id, String username, String password, String type,
			int banned) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.type = type;
		this.banned = banned;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int isBanned() {
		return banned;
	}
	public void setBanned(int banned) {
		this.banned = banned;
	}
	private static final long serialVersionUID = 1L;
	
	@Id  
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="type")
	private String type;
	
	@Column(name="banned")
	private int banned;
	
	@OneToMany (mappedBy="client")
	private Collection<Post> posts = new ArrayList<Post>();
	
	@OneToMany (mappedBy="client")
	private Collection<Comment> comments = new ArrayList<Comment>();
}
