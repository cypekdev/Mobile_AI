package com.github.cypekdev;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Api {
    private OkHttpClient httpClient;
    private String endpointUrl;
    private String model;

    public Api(String model, String endpointUrl) {
        this.model = model;
        this.endpointUrl = endpointUrl;

        httpClient = new OkHttpClient();
    }

    public Response sendPrompt(String prompt, StreamCallback callback, JSONArray context) {
        try {
            JSONObject json = new JSONObject()
                    .put("model", model)
                    .put("stream", true)
                    .put("messages", context
                            .put(new JSONObject()
                                    .put("role", "user")
                                    .put("content", prompt)));

            RequestBody body = RequestBody.create(
                    json.toString(),
                    MediaType.get("application/json")
            );

            Request request = new Request.Builder()
                    .url(endpointUrl)
                    .post(body)
                    .build();

            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull okhttp3.Response response) throws IOException {

                }
            });

        } catch (Exception e) {

        }

        Response response = new Response();
        return response;
    }
}
