package com.github.cypekdev;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StreamApiClient {

    private OkHttpClient client;
    private String model;


    public interface ApiCallback {
        void onChunk(String data);
        void onComplete();
        void onFailure(Exception e);
    }

    public StreamApiClient() {
        client = new OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .build();
        model = "gemma3";
    }

    public void chat(String prompt, ApiCallback apiCallback) {
        try {
            JSONObject jsonObject = new JSONObject()
                    .put("model", model)
                    .put("messages", new JSONArray()
                            .put(new JSONObject()
                                    .put("role", "user")
                                    .put("content", prompt)));

            RequestBody requestBody = RequestBody.create(
                    jsonObject.toString(),
                    MediaType.get("application/json"));

            Request request = new Request.Builder()
                    .url("http://192.168.1.69:11434/api/chat")
                    .post(requestBody)
                    .build();


            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.e("Stream", "Failed", e);
                    apiCallback.onFailure(e);
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
                            apiCallback.onChunk(chunk);
                        }

                        Log.d("Stream", "Completed");
                        apiCallback.onComplete();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        } catch (Exception e) {
            apiCallback.onFailure(e);
            Log.e("Stream", "Failed", e);
        }


    }

}
