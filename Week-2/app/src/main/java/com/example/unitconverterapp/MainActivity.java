package com.example.unitconverterapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Spinner spinnerFrom, spinnerTo;
    EditText editTextValue;
    Button btnConvert;
    TextView textResult;

    String[] units = {"Inch", "Foot", "Yard", "Mile", "Pound", "Ounce", "Ton", "Celsius", "Fahrenheit", "Kelvin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        editTextValue = findViewById(R.id.editTextValue);
        btnConvert = findViewById(R.id.btnConvert);
        textResult = findViewById(R.id.textResult);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        btnConvert.setOnClickListener(v -> convert());
    }

    private void convert() {
        // Get the selected units and input value
        String fromUnit = spinnerFrom.getSelectedItem().toString();
        String toUnit = spinnerTo.getSelectedItem().toString();
        String inputValue = editTextValue.getText().toString();

        // Check if the input value is empty
        if (inputValue.isEmpty()) {
            textResult.setText("Please enter a value.");
            return;
        }

        // Try parsing the input value as a double
        double value;
        try {
            value = Double.parseDouble(inputValue);
        } catch (NumberFormatException e) {
            textResult.setText("Invalid input. Please enter a valid number.");
            return;
        }

        // Check if the source and destination units are the same
        if (fromUnit.equals(toUnit)) {
            textResult.setText("Source and destination units are the same. No conversion needed.");
            return;
        }

        // Perform the conversion
        double result = 0;
        boolean validConversion = true;

        if (fromUnit.equals("Inch") && toUnit.equals("Foot")) result = value / 12;
        else if (fromUnit.equals("Foot") && toUnit.equals("Inch")) result = value * 12;
        else if (fromUnit.equals("Celsius") && toUnit.equals("Fahrenheit")) result = (value * 1.8) + 32;
        else if (fromUnit.equals("Fahrenheit") && toUnit.equals("Celsius")) result = (value - 32) / 1.8;
        else if (fromUnit.equals("Celsius") && toUnit.equals("Kelvin")) result = value + 273.15;
        else if (fromUnit.equals("Kelvin") && toUnit.equals("Celsius")) result = value - 273.15;
        else {
            validConversion = false;
        }

        // Handle invalid conversion
        if (!validConversion) {
            textResult.setText("Invalid conversion between the selected units.");
        } else {
            // Display the converted value
            textResult.setText("Converted Value: " + result);
        }
    }
}
