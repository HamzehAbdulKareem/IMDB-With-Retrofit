package com.example.retrofitwithimdb.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.retrofitwithimdb.R;
import com.example.retrofitwithimdb.models.LoginCallbackInterface;
import com.example.retrofitwithimdb.models.MainActivity;
import com.example.retrofitwithimdb.presenter.LoginPresenter;
import com.google.firebase.auth.FirebaseAuth;

public class UserLoginActivity extends AppCompatActivity implements LoginViewInterface {
    Button loginButton;
    Button signupButton, logoutButton;
    EditText loginEmail, loginPassword;
    String email, password;
    FirebaseAuth firebaseAuth;
    LoginPresenter loginPresenter;
    LoginCallbackInterface repositryInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        loginPresenter = new LoginPresenter(this,UserLoginActivity.this);

        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);
        loginEmail = findViewById(R.id.loginEmailText);
        loginPassword = findViewById(R.id.loginPasswordText);
        logoutButton = findViewById(R.id.logoutButton);
//        loginPresenter.setContext(UserLoginActivity.this);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(UserLoginActivity.this, UserSignupActivity.class));

            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = loginEmail.getText().toString();
                password = loginPassword.getText().toString();
                loginPresenter.handleLogin(email, password);

            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.logOut();
        }
    });
}

private void goToMain(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
}

    @Override
    public void showMain() {
        goToMain();
    }
}

