package com.example.retrofitwithimdb.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.retrofitwithimdb.view.UserSignupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupRepositry {
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    Context context;
    SignupCallBackInterface callback;
    Map<String,String> infoCollection;
    public SignupRepositry(Context context,  SignupCallBackInterface callBack){
        this.context = context;
        this.callback = callBack;
    }


    public void setupAuth(String email, String password){
        fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText((Activity)context, "User Created", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = fAuth.getCurrentUser();
                            callback.onSignUpSuccess();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("fail", "createUserWithEmail:failure", task.getException());
                            Toast.makeText((Activity)context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    public void addToFireStore(String firstName, String lastName, String age){

        infoCollection = new HashMap<>();


        infoCollection.put("First name",firstName);
        infoCollection.put("Last name",lastName);
        infoCollection.put("Age",age);
        fStore.collection("Users").document(fAuth.getUid())    .set(infoCollection)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }
                });

    }
}
