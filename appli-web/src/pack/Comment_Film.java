package pack;

import java.io.Serializable;

public class Comment_Film implements Serializable {
	int commentID;
	int imdbID;
	
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int personId) {
		this.commentID = commentID;
	}
	public int getImdbID() {
		return imdbID;
	}
	public void setImdbID(int imdbID) {
		this.imdbID = imdbID;
	}
}
