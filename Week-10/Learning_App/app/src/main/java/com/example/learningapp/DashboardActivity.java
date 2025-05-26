package com.example.learningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 0, 0, 24);
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

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(android.view.MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_profile) {
                    startActivity(new Intent(DashboardActivity.this, ProfileActivity.class));
                    return true;
                } else if (id == R.id.nav_history) {
                    startActivity(new Intent(DashboardActivity.this, HistoryActivity.class));
                    return true;
                } else if (id == R.id.nav_upgrade) {
                    startActivity(new Intent(DashboardActivity.this, UpgradeActivity.class));
                    return true;
                }

                return false;
            }
        });
    }
}
