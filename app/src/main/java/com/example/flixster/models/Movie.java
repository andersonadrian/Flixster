package com.example.flixster.models;

import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class Movie {
    String posterPath;
    String backdropPath;
    String title;
    String overview;
    private String url;
    private String pictureSize;

    public final String CONFIG_API = "https://api.themoviedb.org/3/configuration?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MovieActivity";



    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        backdropPath = jsonObject.getString("backdrop_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");

    }
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int counter = 0; counter < movieJsonArray.length(); counter++){
                movies.add(new Movie(movieJsonArray.getJSONObject(counter)));
        }
        return movies;
    }

    public String getAPIConfig(){
        AsyncHttpClient client_2 = new AsyncHttpClient();
        client_2.get(CONFIG_API, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONObject imagesObject = jsonObject.getJSONObject("images");
                    String secureBaseURL = imagesObject.getString("secure_base_url");
                    JSONArray posterSizesArray = imagesObject.optJSONArray("poster_sizes");
                    String posterSize = posterSizesArray.getString(3);
                    Log.d(TAG, posterSize);
                    url = secureBaseURL;
                    pictureSize = posterSize;


                } catch (JSONException e) {
                    Log.e(TAG, "Hit JSON exception", e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
        return url + pictureSize;
    }


    public String getPosterPath() {
        return getAPIConfig() + posterPath;
    }

    public String getBackdropPath() {
        return getAPIConfig() + backdropPath;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
