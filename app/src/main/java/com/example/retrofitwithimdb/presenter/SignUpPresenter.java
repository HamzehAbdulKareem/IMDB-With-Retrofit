package com.example.retrofitwithimdb.presenter;

import android.content.Context;

import com.example.retrofitwithimdb.models.LoginCallbackInterface;
import com.example.retrofitwithimdb.models.SignupCallBackInterface;
import com.example.retrofitwithimdb.models.SignupRepositry;
import com.example.retrofitwithimdb.view.signupViewInterface;

public class SignUpPresenter implements SignUpPresenterInterface, SignupCallBackInterface {
    Context context;
    SignupRepositry signupRepositry;
    SignupCallBackInterface callbackInterface;
    com.example.retrofitwithimdb.view.signupViewInterface signupViewInterface;

    public SignUpPresenter(Context context, signupViewInterface signupViewInterface){
        this.context = context;
        this.signupViewInterface = signupViewInterface;
    }


    @Override
    public void setupAuth(String email, String password) {
        signupRepositry = new SignupRepositry(context,this);
        signupRepositry.setupAuth(email,password);
    }

    @Override
    public void sendToFirestore(String firstName, String lastName, String age) {
        signupRepositry = new SignupRepositry(context,this);
        signupRepositry.addToFireStore(firstName,lastName,age);
    }

    @Override
    public void onSignUpSuccess() {
        signupViewInterface.showMainFromSignup();
    }
}
