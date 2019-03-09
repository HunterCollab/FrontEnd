package com.example.socialmediaapp.loopjtasks;

import android.content.Context;

import com.example.socialmediaapp.config.GlobalConfig;
import com.example.socialmediaapp.tools.GeneralTools;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class DoSkillSearch {

    private final Context context;
    private final OnDoSkillSearchComplete searchListener;

    public DoSkillSearch(Context context, OnDoSkillSearchComplete listener) {
        this.context = context;
        this.searchListener = listener;

    }

    public void doSkillSearch(String constraint){


        AsyncHttpClient asyncHttpClient = GeneralTools.createAsyncHttpClient(context);

        final RequestParams requestParams = new RequestParams();
        requestParams.put("query", constraint);

        asyncHttpClient.get(GlobalConfig.BASE_API_URL + "/search/skills", requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                ArrayList<String> stringList = new ArrayList<>();

                System.out.println("Response: " + response);

                try {
                    JSONArray terms = null;
                    terms = response.getJSONArray("matches");

                    System.out.println("here");
                    for(int i=0; i < terms.length(); i++){
                        String term = terms.getString(i);
                        System.out.println("term: " + term);
                        stringList.add(term);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("skillList: " + stringList);
                searchListener.searchSkillComplete(stringList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                ArrayList<String> stringList = new ArrayList<>();
                stringList.add("No match");
                searchListener.searchSkillComplete(stringList);
            }
        });

    }

    // interface
    // abstract function declared here
    public interface  OnDoSkillSearchComplete {
        public void searchSkillComplete(ArrayList<String> message);
    }

}
