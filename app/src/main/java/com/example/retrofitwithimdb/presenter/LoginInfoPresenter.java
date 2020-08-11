package com.example.retrofitwithimdb.presenter;

import android.content.Context;

public interface LoginInfoPresenter {
    void handleLogin(String email, String password);
    void logOut();

}
