package com.example.james.bool;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by james on 11/14/14.
 */
public class MainPageFragment extends Fragment {

    public MainPageFragment(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my, container, false);
        ListView listViewQuestion = (ListView) rootView.findViewById(R.id.listquestions);
        ArrayList<String> questions = new ArrayList<String>();
        questions.add("Do you like Facebook or Google?");
        questions.add("Do you like Filippos or James?");
        ArrayAdapter<String> questionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, );
        listViewQuestion.setAdapter(questionAdapter);


        return rootView;

    }

}
