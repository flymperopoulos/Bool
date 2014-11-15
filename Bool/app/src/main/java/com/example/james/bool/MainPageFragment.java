package com.example.james.bool;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by james on 11/14/14.
 */
public class MainPageFragment extends Fragment {

    public MainPageFragment(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_my, container, false);
    return rootView;
        
    }

}
