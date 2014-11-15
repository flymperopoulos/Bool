package com.example.james.bool;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by james on 11/14/14.
 */
public class MainPageFragment extends Fragment {

    private Context context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }
    public MainPageFragment(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my, container, false);
        ListView listViewQuestion = (ListView) rootView.findViewById(R.id.listquestions);
        final ArrayList<String> questions = new ArrayList<String>();
        questions.add("Do you like Facebook or Google?");
        questions.add("Do you like Filippos or James?");
        final ArrayAdapter<String> questionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,questions);
        listViewQuestion.setAdapter(questionAdapter);

        listViewQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("What do you think?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue accessing library
                        questions.remove(i);
                        questionAdapter.notifyDataSetChanged();
                    }
                })

                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        questions.remove(i);
                        questionAdapter.notifyDataSetChanged();
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
            }
        });
        return rootView;
    }
}
