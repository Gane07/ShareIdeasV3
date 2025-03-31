package com.shareidea.comment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shareidea.comment.model.Comment;
import com.shareidea.comment.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository repo;

    // Create a new comment
    public Comment addComment(Comment comment) {
        return repo.save(comment);
    }

    // Get all comments
    public List<Comment> getAllComments() {
        return repo.findAll();
    }

    // Get comments by ideaId
    public List<Comment> getCommentsByIdea(Long ideaId) {
    	
    	Optional<List<Comment>> optional = repo.findByIdeaId(ideaId);
    	
    	if(optional.isPresent()) {
    		List<Comment> commentList = optional.get();
    		return commentList;
    	}
    	else {
    		 return null;
    	}
    	
    }

    // Get comments by username
    public List<Comment> getCommentsByUser(String username) {
    	
    	Optional<List<Comment>> optional = repo.findByUsername(username);
    	
    	if(optional.isPresent()) {
    		List<Comment> commentList = optional.get();
    		return commentList;
    	}
    	else {
    		 return null;
    	}
    }
    
    public List<Comment> getCommentsByCommentUser(String commentUsername){
    	
    	Optional<List<Comment>> optional = repo.findByCommentUsername(commentUsername);
    	
    	if(optional.isPresent()) {
    		List<Comment> commentList = optional.get();
    		return commentList;
    	}
    	else {
    		 return null;
    	}
    	
    	
    }

    // Update a comment
    public boolean updateComment(Long id, String content) {
        Optional<Comment> optionalComment = repo.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setContent(content);
            repo.save(comment);
            return true;
        } else {
            return false;
        }
    }

    // Delete a single comment
    public boolean deleteComment(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    // Delete all comments for an idea
    public boolean deleteCommentsByIdea(Long ideaId) {
        Optional<List<Comment>> optional = repo.findByIdeaId(ideaId);
        
        if(optional.isPresent()) {
    		List<Comment> commentList = optional.get();
    		repo.deleteAll(commentList);
            return true;
    	}
    	else {
    		 return false;
    	}
        
    }

    // Delete all comments by a user
    public boolean deleteCommentsByUser(String username) {
        Optional<List<Comment>> optional = repo.findByUsername(username);
        
        if(optional.isPresent()) {
    		List<Comment> commentList = optional.get();
    		repo.deleteAll(commentList);
            return true;
    	}
    	else {
    		 return false;
    	}
        
    }
}

