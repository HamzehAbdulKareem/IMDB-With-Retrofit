package com.example.retrofitwithimdb;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class MovieResults implements Parcelable {
    double popularity, vote_average;
    int vote_count, id;
    boolean video, adult;
    String poster_path, title, overView;
    String backdrop_path, original_languag, original_title;
    int [] genre_ids;
    Date release_date;

    protected MovieResults(Parcel in) {
        popularity = in.readDouble();
        vote_average = in.readDouble();
        vote_count = in.readInt();
        id = in.readInt();
        video = in.readByte() != 0;
        adult = in.readByte() != 0;
        poster_path = in.readString();
        title = in.readString();
        overView = in.readString();
        backdrop_path = in.readString();
        original_languag = in.readString();
        original_title = in.readString();
        genre_ids = in.createIntArray();
    }

    public static final Creator<MovieResults> CREATOR = new Creator<MovieResults>() {
        @Override
        public MovieResults createFromParcel(Parcel in) {
            return new MovieResults(in);
        }

        @Override
        public MovieResults[] newArray(int size) {
            return new MovieResults[size];
        }
    };

    public double getPopularity() {
        return popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public int getId() {
        return id;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOriginal_language() {
        return original_languag;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public String getTitle() {
        return title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return overView;
    }

    public Date getRelease_date() {
        return release_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(popularity);
        dest.writeDouble(vote_average);
        dest.writeInt(vote_count);
        dest.writeInt(id);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(poster_path);
        dest.writeString(title);
        dest.writeString(overView);
        dest.writeString(backdrop_path);
        dest.writeString(original_languag);
        dest.writeString(original_title);
        dest.writeIntArray(genre_ids);
    }
}
