package com.example.learningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {
    LinearLayout resultsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        resultsContainer = findViewById(R.id.resultsContainer);

        // Retrieve data passed from TaskDetailActivity
        String[] questions = getIntent().getStringArrayExtra("questions");
        String[] aiAnswers = getIntent().getStringArrayExtra("answers");
        String[] userAnswers = getIntent().getStringArrayExtra("user_answers");

        // If data is missing or incorrect, set default values
        if (questions == null || aiAnswers == null || userAnswers == null || questions.length != aiAnswers.length) {
            questions = new String[]{"No questions available"};
            aiAnswers = new String[]{"N/A"};
            userAnswers = new String[]{"N/A"};
        }

        // Loop through questions and answers to display results
        for (int i = 0; i < questions.length; i++) {
            View card = getLayoutInflater().inflate(R.layout.result_card, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 0, 0, 24);
            card.setLayoutParams(layoutParams);

            TextView questionText = card.findViewById(R.id.questionText);
            TextView aiAnswerText = card.findViewById(R.id.correctAnswerText);
            TextView userAnswerText = card.findViewById(R.id.userAnswerText);
            TextView resultText = card.findViewById(R.id.resultText);

            // Set the question and answers
            questionText.setText("Q" + (i + 1) + ": " + questions[i]);
            aiAnswerText.setText("AI Answer: " + aiAnswers[i]);
            userAnswerText.setText("Your Answer: " + (userAnswers[i].isEmpty() ? "No answer" : userAnswers[i]));

            // Compare the user's answer with the correct answer
            if (userAnswers[i].equals(aiAnswers[i])) {
                resultText.setText("Correct");
                resultText.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            } else {
                resultText.setText("Incorrect");
                resultText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }

            resultsContainer.addView(card);
        }

        // Set continue button to navigate back to the DashboardActivity
        findViewById(R.id.continueBtn).setOnClickListener(v -> {
            Intent intent = new Intent(ResultsActivity.this, DashboardActivity.class);
            startActivity(intent);
        });
    }
}
