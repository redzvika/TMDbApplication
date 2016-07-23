package com.redzvika.tikaltest.ui;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.TextView;

import com.redzvika.tikaltest.R;
import com.redzvika.tikaltest.background.MovieProvider;
import com.redzvika.tikaltest.background.MoviesData;
import com.squareup.picasso.Picasso;
public class CustomAdapter extends BaseAdapter {
	Cursor cursor;
	Context mContext;
	LayoutInflater inflater;

	public CustomAdapter(Context context, Cursor cursor) {
		this.mContext = context;
		this.cursor = cursor;
		this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void swapAdapter(Cursor cursor){
		this.cursor=cursor;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (cursor==null){
			return 0;
		}
		return cursor.getCount();
	}

	@Override
	public MoviesData.Movie getItem(int position) {
		cursor.moveToPosition(position);
		MoviesData.Movie movie=new MoviesData.Movie();
		movie.origianl_title=cursor.getString(cursor.getColumnIndex(MovieProvider.ORIGINAL_TITLE));
		movie.id=cursor.getString(cursor.getColumnIndex(MovieProvider.MOVIE_ID));
		movie.overview=cursor.getString(cursor.getColumnIndex(MovieProvider.OVERVIEW));
		movie.release_date=cursor.getString(cursor.getColumnIndex(MovieProvider.RELEASE_DATE));
		movie.vote_averge=cursor.getString(cursor.getColumnIndex(MovieProvider.VOTE_AVERAGE));
		movie.backdrop_path=cursor.getString(cursor.getColumnIndex(MovieProvider.BACKDROP_PATH));
		movie.poster_path =cursor.getString(cursor.getColumnIndex(MovieProvider.POSTER_PATH));
		return movie;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ImageDataDisplayHolder holder;
		cursor.moveToPosition(position);
		if (row == null) {
			row = inflater.inflate(R.layout.grid_view_element, parent, false);
			holder = new ImageDataDisplayHolder();
			holder.title=(TextView) row.findViewById(R.id.title);
			holder.imageView=(ImageView) row.findViewById(R.id.image);
			row.setTag(holder);
		} else {
			holder = (ImageDataDisplayHolder) row.getTag();
		}

		String poster_path =cursor.getString(cursor.getColumnIndex(MovieProvider.POSTER_PATH));
		holder.title.setText(cursor.getString(cursor.getColumnIndex(MovieProvider.ORIGINAL_TITLE)));
		//poster_path="http://farm6.staticflickr.com/5511/14318174782_3026ba63c7_q.jpg";
		Picasso.with(mContext).load(poster_path).resize(200, 200).placeholder(R.drawable.placeholder).error(R.drawable.error).into(holder.imageView);
		return  row;
	}

	private static class ImageDataDisplayHolder {
		TextView  title;
		ImageView imageView;
	}



}
