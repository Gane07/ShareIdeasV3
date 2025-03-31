package com.shareideas.ideas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ideas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Changed from username to Long id (as primary key)
    
    private String username;

    private String title;

    @Lob
    private String description;
    
    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'Open'")
    private String status;
    
    
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] document;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    public Ideas() {
    }

	

	public Ideas(Long id, String username, String title, String description, String status, byte[] document, byte[] image) {
		super();
		this.id = id;
		this.username = username;
		this.title = title;
		this.description = description;
		this.status = status;
		this.document = document;
		this.image = image;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public byte[] getDocument() {
		return document;
	}

	public void setDocument(byte[] document) {
		this.document = document;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}


	@Override
	public String toString() {
		return "Ideas [id=" + id + ", username=" + username + ", title=" + title + ", description=" + description + "]";
	}

}
