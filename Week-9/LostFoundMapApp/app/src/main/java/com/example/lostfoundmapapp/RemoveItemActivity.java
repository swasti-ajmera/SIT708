package com.example.lostfoundmapapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RemoveItemActivity extends AppCompatActivity {

    TextView textItemName, textDays, textLocation;
    Button btnRemove;
    DatabaseHelper dbHelper;
    int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_item);

        textItemName = findViewById(R.id.textItemName);
        textDays = findViewById(R.id.textDays);
        textLocation = findViewById(R.id.textLocation);
        btnRemove = findViewById(R.id.btnRemove);

        dbHelper = new DatabaseHelper(this);
        itemId = getIntent().getIntExtra("ITEM_ID", -1);

        if (itemId == -1) {
            Toast.makeText(this, "Error: Item not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadItemDetails();

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteItem(itemId);
                Toast.makeText(RemoveItemActivity.this, "Item removed", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK); // <-- notify previous activity
                finish(); // Go back
            }
        });
    }

    private void loadItemDetails() {
        Cursor cursor = dbHelper.getItemById(itemId);
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(2); // name
            String dateStr = cursor.getString(5); // date
            String location = cursor.getString(6); // location

            textItemName.setText("Item: " + name);
            textLocation.setText("Location: " + location);
            textDays.setText("Days since reported: " + calculateDaysSince(dateStr));
        } else {
            Toast.makeText(this, "Item not found.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private long calculateDaysSince(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date reportedDate = sdf.parse(dateStr);
            Date today = new Date();
            long diffMillis = today.getTime() - reportedDate.getTime();
            return TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            return -1;
        }
    }
}
