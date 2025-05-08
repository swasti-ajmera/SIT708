package com.example.learningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardActivity extends AppCompatActivity {
    LinearLayout tasksContainer;
    String[] dummyTasks = {"Generated Task 1", "Generated Task 2"};
    String[] dummyDesc = {"Solve algebra problems", "Write a short essay"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        String username = getIntent().getStringExtra("username");
        TextView greeting = findViewById(R.id.greeting);
        tasksContainer = findViewById(R.id.tasksContainer);
        greeting.setText("Hello " + username);

        for (int i = 0; i < dummyTasks.length; i++) {
            View card = getLayoutInflater().inflate(R.layout.task_card, null);
            // Create layout params with margin
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 0, 0, 24);  // left, top, right, bottom
            card.setLayoutParams(layoutParams);
            ((TextView) card.findViewById(R.id.taskTitle)).setText(dummyTasks[i]);
            ((TextView) card.findViewById(R.id.taskDesc)).setText(dummyDesc[i]);
            Button viewBtn = card.findViewById(R.id.viewTaskBtn);
            int finalI = i;
            viewBtn.setOnClickListener(v -> {
                Intent intent = new Intent(DashboardActivity.this, TaskDetailActivity.class);
                intent.putExtra("taskTitle", dummyTasks[finalI]);
                startActivity(intent);
            });
            tasksContainer.addView(card);
        }
    }
}
