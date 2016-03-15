package com.example.android.poplularmoviestage1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @brief Movie data store
 * Created by Saurabh on 3/14/2016.
 */
public class MovieStore implements Parcelable {

    private String moviePoster;
    private String movieTitle;
    private String movieRating;
    private String movieOverview;
    private String movieRelDate;


    public MovieStore(String mTitle, String mPoster, String mOverview,String mRating, String mRelDate){

        this.moviePoster = mPoster;
        this.movieTitle = mTitle;
        this.movieRating = mRating;
        this.movieOverview = mOverview;
        this.movieRelDate = mRelDate;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public String getMovieRelDate() {
        return movieRelDate;
    }

    /**
     * @brief Write out
     * @param pOut
     * @param pFlags
     */
    @Override
    public void writeToParcel(Parcel pOut, int pFlags) {

        pOut.writeString(movieTitle);
        pOut.writeString(moviePoster);
        pOut.writeString(movieOverview);
        pOut.writeString(movieRating);
        pOut.writeString(movieRelDate);
    }

    @Override
    public int describeContents() {

        return 0;
    }

    /**
     * @brief
     * @param pIn
     */
    private MovieStore(Parcel pIn) {

        movieTitle = pIn.readString();
        moviePoster = pIn.readString();
        movieOverview = pIn.readString();
        movieRating = pIn.readString();
        movieRelDate = pIn.readString();
    }

    /**
     * @brief Create the store
     */
    public static final Parcelable.Creator<MovieStore> CREATOR = new Parcelable.Creator<MovieStore>() {

        public MovieStore createFromParcel(Parcel pIn) {

            return new MovieStore(pIn);
        }

        public MovieStore[] newArray(int pSize) {

            return new MovieStore[pSize];
        }
    };
}
