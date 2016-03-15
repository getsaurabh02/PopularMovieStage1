package com.example.android.poplularmoviestage1;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @brief Class to get the movie details asynchronous
 * Created by Saurabh on 3/14/2016.
 */
public class GetMovieDetails extends AsyncTask<String, Void, List<MovieStore>> {

    public GetResponseInBackground responsedelegator;

    private final String LOG_TAG = GetMovieDetails.class.getSimpleName();
    private final String Size_for_movie_poster= "w185";
    private final String moviePosterBase = "http://image.tmdb.org/t/p/";

    //@Review: Movie db aoi key to be pasted here
    private final String Movie_DB_API_Key= "MOVIE_DB_API_KEY";


    /**
     * @brief Set the delegator
     * @param delegate
     */
    public GetMovieDetails(GetResponseInBackground delegate){
        this.responsedelegator = delegate;
    }

    /**
     * @brief Send Request n Get Data
     * @param params
     * @return
     */
    @Override
    protected List<MovieStore> doInBackground(String... params) {

        //Case: No params; return
        if (params.length == 0) {
            return null;
        }

        HttpURLConnection HttpURLConnection = null;
        BufferedReader bufferedReader = null;
        String moviesString = null;

        //begin the block
        try {

            final String MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
            final String API_KEY_STRING = "api_key";
            final String SORT_BY = "sort_by";

            String sortBy = params[0];

            Uri appendedURL = Uri.parse(MOVIE_DB_BASE_URL).buildUpon().appendQueryParameter(SORT_BY, sortBy)
                    .appendQueryParameter(API_KEY_STRING, Movie_DB_API_Key).build();

            //Construct the URL
            URL final_url = new URL(appendedURL.toString());

            HttpURLConnection = (HttpURLConnection) final_url.openConnection();
            HttpURLConnection.setRequestMethod("GET");

            //Connect
            HttpURLConnection.connect();

            //Get the input stream
            InputStream responseInputStream = HttpURLConnection.getInputStream();
            StringBuffer responseBuffer = new StringBuffer();

            //Case: No Input Stream; return
            if (responseInputStream == null) {
                return null;
            }

            bufferedReader = new BufferedReader(new InputStreamReader(responseInputStream));

            String lineBuffer;

            while ((lineBuffer = bufferedReader.readLine()) != null) {

                responseBuffer.append(lineBuffer + "\n");
            }

            if (responseBuffer.length() == 0) {
                return null;
            }

            moviesString = responseBuffer.toString();

            //Catch block
        } catch (IOException e) {

            //Log the error
            Log.e(LOG_TAG, "Error ", e);
            return null;

            //Finally block
        } finally {

            if (HttpURLConnection != null) {

                HttpURLConnection.disconnect();
            }

            if (bufferedReader != null) {

                try {

                    bufferedReader.close();

                } catch (final IOException e) {
                    //Log the error
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {

            return extractData(moviesString);

        } catch (JSONException e) {

            //Log the error
            Log.e(LOG_TAG, e.getMessage(), e);

            e.printStackTrace();
        }

        return null;
    }


    /**
     * @brief Return the movie list
     * @param movieResult
     */
    @Override
    protected void onPostExecute(List<MovieStore> movieResult) {

        if (movieResult != null) {

            responsedelegator.onTaskCompleted(movieResult);
        }
    }

    /**
     * @brief Get the movie year
     * @param movieDate
     * @return
     */
    private String getYear(String movieDate){

        final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        final Calendar calenderObj = Calendar.getInstance();

        try {

            calenderObj.setTime(dateformat.parse(movieDate));

        } catch (ParseException excptn) {

            excptn.printStackTrace();
        }

        return Integer.toString(calenderObj.get(Calendar.YEAR));
    }

    /**
     * @berief Parse the movie data
     * @param jsonStringForMovies
     * @return
     * @throws JSONException
     */
    private List<MovieStore> extractData(String jsonStringForMovies) throws JSONException {

        // Items to extract
        final String Results = "results";
        final String Average_Vote = "vote_average";
        final String Path_of_Poster = "poster_path";
        final String Orig_title = "original_title";
        final String Overv = "overview";
        final String Date_of_Release = "release_date";

        JSONObject jsonMoviObj = new JSONObject(jsonStringForMovies);
        JSONArray movieArr = jsonMoviObj.getJSONArray(Results);
        int length =  movieArr.length();
        List<MovieStore> listOfMovie = new ArrayList<MovieStore>();

        //Walk the length and create a new movie object
        for(int i = 0; i < length; ++i) {

            JSONObject movieObj = movieArr.getJSONObject(i);
            String movieTitle = movieObj.getString(Orig_title);
            String moviePoster = moviePosterBase + Size_for_movie_poster + movieObj.getString(Path_of_Poster);

            String MovieVote = movieObj.getString(Average_Vote);
            String MovieOverview = movieObj.getString(Overv);
            String MovieDate = getYear(movieObj.getString(Date_of_Release));

            //Add the new object to the list
            listOfMovie.add(new MovieStore(movieTitle, moviePoster, MovieOverview, MovieVote, MovieDate));

        }
        return listOfMovie;
    }
}
