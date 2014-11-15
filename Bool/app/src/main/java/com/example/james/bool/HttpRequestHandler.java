package com.example.james.bool;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by james on 11/15/14.
 */
public class HttpRequestHandler {

    Context context;
    RequestQueue queue;
    String URL;


    public HttpRequestHandler(Context context){
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
        URL = "something something";
    }

    public void getQuestions(){

    }

    public void getAnswers(){

    }
}
