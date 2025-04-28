package com.github.cypekdev;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Chat {
    private ArrayList<Request> requests;

    private JSONArray getChatContext() {
        JSONArray messages = new JSONArray();

        try {
            for (Request req : requests) {
                JSONObject user = new JSONObject()
                        .put("role", "user")
                        .put("content", req.getContent());

                JSONObject assistant = new JSONObject()
                        .put("role", "assistant")
                        .put("content", req.getResponse().getContent());

                messages
                        .put(user)
                        .put(assistant);
            }
        } catch (Exception e) {

        }

        return messages;
    }

    public void sendMessage(Api api, String content, StreamCallback callback) {
        Request request = new Request(api, content, getChatContext(), callback);
        requests.add(request);
    }
}
