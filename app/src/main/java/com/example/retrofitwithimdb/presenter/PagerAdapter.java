package com.example.retrofitwithimdb.presenter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.retrofitwithimdb.R;
import com.example.retrofitwithimdb.models.MovieResults;
import com.example.retrofitwithimdb.view.MoviesFragment;

import java.util.ArrayList;

public class PagerAdapter extends FragmentPagerAdapter {
    Context context;
    private MoviesFragment mostPopular;
    private MoviesFragment topRated;
    public PagerAdapter(FragmentManager fm, Context context, MoviesFragment.DataRefresher mpRefreshData, MoviesFragment.DataRefresher tpRefreshData,MoviesFragment.DataGetter dataGetter) {
        super(fm);
        mostPopular = new MoviesFragment();
        mostPopular.setDataGetter(dataGetter);
        mostPopular.setRefreshData(mpRefreshData);
        topRated = new MoviesFragment();
        topRated.setDataGetter(dataGetter);
        topRated.setRefreshData(tpRefreshData);
        this.context = context;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                return mostPopular;
            case 1:
                return topRated;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2; //three fragments
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getResources().getString(R.string.pop);
            case 1:
                return context.getResources().getString(R.string.tr);
            default: return "Fragment";
        }
    }

    public void setMostPopularResults(ArrayList<MovieResults> movieResults){
        mostPopular.setResults(movieResults);
    }

    public void setTopRatedResults(ArrayList<MovieResults> movieResults){
        topRated.setResults(movieResults);
    }

}
