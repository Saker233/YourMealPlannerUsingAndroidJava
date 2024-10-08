package com.example.yourmealplanner.Authentecation.view;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yourmealplanner.MainActivity;
import com.example.yourmealplanner.Network.NetworkConnection;
import com.example.yourmealplanner.R;
import com.example.yourmealplanner.Authentecation.model.UserDatabase;
import com.example.yourmealplanner.Authentecation.presenter.LoginPresenter;
import com.google.android.material.snackbar.Snackbar;

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
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        if (!NetworkConnection.isConnected(this)) {


            showNoConnectionSnackbar();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NetworkConnection.isConnected(LoginActivity.this)) {
                    showNoConnectionSnackbar();
                    Toast.makeText(LoginActivity.this, "No internet connection. You can only access the Guest option.", Toast.LENGTH_SHORT).show();
                } else {
                    String email = emailInput.getText().toString().trim();
                    String password = passwordInput.getText().toString().trim();
                    presenter.login(email, password);
                }
            }
        });



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkConnection.isConnected(LoginActivity.this)) {
                    showNoConnectionSnackbar();
                    Toast.makeText(LoginActivity.this, "No internet connection. You can only access the Guest option.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            }
        });

        txtGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.putBoolean("isGuest", true);
                editor.apply();


                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean isUserLoggedIn() {
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isGuest = preferences.getBoolean("isGuest", false);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);
        return isLoggedIn || isGuest;
    }


    private void showNoConnectionSnackbar() {
        View rootView = findViewById(android.R.id.content);

        Snackbar snackbar = Snackbar.make(rootView,
                        "No internet connection. You can only access the Guest option.", Snackbar.LENGTH_INDEFINITE)
                .setAction("Settings", v -> {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                })
                .setActionTextColor(getResources().getColor(R.color.white));

        snackbar.setAction("Dismiss", v -> snackbar.dismiss());

        snackbar.show();
    }



    @Override
    public void onLoginSuccess(String userId) {
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putBoolean("isGuest", false);
        editor.putString("userId", userId);
        Log.d("LoginActivity", "User ID saved: " + userId);

        editor.apply();

        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onLoginFailed() {

        Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();

    }
}

