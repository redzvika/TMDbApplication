package com.redzvika.tikaltest.background;

import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by primeuser on 7/22/16.
 */
public class GetRequestFromServer extends  BaseCommunication {


    //eturn config['api']['base.url']+self.poster_sizes(img_size)+img_path

    private static String TAG="GetRequestFromServer";
    private HttpURLConnection httpConn;

    //26ec0c794803b24ca20910bef3e45993
    private String CONFIG_PATTERN = "https://api.themoviedb.org/3/configuration?api_key=%s";
    private String IMG_PATTERN = "https://api.themoviedb.org/3/movie/%s/images?api_key=%s";

    //https://api.themoviedb.org/3/movie/209112/images?api_key=26ec0c794803b24ca20910bef3e45993
    //"http://api.themoviedb.org/3/discover/movie?api_key=26ec0c794803b24ca20910bef3e45993&sort_by=popularity.desc";
    private String DISCOVER_PATTERN = "https://api.themoviedb.org/3/discover/movie?api_key=%s&sort_by=popularity.desc";

   // "/cGOPbv9wA5gEejkUN892JrveARt.jpg"
    //"http://image.tmdb.org/t/p/w500/o4I5sHdjzs29hBWzHtS2MKD3JsM.jpg"
    public GetRequestFromServer(String key){
        super(key);
    }



    public   String downloadConfig() throws Exception{
        initPreSSLConnectionData();
        String jsonOutput=null;

        URL url = new URL(String.format(CONFIG_PATTERN,key));
        httpConn = (HttpsURLConnection) url.openConnection();

        httpConn.setRequestMethod("GET");
        int responseCode = httpConn.getResponseCode();
        if (responseCode==HttpURLConnection.HTTP_OK) {
            jsonOutput=convertStreamToString(httpConn.getInputStream());
        }else{
            Log.e(TAG, "finish: "+convertStreamToString(httpConn.getErrorStream()));
            throw new IOException("Server returned non-OK status: " + responseCode);
        }
       // httpConn.disconnect();

        return jsonOutput;
    }


    public   String downloadMostPopularMovies() throws Exception{
        initPreSSLConnectionData();
        String jsonOutput=null;

        URL url = new URL(String.format(DISCOVER_PATTERN,key));
        httpConn = (HttpsURLConnection) url.openConnection();

        httpConn.setRequestMethod("GET");
        int responseCode = httpConn.getResponseCode();
        if (responseCode==HttpURLConnection.HTTP_OK) {
            jsonOutput=convertStreamToString(httpConn.getInputStream());
        }else{
            Log.e(TAG, "finish: "+convertStreamToString(httpConn.getErrorStream()));
            throw new IOException("Server returned non-OK status: " + responseCode);
        }
        return jsonOutput;
    }
}

