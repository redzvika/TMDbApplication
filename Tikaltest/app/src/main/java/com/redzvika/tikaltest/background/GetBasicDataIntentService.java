package com.redzvika.tikaltest.background;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by primeuser on 7/22/16.
 */
public class GetBasicDataIntentService extends IntentService{

    private static String TAG="GetBasicDataIntentService";
    public  static String TMDb_KEY="TMDb_KEY";


    private  Handler handler = new Handler(Looper.getMainLooper());
    private Context context;

    public GetBasicDataIntentService() {
        super("GetBasicDataIntentService");
        context = this;
    }
    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent!=null){
            String key = intent.getStringExtra(TMDb_KEY);

            GetRequestFromServer requestFromServer=new GetRequestFromServer(key);
            try {
                String configurationJsonData=requestFromServer.downloadConfig();
                ConfigurationData config= ConfigurationData.parseConfig(configurationJsonData);
                String popularMoviesJson=requestFromServer.downloadMostPopularMovies();
                MoviesData movies=MoviesData.parseConfig(config,popularMoviesJson);
                for (MoviesData.Movie movie:movies.getMovieDB()){
                    addRecord(movie);
                }

               // traversDataBase();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"AllIsWell",Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception e){
                Log.e(TAG,e.getMessage(),e);
            }
        }



    }


    /**
     * Add record to content provider ,if entry already exists don't enter it.
     * @param movie
     */
    private void addRecord(MoviesData.Movie movie){

        if (checkifExsits(movie.id)){
            Log.d(TAG,"movie with id ="+movie.id +" already in database");
            return;
        }

        ContentValues values = new ContentValues();
        values.put(MovieProvider.MOVIE_ID,movie.id);
        values.put(MovieProvider.ORIGINAL_TITLE,movie.origianl_title);
        values.put(MovieProvider.OVERVIEW,movie.overview);
        values.put(MovieProvider.RELEASE_DATE,movie.release_date);
        values.put(MovieProvider.VOTE_AVERAGE,movie.vote_averge);
        values.put(MovieProvider.POSTER_PATH,movie.poster_path);
        values.put(MovieProvider.BACKDROP_PATH,movie.backdrop_path);
        Uri uri = getContentResolver().insert(MovieProvider.CONTENT_URI, values);
        Log.d("uri recevies",uri.toString());

    }

    /**
     * check if entry already exists compare with movie_id
     * @param movie_id
     * @return
     */
    public boolean checkifExsits(String movie_id){
        String URL = "content://com.redzvika.provider.MovieProv/movies";
        Uri movies = Uri.parse(URL);
        // Cursor c = getContentResolver().query(movies, null, null, null, "original_title");
        Cursor c = getContentResolver().query(movies, null, "movie_id ="+movie_id,null, "original_title");
        boolean value=false;
        if (c.getCount()>0){
            value=true;
        }
        if (!c.isClosed()){
            c.close();;
        }
        return value;
    }

    public void traversDataBase() {
        // Show all the birthdays sorted by friend's name
        String URL = "content://com.redzvika.provider.MovieProv/movies";
        Uri movies = Uri.parse(URL);
        Cursor c = getContentResolver().query(movies, null, null,null, "original_title");

        Log.d(TAG,"database size" +c.getCount());
        if (!c.moveToFirst()) {
            Log.d(TAG," no content yet!");
        }else{
            do{
                StringBuilder builder=new StringBuilder();
                builder.append("ID ").append(c.getString(c.getColumnIndex(MovieProvider.ID))).append("\r\n");
                builder.append("MOVIE_ID ").append(c.getString(c.getColumnIndex(MovieProvider.MOVIE_ID))).append("\r\n");
                builder.append("ORIGINAL_TITLE ").append(c.getString(c.getColumnIndex(MovieProvider.ORIGINAL_TITLE))).append("\r\n");
                builder.append("OVERVIEW ").append(c.getString(c.getColumnIndex(MovieProvider.OVERVIEW))).append("\r\n");
                builder.append("RELEASE_DATE ").append(c.getString(c.getColumnIndex(MovieProvider.RELEASE_DATE))).append("\r\n");
                builder.append("VOTE_AVERAGE ").append(c.getString(c.getColumnIndex(MovieProvider.VOTE_AVERAGE))).append("\r\n");
                builder.append("POSTER_PATH ").append(c.getString(c.getColumnIndex(MovieProvider.POSTER_PATH))).append("\r\n");
                builder.append("BACKDROP_PATH ").append(c.getString(c.getColumnIndex(MovieProvider.BACKDROP_PATH))).append("\r\n");
                String output=builder.toString();
                Log.d(TAG,"Data" +output);
            } while (c.moveToNext());
            if (!c.isClosed()){
                c.close();
            }
        }

    }

}
