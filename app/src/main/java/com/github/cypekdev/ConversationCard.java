package com.github.cypekdev;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ConversationCard {
    private final String userPrompt;
    private String assistantAnswer;
    private boolean completed;

    public ConversationCard(String userPrompt) {
        this.userPrompt = userPrompt;
        this.assistantAnswer = "";
        this.completed = false;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void appendAssistantAnswer(String chunk) {
        assistantAnswer = assistantAnswer + chunk;
    }

    public void setCompleted() {
        completed = true;
    }

    public String getUserPrompt() {
        return userPrompt;
    }

    public String getAssistantAnswer() {
        return assistantAnswer;
    }
}
