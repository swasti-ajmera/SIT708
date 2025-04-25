package com.example.itubeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PlaylistActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> playlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        listView = findViewById(R.id.listViewPlaylist);

        // Retrieve saved playlist from SharedPreferences
        playlist = new ArrayList<>(getSharedPreferences("playlist", MODE_PRIVATE)
                .getStringSet("links", new HashSet<>()));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                playlist
        );
        listView.setAdapter(adapter);

        // On item click, open VideoPlayerActivity
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedUrl = playlist.get(position);
            Intent intent = new Intent(PlaylistActivity.this, VideoPlayerActivity.class);
            intent.putExtra("videoUrl", selectedUrl);
            startActivity(intent);
        });
    }
}
