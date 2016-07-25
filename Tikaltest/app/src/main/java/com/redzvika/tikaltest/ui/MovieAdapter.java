package com.redzvika.tikaltest.ui;

/**
 * Created by primeuser on 7/25/16.
 */




import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidessence.recyclerviewcursoradapter.RecyclerViewCursorAdapter;
import com.androidessence.recyclerviewcursoradapter.RecyclerViewCursorViewHolder;
import com.redzvika.tikaltest.R;
import com.redzvika.tikaltest.background.MovieProvider;
import com.redzvika.tikaltest.background.MoviesData;
import com.squareup.picasso.Picasso;

public class MovieAdapter extends RecyclerViewCursorAdapter<MovieAdapter.MovieViewHolder>{


    /**
     *  Callback from ItemClicked.
     */
    private MainFragment.OnItemSelectedListener onItemClickListener;

    /**
     * Constructor.
     * @param context The Context the Adapter is displayed in.
     */
    public MovieAdapter(Context context, MainFragment.OnItemSelectedListener onItemClickListener) {
        super(context);
        this.onItemClickListener=onItemClickListener;
        setupCursorAdapter(null, 0, R.layout.grid_view_element, false);
    }

    /**
     * Returns the ViewHolder to use for this adapter.
     */
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), parent));
    }

    /**
     * Moves the Cursor of the CursorAdapter to the appropriate position and binds the view for
     * that item.
     */
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        // use synchronized when accessing the cursor between onBindViewHolder & getItem
        synchronized (this) {
            // Move cursor to this position
            mCursorAdapter.getCursor().moveToPosition(position);

            // Set the ViewHolder
            setViewHolder(holder);

            // Bind this view
            mCursorAdapter.bindView(null, mContext, mCursorAdapter.getCursor());
        }
    }


    /**
     *
     * @param position
     * @return
     */
    public MoviesData.Movie getItem(int position) {
        MoviesData.Movie movie = new MoviesData.Movie();
        // use synchronized when accessing the cursor between onBindViewHolder & getItem
        synchronized (this) {
            Cursor cursor = mCursorAdapter.getCursor();
            cursor.moveToPosition(position);
            movie.origianl_title = cursor.getString(cursor.getColumnIndex(MovieProvider.ORIGINAL_TITLE));
            movie.id = cursor.getString(cursor.getColumnIndex(MovieProvider.MOVIE_ID));
            movie.overview = cursor.getString(cursor.getColumnIndex(MovieProvider.OVERVIEW));
            movie.release_date = cursor.getString(cursor.getColumnIndex(MovieProvider.RELEASE_DATE));
            movie.vote_averge = cursor.getString(cursor.getColumnIndex(MovieProvider.VOTE_AVERAGE));
            movie.backdrop_path = cursor.getString(cursor.getColumnIndex(MovieProvider.BACKDROP_PATH));
            movie.poster_path = cursor.getString(cursor.getColumnIndex(MovieProvider.POSTER_PATH));
        }
        return movie;
    }




    /**
     * ViewHolder used to display a movie name.
     */
    public class MovieViewHolder extends RecyclerViewCursorViewHolder implements View.OnClickListener {


        public final TextView  title;
        public final ImageView imageView;

        public MovieViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            imageView=(ImageView) view.findViewById(R.id.image);
            title.setOnClickListener(this);
            imageView.setOnClickListener(this);
        }

        @Override
        public void bindCursor(Cursor cursor) {
            String poster_path =cursor.getString(cursor.getColumnIndex(MovieProvider.POSTER_PATH));
            title.setText(cursor.getString(cursor.getColumnIndex(MovieProvider.ORIGINAL_TITLE)));
            Picasso.with(mContext).load(poster_path).resize(200, 200).placeholder(R.drawable.placeholder).error(R.drawable.error).into(imageView);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemSelected(getItem(getAdapterPosition()));
        }
    }
}

