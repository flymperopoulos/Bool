package com.example.james.bool;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by james on 11/14/14.
 */
public class MainPageFragment extends Fragment {

    Context context;
    ArrayList<String> questions;
    QuestionAdapter questionAdapter;
;

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

        final ImageView check = (ImageView) rootView.findViewById(R.id.check);
        final ImageView nope = (ImageView) rootView.findViewById(R.id.nope);


        questions = ((MyTabActivity)getActivity()).questions;
        questionAdapter = ((MyTabActivity)getActivity()).questionAdapter;
        httpRequestHandler = ((MyTabActivity)getActivity()).httpRequestHandler;

        httpRequestHandler.getQuestions();
        questions = httpRequestHandler.questionList;

        questionAdapter.notifyDataSetChanged();

        listViewQuestion.setAdapter(questionAdapter);

        listViewQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int i, long l) {
                String pickedQuestion = (String) listViewQuestion.getItemAtPosition(i);
                pickedQuestion = pickedQuestion.replaceAll("^\\p{Punct}*|\\p{Punct}+$|\\p{Punct}{2,}", "");
                ArrayList<String> split = new ArrayList<String>(Arrays.asList(pickedQuestion.split(" ")));
                listViewQuestion.setOnTouchListener(new OnSwipeTouchListener(context) {
                    public void onSwipeTop() {}

                    public void onSwipeRight() {

//                        nope.setVisibility(ImageView.VISIBLE);
                        httpRequestHandler.postAnswers("B", (String) listViewQuestion.getItemAtPosition(i));

//                        (rootView.findViewById(R.id.check)).setVisibility(ImageView.VISIBLE);
                        slideToLeft(view);
                        questionAdapter.removeQuestions(i);

                    }

                    public void onSwipeLeft() {
                        check.setVisibility(ImageView.VISIBLE);
                        httpRequestHandler.postAnswers("A",(String) listViewQuestion.getItemAtPosition(i));

                        slideToRight(view);

                        questionAdapter.removeQuestions(i);
                    }

                    public void onSwipeBottom() {}

                    public boolean onTouch(View v, MotionEvent event) {
                        return gestureDetector.onTouchEvent(event);
                    }
                });



//                if (split.contains("or")) {
//                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
//                    alert.setTitle("What do you think?");
//                    final String posAns =split.get(split.indexOf("or")-1);
//                    final String negAns =split.get(split.indexOf("or")-1);
//                    alert.setPositiveButton(posAns, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            // Continue accessing library
//
//                            httpRequestHandler.postAnswers("B", (String) listViewQuestion.getItemAtPosition(i));
//                            questionAdapter.removeQuestions(i);
//                            Log.d("BREAKING", "why");
//                        }
//                    })
//                            .setNegativeButton(negAns, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // do nothing
//
//                                    httpRequestHandler.postAnswers("A",(String) listViewQuestion.getItemAtPosition(i));
//                                    questionAdapter.removeQuestions(i);
//                                    Log.d("BREAKING", "why2");
//                                }
//                            })
//
//                            .setIcon(android.R.drawable.ic_dialog_alert)
//                            .show();
//                } else {
//
//                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
//                    alert.setTitle("What do you think?");
//                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            httpRequestHandler.postAnswers("B", (String) listViewQuestion.getItemAtPosition(i));
//                            questionAdapter.removeQuestions(i);
//
//                            Log.d("BREAKING", "why3");
//                        }
//                    })
//
//                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // do nothing
//
//                                    httpRequestHandler.postAnswers("A", (String) listViewQuestion.getItemAtPosition(i));
//                                    questionAdapter.removeQuestions(i);
//                                    Log.d("BREAKING", "why4");
//                                }
//                            })
//                            .setIcon(android.R.drawable.ic_dialog_alert)
//                            .show();
//                }
            }
        });

        return rootView;
    }
    public void slideToRight(View view){
        TranslateAnimation animate = new TranslateAnimation(0,view.getWidth(),0,0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }
    // To animate view slide out from right to left
    public void slideToLeft(View view){
        TranslateAnimation animate = new TranslateAnimation(0,-view.getWidth(),0,0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

}
