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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
        Log.i("entered","resumed");
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), MainActivity.this, mostPopRef, topRatRef, dGetter);
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        setupTabLayout();
        getMostPopular();

        getTopRated();


    }


    public void getMostPopular() {
        Retrofit retrofit = retrofitClient.getRetrofitWithOkhttp(MainActivity.this);

        JsonHolder jsonHolder = retrofit.create(JsonHolder.class);


        Observable<MovieClass> observable = jsonHolder.getPopular().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        Observer<MovieClass> observer = new Observer<MovieClass>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull MovieClass movieClass) {
                pagerAdapter.setMostPopularResults(movieClass.results);
                pagerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Log.i("error",e.toString());
            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribe(observer);



    }


    public void getTopRated() {
        Retrofit retrofit = retrofitClient.getRetrofitWithOkhttp(MainActivity.this);

        JsonHolder jsonHolder = retrofit.create(JsonHolder.class);

        Observable<MovieClass> topRatedObservable = jsonHolder.getTopRated().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        Observer<MovieClass> topRatedObserver = new Observer<MovieClass>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull MovieClass movieClass) {
                pagerAdapter.setTopRatedResults(movieClass.results);
                pagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        topRatedObservable.subscribe(topRatedObserver);
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

        Observable<DetailsClass> getDetailsObservable = jsonHolder.getMovieDetails(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        Observer<DetailsClass> getDetailsObserver = new Observer<DetailsClass>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull DetailsClass detailsClass) {

                DetailsDialog detailsDialog = new DetailsDialog(detailsClass.getTitle(), Double.toString(detailsClass.getPopularity()), Boolean.toString(detailsClass.isAdult()), detailsClass.getStatus(), Double.toString(detailsClass.getVote_average()), Double.toString(detailsClass.getVote_count()));
                detailsDialog.show(getSupportFragmentManager(), "Details");
                dialogl.dismiss();
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        getDetailsObservable.subscribe(getDetailsObserver);


    }


}
