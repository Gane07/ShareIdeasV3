package com.shareidea.message.service;

import java.util.List;

import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PathVariable;

import com.shareidea.message.model.Message;

public interface MessageService {
	
	List<Message> getMessageService(String username, String withUser);
	
	void sendMessageService(Message message);

//	<TypingEvent> void sendTypingEventService(TypingEvent event);
	
//	List<Message> getConversationPartners(String username);

}
