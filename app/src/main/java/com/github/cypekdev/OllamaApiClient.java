package com.github.cypekdev;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OllamaApiClient implements StreamApiClient {
    private OkHttpClient client;
    private String model;

    public OllamaApiClient() {
        client = new OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .build();
        model = "gemma3:12b";
    }

    private String buildRequestBody(List<ConversationCard> context, String prompt) throws JSONException {
        JSONArray messages = new JSONArray();
        for (ConversationCard conversationCard : context) {
            messages.put(new JSONObject()
                    .put("role", "user")
                    .put("content", conversationCard.getUserPrompt()));

            messages.put(new JSONObject()
                    .put("role", "assistant")
                    .put("content", conversationCard.getAssistantAnswer()));
        }

        messages.put(new JSONObject()
                .put("role", "user")
                .put("content", prompt));

        JSONObject jsonObject = new JSONObject()
                .put("model", model)
                .put("messages", messages);

        return jsonObject.toString();
    }

    @Override
    public void chat(List<ConversationCard> context, ConversationCard conversationCard) {
        try {
            RequestBody requestBody = RequestBody.create(
                    buildRequestBody(context, conversationCard.getUserPrompt()),
                    MediaType.get("application/json"));

            okhttp3.Request request = new Request.Builder()
                    .url("http://192.168.1.80:11434/api/chat")
                    .post(requestBody)
                    .build();


            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.e("Stream", "Failed", e);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (!response.isSuccessful()) throw new IOException("unsuccessful response: " + response);

                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().byteStream()))) {
                        String line;

                        while ((line = reader.readLine()) != null) {
                            Log.d("Stream", line);
                            String chunk = (new JSONObject(line))
                                    .getJSONObject("message")
                                    .getString("content");

                            conversationCard.appendAssistantAnswer(chunk);
                        }

                        conversationCard.setCompleted();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        } catch (Exception e) {
            Log.e("Stream", "Failed", e);
        }
    }
}
