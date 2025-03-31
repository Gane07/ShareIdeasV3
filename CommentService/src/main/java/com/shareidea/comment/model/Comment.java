package com.shareidea.comment.model;

import jakarta.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long ideaId;
    private String username;
    private String commentUsername;

    @Lob
    private String content;

	public Comment(Long id, Long ideaId, String username, String commentUsername, String content) {
		super();
		this.id = id;
		this.ideaId = ideaId;
		this.username = username;
		this.commentUsername = commentUsername;
		this.content = content;
	}

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdeaId() {
		return ideaId;
	}

	public void setIdeaId(Long ideaId) {
		this.ideaId = ideaId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCommentUsername() {
		return commentUsername;
	}

	public void setCommentUsername(String commentUsername) {
		this.commentUsername = commentUsername;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

    

}

