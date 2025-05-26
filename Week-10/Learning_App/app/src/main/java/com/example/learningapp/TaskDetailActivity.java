package com.example.learningapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TaskDetailActivity extends AppCompatActivity {

    LinearLayout questionContainer;
    Button submitBtn;
    TextView taskNameText;

    List<String> aiQuestions = new ArrayList<>();
    List<String> aiAnswers = new ArrayList<>();
    List<String> userAnswers = new ArrayList<>();
    List<List<String>> allOptions = new ArrayList<>();

    String taskName = "Sample Task";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        questionContainer = findViewById(R.id.questionContainer);
        submitBtn = findViewById(R.id.submitBtn);
        taskNameText = findViewById(R.id.taskName);
        taskNameText.setText(taskName);

        fetchQuizQuestions();

        submitBtn.setOnClickListener(v -> {
            collectUserAnswers();

            OkHttpClient client = new OkHttpClient();
            String username = "swasti"; // Replace with dynamic username if needed

            for (int i = 0; i < aiQuestions.size(); i++) {
                JSONObject json = new JSONObject();
                try {
                    json.put("username", username);
                    json.put("question", aiQuestions.get(i));
                    json.put("selected", userAnswers.get(i));
                    json.put("correct", aiAnswers.get(i));
                    json.put("options", new JSONArray(allOptions.get(i)));
                } catch (JSONException e) {
                    Log.e("SubmitAnswer", "JSON error: " + e.getMessage());
                    continue;
                }

                Request request = new Request.Builder()
                        .url("http://10.0.2.2:5000/submitAnswer")
                        .post(RequestBody.create(
                                json.toString(),
                                MediaType.parse("application/json")
                        ))
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("SubmitAnswer", "Failed: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        if (!response.isSuccessful()) {
                            Log.e("SubmitAnswer", "Failed response: " + response.code());
                        } else {
                            Log.d("SubmitAnswer", "Submitted successfully");
                        }
                    }
                });
            }

            Intent intent = new Intent(TaskDetailActivity.this, ResultsActivity.class);
            intent.putExtra("questions", aiQuestions.toArray(new String[0]));
            intent.putExtra("answers", aiAnswers.toArray(new String[0]));
            intent.putExtra("user_answers", userAnswers.toArray(new String[0]));
            startActivity(intent);
        });
    }

    private void fetchQuizQuestions() {
        String url = "http://10.0.2.2:5000/getQuiz?topic=movies";

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TaskDetail", "API Request Failed: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e("TaskDetail", "Unexpected response: " + response);
                    return;
                }

                String jsonString = response.body().string();

                runOnUiThread(() -> {
                    try {
                        JSONObject responseObject = new JSONObject(jsonString);
                        JSONArray questionsArray = responseObject.getJSONArray("quiz");

                        for (int i = 0; i < questionsArray.length(); i++) {
                            JSONObject qObj = questionsArray.getJSONObject(i);
                            String question = qObj.getString("question");
                            String answer = qObj.getString("correct_answer"); // This is a letter like "B"
                            JSONArray optionsArray = qObj.getJSONArray("options");

                            aiQuestions.add(question);
                            aiAnswers.add(answer);
                            userAnswers.add(""); // Initialize empty

                            List<String> optionsList = new ArrayList<>();
                            View card = getLayoutInflater().inflate(R.layout.question_card, null);
                            ((TextView) card.findViewById(R.id.questionText)).setText(question);

                            RadioGroup radioGroup = card.findViewById(R.id.optionsGroup);
                            String[] letters = {"A", "B", "C", "D"};

                            for (int j = 0; j < optionsArray.length(); j++) {
                                String option = optionsArray.getString(j);
                                optionsList.add(option);
                                RadioButton radioButton = new RadioButton(TaskDetailActivity.this);
                                radioButton.setText(option);
                                radioButton.setTextColor(getResources().getColor(android.R.color.white));
                                radioGroup.addView(radioButton);
                            }

                            allOptions.add(optionsList);

                            int finalI = i;
                            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                                int selectedIndex = radioGroup.indexOfChild(group.findViewById(checkedId));
                                if (selectedIndex != -1 && selectedIndex < letters.length) {
                                    userAnswers.set(finalI, letters[selectedIndex]); // ✅ Store letter
                                }
                            });

                            questionContainer.addView(card);
                        }
                    } catch (JSONException e) {
                        Log.e("TaskDetail", "JSON parsing error: " + e.getMessage());
                    }
                });
            }
        });
    }

    private void collectUserAnswers() {
        for (int i = 0; i < aiQuestions.size(); i++) {
            if (userAnswers.size() <= i || userAnswers.get(i) == null || userAnswers.get(i).isEmpty()) {
                userAnswers.set(i, "");
            }
        }
    }
}
