package com.shareidea.comment.service;

import java.util.List;

import com.shareidea.comment.model.Comment;

public interface CommentService {
	
	public Comment addComment(Comment comment);
	public List<Comment> getAllComments();
	public List<Comment> getCommentsByIdea(Long ideaId);
	public List<Comment> getCommentsByUser(String username);
	public boolean updateComment(Long id, String content);
	public boolean deleteComment(Long id);
	public boolean deleteCommentsByIdea(Long ideaId);
	public boolean deleteCommentsByUser(String username);
	public List<Comment> getCommentsByCommentUser(String commentUsername);

}
