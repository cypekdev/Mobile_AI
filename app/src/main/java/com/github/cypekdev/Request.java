package com.github.cypekdev;

import org.json.JSONArray;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Request {
    private Api api;
    private Response response;
    private String content;
    private LocalDateTime requestTime;
    private StreamCallback callback;

    public Request(Api api, String content, JSONArray context, StreamCallback callback) {
        this.api = api;
        this.content = content;
        this.callback = callback;

        api.sendPrompt(content, callback, context);

        requestTime = LocalDateTime.now();
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public String getContent() {
        return content;
    }

    public Response getResponse() {
        return response;
    }
}
