package com.example.retrofitwithimdb.models;

import com.example.retrofitwithimdb.models.DetailsClass;
import com.example.retrofitwithimdb.models.MovieClass;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonHolder {
    @GET("3/movie/popular?api_key=306336cb31c7909d625fb19cfe981f1a&language=en-US&page=1")
    Observable<MovieClass> getPopular();

    @GET("3/movie/{id}}?api_key=306336cb31c7909d625fb19cfe981f1a&language=en-US#")
    Observable<DetailsClass> getMovieDetails(@Path("id") int movieID);

    @GET("/3/movie/top_rated?api_key=306336cb31c7909d625fb19cfe981f1a&language=en-US&page=1")
    Observable<MovieClass> getTopRated();

}
