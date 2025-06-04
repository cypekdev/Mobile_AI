package com.github.cypekdev;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ConversationCard {
    private final String userPrompt;
    private final MutableLiveData<String> assistantAnswer;


    public ConversationCard(String userPrompt) {
        this.userPrompt = userPrompt;
        this.assistantAnswer = new MutableLiveData<>("");
    }

    public void appendAssistantAnswer(String chunk) {
        assistantAnswer.postValue(assistantAnswer.getValue() + chunk);
    }

    public String getUserPrompt() {
        return userPrompt;
    }

    public LiveData<String> getAssistantAnswer() {
        return assistantAnswer;
    }
}
