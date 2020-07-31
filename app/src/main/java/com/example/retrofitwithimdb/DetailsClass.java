package com.example.retrofitwithimdb;

public class DetailsClass {
    boolean adult, video;
    Object backdrop_path, belongs_to_collection;
    int budget, id, revenue, runtime, vote_count;
    String homepage, imdb_id, original_language, original_title, overview, poster_path, status, tagline, title;
    double popularity, vote_average;
    Genres [] genres;
    ProductionCompaniesClass [] production_companies;
    ProductionCountresClass [] production_countries;
    SpokenLanguagesClass [] spoken_languages;

    public boolean isAdult() {
        return adult;
    }

    public boolean isVideo() {
        return video;
    }

    public Object getBackdrop_path() {
        return backdrop_path;
    }

    public Object getBelongs_to_collection() {
        return belongs_to_collection;
    }

    public int getBudget() {
        return budget;
    }

    public int getId() {
        return id;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public int getVote_count() {
        return vote_count;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public String getTitle() {
        return title;
    }

    public double getPopularity() {
        return popularity;
    }

    public double getVote_average() {
        return vote_average;
    }

    public Genres[] getGenres() {
        return genres;
    }

    public ProductionCompaniesClass[] getProduction_companies() {
        return production_companies;
    }

    public ProductionCountresClass[] getProduction_countries() {
        return production_countries;
    }

    public SpokenLanguagesClass[] getSpoken_languages() {
        return spoken_languages;
    }
}
