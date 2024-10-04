package com.example.yourmealplanner.Authentecation.view;



import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yourmealplanner.Authentecation.model.PasswordUtils;
import com.example.yourmealplanner.Authentecation.model.User;
import com.example.yourmealplanner.R;
import com.example.yourmealplanner.Authentecation.model.UserDatabase;
import com.example.yourmealplanner.Authentecation.presenter.RegisterPresenter;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity implements RegisterView {
    private EditText edtTxtRegEmail;
    private EditText edtTxtRegPass;
    private EditText edtTxtRegConPass;
    private ImageButton btnRegRegister;
    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        edtTxtRegEmail = findViewById(R.id.edtTxtRegEmail);
        edtTxtRegPass = findViewById(R.id.edtTxtRegPass);
        edtTxtRegConPass = findViewById(R.id.edtTxtRegConPass);
        btnRegRegister = findViewById(R.id.btnRegRegister);

        UserDatabase userDatabase = UserDatabase.getDatabase(this);
        presenter = new RegisterPresenter(this, userDatabase);

        btnRegRegister.setOnClickListener(view -> {
            String inputEmail  = edtTxtRegEmail.getText().toString().trim();
            String inputPassword  = edtTxtRegPass.getText().toString().trim();
            String confirmPassword = edtTxtRegConPass.getText().toString().trim();

            if (inputPassword.equals(confirmPassword)) {
                byte[] salt = PasswordUtils.generateSalt();
                String hashedPassword = PasswordUtils.hashPassword(inputPassword, salt);

                User user = new User();
                user.setUserId(UUID.randomUUID().toString());
                user.setEmail(inputEmail);
                user.setHashedPassword(hashedPassword);
                user.setSalt(salt);

                presenter.register(user);
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRegisterSuccess() {
        runOnUiThread(() -> {
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @Override
    public void onRegisterFailed() {
        Toast.makeText(this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
    }
}

