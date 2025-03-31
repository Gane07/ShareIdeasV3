package com.shareidea.message.model;

import org.antlr.v4.runtime.misc.NotNull;

public class TypingEvent {
    private String fromUser;
    private String toUser;
    private boolean isTyping;

    // Default constructor (required for frameworks like Jackson)
    public TypingEvent() {}

    // Parameterized constructor (optional, for convenience)
    public TypingEvent(String fromUser, String toUser, boolean isTyping) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.isTyping = isTyping;
    }

    // Getters and setters
    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public boolean isTyping() {
        return isTyping;
    }

    public void setTyping(boolean isTyping) {
        this.isTyping = isTyping;
    }
}
