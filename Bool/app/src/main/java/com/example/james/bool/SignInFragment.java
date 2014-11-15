package com.example.james.bool;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.security.spec.PSSParameterSpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by flymperopoulos on 11/15/2014.
 */
public class SignInFragment extends Fragment {

    HttpRequestHandler httpRequestHandler;
    private Context context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment, container, false);
        Button signUp = (Button) rootView.findViewById(R.id.sign_up_button);
        httpRequestHandler = ((MyActivity)getActivity()).httpRequestHandler;

        final TextView ForgotPassword = (TextView) rootView.findViewById(R.id.forgot_password);
        ForgotPassword.setMovementMethod(LinkMovementMethod.getInstance());

        final EditText Email = (EditText) rootView.findViewById(R.id.email);
        final EditText Password = (EditText) rootView.findViewById(R.id.password);

        final String emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

        final RelativeLayout relativeLayout = (RelativeLayout)rootView.findViewById(R.id.relativelayout);
        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(v==relativeLayout){
                    InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(Email.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(Password.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Checks if input type email is valid
                String email = Email.getText().toString();
                Pattern pattern = Pattern.compile(emailPattern);
                Matcher matcher = pattern.matcher(email);

                if (matcher.matches()&& Password.length()>8){
                    httpRequestHandler.postCredentials(email, Password.getText().toString());

                    MyActivity activity = (MyActivity) getActivity();
                    activity.changeToEditProfile();

                }
                else if(!matcher.matches() && Password.length()>8){
                    Toast.makeText(context,"Invalid email address",Toast.LENGTH_SHORT).show();
                }
                else if(matcher.matches() && Password.length() < 8) {
                    Toast.makeText(context, "Password less than 8 characters", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "Invalid email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }
}
