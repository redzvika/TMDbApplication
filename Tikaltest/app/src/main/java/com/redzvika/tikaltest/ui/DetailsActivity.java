package com.redzvika.tikaltest.ui;

import android.app.Activity;

import android.os.Bundle;

import com.redzvika.tikaltest.R;
import com.redzvika.tikaltest.background.MoviesData;

/**
 * Created by primeuser on 7/18/16.
 */
public class DetailsActivity extends Activity {

    public static String EXTRA_DATA="extra_data";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        MoviesData.Movie  movie;
        Bundle extras = getIntent().getExtras();
        if (extras != null && (movie=( MoviesData.Movie )extras.get(EXTRA_DATA))!=null) {
             DetailsFragment fragment = (DetailsFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
             if (fragment != null && fragment.isInLayout()) {
                 fragment.setPersistentData(movie);
             }
         }



    }




}
