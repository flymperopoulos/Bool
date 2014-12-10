package com.example.james.bool.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.james.bool.R;

/**
 * Created by james on 11/15/14.
 */
public class GroupFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.group_fragment, container, false);



        return rootView;
    }
}
