package com.example.james.bool;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
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
    QuestionAdapter questionAdapter;
    String id;
    Map<String, String> nameId;


    public HttpRequestHandler(Context context){
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
        URL = "http://104.131.46.241:3000/questions";
        nameId = new HashMap<String, String>();


    }

    public void getQuestions(){
        questionAdapter = ((MyTabActivity)context).questionAdapter;
        Log.d("adapter", questionAdapter.toString());
        questionAdapter.reset();
        ArrayList<String> yo = new ArrayList<String>();
        JsonArrayRequest jReq = new JsonArrayRequest(URL,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            String s = response.getJSONObject(i).getString("question");
                            Log.d("BITCH", s);
                            questionAdapter.addQuestions(s);

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
    public void postQuestion(String q, String a, String b) {
        // Create a new HttpClient and Post Header
        JSONObject obj = createJSON(q, a, b);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL, obj,
                new Response.Listener<JSONObject>(){
                    public void onResponse(JSONObject response){
                        Log.d("JSON returning", response.toString());
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                error.printStackTrace();

            }
        });
        queue.add(jsonRequest);

    }
    public JSONObject createJSON(String q, String a, String b){
        id = ((MyTabActivity)context).id;
        JSONObject object = new JSONObject();
        try{
            Map<String, String> temp = new HashMap<String, String>();
            object.put("question", q);
            object.put("optionA", a);
            object.put("optionB", b);
            object.put("answersA", temp);
            object.put("answersB", temp);
            object.put("poster", id);
//            object.put("timestamp", System.currentTimeMillis());

        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return object;
    }

    public void postAnswers(String answer, String q){

//        JSONObject obj = createJSON(q, a, b);
//        Map<String>
//        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL, obj,
//                new Response.Listener<JSONObject>(){
//                    public void onResponse(JSONObject response){
//                        Log.d("JSON returning", response.toString());
//                    }
//                }, new Response.ErrorListener(){
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // Handle error
//                error.printStackTrace();
//
//            }
//        });
//
//        queue.add(jsonRequest);
    }
}
