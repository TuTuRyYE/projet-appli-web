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
		    name="searchPseudo",
		    query="SELECT u FROM User u WHERE u.pseudo =:pseudo"
		),
	@NamedQuery(
		    name="searchEmail",
		    query="SELECT u FROM User u WHERE u.email =:email"
		)
})

public class User {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)  
	int id;
	String pseudo;
	String email;
	String mdp;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	

}
