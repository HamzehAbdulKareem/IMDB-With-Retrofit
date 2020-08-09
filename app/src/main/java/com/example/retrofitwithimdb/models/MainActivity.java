package com.example.retrofitwithimdb.models;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;



import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.example.retrofitwithimdb.view.DetailsDialog;
import com.example.retrofitwithimdb.R;
import com.example.retrofitwithimdb.view.MoviesFragment;
import com.example.retrofitwithimdb.presenter.PagerAdapter;
import com.example.retrofitwithimdb.view.UserLoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    AlertDialog.Builder alert;
    AlertDialog dialogl;
    RetrofitClient retrofitClient = new RetrofitClient();
    MoviesFragment.DataGetter dGetter = new MoviesFragment.DataGetter() {
        @Override
        public void getData(int id) {
            alert = new AlertDialog.Builder(MainActivity.this);
            dialogl = alert.setTitle("")
                    .setMessage(getResources().getString(R.string.dialogload))
                    .create();

            dialogl.show();

            getDetails(id);


        }
    };


    MoviesFragment.DataRefresher mostPopRef = new MoviesFragment.DataRefresher() {
        @Override
        public void renewData() {
            getMostPopular();
        }
    };

    MoviesFragment.DataRefresher topRatRef = new MoviesFragment.DataRefresher() {
        @Override
        public void renewData() {
            getTopRated();
        }
    };

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocHelper.onAttach(newBase, "en"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.changeLanguageButton:
                if (LocHelper.getPersistedData(MainActivity.this, "rr").equals("en")) {

                    Context context = LocHelper.setLocale(MainActivity.this, "ar");
                    Resources resources = context.getResources();
                    finish();
                    startActivity(getIntent());
                    break;

                } else {

                    Context context = LocHelper.setLocale(MainActivity.this, "en");
                    Resources resources = context.getResources();
                    finish();
                    startActivity(getIntent());
                    break;

                }

            case R.id.userButton:
                Intent intent = new Intent(this, UserLoginActivity.class);
                startActivity(intent);
//            DetailsDialog detailsDialog = new DetailsDialog();
//            detailsDialog.show(getSupportFragmentManager(),"dialog");

                return (true);

        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        LocHelper.onAttach(this);
        setContentView(R.layout.activity_main);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), this, mostPopRef, topRatRef, dGetter);
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        setupTabLayout();


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d(TAG, msg);
                        Log.i("token", token);
                    }
                });

    }

    @Override
    public void onResume() {
        super.onResume();


        getMostPopular();

        getTopRated();


    }


    public void getMostPopular() {
        Retrofit retrofit = retrofitClient.getRetrofitWithOkhttp(MainActivity.this);

        JsonHolder jsonHolder = retrofit.create(JsonHolder.class);

        Call<MovieClass> call = jsonHolder.getPopular();

        call.enqueue(new Callback<MovieClass>() {
            @Override
            public void onResponse(Call<MovieClass> call, Response<MovieClass> response) {
                if (!response.isSuccessful()) {
                    Log.i("results", "error retrieving");
                    return;
                }
                Log.i("results", response.body().results.toString());
                pagerAdapter.setMostPopularResults(response.body().results);

                pagerAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<MovieClass> call, Throwable t) {
                Log.i("res", t.toString());
            }
        });

    }


    public void getTopRated() {
        Retrofit retrofit = retrofitClient.getRetrofitWithOkhttp(MainActivity.this);

        JsonHolder jsonHolder = retrofit.create(JsonHolder.class);

        Call<MovieClass> call = jsonHolder.getTopRated();

        call.enqueue(new Callback<MovieClass>() {
            @Override
            public void onResponse(Call<MovieClass> call, Response<MovieClass> response) {
                if (!response.isSuccessful()) {

                    return;
                }
                pagerAdapter.setTopRatedResults(response.body().results);
                pagerAdapter.notifyDataSetChanged();
                Log.i("res", response.body().toString());

            }

            @Override
            public void onFailure(Call<MovieClass> call, Throwable t) {
                Log.i("res", t.toString());
            }
        });
    }


    private void setupLocale(String loc) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(new Locale(loc.toLowerCase()));
        } else {
            conf.locale = new Locale(loc.toLowerCase());
        }

        res.updateConfiguration(conf, dm);
    }


    public void setupTabLayout() {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#fafafa"));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
    }


    public void getDetails(int id) {
        Retrofit retrofit = retrofitClient.getRetrofitWithOkhttp(MainActivity.this);

        JsonHolder jsonHolder = retrofit.create(JsonHolder.class);

        Call<DetailsClass> call = jsonHolder.getMovieDetails(id);
        call.enqueue(new Callback<DetailsClass>() {
            @Override
            public void onResponse(Call<DetailsClass> call, Response<DetailsClass> response) {
                dialogl.dismiss();
                if (!response.isSuccessful()) {
                    Log.i("response :", Integer.toString(response.code()));

                }
                DetailsClass detailsFragment = new DetailsClass();
                DetailsClass responseResult = response.body();

                DetailsDialog detailsDialog = new DetailsDialog(responseResult.getTitle(), Double.toString(responseResult.getPopularity()), Boolean.toString(responseResult.isAdult()), responseResult.getStatus(), Double.toString(responseResult.getVote_average()), Double.toString(responseResult.getVote_count()));
                detailsDialog.show(getSupportFragmentManager(), "Details");
            }

            @Override
            public void onFailure(Call<DetailsClass> call, Throwable t) {
                Log.i("Response error", t.toString());
            }
        });
    }


}
