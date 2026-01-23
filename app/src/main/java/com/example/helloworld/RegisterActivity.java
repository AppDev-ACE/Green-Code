package com.example.helloworld;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        TextInputEditText emailEt = findViewById(R.id.etEmail);
        TextInputEditText passwordEt = findViewById(R.id.etPassword);
        Button signupBtn = findViewById(R.id.btnSignup);
        TextView loginTxt = findViewById(R.id.tvLogin);


        FirebaseAuth auth = FirebaseAuth.getInstance();


        signupBtn.setOnClickListener(v -> {
            String email = emailEt.getText().toString();
            String password = passwordEt.getText().toString();

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(
                                    RegisterActivity.this,
                                    "Account created. Please login.",
                                    Toast.LENGTH_SHORT
                            ).show();
                            finish(); // go back to Login
                        } else {
                            Toast.makeText(
                                    RegisterActivity.this,
                                    task.getException().getMessage(),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    });
        });


        loginTxt.setOnClickListener(v -> finish());
    }
}