package com.example.yourmealplanner.Authentecation.presenter;



import com.example.yourmealplanner.Authentecation.model.User;
import com.example.yourmealplanner.Authentecation.model.UserDatabase;
import com.example.yourmealplanner.Authentecation.view.RegisterView;

public class RegisterPresenter {
    private final RegisterView view;
    private final UserDatabase userDatabase;

    public RegisterPresenter(RegisterView view, UserDatabase userDatabase) {
        this.view = view;
        this.userDatabase = userDatabase;
    }

    public void register(User user) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    userDatabase.userDao().insert(user);
                    view.onRegisterSuccess();
                } catch (Exception e) {
                    view.onRegisterFailed();
                }
            }
        }).start();

    }
}

