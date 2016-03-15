package com.example.android.poplularmoviestage1;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @brief Activity which shows the movie details
 * */
public class Description_of_movie extends AppCompatActivity {

    //private final String LOG_TAG = Description_of_movie.class.getSimpleName();
    private FragmentManager Obj_fragmentManager = getFragmentManager();

    /**
     * @brief Handing the creation of the activity
     */

    @Override
    protected void onCreate(Bundle StoredStateOfInstance) {
        super.onCreate(StoredStateOfInstance);
        setContentView(R.layout.activity_description_of_movie);

        //Saved instance not found
        if (StoredStateOfInstance == null) {
            Obj_fragmentManager.beginTransaction().add(R.id.container, new Description_of_movies()).commit();
        }
        getSupportActionBar().setElevation(0f);
    }

    /**
     * @brief Handling the option selection
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int item_id = item.getItemId();

        if (item_id  == R.id.action_settings) {

            startActivity(new Intent(this, PreferenceNSettingActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * @brief Menu option creation handling
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_description_of_movie, menu);
        return true;
    }

}
