package com.example.retrofitwithimdb.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retrofitwithimdb.R;
import com.example.retrofitwithimdb.models.MainActivity;
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

public class UserSignupActivity extends AppCompatActivity {
    private FirebaseAuth fAuth;
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText ageText;
    private EditText emailText;
    private EditText passwordText;

    private String email, password, fName, lName, age;
    private Button signupB;
    Map<String,String> infoCollection;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

firstNameText = findViewById(R.id.firstNameText);
emailText = findViewById(R.id.emailText);
passwordText = findViewById(R.id.passwordText);
lastNameText = findViewById(R.id.lastNameText);
ageText = findViewById(R.id.ageText);



signupB = findViewById(R.id.signup);

signupB.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        email = emailText.getText().toString();
        password = passwordText.getText().toString();
        setupAuth();
        addToFirestore();


    }
});


    }

    public void setupAuth(){
        fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(UserSignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(UserSignupActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = fAuth.getCurrentUser();
                            startActivity(new Intent(UserSignupActivity.this, MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("fail", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(UserSignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }


    public void addToFirestore(){
        infoCollection = new HashMap<>();
        fName = firstNameText.getText().toString();
        lName = lastNameText.getText().toString();
        age = ageText.getText().toString();

        infoCollection.put("First name",fName);
        infoCollection.put("Last name",lName);
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