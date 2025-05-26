package com.example.learningapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    TextView usernameText, emailText, totalQText, correctQText, incorrectQText;
    Button shareBtn;

    String username = "swasti"; // should ideally come from login
    String email = "swasti@gmail.com"; // should also come from login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Bind views
        usernameText = findViewById(R.id.usernameText);
        emailText = findViewById(R.id.emailText);
        totalQText = findViewById(R.id.totalQText);
        correctQText = findViewById(R.id.correctQText);
        incorrectQText = findViewById(R.id.incorrectQText);
        shareBtn = findViewById(R.id.shareBtn);

        // Display username and email
        usernameText.setText(username);
        emailText.setText(email);

        // Load and calculate stats
        fetchHistoryAndUpdateStats(username);

        // Share button
        shareBtn.setOnClickListener(v -> {
            String shareableLink = "https://yourapp.com/profile/" + username;
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out my profile and learning stats: " + shareableLink);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });
    }

    private void fetchHistoryAndUpdateStats(String username) {
        String url = "http://10.0.2.2:5000/getHistory?username=" + username;

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray historyArray = response.getJSONArray("history");

                        int total = historyArray.length();
                        int correct = 0;
                        int incorrect = 0;

                        for (int i = 0; i < total; i++) {
                            JSONObject obj = historyArray.getJSONObject(i);
                            String selected = obj.getString("selected");
                            String correctAns = obj.getString("correct");

                            if (!selected.isEmpty()) {
                                if (selected.equals(correctAns)) {
                                    correct++;
                                } else {
                                    incorrect++;
                                }
                            }
                        }

                        totalQText.setText("Total Questions: " + total);
                        correctQText.setText("Correct Answers: " + correct);
                        incorrectQText.setText("Wrong Answers: " + incorrect);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Failed to parse history", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Failed to fetch history", Toast.LENGTH_SHORT).show();
                }
        );

        queue.add(request);
    }
}
