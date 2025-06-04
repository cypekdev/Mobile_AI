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

    public List<ConversationCard> getConversation() {
        return new ArrayList<>(conversation);
    }

    public void send(StreamApiClient client, String prompt) {
        ConversationCard newConversationCard = new ConversationCard(prompt);

        client.chat(conversation, newConversationCard);

        conversation.add(newConversationCard);
    }
}
