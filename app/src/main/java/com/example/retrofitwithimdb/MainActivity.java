package com.example.retrofitwithimdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.material.tabs.TabLayout;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    PagerAdapter pagerAdapter;

    @Override
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(LocHelper.onAttach(newBase,"en"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.changeLanguageButton:
            if (LocHelper.getPersistedData(MainActivity.this,"rr").equals("en")){

                Context context = LocHelper.setLocale(MainActivity.this,"ar");
                Resources resources = context.getResources();
                finish();
                startActivity(getIntent());

            }
            else {

                Context context = LocHelper.setLocale(MainActivity.this,"en");
                Resources resources = context.getResources();
                finish();
                startActivity(getIntent());

            }            return(true);
       
    }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        LocHelper.onAttach(this);
        setContentView(R.layout.activity_main);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),this);

         getMostPopular();

        getTopRated();

        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);

        setupTabLayout();
    }




    public void getMostPopular(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonHolder jsonHolder = retrofit.create(JsonHolder.class);

        Call<MovieClass> call = jsonHolder.getPopular();

        call.enqueue(new Callback<MovieClass>() {
            @Override
            public void onResponse(Call<MovieClass> call, Response<MovieClass> response) {
                if (!response.isSuccessful()){

                    return;
                }
                pagerAdapter.setMostPopularResults(response.body().results);
                pagerAdapter.notifyDataSetChanged();


            }
            @Override
            public void onFailure(Call<MovieClass> call, Throwable t) {
                Log.i("res",t.toString());
            }
        });

    }




    public void getTopRated(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonHolder jsonHolder = retrofit.create(JsonHolder.class);

        Call<MovieClass> call = jsonHolder.getTopRated();

        call.enqueue(new Callback<MovieClass>() {
            @Override
            public void onResponse(Call<MovieClass> call, Response<MovieClass> response) {
                if (!response.isSuccessful()){

                    return;
                }
                pagerAdapter.setTopRatedResults(response.body().results);
                pagerAdapter.notifyDataSetChanged();
                Log.i("res",response.body().toString());

            }
            @Override
            public void onFailure(Call<MovieClass> call, Throwable t) {
                Log.i("res",t.toString());
            }
        });
    }



    private void setupLocale(String loc){
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            conf.setLocale(new Locale(loc.toLowerCase()));
        }
        else {
            conf.locale = new Locale(loc.toLowerCase());
        }

        res.updateConfiguration(conf,dm);
    }



    public void setupTabLayout(){
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#fafafa"));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
    }

}
