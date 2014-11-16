package com.example.james.bool;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


public class MyTabActivity extends Activity {

    ActionBar.Tab tab1, tab2, tab3;
    Fragment fragmentTab1 = new GroupFragment();
    Fragment fragmentTab2 = new MainPageFragment();
    Fragment fragmentTab3 = new ResultFragment();
    ActionBar actionBar;
    QuestionAdapter questionAdapter;
    QuestionAdapter myQuestionAdapter;

    Map<String, String> answered;

    ArrayList<String> questions;
    ArrayList<String> myQuestion;

    HttpRequestHandler httpRequestHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitytab_my);

        httpRequestHandler = new HttpRequestHandler(this);

        questions = new ArrayList<String>();
        questions.add("TO");
        myQuestion= new ArrayList<String>();
        questionAdapter = new QuestionAdapter(this, R.layout.question_item, questions);
        myQuestionAdapter = new QuestionAdapter(this, R.layout.question_item, myQuestion);

        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        tab1 = actionBar.newTab().setIcon(R.drawable.groups);
        tab2 = actionBar.newTab().setIcon(R.drawable.bull1);
        tab3 = actionBar.newTab().setIcon(R.drawable.profile);

//        tab1 = actionBar.newTab().setText("Group");
//        tab2 = actionBar.newTab().setText("Questions");
//        tab3 = actionBar.newTab().setText("Result");

        tab1.setTabListener(new MyTabListener(fragmentTab1));
        tab2.setTabListener(new MyTabListener(fragmentTab2));
        tab3.setTabListener(new MyTabListener(fragmentTab3));

        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.addTab(tab3);
        actionBar.selectTab(tab2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_questions:
                addQuestion();
                return true;
            case R.id.logout:
                Intent BeginMain = new Intent("android.intent.action.BEGINMAIN");
                startActivity(BeginMain);
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void addQuestion(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("New Question");
        final EditText edit = new EditText(this);
        alert.setView(edit);
        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String newQuestion = edit.getText().toString();
                newQuestion = newQuestion.replaceAll("^\\p{Punct}*|\\p{Punct}+$|\\p{Punct}{2,}", "");
                ArrayList<String> split = new ArrayList<String>(Arrays.asList(newQuestion.split(" ")));
                if (split.contains("or")){
                    String a = split.get(split.indexOf("or")-1);
                    String b = split.get(split.indexOf("or")+1);
                    httpRequestHandler.postQuestion(newQuestion, a, b);
                }
                else{
                    httpRequestHandler.postQuestion(newQuestion, "yes", "no");
                }
            }
        })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // do nothing
                }
            })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
