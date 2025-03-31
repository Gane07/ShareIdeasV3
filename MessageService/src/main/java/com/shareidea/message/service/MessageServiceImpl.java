package com.shareidea.message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.shareidea.message.model.Message;
import com.shareidea.message.repository.MessageRepository;

@Service
public class MessageServiceImpl<TypingEvent> implements MessageService{
	
	@Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

	@Override
	public List<Message> getMessageService(String username, String withUser) {
		
		List<Message> message = messageRepository.findByFromUserAndToUserOrToUserAndFromUser(username, withUser, withUser, username);
		
		return message;
	}

	@Override
	public void sendMessageService(Message message) {
		
		message.setFromUser(message.getFromUser().trim()); // Trim whitespace from sender
        message.setToUser(message.getToUser().trim());     // Trim whitespace from recipient
        messageRepository.save(message);                   // Save to MySQL
        // Broadcast to recipient's topic
        messagingTemplate.convertAndSend("/topic/" + message.getToUser(), message);
        // Also send to sender's topic for their own view
        messagingTemplate.convertAndSend("/topic/" + message.getFromUser(), message);
		
	}
	
	public void markMessagesAsRead(@PathVariable String username, @PathVariable String withUser) {
		
		List<Message> messages = messageRepository.findByFromUserAndToUserOrToUserAndFromUser(withUser, username, username, withUser);
        messages.forEach(msg -> {
            if (!msg.isReading() && msg.getToUser().equals(username)) {
                msg.setReading(true);
                messageRepository.save(msg);
                messagingTemplate.convertAndSend("/topic/" + msg.getFromUser(), msg); // Notify sender
            }
        });
		
	}
	
	
	

//	@Override
//	public List<Message> getConversationPartners(String username) {
//		
//		return  messageRepository.findByFromUserOrToUser(username,username);
//	}

}
