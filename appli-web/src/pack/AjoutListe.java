package pack;

import java.io.Serializable;

public class AjoutListe implements Serializable {
	String imdbID;
	String username;
	
	public String getImdbID() {
		return imdbID;
	}
	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
