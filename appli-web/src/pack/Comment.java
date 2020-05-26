package pack;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Comment {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	String commentID;
	
	@ManyToOne
	@JsonIgnore
	User user;
    String text;

	public String getcommentID() {
		return commentID;
	}

	public void setcommentID(String commentID) {
		this.commentID = commentID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
    public String getText() {
        return text;
    }

    public void setText() {
        this.text = text; 
    }
}
