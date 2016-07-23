package com.redzvika.tikaltest.background;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by primeuser on 7/22/16.
 */
public class ConfigurationData {

    private ConfigurationData(){

    }

    public static String BASE_URL="base_url";
    public static String SECURE_BASE_URL="secure_base_url";
    public static String BACKDROP_SIZES="backdrop_sizes";
    public static String LOGO_SIZES="logo_sizes";
    public static String POSTER_SIZES="poster_sizes";
    public static String STILL_SIZES="still_sizes";
    public static String PROFILE_SIZES="profile_sizes";


    public  String base_url;
    public  String secure_base_url;
    public String[] backdrop_sizes;
    public String[] logo_sizes;
    public String[] poster_sizes;
    public String[] still_sizes;
    public String[] profile_sizes;


    public static ConfigurationData  parseConfig(String jsonString) throws Exception{
        ConfigurationData configurationData=new ConfigurationData();

        JSONObject mainObject = new JSONObject(jsonString);
        JSONObject images= mainObject.optJSONObject("images");

        if (images.has(ConfigurationData.BASE_URL)) {
            configurationData.base_url=images.getString(ConfigurationData.BASE_URL);
        }

        if (images.has(ConfigurationData.SECURE_BASE_URL)) {
            configurationData.secure_base_url=images.getString(ConfigurationData.SECURE_BASE_URL);
        }
        if (images.has(ConfigurationData.BACKDROP_SIZES)){
            configurationData.backdrop_sizes=stringOfArraysFromJson(images.getJSONArray(ConfigurationData.BACKDROP_SIZES));
        }

        if (images.has(ConfigurationData.LOGO_SIZES)){
            configurationData.logo_sizes=stringOfArraysFromJson(images.getJSONArray(ConfigurationData.LOGO_SIZES));
        }

        if (images.has(ConfigurationData.POSTER_SIZES)){
            configurationData.poster_sizes=stringOfArraysFromJson(images.getJSONArray(ConfigurationData.POSTER_SIZES));
        }

        if (images.has(ConfigurationData.STILL_SIZES)){
            configurationData.still_sizes=stringOfArraysFromJson(images.getJSONArray(ConfigurationData.STILL_SIZES));
        }

        if (images.has(ConfigurationData.PROFILE_SIZES)){
            configurationData.profile_sizes=stringOfArraysFromJson(images.getJSONArray(ConfigurationData.PROFILE_SIZES));
        }

        return configurationData;
    }


    private static String [] stringOfArraysFromJson(JSONArray jsonArray) throws  Exception{
        String [] output=new String[jsonArray.length()];
        for (int j = 0; j < jsonArray.length(); j++) {
            String data = jsonArray.getString(j);
            output[j]=data;

        }
        return output;

    }
}
