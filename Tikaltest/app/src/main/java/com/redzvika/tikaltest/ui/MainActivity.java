package com.redzvika.tikaltest.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.redzvika.tikaltest.R;
import com.redzvika.tikaltest.background.MoviesData;

public class MainActivity extends AppCompatActivity implements  MainFragment.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onItemSelected(MoviesData.Movie  movie) {


        DetailsFragment fragment = (DetailsFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
        if (fragment != null && fragment.isInLayout()) {
            fragment.setPersistentData(movie);
        } else {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_DATA, movie);
            startActivity(intent);

        }
    }
}
