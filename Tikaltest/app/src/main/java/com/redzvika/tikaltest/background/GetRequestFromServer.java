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



    private static String TAG="GetRequestFromServer";
    private HttpURLConnection httpConn;


    private String CONFIG_PATTERN = "https://api.themoviedb.org/3/configuration?api_key=%s";
    private String IMG_PATTERN = "https://api.themoviedb.org/3/movie/%s/images?api_key=%s";
    private String DISCOVER_PATTERN = "https://api.themoviedb.org/3/discover/movie?api_key=%s&sort_by=popularity.desc";

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

