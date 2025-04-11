package com.example.calculatorapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CalculatorActivity extends AppCompatActivity {

    EditText input1, input2;
    Button btnAdd, btnSubtract;
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        btnAdd = findViewById(R.id.btnAdd);
        btnSubtract = findViewById(R.id.btnSubtract);
        resultText = findViewById(R.id.resultText);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate('+');
            }
        });

        btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate('-');
            }
        });
    }

    private void calculate(char operator) {
        String num1Str = input1.getText().toString();
        String num2Str = input2.getText().toString();

        if (num1Str.isEmpty() || num2Str.isEmpty()) {
            resultText.setText("Please enter both numbers");
            return;
        }

        double num1 = Double.parseDouble(num1Str);
        double num2 = Double.parseDouble(num2Str);
        double result = 0;

        if (operator == '+') {
            result = num1 + num2;
        } else if (operator == '-') {
            result = num1 - num2;
        }

        resultText.setText("Result: " + result);
    }
}
