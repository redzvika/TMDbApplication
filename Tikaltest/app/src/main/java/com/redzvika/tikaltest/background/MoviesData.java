package com.redzvika.tikaltest.background;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by primeuser on 7/22/16.
 */
public class MoviesData {

    private List<Movie> movieDB=new ArrayList<Movie>();

    public static class Movie implements Parcelable {

        private static final String POSTER_PATH ="poster_path";
        private static final String OVERVIEW="overview";
        private static final String RELEASE_DATE="release_date";
        private static final String ID="id";
        private static final String ORIGINAL_TITLE="original_title";
        private static final String BACKDROP_PATH="backdrop_path";
        private static final String VOTE_AVERAGE="vote_average";


        public String poster_path;
        public String overview;
        public String release_date;
        public String id;
        public String origianl_title;
        public String backdrop_path;
        public String vote_averge;

        public Movie(){

        }

        protected Movie(Parcel in) {
            poster_path = in.readString();
            overview = in.readString();
            release_date = in.readString();
            id = in.readString();
            origianl_title = in.readString();
            backdrop_path = in.readString();
            vote_averge = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(poster_path);
            dest.writeString(overview);
            dest.writeString(release_date);
            dest.writeString(id);
            dest.writeString(origianl_title);
            dest.writeString(backdrop_path);
            dest.writeString(vote_averge);
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
            @Override
            public Movie createFromParcel(Parcel in) {
                return new Movie(in);
            }

            @Override
            public Movie[] newArray(int size) {
                return new Movie[size];
            }
        };


    }


    public  List<Movie> getMovieDB(){
        return  movieDB;
    }

    private MoviesData(){
    }

    public static MoviesData  parseConfig(ConfigurationData configurationData,String jsonString) throws Exception{
        MoviesData moviesData=new MoviesData();
        JSONObject mainObject = new JSONObject(jsonString);
        JSONArray results= mainObject.getJSONArray("results");
        for (int i=0;i<results.length();i++){

            Movie movieData=new Movie();
            JSONObject result = results.getJSONObject(i);

            if (result.has(Movie.POSTER_PATH)) {
                movieData.poster_path =configurationData.secure_base_url+configurationData.poster_sizes[1]+result.getString(Movie.POSTER_PATH);
            }
            if (result.has(Movie.BACKDROP_PATH)) {
                movieData.backdrop_path=configurationData.secure_base_url+configurationData.backdrop_sizes[1]+result.getString(Movie.BACKDROP_PATH);
            }
            if (result.has(Movie.RELEASE_DATE)) {
                movieData.release_date=result.getString(Movie.RELEASE_DATE);
            }
            if (result.has(Movie.ID)) {
                movieData.id=result.getString(Movie.ID);
            }
            if (result.has(Movie.OVERVIEW)) {
                movieData.overview=result.getString(Movie.OVERVIEW);
            }

            if (result.has(Movie.ORIGINAL_TITLE)) {
                movieData.origianl_title=result.getString(Movie.ORIGINAL_TITLE);
            }

            if (result.has(Movie.VOTE_AVERAGE)) {
                movieData.vote_averge=result.getString(Movie.VOTE_AVERAGE);
            }
            moviesData.getMovieDB().add(movieData);

        }


        return moviesData;
    }

}
