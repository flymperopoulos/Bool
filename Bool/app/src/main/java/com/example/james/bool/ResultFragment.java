package com.example.james.bool;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by james on 11/15/14.
 */
public class ResultFragment extends Fragment {

    public ResultFragment(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.result_fragment, container, false);



        return rootView;
    }
}
