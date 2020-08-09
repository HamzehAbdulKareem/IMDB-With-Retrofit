package com.example.retrofitwithimdb.models;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import com.example.retrofitwithimdb.view.DetailsFragment;
import com.example.retrofitwithimdb.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity {
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("ID");
//        Toast.makeText(getApplicationContext(),,Toast.LENGTH_SHORT).show();
    getDetails();
    }

    public void getDetails(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonHolder jsonHolder = retrofit.create(JsonHolder.class);

        Call<DetailsClass> call = jsonHolder.getMovieDetails(id);
        call.enqueue(new Callback<DetailsClass>() {
            @Override
            public void onResponse(Call<DetailsClass> call, Response<DetailsClass> response) {
                if (!response.isSuccessful()){
                    Log.i("response :", Integer.toString(response.code()));

                }
                DetailsClass detailsFragment = new DetailsClass();
                DetailsClass responseResult = response.body();
                callMovieDetails(response);
                Log.i("Response",response.body().toString());
            }
            @Override
            public void onFailure(Call<DetailsClass> call, Throwable t) {
                Log.i("Response error",t.toString());
            }
        });
    }


         public void callMovieDetails(Response<DetailsClass> response){
            DetailsClass responseResult = response.body();
            DetailsFragment detailsFragment = DetailsFragment.newInstance(responseResult);
            detailsFragment.newInstance(responseResult);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.aaLayout,detailsFragment)
                    .commit();
//        constraintLayout.setVisibility(View.INVISIBLE);


    }
}