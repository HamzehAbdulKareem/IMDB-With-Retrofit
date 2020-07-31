package com.example.retrofitwithimdb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonHolder {
    @GET("3/movie/popular?api_key=306336cb31c7909d625fb19cfe981f1a&language=en-US&page=1")
    Call<MovieClass> getMovies();

    @GET("3/movie/{id}}?api_key=306336cb31c7909d625fb19cfe981f1a&language=en-US#")
    Call<DetailsClass> getMovieDetails(@Path("id") int movieID);


}
