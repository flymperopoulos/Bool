package com.example.james.bool;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by james on 11/15/14.
 */
public class ResultFragment extends Fragment {


    ArrayList<String> myQuestions;
    QuestionAdapter myQuestionAdapter;
    HttpRequestHandler httpRequestHandler;

    public ResultFragment(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.result_fragment, container, false);

        myQuestions = ((MyTabActivity)getActivity()).myQuestion;
        myQuestionAdapter = ((MyTabActivity)getActivity()).myQuestionAdapter;
        httpRequestHandler = ((MyTabActivity)getActivity()).httpRequestHandler;

        ListView myQuestionListView = (ListView) rootView.findViewById(R.id.myquestionList);
        myQuestionListView.setAdapter(myQuestionAdapter);

        httpRequestHandler.getAnswers();





        return rootView;
    }
}
