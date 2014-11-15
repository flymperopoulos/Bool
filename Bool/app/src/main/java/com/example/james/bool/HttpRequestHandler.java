package com.example.james.bool;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
* Created by james on 11/15/14.
*/
public class HttpRequestHandler {

    Context context;
    RequestQueue queue;
    String URL;
    ArrayList<String> questionList;
    ArrayList<String> answerList;


    public HttpRequestHandler(Context context){
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
        URL = "http://104.131.46.241:3000/questions";

    }

    public void getQuestions(){
        questionList = new ArrayList<String>();
        ArrayList<String> yo = new ArrayList<String>();
        JsonArrayRequest jReq = new JsonArrayRequest(URL,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            String s = response.getJSONObject(i).getString("question");
                            Log.d("BITCH", s);
                            questionList.add(s);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error

            }
        });
        queue.add(jReq);


    }

    public void getAnswers(){
        final ArrayList<String> answerList = new ArrayList<String>();
        JsonArrayRequest jReq = new JsonArrayRequest(URL,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            String s = response.getJSONObject(i).getString("answers");
                            Log.d("BITCH", s);
                            answerList.add(s);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error

            }
        });
        queue.add(jReq);

    }
}

