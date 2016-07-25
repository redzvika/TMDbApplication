package com.redzvika.tikaltest.background;

import java.util.HashMap;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class MovieProvider extends ContentProvider {
	 // fields for my content provider

	 private static String TAG="MovieProvider";
	 static final String PROVIDER_NAME = "com.redzvika.provider.MovieProv";
	 static final String URL = "content://" + PROVIDER_NAME + "/movies";
	 public static final Uri CONTENT_URI = Uri.parse(URL);
	   
	 // fields for the database
	 static final String ID = "_id";
	 //static final String NAME = "name";
	 //static final String BIRTHDAY = "birthday";
	 public static final String POSTER_PATH ="poster_path";
	 public static final String OVERVIEW="overview";
	 public static final String RELEASE_DATE="release_date";
	 public static final String MOVIE_ID="movie_id";
	 public static final String ORIGINAL_TITLE="original_title";
	 public static final String BACKDROP_PATH="backdrop_path";
	 public static final String VOTE_AVERAGE="vote_average";

	 
	 // integer values used in content URI
	 static final int MOVIES = 1;
	 static final int MOVIES_ID = 2;
	 
	 DBHelper dbHelper;
	   
	 // projection map for a query
	 private static HashMap<String, String> MovieMap;
	 
	 // maps content URI "patterns" to the integer values that were set above
	 static final UriMatcher uriMatcher;
	   static{
	      uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	      uriMatcher.addURI(PROVIDER_NAME, "movies", MOVIES);
	      uriMatcher.addURI(PROVIDER_NAME, "movies/#", MOVIES_ID);
	   }
	   
	   // database declarations
	   private SQLiteDatabase database;
	   static final String DATABASE_NAME = "MoivesTBDB";
	   static final String TABLE_NAME = "movieTable";
	   static final int DATABASE_VERSION = 1;
	   static final String CREATE_TABLE = 
	      " CREATE TABLE " + TABLE_NAME +
	      " ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
		  MOVIE_ID + " TEXT NOT NULL, "+
		  ORIGINAL_TITLE + " TEXT NOT NULL, "+
		  OVERVIEW + " TEXT NOT NULL, "+
		  RELEASE_DATE + " TEXT NOT NULL, "+
		  VOTE_AVERAGE + " TEXT NOT NULL, "+
          POSTER_PATH + " TEXT NOT NULL, "+
          BACKDROP_PATH + " TEXT NOT NULL);";
	 
	   
	   // class that creates and manages the provider's database 
	   private static class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			 Log.d(TAG, "onCreate: CREATE_TABLE "+CREATE_TABLE);
			 db.execSQL(CREATE_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			Log.w(DBHelper.class.getName(),
			        "Upgrading database from version " + oldVersion + " to "
			            + newVersion + ". Old data will be destroyed");
			db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
	        onCreate(db);
		}
		
	   }
	   
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		Context context = getContext();
		dbHelper = new DBHelper(context);
		// permissions to be writable
		database = dbHelper.getWritableDatabase();

	    if(database == null)
	    	return false;
	    else
	    	return true;	
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		 SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		 // the TABLE_NAME to query on
		 queryBuilder.setTables(TABLE_NAME);
	      
	      switch (uriMatcher.match(uri)) {
	      // maps all database column names
	      case MOVIES:
	    	  queryBuilder.setProjectionMap(MovieMap);
	         break;
	      case MOVIES_ID:
	    	  queryBuilder.appendWhere( ID + "=" + uri.getLastPathSegment());
	         break;
	      default:
	         throw new IllegalArgumentException("Unknown URI " + uri);
	      }
	      if (sortOrder == null || sortOrder == ""){
	         // No sorting-> sort on names by default
	         sortOrder = ORIGINAL_TITLE;
	      }
	      Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
	      /** 
	       * register to watch a content URI for changes
	       */
	      cursor.setNotificationUri(getContext().getContentResolver(), uri);

	      return cursor;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		long row = database.insert(TABLE_NAME, "", values);
	      
		// If record is added successfully
	      if(row > 0) {
	         Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
	         getContext().getContentResolver().notifyChange(newUri, null);
	         return newUri;
	      }
	      throw new SQLException("Fail to add a new record into " + uri);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		 int count = 0;
	      
	      switch (uriMatcher.match(uri)){
	      case MOVIES:
	         count = database.update(TABLE_NAME, values, selection, selectionArgs);
	         break;
	      case MOVIES_ID:
	         count = database.update(TABLE_NAME, values, ID + 
	                 " = " + uri.getLastPathSegment() + 
	                 (!TextUtils.isEmpty(selection) ? " AND (" +
	                 selection + ')' : ""), selectionArgs);
	         break;
	      default: 
	         throw new IllegalArgumentException("Unsupported URI " + uri );
	      }
	      getContext().getContentResolver().notifyChange(uri, null);
	      return count;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		int count = 0;
		
		 switch (uriMatcher.match(uri)){
	      case MOVIES:
	    	  // delete all the records of the table
	    	  count = database.delete(TABLE_NAME, selection, selectionArgs);
	    	  break;
	      case MOVIES_ID:
	      	  String id = uri.getLastPathSegment();	//gets the id
	          count = database.delete( TABLE_NAME, ID +  " = " + id + 
	                (!TextUtils.isEmpty(selection) ? " AND (" + 
	                selection + ')' : ""), selectionArgs);
	          break;
	      default: 
	          throw new IllegalArgumentException("Unsupported URI " + uri);
	      }
	      
	      getContext().getContentResolver().notifyChange(uri, null);
	      return count;
		
		
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		switch (uriMatcher.match(uri)){
	      // Get all friend-birthday records 
	      case MOVIES:
	         return "vnd.android.cursor.dir/vnd.redzvika.movies";
	      // Get a particular friend 
	      case MOVIES_ID:
	         return "vnd.android.cursor.item/vnd.redzvika.movies";
	      default:
	         throw new IllegalArgumentException("Unsupported URI: " + uri);
	      }
	}


}
