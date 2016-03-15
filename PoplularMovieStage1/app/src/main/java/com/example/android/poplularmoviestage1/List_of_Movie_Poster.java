package com.example.android.poplularmoviestage1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;


/**
 * @brief Fragment to display the list of movie posters on the main activity
 */
public class List_of_Movie_Poster extends Fragment {

    //private final String LOG_TAG = List_of_Movie_Poster.class.getSimpleName();

    private MoviePoster moviePosterObj;
    private final String Movies_In_Store = "stored_movies";
    private SharedPreferences preferences;

    String orderOfSort;

    List<MovieStore> movieObjList = new ArrayList<MovieStore>();

    /**
     * @brief Sets the has option flag as true
     */
    public void MovieListFragment() {

        setHasOptionsMenu(true);
    }

    /**
     * @brief Handles the creation of the activity
     * @param stateForSavedInstance
     */
    @Override
    public void onCreate(Bundle stateForSavedInstance) {

        super.onCreate(stateForSavedInstance);
        setHasOptionsMenu(true);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //Get the order of sorting from preferences
        orderOfSort = preferences.getString(getString(R.string.display_preferences_sort_order_key),
                getString(R.string.display_preferences_sort_default_value));

        if(stateForSavedInstance != null){

            ArrayList<MovieStore> movieStoreArr= new ArrayList<MovieStore>();

            movieStoreArr = stateForSavedInstance.<MovieStore>getParcelableArrayList(Movies_In_Store);

            movieObjList.clear();
            movieObjList.addAll(movieStoreArr);
        }
    }

    /**
     * @param item
     * @brief Handles the menu item selection
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemID = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * @brief Handles the creation of view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Set the movie poster object
        moviePosterObj = new MoviePoster(getActivity(),R.layout.movie_poster,R.id.list_item_poster_imageview,
                new ArrayList<String>());

        View view = inflater.inflate(R.layout.fragment_list_of__movie__poster, container, false);

        //Grid view
        GridView gridVewForm = (GridView) view.findViewById(R.id.main_movie_grid);
        gridVewForm.setAdapter(moviePosterObj);

        //
        gridVewForm.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /**
             * #brief Handles the selection on view
             * @param adapterView
             * @param view
             * @param position
             * @param l
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                MovieStore movieDetails = movieObjList.get(position);

                //Set the movie detail intent
                Intent intentObj = new Intent(getActivity(), Description_of_movie.class).putExtra("movies_details", movieDetails);

                //Begin the activity
                startActivity(intentObj);
            }

        });
        return view;
    }


    /**
     * @brief On Start
     */
    @Override
    public void onStart() {

        super.onStart();

        // get ppreference
        String newSortOrder = preferences.getString(getString(R.string.display_preferences_sort_order_key),
                getString(R.string.display_preferences_sort_default_value));

        //Case: Match the order
        if(movieObjList.size() > 0 && newSortOrder.equals(orderOfSort)) {
            //call for an update
            updatePosterAdapter();
        }else{

            orderOfSort = newSortOrder;
            getMovies();
        }
    }

    /**
     * @brief
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        ArrayList<MovieStore> storedMoviesArr = new ArrayList<MovieStore>();
        storedMoviesArr.addAll(movieObjList);

        outState.putParcelableArrayList(Movies_In_Store, storedMoviesArr);
    }

    /**
     * @brief
     */
    private void getMovies() {

        GetMovieDetails getMovieDetails = new GetMovieDetails(new GetResponseInBackground() {

            /**
             * @brief
             * @param movieResults
             */
            @Override
            public void onTaskCompleted(List<MovieStore> movieResults) {

                movieObjList.clear();
                movieObjList.addAll(movieResults);

                updatePosterAdapter();
            }
        });

        getMovieDetails.execute(orderOfSort);
    }

    /**
     * @brief
     */
    private void updatePosterAdapter() {

        moviePosterObj.clear();

        for(MovieStore movie : movieObjList) {

            moviePosterObj.add(movie.getMoviePoster());
        }
    }
}
