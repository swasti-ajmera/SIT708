package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAdvertActivity extends AppCompatActivity {

    RadioGroup radioGroupType;
    RadioButton radioLost, radioFound;
    EditText editName, editPhone, editDescription, editDate, editLocation;
    Button btnSave;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        radioGroupType = findViewById(R.id.radioGroupType);
        radioLost = findViewById(R.id.radioLost);
        radioFound = findViewById(R.id.radioFound);
        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editDescription = findViewById(R.id.editDescription);
        editDate = findViewById(R.id.editDate);
        editLocation = findViewById(R.id.editLocation);
        btnSave = findViewById(R.id.btnSave);

        dbHelper = new DatabaseHelper(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = radioLost.isChecked() ? "Lost" : "Found";
                String name = editName.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();
                String description = editDescription.getText().toString().trim();
                String date = editDate.getText().toString().trim();
                String location = editLocation.getText().toString().trim();

                if (name.isEmpty() || phone.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty()) {
                    Toast.makeText(CreateAdvertActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean inserted = dbHelper.insertItem(type, name, phone, description, date, location);

                if (inserted) {
                    Toast.makeText(CreateAdvertActivity.this, "Advert saved successfully!", Toast.LENGTH_SHORT).show();

                    // ✅ Redirect to ShowItemsActivity
                    Intent intent = new Intent(CreateAdvertActivity.this, ShowItemsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(CreateAdvertActivity.this, "Failed to save advert.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
