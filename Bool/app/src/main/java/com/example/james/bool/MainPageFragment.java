package com.example.james.bool;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.Arrays;

/**
 * Created by james on 11/14/14.
 */
public class MainPageFragment extends Fragment {

    Context context;
    ArrayList<String> questions;
    QuestionAdapter questionAdapter;
    RequestQueue queue;
    JSONArray questionList;

    HttpRequestHandler httpRequestHandler;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }
    public MainPageFragment(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_my, container, false);
        final ListView listViewQuestion = (ListView) rootView.findViewById(R.id.listquestions);
        questions = ((MyTabActivity)getActivity()).questions;
        questionAdapter = ((MyTabActivity)getActivity()).questionAdapter;
        httpRequestHandler = ((MyTabActivity)getActivity()).httpRequestHandler;

        httpRequestHandler.getQuestions();
        questions = httpRequestHandler.questionList;



        questionAdapter.notifyDataSetChanged();

        listViewQuestion.setAdapter(questionAdapter);

        listViewQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                String pickedQuestion = (String) listViewQuestion.getItemAtPosition(i);
                pickedQuestion = pickedQuestion.replaceAll("^\\p{Punct}*|\\p{Punct}+$|\\p{Punct}{2,}", "");
                ArrayList<String> split = new ArrayList<String>(Arrays.asList(pickedQuestion.split(" ")));
                if (split.contains("or")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("What do you think?");
                    alert.setPositiveButton(split.get(split.indexOf("or")-1), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue accessing library
                            questions.remove(i);
                            questionAdapter.notifyDataSetChanged();
                        }
                    })
                            .setNegativeButton(split.get(split.indexOf("or")+1), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                    questions.remove(i);
                                    questionAdapter.notifyDataSetChanged();
                                }
                            })

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {

                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("What do you think?");
                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue accessing library
                            questions.remove(i);
                            questionAdapter.notifyDataSetChanged();
                        }
                    })

                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                    questions.remove(i);
                                    questionAdapter.notifyDataSetChanged();
                                }
                            })

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });


        String URL = "http://104.131.46.241:3000/questions";

        queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jReq = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<String> result = new ArrayList<String>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                String s = response.getJSONObject(i).getString("question");
                                Log.d("BITCH", s);
                                questions.add(s);
                            questionAdapter.addQuestions(s);
                                questionAdapter.notifyDataSetChanged();


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






//        listViewQuestion.setOnTouchListener(new OnSwipeTouchListener(getActivity()){
//            public void onSwipeTop() {}
//
//            public void onSwipeRight() {
//                Toast.makeText(getActivity(), "right", Toast.LENGTH_SHORT).show();
//            }
//
//            public void onSwipeLeft() {
//                Toast.makeText(getActivity(), "left", Toast.LENGTH_SHORT).show();
//            }
//
//            public void onSwipeBottom() {}
//        });

        return rootView;
    }
}
