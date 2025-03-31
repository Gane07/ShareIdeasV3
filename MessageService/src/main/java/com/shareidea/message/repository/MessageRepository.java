package com.shareidea.message.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shareidea.message.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{
	
	@Query("SELECT m FROM Message m WHERE (LOWER(m.fromUser) = LOWER(:fromUser) AND LOWER(m.toUser) = LOWER(:toUser)) OR (LOWER(m.fromUser) = LOWER(:toUser) AND LOWER(m.toUser) = LOWER(:fromUser))")
	List<Message> findByFromUserAndToUserOrToUserAndFromUser(@Param("fromUser") String fromUser, @Param("toUser") String toUser, @Param("toUser") String toUser2, @Param("fromUser") String fromUser2);
	
	@Query("SELECT m FROM Message m WHERE m.fromUser = :username OR m.toUser = :username")
    List<Message> findByFromUserOrToUser(@Param("username") String username);
	
	@Modifying
    @Query("DELETE FROM Message m WHERE (m.fromUser = :user1 AND m.toUser = :user2) OR (m.fromUser = :user2 AND m.toUser = :user1)")
    int deleteConversation(@Param("user1") String user1, @Param("user2") String user2);

}
