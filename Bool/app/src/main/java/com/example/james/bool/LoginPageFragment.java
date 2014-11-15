package com.example.james.bool;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by james on 11/15/14.
 */
public class LoginPageFragment extends Fragment {

    Context context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment, container, false);
        Button signIn = (Button) rootView.findViewById(R.id.sign_in_button);
        Button signUp = (Button) rootView.findViewById(R.id.sign_up_button);
        

        return rootView;
    }
}
