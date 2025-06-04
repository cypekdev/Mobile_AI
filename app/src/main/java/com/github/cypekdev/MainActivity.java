package com.github.cypekdev;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    StreamApiClient client;
    Button sendBtn;
    EditText promptET;
    ListView conversationLV;
    ChatViewModel chatViewModel;
    ChatAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sendBtn = findViewById(R.id.sendPromptBtn);
        promptET = findViewById(R.id.promptEditText);
        conversationLV = findViewById(R.id.conversation);

        client = new OllamaApiClient();

        Chat chat = new Chat() {
            @Override
            public void send(StreamApiClient client, String prompt) {
                ConversationCard newConversationCard = new ConversationCard(prompt);

//                newConversationCard.appendAssistantAnswer("Chuj ci w dupe");

                new Thread(() -> {
                    try {
                        String[] parts = {"Witaj, ", "to ", "odpowiedź "};
                        for (String part : parts) {
                            newConversationCard.appendAssistantAnswer(part);
                            Thread.sleep(500);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();

                conversation.add(newConversationCard);
            }
        };
        chatViewModel = new ViewModelProvider(this, new ChatViewModelFactory(chat)).get(ChatViewModel.class);
        adapter = new ChatAdapter(this, new ArrayList<>());

        conversationLV.setAdapter(adapter);

        chatViewModel.getConversation().observe(this, new Observer<List<ConversationCard>>() {
            @Override
            public void onChanged(List<ConversationCard> conversationCards) {
                adapter.clear();
                adapter.addAll(conversationCards);
                adapter.notifyDataSetChanged();
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prompt = promptET.getText().toString();
                chatViewModel.sendPrompt(client, prompt);
                promptET.setText("");
            }
        });
    }
}