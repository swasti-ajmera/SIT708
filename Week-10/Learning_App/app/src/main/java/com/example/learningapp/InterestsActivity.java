package com.example.learningapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class InterestsActivity extends AppCompatActivity {
    Button nextBtn;
    ArrayList<String> selectedInterests = new ArrayList<>();
    String[] interests = {"Math", "Science", "History", "AI", "Coding", "Biology",
            "Chemistry", "Physics", "English", "Geography", "Music", "Art", "Data", "Robotics"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        nextBtn = findViewById(R.id.nextBtn);
        LinearLayout container = findViewById(R.id.interestsContainer);
        String username = getIntent().getStringExtra("username");

        for (String topic : interests) {
            CheckBox cb = new CheckBox(this);
            cb.setText(topic);
            cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked && selectedInterests.size() < 10) {
                    selectedInterests.add(topic);
                } else if (!isChecked) {
                    selectedInterests.remove(topic);
                } else {
                    buttonView.setChecked(false);
                }
            });
            container.addView(cb);
        }

        nextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(InterestsActivity.this, DashboardActivity.class);
            intent.putExtra("username", username);
            intent.putStringArrayListExtra("interests", selectedInterests);
            startActivity(intent);
        });
    }
}
