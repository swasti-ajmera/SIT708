package com.example.chatbotapp;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ChatApi {
    @Headers("Content-Type: application/json")
    @POST("/chat")
    Call<ChatResponse> sendMessage(@Body ChatRequest request);
}