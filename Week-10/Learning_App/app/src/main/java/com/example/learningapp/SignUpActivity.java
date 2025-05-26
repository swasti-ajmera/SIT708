package com.example.learningapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpActivity extends AppCompatActivity {
    EditText username, email, confirmEmail, password, confirmPassword, phone;
    Button createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        confirmEmail = findViewById(R.id.confirm_email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        phone = findViewById(R.id.phone);
        createAccount = findViewById(R.id.create_account);

        createAccount.setOnClickListener(v -> {
            String uname = username.getText().toString();
            Intent intent = new Intent(SignUpActivity.this, InterestsActivity.class);
            intent.putExtra("username", uname);
            startActivity(intent);
        });
    }
}
