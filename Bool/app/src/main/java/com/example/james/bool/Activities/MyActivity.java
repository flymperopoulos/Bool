package com.example.james.bool.Activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.james.bool.Fragments.EditProfileFragment;
import com.example.james.bool.Fragments.MainPageFragment;
import com.example.james.bool.Fragments.SignInFragment;
import com.example.james.bool.HttpRequestHandler;
import com.example.james.bool.R;


public class MyActivity extends Activity {

    public HttpRequestHandler httpRequestHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        httpRequestHandler = new HttpRequestHandler(this);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new SignInFragment())
                    .commit();
        }
    }

    public void changeToSignInFragment() {
        SignInFragment fragment = new SignInFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    public void changeToMainPageFragment() {
        MainPageFragment fragment = new MainPageFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    public void changeToEditProfile() {
        EditProfileFragment fragment = new EditProfileFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}
