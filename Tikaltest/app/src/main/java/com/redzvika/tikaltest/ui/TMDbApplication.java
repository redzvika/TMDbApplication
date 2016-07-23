package com.redzvika.tikaltest.ui;

import android.app.Application;
import android.content.Intent;

import com.redzvika.tikaltest.R;
import com.redzvika.tikaltest.background.GetBasicDataIntentService;
import com.redzvika.tikaltest.background.MovieProvider;


import android.content.ContentValues;
import android.database.Cursor;
/**
 * Created by primeuser on 7/22/16.
 */
public class TMDbApplication extends Application {


    public void onCreate() {
        super.onCreate();
        // start an IntentService to download relevant data and fill the content Provider
        Intent msgIntent = new Intent(this, GetBasicDataIntentService.class);
        String key = getString(R.string.moviedbkey);
        msgIntent.putExtra(GetBasicDataIntentService.TMDb_KEY, key);
        startService(msgIntent);
    }



}
