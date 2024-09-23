package com.example.yourmealplanner.Authentecation.view;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yourmealplanner.MainActivity;
import com.example.yourmealplanner.R;
import com.example.yourmealplanner.Authentecation.model.UserDatabase;
import com.example.yourmealplanner.Authentecation.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private EditText emailInput;
    private EditText passwordInput;
    private ImageButton btnLogin;
    private LoginPresenter presenter;


    private ImageButton btnRegister;
    private TextView txtGuest;
    private CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);



        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        txtGuest = findViewById(R.id.txtGuest);
        checkBox = findViewById(R.id.checkBox);


        UserDatabase userDatabase = UserDatabase.getDatabase(this);
        presenter = new LoginPresenter(this, userDatabase, this);



        if (isUserLoggedIn()) {

            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            return;
        }

        btnLogin.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            presenter.login(email, password);
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Guest login functionality
        txtGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean isUserLoggedIn() {
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        return preferences.getBoolean("isLoggedIn", false);
    }


    @Override
    public void onLoginSuccess() {

        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onLoginFailed() {

        Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();

    }
}

