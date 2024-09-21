package com.example.yourmealplanner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText edtTxtRegEmail;
    EditText edtTxtRegPass;
    EditText edtTxtRegConPass;

    ImageButton btnRegRegister;
    private SharedPreferences preferences;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register_page);

        edtTxtRegEmail = findViewById(R.id.edtTxtRegEmail);
        edtTxtRegPass = findViewById(R.id.edtTxtRegPass);
        edtTxtRegConPass =  findViewById(R.id.edtTxtRegConPass);

        btnRegRegister = findViewById(R.id.btnRegRegister);


        preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);

        btnRegRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputEmail = edtTxtRegEmail.getText().toString().trim();
                String inputPassword = edtTxtRegPass.getText().toString().trim();
                String inputConfirmPassword = edtTxtRegConPass.getText().toString().trim();


                // Check whether the email or passowrd input fields empty or not
                if (inputEmail.isEmpty() || inputPassword.isEmpty() || inputConfirmPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }


                // check whether the email pattern acceptable or not with regex
                if (!EMAIL_PATTERN.matcher(inputEmail).matches()) {
                    Toast.makeText(RegisterActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                    return;
                }


                // cehck if the password 6 char at least or not
                if (inputPassword.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                    return;
                }

                // check if the password and confiorm passowrd match or not
                if (!inputPassword.equals(inputConfirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }


                // If all the corner cases pass we store the new login credentials into shared pref
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("email", inputEmail);
                editor.putString("password", inputPassword);
                editor.apply();


                Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                // go back again to the login page
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });







    }
}
