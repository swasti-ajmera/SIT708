package com.example.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView tvQuestion, tvProgress;
    private Button[] optionButtons = new Button[4];
    private Button btnSubmit;
    private ProgressBar progressBar;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int selectedOptionIndex = -1;
    private int score = 0;
    private String userName;
    private boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        userName = getIntent().getStringExtra("userName");

        tvQuestion = findViewById(R.id.tv_question);
        tvProgress = findViewById(R.id.tv_progress);
        progressBar = findViewById(R.id.progress_bar);
        optionButtons[0] = findViewById(R.id.btn_option1);
        optionButtons[1] = findViewById(R.id.btn_option2);
        optionButtons[2] = findViewById(R.id.btn_option3);
        optionButtons[3] = findViewById(R.id.btn_option4);
        btnSubmit = findViewById(R.id.btn_submit);

        questions = getQuestions();
        loadQuestion();

        for (int i = 0; i < optionButtons.length; i++) {
            final int index = i;
            optionButtons[i].setOnClickListener(v -> {
                if (!answered) {
                    selectedOptionIndex = index;
                    highlightSelectedOption();
                }
            });
        }

        btnSubmit.setOnClickListener(v -> {
            if (!answered && selectedOptionIndex != -1) {
                checkAnswer();
                answered = true;
                btnSubmit.setText(currentQuestionIndex == questions.size() - 1 ? "Finish" : "Next");
            } else if (answered) {
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    loadQuestion();
                    answered = false;
                    selectedOptionIndex = -1;
                    btnSubmit.setText("Submit");
                } else {
                    // Quiz finished
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra("score", score);
                    intent.putExtra("total", questions.size());
                    intent.putExtra("userName", userName);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void loadQuestion() {
        Question q = questions.get(currentQuestionIndex);
        tvQuestion.setText(q.getQuestion());
        String[] options = q.getOptions();
        for (int i = 0; i < options.length; i++) {
            optionButtons[i].setText(options[i]);
            optionButtons[i].setBackgroundColor(Color.LTGRAY); // reset color
        }
        tvProgress.setText("Question " + (currentQuestionIndex + 1) + "/" + questions.size());
        progressBar.setProgress((int) (((currentQuestionIndex + 1) / (float) questions.size()) * 100));
    }

    private void highlightSelectedOption() {
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setBackgroundColor(i == selectedOptionIndex ? Color.YELLOW : Color.LTGRAY);
        }
    }

    private void checkAnswer() {
        int correct = questions.get(currentQuestionIndex).getCorrectAnswerIndex();
        if (selectedOptionIndex == correct) {
            optionButtons[correct].setBackgroundColor(Color.GREEN);
            score++;
        } else {
            optionButtons[selectedOptionIndex].setBackgroundColor(Color.RED);
            optionButtons[correct].setBackgroundColor(Color.GREEN);
        }
    }

    private List<Question> getQuestions() {
        List<Question> questionList = new ArrayList<>();
        questionList.add(new Question("What is the capital of France?",
                new String[]{"London", "Berlin", "Paris", "Madrid"}, 2));
        questionList.add(new Question("Which planet is known as the Red Planet?",
                new String[]{"Earth", "Venus", "Jupiter", "Mars"}, 3));
        questionList.add(new Question("What is 2 + 2?",
                new String[]{"3", "4", "5", "6"}, 1));
        questionList.add(new Question("What is the capital of India?",
                new String[]{"New Delhi", "Ahmedabad", "Mumbai", "Jaipur"}, 0));
        return questionList;
    }
}
