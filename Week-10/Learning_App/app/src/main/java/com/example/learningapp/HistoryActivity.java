package com.example.learningapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView historyRecyclerView;
    HistoryAdapter adapter;
    List<HistoryItem> historyList = new ArrayList<>();
    String username = "swasti"; // Replace with dynamic username if needed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyRecyclerView = findViewById(R.id.historyRecyclerView);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new HistoryAdapter(historyList);
        historyRecyclerView.setAdapter(adapter);

        fetchHistory();
    }

    private void fetchHistory() {
        String url = "http://10.0.2.2:5000/getHistory?username=swasti";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray historyArray = response.getJSONArray("history");

                        for (int i = 0; i < historyArray.length(); i++) {
                            JSONObject obj = historyArray.getJSONObject(i);
                            String question = obj.getString("question");
                            String selected = obj.getString("selected");
                            String correct = obj.getString("correct");

                            JSONArray optionsJson = obj.getJSONArray("options");
                            List<String> options = new ArrayList<>();
                            for (int j = 0; j < optionsJson.length(); j++) {
                                options.add(optionsJson.getString(j));
                            }

                            historyList.add(new HistoryItem(question, options, selected, correct));
                        }

                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Failed to parse history", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Server Error", Toast.LENGTH_SHORT).show();
                });

        queue.add(request);
    }
}
