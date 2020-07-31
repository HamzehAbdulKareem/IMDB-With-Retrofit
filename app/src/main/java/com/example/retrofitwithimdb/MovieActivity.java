package com.example.retrofitwithimdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        sendRequest();
    }



    public void sendRequest(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonHolder jsonHolder = retrofit.create(JsonHolder.class);

        Call<MovieClass> call = jsonHolder.getMovies();

        call.enqueue(new Callback<MovieClass>() {
            @Override
            public void onResponse(Call<MovieClass> call, Response<MovieClass> response) {
                if (!response.isSuccessful()){

                    return;
                }
                callFragment(response);
                Log.i("res",response.body().toString());

            }
            @Override
            public void onFailure(Call<MovieClass> call, Throwable t) {
                Log.i("res",t.toString());
            }
        });
    }


    public void callFragment(Response<MovieClass> response){
        MovieClass responseResult = response.body();
        MoviesFragment moviesFragment = MoviesFragment.newInstance(responseResult);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainAct,moviesFragment)
                .commit();
//        constraintLayout.setVisibility(View.INVISIBLE);


    }
}