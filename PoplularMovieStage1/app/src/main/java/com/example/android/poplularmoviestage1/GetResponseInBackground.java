package com.example.android.poplularmoviestage1;

import java.util.List;

/**
 * @brief Interface for doing background actvity
 * Created by Saurabh on 3/14/2016.
 */
public interface GetResponseInBackground {

    void onTaskCompleted( List<MovieStore> results );

}
