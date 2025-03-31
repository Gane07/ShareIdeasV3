package com.shareidea.comment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shareidea.comment.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	Optional<List<Comment>> findByIdeaId(Long ideaId);
    Optional<List<Comment>> findByUsername(String username);
    Optional<List<Comment>> findByCommentUsername(String commentUsername);

}
