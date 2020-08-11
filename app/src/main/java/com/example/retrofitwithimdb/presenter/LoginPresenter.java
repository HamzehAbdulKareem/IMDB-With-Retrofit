package com.example.retrofitwithimdb.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.retrofitwithimdb.models.LoginCallbackInterface;
import com.example.retrofitwithimdb.models.LoginRepositry;
import com.example.retrofitwithimdb.models.MainActivity;
import com.example.retrofitwithimdb.presenter.LoginInfoPresenter;
import com.example.retrofitwithimdb.view.LoginViewInterface;
import com.example.retrofitwithimdb.view.UserLoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenter implements LoginInfoPresenter, LoginCallbackInterface {
    LoginRepositry loginRepositry;
    Context context;
    LoginInfoPresenter loginInfoPresenter;
    LoginViewInterface viewInterface;

    public LoginPresenter(LoginViewInterface viewInterface, Context context) {
        this.viewInterface = viewInterface;
        this.context = context;
    }

    @Override
    public void handleLogin(String email, String password) {
        loginRepositry = new LoginRepositry(context, this);
        loginRepositry.signUp(email, password);
    }

    @Override
    public void logOut() {
        loginRepositry = new LoginRepositry(context, this);
        loginRepositry.logOut();
    }


    @Override
    public void onLoginSuccess() {
        viewInterface.showMain();
    }
}
