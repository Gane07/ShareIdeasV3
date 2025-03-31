package com.shareidea.message.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shareidea.message.model.Message;
import com.shareidea.message.model.TypingEvent;
import com.shareidea.message.repository.MessageRepository;
import com.shareidea.message.service.MessageService;

@RestController
@RequestMapping("api/messages")
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@CrossOrigin("*")
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/{username}")
    public ResponseEntity<?> getMessages(@PathVariable String username, @RequestParam String withUser) {
        try {
            List<Message> messages = messageService.getMessageService(username, withUser);
            if (messages.isEmpty()) {
                return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
            }
            return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Messages Are Not Retrieved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/conversations/{username}")
    public List<String> getConversationPartners(@PathVariable String username) {
        List<Message> messages = messageRepository.findByFromUserOrToUser(username);
        return messages.stream().flatMap(msg -> List.of(msg.getFromUser(), msg.getToUser()).stream())
                .filter(user -> !user.equals(username)).distinct().collect(Collectors.toList());
    }

    @GetMapping("/all-users")
    public List<String> getAllUsers() {
        List<Message> messages = messageRepository.findAll();
        return messages.stream().flatMap(msg -> List.of(msg.getFromUser(), msg.getToUser()).stream()).distinct()
                .collect(Collectors.toList());
    }

    @DeleteMapping("/conversation/{username}/{withUser}")
    @Transactional
    public ResponseEntity<String> deleteConversation(@PathVariable String username, @PathVariable String withUser) {
        try {
            int deletedCount = messageRepository.deleteConversation(username, withUser);
            if (deletedCount > 0) {
                return ResponseEntity.ok("Conversation deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No conversation found to delete");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete conversation: " + e.getMessage());
        }
    }

    @PostMapping("/read/{username}/{withUser}")
    @Transactional
    public ResponseEntity<String> markMessagesAsRead(@PathVariable String username, @PathVariable String withUser) {
        System.out.println("Marking messages as read for " + username + " from " + withUser);
        List<Message> messages = messageRepository.findByFromUserAndToUserOrToUserAndFromUser(withUser, username, username, withUser);
        boolean updated = false;
        for (Message msg : messages) {
            if (!msg.isReading() && msg.getToUser().equals(username)) {
                msg.setReading(true);
                messageRepository.save(msg);
                System.out.println("Updated message " + msg.getId() + " to read: true, broadcasting to " + msg.getFromUser());
                messagingTemplate.convertAndSend("/topic/" + msg.getFromUser(), msg);
                updated = true;
            }
        }
        String response = updated ? "Messages marked as read" : "No unread messages to mark";
        System.out.println("Mark read for " + username + " with " + withUser + ": " + response);
        return ResponseEntity.ok(response);
    }

    @MessageMapping("/send")
    public void sendMessage(@Payload Message message) {
        message.setFromUser(message.getFromUser().trim());
        message.setToUser(message.getToUser().trim());
        message.setReading(false);
        Message savedMessage = messageRepository.save(message);
        System.out.println("Saved and sent message: " + savedMessage.getId() + " from " + savedMessage.getFromUser() + " to " + savedMessage.getToUser());
        messagingTemplate.convertAndSend("/topic/" + message.getToUser(), savedMessage);
        messagingTemplate.convertAndSend("/topic/" + message.getFromUser(), savedMessage);
    }

    @MessageMapping("/typing")
    public void sendTypingEvent(@Payload TypingEvent event) {
        System.out.println("Typing event: " + event.getFromUser() + " to " + event.getToUser() + ", isTyping: " + event.isTyping());
        messagingTemplate.convertAndSend("/topic/typing/" + event.getToUser(), event);
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<List<Message>> searchMessages(@PathVariable String username, @RequestParam String query) {
        List<Message> messages = messageRepository.findByFromUserOrToUser(username).stream()
                .filter(msg -> msg.getContent().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/pinned/{username}")
    public ResponseEntity<List<String>> getPinnedUsers(@PathVariable String username) {
        return ResponseEntity.ok(List.of("Ganesh"));
    }

    @PostMapping("/pin/{messageId}")
    @Transactional
    public ResponseEntity<String> pinMessage(@PathVariable Long messageId) {
        System.out.println("Pinning message: " + messageId);
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        message.setPinned(true);
        messageRepository.save(message);
        System.out.println("Message " + messageId + " pinned, broadcasting to " + message.getFromUser() + " and " + message.getToUser());
        messagingTemplate.convertAndSend("/topic/" + message.getFromUser(), message);
        messagingTemplate.convertAndSend("/topic/" + message.getToUser(), message);
        return ResponseEntity.ok("Message pinned");
    }

    @PostMapping("/unpin/{messageId}")
    @Transactional
    public ResponseEntity<String> unpinMessage(@PathVariable Long messageId) {
        System.out.println("Unpinning message: " + messageId);
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        message.setPinned(false);
        messageRepository.save(message);
        System.out.println("Message " + messageId + " unpinned, broadcasting to " + message.getFromUser() + " and " + message.getToUser());
        messagingTemplate.convertAndSend("/topic/" + message.getFromUser(), message);
        messagingTemplate.convertAndSend("/topic/" + message.getToUser(), message);
        return ResponseEntity.ok("Message unpinned");
    }
}