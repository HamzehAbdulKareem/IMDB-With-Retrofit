package com.example.retrofitwithimdb.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.retrofitwithimdb.presenter.LoginInfoPresenter;
import com.example.retrofitwithimdb.presenter.LoginPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginRepositry {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Context context;
    LoginCallbackInterface callBack;



    public LoginRepositry(Context context, LoginCallbackInterface callBack){
        this.context = context;
        this.callBack = callBack;
    }

    public void signUp(String email, String password){
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.i("status", "signInWithEmail:success");
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                callBack.onLoginSuccess();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "signInWithEmail:failure", task.getException());
                                Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
        }
        else {
            Toast.makeText((Activity)context, "already signed in", Toast.LENGTH_SHORT).show();
        }
    }


    public void logOut(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth
                .signOut();
        Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show();


    }



}





