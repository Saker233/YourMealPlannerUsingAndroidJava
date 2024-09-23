package com.example.yourmealplanner.Authentecation.presenter;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.lifecycle.LifecycleOwner;

import com.example.yourmealplanner.Authentecation.model.PasswordUtils;
import com.example.yourmealplanner.Authentecation.model.User;
import com.example.yourmealplanner.Authentecation.model.UserDatabase;
import com.example.yourmealplanner.Authentecation.view.LoginView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginPresenter {
    private final LoginView view;
    private final UserDatabase userDatabase;
    private Context context;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public LoginPresenter(LoginView view, UserDatabase userDatabase, Context context) {
        this.view = view;
        this.userDatabase = userDatabase;
        this.context = context;
    }



    public void login(String email, String password) {
        userDatabase.userDao().getUserByEmail(email).observe((LifecycleOwner) view, user -> {
            if (user != null) {
                // Hash the provided password with the retrieved salt
                String hashedInputPassword = PasswordUtils.hashPassword(password, user.getSalt());
                if (hashedInputPassword.equals(user.getHashedPassword())) {
                    // Login successful
                    // Update SharedPreferences and notify the view...
                    view.onLoginSuccess();
                } else {
                    // Login failed
                    view.onLoginFailed();
                }
            } else {
                // Login failed
                view.onLoginFailed();
            }
        });
    }
}

