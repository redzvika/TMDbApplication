package com.redzvika.tikaltest.ui;


import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.redzvika.tikaltest.R;
import com.redzvika.tikaltest.background.MoviesData;


/**
 * Created by primeuser on 7/18/16.
 */
public class MainFragment extends Fragment implements  LoaderManager.LoaderCallbacks<Cursor> {


    /**
     * stores activity
     */
    private Activity activity;

    /**
     * stores callback to handle clicks in the Grid/
     */
    private OnItemSelectedListener onItemSelectedListener;

    /**
     *
     */
    private MovieAdapter movieAdapter;

    /**
     *
     */
    private RecyclerView recyclerView;


    /**
     *
     */
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getLoaderManager().initLoader(0, null, this);
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.movie_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        movieAdapter=new MovieAdapter(activity,onItemSelectedListener);
        recyclerView.setAdapter(movieAdapter);
        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            onItemSelectedListener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implemenet MainFragment.OnItemSelectedListener");
        }
        activity =(Activity)context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        onItemSelectedListener = null;
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String URL = "content://com.redzvika.provider.MovieProv/movies";
        Uri baseUri=Uri.parse(URL);
        return new CursorLoader(getActivity(), baseUri, null, null, null, "original_title");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        movieAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        movieAdapter.swapCursor(null);
    }

    /**
     * interface that MainActivity implements to send the MoviesData.Movie to the fragment/Activity .
     */
    public  interface OnItemSelectedListener {
        public void onItemSelected( MoviesData.Movie  movie);
    }

}
