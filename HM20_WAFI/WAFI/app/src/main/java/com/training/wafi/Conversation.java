package com.training.wafi;

public class Conversation {
    private String conversationId;
    private String user1Id;
    private String user2Id;

    public Conversation() {
        // Default constructor required for calls to DataSnapshot.getValue(Conversation.class)
    }

    public Conversation(String conversationId, String user1Id, String user2Id) {
        this.conversationId = conversationId;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getUser1Id() {
        return user1Id;
    }

    public String getUser2Id() {
        return user2Id;
    }
}

