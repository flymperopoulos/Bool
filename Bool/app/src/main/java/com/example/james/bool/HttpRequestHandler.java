package com.example.james.bool;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

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
    ArrayList<String> myQuestionList;

    QuestionAdapter questionAdapter;
    QuestionAdapter myQuestionAdapter;

    Map<String, String> nameId;

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
        Log.d("adapter", Integer.toString(questionAdapter.getCount()));
        questionAdapter.reset();
        JsonArrayRequest jReq = new JsonArrayRequest(URL,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Boolean add = true;
                    Log.d("Going", "begin");
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            if(response.getJSONObject(i).getJSONArray("answersA").length() > 0){
                                JSONArray answeredA  = response.getJSONObject(i).getJSONArray("answersA");
                                for(int j = 0; j< answeredA.length(); j++){
                                    if((answeredA.get(j).toString().contains(id))){
                                        add = false;
                                        break;
                                    }
                                }
                            }
                            if(response.getJSONObject(i).getJSONArray("answersB").length() > 0){
                                JSONArray answeredB  = response.getJSONObject(i).getJSONArray("answersB");
                                for(int j = 0; j< answeredB.length(); j++){
                                    if ((answeredB.get(j).toString().contains(id))){
                                        add = false;
                                        break;
                                    }
                                }
                            }
                            if (add){
                                String s = response.getJSONObject(i).getString("question");
                                questionAdapter.addQuestions(s);
                                String userid = response.getJSONObject(i).getString("_id");
                                nameId.put(s, userid);
                            }

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

        myQuestionList = new ArrayList<String>();
        myQuestionAdapter = ((MyTabActivity)context).myQuestionAdapter;
        myQuestionAdapter.reset();
        if(myQuestionAdapter !=null) {
            Log.d("ADAPTER COUNT", Integer.toString(myQuestionAdapter.getCount()));
        }
        Log.d("MY ID", id);
        JsonArrayRequest jReq = new JsonArrayRequest("http://104.131.46.241:3000/questions/user/" + id,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            if(response.getJSONObject(i).getString("question") == null){

                            }else{
                                String s = response.getJSONObject(i).getString("question");
                                myQuestionAdapter.addQuestions(s);
                                Log.d("STRING", s);
                            }

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

    public void postCredentials(final String email1, final String password1){
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

        JSONObject object = new JSONObject();
        try{
            object.put("question", q);
            object.put("optionA", a);
            object.put("optionB", b);
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

        Log.d(q, URL + "/" + nameId.get(q));
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, URL+"/"+ nameId.get(q), obj,
                null, null);

        queue.add(jsonRequest);
    }
}

