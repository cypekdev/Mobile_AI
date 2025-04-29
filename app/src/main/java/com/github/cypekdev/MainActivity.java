package com.github.cypekdev;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Api api;
    Chat chat;
    Button sendBtn;
    EditText promptET;

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

        chat = new Chat();
        api = new Api("gemma3:12b", "192.168.1.69:11434/api/chat");

        sendBtn = findViewById(R.id.sendPromptBtn);
        promptET = findViewById(R.id.promptEditText);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prompt = promptET.getText().toString();
                Log.d("Prompt", prompt);

                chat.sendMessage(api, prompt, new StreamCallback() {
                    @Override
                    public void onMessageChunk(String message) {
                        Log.d("Prompt", message);

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }
                });

            }
        });




    }
}