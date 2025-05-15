package com.example.chatbotapp;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.Gravity;
import android.graphics.Color;

import java.util.*;

import okhttp3.OkHttpClient;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {
    LinearLayout chatLayout;
    EditText messageEditText;
    Button sendButton;
    ScrollView chatScrollView;

    List<Map<String, String>> chatHistory = new ArrayList<>();
    ChatApi chatApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatLayout = findViewById(R.id.chatLayout);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);
        chatScrollView = findViewById(R.id.chatScrollView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().readTimeout(10, java.util.concurrent.TimeUnit.MINUTES).build())
                .build();

        chatApi = retrofit.create(ChatApi.class);

        sendButton.setOnClickListener(v -> {
            String message = messageEditText.getText().toString().trim();
            if (!message.isEmpty()) {
                addMessage("You: " + message, Gravity.END, "#DCF8C6");  // light green
                messageEditText.setText("");

//                Map<String, Object> body = new HashMap<>();
//                body.put("message", message); // backend expects "message"
//                body.put("chatHistory", chatHistory);
// chatApi.sendMessage(body).enqueue(new Callback<ChatResponse>()
                ChatRequest request = new ChatRequest(message, chatHistory);

                chatApi.sendMessage(request).enqueue(new Callback<ChatResponse>() {
                    @Override
                    public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            String reply = response.body().getReply();
                            addMessage("Bot: " + reply, Gravity.START, "#FFFFFF");  // white bubble

                            Map<String, String> chatEntry = new HashMap<>();
                            chatEntry.put("User", message);
                            chatEntry.put("Llama", reply);
                            chatHistory.add(chatEntry);
                        } else {
                            addMessage("Bot: [No response or error]", Gravity.START, "#FFCDD2");  // red shade
                        }
                    }

                    @Override
                    public void onFailure(Call<ChatResponse> call, Throwable t) {
                        addMessage("Bot: Error - " + t.getMessage(), Gravity.START, "#FFCDD2");
                    }
                });
            }
        });
    }

    private void addMessage(String msg, int gravity, String bubbleColor) {
        TextView textView = new TextView(this);
        textView.setText(msg);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.parseColor(bubbleColor));
        textView.setPadding(20, 10, 20, 10);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 10);
        params.gravity = gravity;

        textView.setLayoutParams(params);
        chatLayout.addView(textView);

        chatScrollView.post(() -> chatScrollView.fullScroll(ScrollView.FOCUS_DOWN));
    }
}
