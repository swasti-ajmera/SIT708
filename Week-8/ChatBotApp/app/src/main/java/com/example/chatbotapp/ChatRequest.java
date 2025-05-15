package com.example.chatbotapp;

import java.util.List;
import java.util.Map;

public class ChatRequest {
    private String message;
    private List<Map<String, String>> chatHistory;

    public ChatRequest(String message, List<Map<String, String>> chatHistory) {
        this.message = message;
        this.chatHistory = chatHistory;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Map<String, String>> getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(List<Map<String, String>> chatHistory) {
        this.chatHistory = chatHistory;
    }
}
