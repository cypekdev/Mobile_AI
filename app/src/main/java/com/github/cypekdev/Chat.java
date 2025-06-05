package com.github.cypekdev;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    protected final List<ConversationCard> conversation;

    public Chat() {
        this.conversation = new ArrayList<>();
    }

    private boolean isAnswerCompleted() {
        if (conversation.isEmpty()) {
            return true;
        }

        int lastIndex = conversation.size() - 1;
        ConversationCard lastCC = conversation.get(lastIndex);
        return lastCC.isCompleted();
    }

    public List<ConversationCard> getConversation() {
        return new ArrayList<>(conversation);
    }

    public void send(StreamApiClient client, String prompt) {

        if (isAnswerCompleted()) {
            ConversationCard newConversationCard = new ConversationCard(prompt);
            client.chat(conversation, newConversationCard);
            conversation.add(newConversationCard);
        }
    }
}
