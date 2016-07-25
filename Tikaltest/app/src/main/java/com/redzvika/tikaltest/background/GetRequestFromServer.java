package com.redzvika.tikaltest.background;

import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by primeuser on 7/22/16.
 */
public class GetRequestFromServer {

    protected String key;
    private static String TAG="GetRequestFromServer";
    private HttpURLConnection httpConn;
    private String CONFIG_PATTERN = "https://api.themoviedb.org/3/configuration?api_key=%s";
    private String IMG_PATTERN = "https://api.themoviedb.org/3/movie/%s/images?api_key=%s";
    private String DISCOVER_PATTERN = "https://api.themoviedb.org/3/discover/movie?api_key=%s&sort_by=popularity.desc";

    public GetRequestFromServer(String key){
        this.key=key;
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
        }else {
            Log.e(TAG, "finish: " + convertStreamToString(httpConn.getErrorStream()));
            throw new IOException("Server returned non-OK status: " + responseCode);
        }
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


    private TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {

                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
                    // not implemented
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
                    // not implemented
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

            }
    };


    private void  initPreSSLConnectionData()throws  Exception{
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }

        });
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }


    private  String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}

