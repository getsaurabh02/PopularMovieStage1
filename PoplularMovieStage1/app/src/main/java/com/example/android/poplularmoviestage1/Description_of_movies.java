package com.example.android.poplularmoviestage1;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * @brief Fragments to show the movie details
 */
public class Description_of_movies extends Fragment {

    //private final String LOG_TAG = Description_of_movies.class.getSimpleName();
    MovieStore moviestoreobj;

    //Set the has option flag
    public Description_of_movies() {

        setHasOptionsMenu(true);
    }

    /**
     * @brief Handling the creation of the view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_description_of_movies, container, false);

        Intent intent_obj = getActivity().getIntent();
        if (intent_obj != null && intent_obj.hasExtra("movies_details")) {

            moviestoreobj = (MovieStore)intent_obj.getParcelableExtra("movies_details");
            DisplayInfo(rootView);
        }
        return rootView;
    }

    /**
     * @brief Set the movie info to be displayed
     * @param view
     */
    private void DisplayInfo(View view){

        ImageView movieposter = (ImageView) view.findViewById(R.id.poster_image_view);
        TextView movietitle = (TextView) view.findViewById(R.id.movie_title_view);
        TextView movierating = (TextView) view.findViewById(R.id.ratings_view);
        TextView moviereldate = (TextView) view.findViewById(R.id.release_date);
        TextView movieoverview = (TextView) view.findViewById(R.id.synopsis_view);

        Picasso.with(getActivity()).load(moviestoreobj.getMoviePoster()).into(movieposter);
        movietitle.setText(moviestoreobj.getMovieTitle());
        movierating.setText(moviestoreobj.getMovieRating() + "/10");
        moviereldate.setText(moviestoreobj.getMovieRelDate());
        movieoverview.setText(moviestoreobj.getMovieOverview());
    }

}
