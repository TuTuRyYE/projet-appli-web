package pack;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NamedQueries({
	@NamedQuery(
		    name="searchImdbID",
		    query="SELECT f FROM Film f WHERE f.imdbID =:imdbID"
		)
})
public class Film {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	String imdbID;
	int nbVue;
	
	@JsonIgnore
	@ManyToMany
	Collection<User> users;

	public String getImdbID() {
		return imdbID;
	}

	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUser(Collection<User> users) {
		this.users = users;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNbVue() {
		return nbVue;
	}

	public void setNbVue(int nbVue) {
		this.nbVue = nbVue;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}
	
	public void addVue() {
		this.nbVue++;
	}	
	
}
