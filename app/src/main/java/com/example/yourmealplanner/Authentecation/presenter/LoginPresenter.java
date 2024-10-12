package com.example.yourmealplanner.Authentecation.presenter;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.lifecycle.LifecycleOwner;

import com.example.yourmealplanner.Authentecation.model.PasswordUtils;
import com.example.yourmealplanner.Authentecation.model.User;
import com.example.yourmealplanner.Authentecation.model.UserDatabase;
import com.example.yourmealplanner.Authentecation.view.LoginView;


public class LoginPresenter {
    private final LoginView view;
    private final UserDatabase userDatabase;
    private Context context;

    public LoginPresenter(LoginView view, UserDatabase userDatabase, Context context) {
        this.view = view;
        this.userDatabase = userDatabase;
        this.context = context;
    }



    public void login(String email, String password) {
        userDatabase.userDao().getUserByEmail(email).observe((LifecycleOwner) view, user -> {
            if (user != null) {
                String hashedInputPassword = PasswordUtils.hashPassword(password, user.getSalt());
                if (constantTimeCompare(hashedInputPassword.getBytes(), user.getHashedPassword().getBytes())) {
                    view.onLoginSuccess(user.getUserId());
                } else {
                    view.onLoginFailed();
                }
            } else {
                view.onLoginFailed();
            }
        });
    }

    public static boolean constantTimeCompare(byte[] a, byte[] b) {


        int result = a.length ^ b.length;

        for (int i = 0; i < Math.min(a.length, b.length); i++) {
            result |= a[i] ^ b[i];
        }

        for (int i = b.length; i < a.length; i++) {
            result |= a[i];
        }

        for (int i = a.length; i < b.length; i++) {
            result |= b[i];
        }

        return result == 0;
    }

}

