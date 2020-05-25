package pack;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


@Entity
@NamedQueries({
	@NamedQuery(
		    name="searchUsername",
		    query="SELECT u FROM User u WHERE u.username =:username"
		),
	@NamedQuery(
		    name="searchEmail",
		    query="SELECT u FROM User u WHERE u.email =:email"
		),
	@NamedQuery(
			name="searchUser",
			query="SELECT u FROM User u WHERE u.username =:user "
					+ "OR u.email =:user"
		)
})

public class User {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)  
	int id;
	String username;
	String email;
	String password;
	String authentificationToken;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String pseudo) {
		this.username = pseudo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String mdp) {
		this.password = mdp;
	}

	public String getAuthentificationToken() {
		return authentificationToken;
	}

	public void setAuthentificationToken(String authentificationToken) {
		this.authentificationToken = authentificationToken;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
	}
	
}
