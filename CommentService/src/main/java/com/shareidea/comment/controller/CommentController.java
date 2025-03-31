package com.shareidea.comment.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shareidea.comment.model.Comment;
import com.shareidea.comment.service.CommentService;

@RestController
@RequestMapping("/comments")
@CrossOrigin("*")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Create a comment
    @PostMapping("/add")
    public ResponseEntity<?> addComment(@RequestBody Comment comment) {
        Comment savedComment = commentService.addComment(comment);
        
        if(savedComment != null) {
        	return new ResponseEntity<Comment>(savedComment,HttpStatus.OK);
        }
        else {
        	return new ResponseEntity<String>("Invalid Data",HttpStatus.CONFLICT);
        }
        
    }

    // Get all comments
    @GetMapping
    public ResponseEntity<?> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        
        if(comments != null) {
        	return new ResponseEntity<List<Comment>>(comments,HttpStatus.OK);
        }
        else {
        	return new ResponseEntity<String>("There Is No Comments",HttpStatus.CONFLICT);
        }
        
    }

    // Get comments by ideaId
    @GetMapping("/idea/{ideaId}")
    public ResponseEntity<?> getCommentsByIdea(@PathVariable Long ideaId) {
        List<Comment> comments = commentService.getCommentsByIdea(ideaId);
        
        if(comments != null) {
        	return new ResponseEntity<List<Comment>>(comments,HttpStatus.OK);
        }
        else {
        	return new ResponseEntity<String>("Invalid Id",HttpStatus.CONFLICT);
        }
    }

    // Get comments by username
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getCommentsOfUser(@PathVariable String username) {
        List<Comment> comments = commentService.getCommentsByUser(username);
        
        if(comments != null) {
        	return new ResponseEntity<List<Comment>>(comments,HttpStatus.OK);
        }
        else {
        	return new ResponseEntity<String>("Invalid Username",HttpStatus.CONFLICT);
        }
    }
    
    @GetMapping("/userbycomment/{commentUsername}")
    public ResponseEntity<?> getCommentsByUserComminted(@PathVariable String commentUsername) {
        List<Comment> comments = commentService.getCommentsByCommentUser(commentUsername);
        
        if(comments != null) {
        	return new ResponseEntity<List<Comment>>(comments,HttpStatus.OK);
        }
        else {
        	return new ResponseEntity<String>("Invalid Username",HttpStatus.CONFLICT);
        }
    }

    // Update a comment
    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody String content) {
        boolean response = commentService.updateComment(id, content);
        
        if(response) {
        	return new ResponseEntity<String>("Comment updated successfully!",HttpStatus.OK);
        }
        else {
        	return new ResponseEntity<String>("Comment not found!",HttpStatus.CONFLICT);
        }
    }

    // Delete a single comment
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        boolean  response = commentService.deleteComment(id);
        
        if (response) {
            return new ResponseEntity<String>("Comment deleted successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Comment not found!", HttpStatus.NOT_FOUND);
        }
    }

    // Delete all comments for an idea
    @DeleteMapping("/deletebyid/{ideaId}")
    public ResponseEntity<?> deleteCommentsByIdea(@PathVariable Long ideaId) {
        boolean response = commentService.deleteCommentsByIdea(ideaId);
        
        if (response) {
            return new ResponseEntity<String>("All comments for the idea deleted!", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("There Is No Comment With That Id", HttpStatus.NOT_FOUND);
        }
        
    }

    // Delete all comments by a user
    @DeleteMapping("/user/{username}")
    public ResponseEntity<?> deleteCommentsByUser(@PathVariable String username) {
        boolean response = commentService.deleteCommentsByUser(username);
        
        if (response) {
            return new ResponseEntity<String>("All user comments deleted!", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("There Is No Comment With That Id", HttpStatus.NOT_FOUND);
        }
        
    }
}

