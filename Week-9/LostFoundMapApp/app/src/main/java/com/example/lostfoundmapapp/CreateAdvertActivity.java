package com.example.lostfoundmapapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.*;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.lostfoundmapapp.DatabaseHelper;
import com.google.android.gms.location.*;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class CreateAdvertActivity extends AppCompatActivity {
    private EditText nameEditText, descriptionEditText, contactEditText, dateEditText;
    private RadioGroup typeRadioGroup;
    private String selectedLocation = "";
    private double selectedLatitude = 0.0;
    private double selectedLongitude = 0.0;
    private FusedLocationProviderClient fusedLocationClient;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        nameEditText = findViewById(R.id.editTextName);
        descriptionEditText = findViewById(R.id.editTextDescription);
        contactEditText = findViewById(R.id.editTextContact);
        dateEditText = findViewById(R.id.editTextDate);
        typeRadioGroup = findViewById(R.id.radioGroupType);

        Button saveButton = findViewById(R.id.buttonSave);
        Button currentLocationButton = findViewById(R.id.buttonCurrentLocation);

        // Init Places API
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyC4lrd09ft3x2TSB8E6TIufkZ4SVg2tJHI");
        }

        // Autocomplete Fragment Setup
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment.setHint("Search location");
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                selectedLocation = place.getName();
                if (place.getLatLng() != null) {
                    selectedLatitude = place.getLatLng().latitude;
                    selectedLongitude = place.getLatLng().longitude;
                }
            }

            @Override
            public void onError(@NonNull com.google.android.gms.common.api.Status status) {
                Toast.makeText(CreateAdvertActivity.this, "Error: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Current Location Setup
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        currentLocationButton.setOnClickListener(v -> getCurrentLocation());

        // Save Button
        saveButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();
            String contact = contactEditText.getText().toString().trim();
            String date = dateEditText.getText().toString().trim();

            int checkedRadioId = typeRadioGroup.getCheckedRadioButtonId();
            if (checkedRadioId == -1) {
                Toast.makeText(this, "Please select Lost or Found", Toast.LENGTH_SHORT).show();
                return;
            }
            String type = ((RadioButton) findViewById(checkedRadioId)).getText().toString();

            if (name.isEmpty() || description.isEmpty() || contact.isEmpty() || selectedLocation.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseHelper db = new DatabaseHelper(this);
            boolean inserted = db.insertItem(type, name, contact, description, date, selectedLocation, selectedLatitude, selectedLongitude);
            if (inserted) {
                Toast.makeText(this, "Ad Saved!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Save failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                selectedLatitude = location.getLatitude();
                selectedLongitude = location.getLongitude();
                selectedLocation = "Lat: " + selectedLatitude + ", Lng: " + selectedLongitude;
                Toast.makeText(this, "Location set from GPS", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }
}
