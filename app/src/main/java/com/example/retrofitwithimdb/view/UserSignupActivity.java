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
    import com.example.retrofitwithimdb.models.LoginCallbackInterface;
    import com.example.retrofitwithimdb.models.MainActivity;
    import com.example.retrofitwithimdb.models.SignupCallBackInterface;
    import com.example.retrofitwithimdb.presenter.SignUpPresenter;
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

    public class UserSignupActivity extends AppCompatActivity implements signupViewInterface {
        private FirebaseAuth fAuth;
        private EditText firstNameText;
        private EditText lastNameText;
        private EditText ageText;
        private EditText emailText;
        private EditText passwordText;

        private Button signupB;

        String fName,lName,age,email,password;

        SignUpPresenter presenter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user_signup);


    firstNameText = findViewById(R.id.firstNameText);
    emailText = findViewById(R.id.emailText);
    passwordText = findViewById(R.id.passwordText);
    lastNameText = findViewById(R.id.lastNameText);
    ageText = findViewById(R.id.ageText);

        presenter =new SignUpPresenter(this,UserSignupActivity.this);

    signupB = findViewById(R.id.signup);

    signupB.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            email = emailText.getText().toString();
            password = passwordText.getText().toString();

            fName = firstNameText.getText().toString();
            lName = lastNameText.getText().toString();
            age = ageText.getText().toString();
            presenter.setupAuth(email,password);
          presenter.sendToFirestore(fName,lName,age);



        }
    });


        }








        @Override
        public void showMainFromSignup() {
            Intent intent = new Intent(UserSignupActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }