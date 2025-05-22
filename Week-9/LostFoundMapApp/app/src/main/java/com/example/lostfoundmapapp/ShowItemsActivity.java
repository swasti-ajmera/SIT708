package com.example.lostfoundmapapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowItemsActivity extends AppCompatActivity {

    ListView listViewItems;
    DatabaseHelper dbHelper;
    ArrayList<String> itemList;
    ArrayList<Integer> itemIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items);

        listViewItems = findViewById(R.id.listViewItems);
        dbHelper = new DatabaseHelper(this);

        loadItemsFromDatabase();

        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                int itemId = itemIds.get(position);
                Intent intent = new Intent(ShowItemsActivity.this, RemoveItemActivity.class);
                intent.putExtra("ITEM_ID", itemId);
                startActivityForResult(intent, 1); // <-- changed here
            }
        });
    }

    private void loadItemsFromDatabase() {
        Cursor cursor = dbHelper.getAllItems();
        itemList = new ArrayList<>();
        itemIds = new ArrayList<>();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No items found.", Toast.LENGTH_SHORT).show();
            listViewItems.setAdapter(null);
            return;
        }

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String type = cursor.getString(1);
            String name = cursor.getString(2);
            String date = cursor.getString(5);
            String location = cursor.getString(6);
            itemList.add(type + ": " + name + "\nDate: " + date + " | Location: " + location);
            itemIds.add(id);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        listViewItems.setAdapter(adapter);
    }

    // Called when returning from RemoveItemActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            loadItemsFromDatabase(); // refresh list
        }
    }
}
