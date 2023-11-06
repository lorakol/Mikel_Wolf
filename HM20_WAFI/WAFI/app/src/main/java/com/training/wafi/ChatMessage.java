package com.training.wafi;

public class ChatMessage {
    private String senderId;
    private String messageContent;

    public ChatMessage() {
        // Default constructor required for calls to DataSnapshot.getValue(ChatMessage.class)
    }

    public ChatMessage(String senderId, String messageContent) {
        this.senderId = senderId;
        this.messageContent = messageContent;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getMessageContent() {
        return messageContent;
    }
}
