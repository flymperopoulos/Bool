package com.example.james.bool;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ClearCacheRequest;
import com.android.volley.toolbox.DiskBasedCache;
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

import java.io.File;
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

    Map<String, String> nameId;
    boolean credentials;

    public static String id;

    public static String password;
    public static String email;


    public HttpRequestHandler(Context context){
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
        URL = "http://104.131.46.241:3000/questions";
    }

    public void getQuestions(){
        questionAdapter = ((MyTabActivity)context).questionAdapter;
        nameId = new HashMap<String, String>();
        Log.d("adapter", questionAdapter.toString());
        questionAdapter.reset();
        ArrayList<String> yo = new ArrayList<String>();
        JsonArrayRequest jReq = new JsonArrayRequest(URL,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
//                            JSONArray answeredA  = response.getJSONObject(i).getJSONArray("answersA");
//                            JSONArray answeredb  = response.getJSONObject(i).getJSONArray("answersB");
//                            for(int j -)


                            String s = response.getJSONObject(i).getString("question");
                            Log.d("BITCH", s);
                            questionAdapter.addQuestions(s);
                            String userid = response.getJSONObject(i).getString("_id");

                            nameId.put(s ,userid);

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

    public boolean postCredentials(final String email1, final String password1){
        JSONObject obj = new JSONObject();


        try {
            obj.put("email",email1);
            obj.put("password",password1);
//            obj.put("firstName", "Sawyer");
//            obj.put("lastName", "Vaughaun");
            Log.d("JSON putting", obj.toString());
        } catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, "http://104.131.46.241:3000/mobilelogin", obj,
                new Response.Listener<JSONObject>(){
                    public void onResponse(JSONObject response){
                            Log.d("RSPONDLSKJFLSKDJFLSDFSDFSD", Integer.toString(response.length()));

                            if(response.length() > 4){
                                try{
                                    id = response.getString("_id");
                                    password = response.getString("password");
                                    email = email1;
                                    Intent BeginMain = new Intent("android.intent.action.LATERMAIN");
                                    BeginMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(BeginMain);
                                }catch(JSONException e){

                                }
                            } else {
                                 try {
                                    if(!(Boolean)(response.get("valid"))) {
                                        Toast.makeText(context, "Invalid Login", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                     else {
                                        password = password1;
                                        email = email1;
                                        MyActivity activity = ((MyActivity) context);
                                        activity.changeToEditProfile();
                                    }
                                } catch (JSONException e) {
                                     e.printStackTrace();
                                }


                                credentials = false;

                            }

                            Log.d("JSON returning", response.toString());


                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        queue.add(jsonRequest);
        return credentials;
    }
    public void signUp(Map<String, String> info){
        Log.d("LENGTH", Integer.toString(info.size()));
        Log.d("LENGTH", info.toString());
        JSONObject obj = new JSONObject();
        try {
            obj.put("password", password);
            obj.put("firstName", info.get("firstName"));
            obj.put("lastName", info.get("lastName"));
            obj.put("age", info.get("age"));
            obj.put("gender", info.get("sex"));
            obj.put("race", info.get("race"));
            obj.put("city", info.get("city"));
            obj.put("state", info.get("state"));
            obj.put("occupation", info.get("occupation"));
            obj.put("email", email);
        }catch (JSONException e){

        }
        Log.d("JSON OBJECT" , obj.toString());
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, "http://104.131.46.241:3000/mobilesignup",  obj,
                new Response.Listener<JSONObject>(){
                    public void onResponse(JSONObject response){
                        Log.d("JSON returning", response.toString());
                        try{
                            id= response.getString("_id");
                            Intent BeginMain = new Intent("android.intent.action.LATERMAIN");
                            BeginMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(BeginMain);
                        }catch (JSONException e){
                        }
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                error.printStackTrace();

            }
        }) {
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError{
            Map<String, String> params = new HashMap<String, String>();
            params.put("Content-Type", "application/json");
            return params;
        }
    };
        queue.add(jsonRequest);


    }

    public JSONObject createJSON(String q, String a, String b){
//        id = ((MyTabActivity)context).id;

        JSONObject object = new JSONObject();
        try{
            ArrayList<String> temp = new ArrayList<String>();
            object.put("question", q);
            object.put("optionA", a);
            object.put("optionB", b);
            object.put("answersA", temp);
            object.put("answersB", temp);
            object.put("poster", id);
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return object;
    }

    public void postAnswers(String answer, String q){

        JSONObject obj = new JSONObject();
        try {
            obj.put("answer", answer);
            obj.put("userid", id);
        }catch(JSONException e){
            e.printStackTrace();
        }

        Log.d("ID ID", URL + "/" + nameId.get(q));
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, URL+"/"+ nameId.get(q), obj,
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
}

