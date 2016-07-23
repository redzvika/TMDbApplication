package com.redzvika.tikaltest.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.redzvika.tikaltest.R;
import com.redzvika.tikaltest.background.MoviesData;
import com.squareup.picasso.Picasso;

/**
 * Created by primeuser on 7/18/16.
 */
public class DetailsFragment extends Fragment {


    private Activity activity;
    private MoviesData.Movie persistentData =null;

    private TextView title;
    private ImageView imageLeft;
    private ImageView imageRight;
    private TextView releaseDate;
    private TextView overview;
    private TextView empty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    private void updateContainer(){
        if (persistentData !=null){
            empty.setVisibility(View.GONE);
            title.setVisibility(View.VISIBLE);
            releaseDate.setVisibility(View.VISIBLE);
            overview.setVisibility(View.VISIBLE);
            imageLeft.setVisibility(View.VISIBLE);
            imageRight.setVisibility(View.VISIBLE);

            title.setText(persistentData.origianl_title);
            releaseDate.setText(persistentData.release_date);
            overview.setText(persistentData.overview);
            Picasso.with(activity).load(persistentData.poster_path).resize(200, 200).placeholder(R.drawable.placeholder).error(R.drawable.error).into(imageLeft);
            Picasso.with(activity).load(persistentData.backdrop_path).resize(200, 200).placeholder(R.drawable.placeholder).error(R.drawable.error).into(imageRight);
        }else{
            empty.setVisibility(View.VISIBLE);
            title.setVisibility(View.GONE);
            releaseDate.setVisibility(View.GONE);
            overview.setVisibility(View.GONE);
            imageLeft.setVisibility(View.GONE);
            imageRight.setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details, container, false);
        title= (TextView) v.findViewById(R.id.title);
        imageLeft= (ImageView) v.findViewById(R.id.imageLeft);
        imageRight= (ImageView) v.findViewById(R.id.imageRight);
        releaseDate= (TextView) v.findViewById(R.id.releaseDate);
        overview= (TextView) v.findViewById(R.id.overview);
        empty= (TextView) v.findViewById(R.id.empty);




        updateContainer();
        return v;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (persistentData !=null){
            outState.putParcelable(DetailsActivity.EXTRA_DATA,persistentData);
        }
        Log.w("///////", "onSaveInstanceState");

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // restore state
        MoviesData.Movie  data;
        if (savedInstanceState!=null && (data=savedInstanceState.getParcelable(DetailsActivity.EXTRA_DATA))!=null){
            persistentData=data;
        }
        Log.w("///////", "onActivityCreated");
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity =(Activity)context;

    }


    public void setPersistentData(final MoviesData.Movie  movie){

        this.persistentData=movie;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateContainer();
            }
        });
    }
}
