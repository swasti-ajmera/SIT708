package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView tvScore, tvCongrats;
    private Button btnRestart, btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvScore = findViewById(R.id.tv_score);
        tvCongrats = findViewById(R.id.tv_congrats);
        btnRestart = findViewById(R.id.btn_restart);
        btnFinish = findViewById(R.id.btn_finish);

        // Get data from intent
        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 0);
        String userName = getIntent().getStringExtra("userName");

        tvCongrats.setText("Congratulations, " + userName + "!");
        tvScore.setText("Your Score: " + score + " / " + total);

        btnRestart.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            intent.putExtra("userName", userName);
            startActivity(intent);
            finish();
        });

        btnFinish.setOnClickListener(v -> finishAffinity()); // closes the app
    }
}
