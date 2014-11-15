package com.example.james.bool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by james on 11/14/14.
 */
public class IntroActivity extends Activity {
    @Override
    protected void onCreate(Bundle FilipposIntro) {
        super.onCreate(FilipposIntro);
        setContentView(R.layout.introactivity);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent BeginMain = new Intent("android.intent.action.BEGINMAIN");
                    startActivity(BeginMain);
                }
            }
        };
        timer.start();
    }
}
