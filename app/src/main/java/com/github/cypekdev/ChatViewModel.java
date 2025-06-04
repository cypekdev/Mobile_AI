package com.github.cypekdev;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ChatViewModel extends ViewModel {

    private final MutableLiveData<List<ConversationCard>> conversationLiveData;
    private final MutableLiveData<String> streamingResponse = new MutableLiveData<>("");
    private final Chat chat;

    public ChatViewModel(Chat chat) {
        this.conversationLiveData = new MutableLiveData<>();
        this.chat = chat;
    }

    public ChatViewModel() {
        this(new Chat());
    }

    public LiveData<List<ConversationCard>> getConversation() {
        return conversationLiveData;
    }

    public void sendPrompt(StreamApiClient client, String prompt) {
        chat.send(client, prompt);
        conversationLiveData.postValue(chat.getConversation());
    }
}
