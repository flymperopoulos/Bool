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
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        ArrayList<String> questions = new ArrayList<String>();
        questions.add("Do you like Facebook or Google?");
        questions.add("Do you like Filippos or James?");
        ArrayAdapter<String> questionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,questions);
        listViewQuestion.setAdapter(questionAdapter);

        listViewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Set a Profile Picture");
                alert.setPositiveButton("Choose from Library", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue accessing library
                        startActivityForResult(new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), SELECT_IMAGE);
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
        });
        return rootView;

    }

}
