package com.example.retrofitwithimdb.presenter;

public interface SignUpPresenterInterface {
    void setupAuth(String email, String password);
    void sendToFirestore(String firstName, String lastName, String age);
}
