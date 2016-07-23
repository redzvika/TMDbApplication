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


import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.GridView;

import com.redzvika.tikaltest.R;
import com.redzvika.tikaltest.background.MoviesData;

import java.util.ArrayList;


/**
 * Created by primeuser on 7/18/16.
 */
public class MainFragment extends Fragment implements  LoaderManager.LoaderCallbacks<Cursor> {


    private Cursor cursorData=null;
    private GridView mGridView;
    private ArrayList<String> mItems=new ArrayList<String>();
    private  Handler handler;

    private Activity activity;
    private  OnItemSelectedListener onItemSelectedListener;

    private CustomAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getLoaderManager().initLoader(0, null, this);


        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mGridView = (GridView) v.findViewById(R.id.gridView);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemSelectedListener.onItemSelected(adapter.getItem(position));
            }
        });

        mGridView.setEmptyView(v.findViewById(R.id.empty));

        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            onItemSelectedListener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implemenet MainFragment.OnItemSelectedListener");
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

        adapter=new CustomAdapter(activity,data);
        mGridView.setAdapter(adapter);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        String x;

    }


    public interface OnItemSelectedListener {
        public void onItemSelected( MoviesData.Movie  movie);
    }

}
