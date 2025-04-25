package com.example.itubeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Set;
import java.util.HashSet;

public class HomeActivity extends AppCompatActivity {

    EditText etYoutubeUrl;
    Button btnPlay, btnAddToPlaylist, btnMyPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        etYoutubeUrl = findViewById(R.id.etYoutubeUrl);
        btnPlay = findViewById(R.id.btnPlay);
        btnAddToPlaylist = findViewById(R.id.btnAddToPlaylist);
        btnMyPlaylist = findViewById(R.id.btnMyPlaylist);

        btnPlay.setOnClickListener(v -> {
            String url = etYoutubeUrl.getText().toString().trim();

            if (url.isEmpty()) {
                Toast.makeText(HomeActivity.this, "Please enter a YouTube URL", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(HomeActivity.this, VideoPlayerActivity.class);
                intent.putExtra("videoUrl", url); // pass raw URL
                startActivity(intent);
            }
        });

        btnAddToPlaylist.setOnClickListener(v -> {
            String url = etYoutubeUrl.getText().toString().trim();

            if (url.isEmpty()) {
                Toast.makeText(HomeActivity.this, "Please enter a YouTube URL", Toast.LENGTH_SHORT).show();
            } else {
                Set<String> savedLinks = getSharedPreferences("playlist", MODE_PRIVATE)
                        .getStringSet("links", new HashSet<>());

                Set<String> updatedLinks = new HashSet<>(savedLinks);
                updatedLinks.add(url); // avoid duplicates

                getSharedPreferences("playlist", MODE_PRIVATE)
                        .edit()
                        .putStringSet("links", updatedLinks)
                        .apply();

                Toast.makeText(this, "Added to Playlist", Toast.LENGTH_SHORT).show();

            }
        });

        btnMyPlaylist.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, PlaylistActivity.class);
            startActivity(intent);
        });
    }
}
