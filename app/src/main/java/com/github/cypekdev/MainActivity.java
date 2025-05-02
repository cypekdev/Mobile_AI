package com.github.cypekdev;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    StreamApiClient client;
    Button sendBtn;
    EditText promptET;
    TextView textView;
    String result;

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

        client = new StreamApiClient();

        sendBtn = findViewById(R.id.sendPromptBtn);
        promptET = findViewById(R.id.promptEditText);
        textView = findViewById(R.id.textView);

        result = "";

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prompt = promptET.getText().toString();
                sendMessage(prompt);
                promptET.setText("");
            }
        });
    }

    private void sendMessage(String prompt) {

        client.chat(prompt, new StreamApiClient.ApiCallback() {
            @Override
            public void onChunk(String data) {
                result += data;
                textView.setText(result);
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}