package com.example.retrofitwithimdb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesFragment extends Fragment {
    MovieRecyclerAdapter adapter;
    ArrayList<MovieResults> results = new ArrayList<>();
    TextView textView;
    MovieRecyclerAdapter.ItemClickListener listener;
    View root;
    SwipeRefreshLayout swiper;
    DataRefresher refreshData;
    static MovieResults movieResults2;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "selectedMovie";
        private static final String ARG_PARAM2 = "b";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MoviesFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static MoviesFragment newInstance(MovieClass movie) {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1,movie.getMovieResults());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("state","Fragment launched");
        root =  inflater.inflate(R.layout.fragment_details, container, false);
        swiper = root.findViewById(R.id.swiper);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recall();
                swiper.setRefreshing(false);
            }
        });
        setOnClickListner();
        callMovieRecyclerAdapter();

        return root;
    }


    public View callMovieRecyclerAdapter(){
        RecyclerView recyclerView = root.findViewById(R.id.MovieRecyceler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MovieRecyclerAdapter(getContext(), results,listener);
        recyclerView.setAdapter(adapter);

        return recyclerView;
    }

    private  void setOnClickListner() {
        listener = new MovieRecyclerAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(getContext(),DetailsActivity.class);
                Bundle bundle = new Bundle();
                int id = results.get(position).getId();
                intent.putExtra("ID",id);
                startActivity(intent);

            }
        };
    }

    public void setResults (ArrayList<MovieResults> arrayResults){
        this.results = arrayResults;
        adapter.addItems(results);
    }

    public void recall(){
       refreshData.renewData();

    }

    public interface DataRefresher {
        void renewData();
    }


    public void setRefreshData(DataRefresher refreshData) {
        this.refreshData = refreshData;
    }
}