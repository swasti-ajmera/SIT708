package com.example.taskmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TaskAdapter adapter;
    AppDatabase db;
    Button btnAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);
        recyclerView = findViewById(R.id.recyclerView);
        btnAddTask = findViewById(R.id.btnAddTask);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(db.taskDao().getAllTasks(), db);
        recyclerView.setAdapter(adapter);

        btnAddTask.setOnClickListener(v ->
                startActivity(new Intent(this, AddEditTaskActivity.class))
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setTasks(db.taskDao().getAllTasks());
    }
}

