package com.example.newsapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

public class NewsDetailActivity extends AppCompatActivity {

    ImageView newsImage;
    TextView newsTitle, newsDesc;
    RecyclerView relatedRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_news_detail);

        newsImage = findViewById(R.id.newsImage);
        newsTitle = findViewById(R.id.newsTitle);
        newsDesc = findViewById(R.id.newsDesc);
        relatedRecycler = findViewById(R.id.relatedRecyclerView);

        // Get data from intent
        String title = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("desc");
        int imageRes = getIntent().getIntExtra("image", R.drawable.sample1);

        newsImage.setImageResource(imageRes);
        newsTitle.setText(title);
        newsDesc.setText(desc);

        List<NewsItem> related = Arrays.asList(
                new NewsItem("Related 1", "Something", R.drawable.sample1),
                new NewsItem("Related 2", "Something", R.drawable.sample2),
                new NewsItem("Related 3", "Something", R.drawable.sample3)
        );

        relatedRecycler.setLayoutManager(new LinearLayoutManager(this));
        relatedRecycler.setAdapter(new NewsAdapter(related));
    }
}
