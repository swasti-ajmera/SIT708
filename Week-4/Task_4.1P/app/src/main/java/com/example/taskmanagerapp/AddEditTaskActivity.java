package com.example.taskmanagerapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditTaskActivity extends AppCompatActivity {

    EditText editTitle, editDescription, editDueDate;
    Button btnSave;
    AppDatabase db;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        editDueDate = findViewById(R.id.editDueDate);
        btnSave = findViewById(R.id.btnSave);
        db = AppDatabase.getInstance(this);

        if (getIntent().hasExtra("task_id")) {
            int id = getIntent().getIntExtra("task_id", -1);
            for (Task t : db.taskDao().getAllTasks()) {
                if (t.id == id) {
                    task = t;
                    break;
                }
            }
            if (task != null) {
                editTitle.setText(task.title);
                editDescription.setText(task.description);
                editDueDate.setText(task.dueDate);
            }
        }

        btnSave.setOnClickListener(v -> {
            if (editTitle.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (task == null) task = new Task();
            task.title = editTitle.getText().toString();
            task.description = editDescription.getText().toString();
            task.dueDate = editDueDate.getText().toString();

            if (task.id == 0)
                db.taskDao().insert(task);
            else
                db.taskDao().update(task);

            finish();
        });
    }
}

