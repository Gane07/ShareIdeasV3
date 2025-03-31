package com.shareideas.ideas.model;

public class IdeasDTO {
	
	private String username;
    private String title;
    private String description;
    private String status;
    private String document; // Base64 string
    private String image;    // Base64 string
	public IdeasDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public IdeasDTO(String username, String title, String description, String status, String document,
			String image) {
		super();
		this.username = username;
		this.title = title;
		this.description = description;
		this.status = status;
		this.document = document;
		this.image = image;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

}
