package com.example.retrofitwithimdb.view;

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

import com.example.retrofitwithimdb.R;
import com.example.retrofitwithimdb.models.MovieClass;
import com.example.retrofitwithimdb.models.MovieResults;
import com.example.retrofitwithimdb.presenter.MovieRecyclerAdapter;

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
    DataGetter dataGetter;

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
    public void onResume(){
        super.onResume();
  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.i("state","Fragment launched");
        root =  inflater.inflate(R.layout.fragment_details, container, false);
        setOnClickListner();
        adapter = new MovieRecyclerAdapter(getContext(), results,listener);
        callMovieRecyclerAdapter();
        swiper = root.findViewById(R.id.swiper);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recall();
                swiper.setRefreshing(false);
            }
        });


        return root;
    }


    public View callMovieRecyclerAdapter(){
        RecyclerView recyclerView = root.findViewById(R.id.MovieRecyceler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return recyclerView;
    }

    private  void setOnClickListner() {
        listener = new MovieRecyclerAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                dataGetter.getData(results.get(position).getId());



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
    public interface DataGetter{
        void getData(int id);
    }
    public void setDataGetter(DataGetter a){
        this.dataGetter = a;
    }


    public void setRefreshData(DataRefresher refreshData) {
        this.refreshData = refreshData;
    }
}