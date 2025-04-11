package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText etName;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.et_name);
        btnStart = findViewById(R.id.btn_start);

        btnStart.setOnClickListener(v -> {
            String name = etName.getText().toString();
            if (!name.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                intent.putExtra("userName", name);
                startActivity(intent);
                finish(); // Close MainActivity
            } else {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
