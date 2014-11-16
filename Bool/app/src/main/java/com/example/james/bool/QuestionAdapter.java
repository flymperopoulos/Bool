package com.example.james.bool;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by james on 11/15/14.
 */
public class QuestionAdapter extends ArrayAdapter<String>{

    private ArrayList<String> questions = new ArrayList<String>();
    private int resource;
    private Context context;


    public QuestionAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.questions = objects;

    }

    private class QuestionHolder {
        TextView name, body;
        ImageView check, nope;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        QuestionHolder holder = new QuestionHolder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(resource, parent, false);

        //TextViews
//        holder.name = (TextView) view.findViewById(R.id.item_profile_name);
        holder.body = (TextView) view.findViewById(R.id.question_item);
        holder.check = (ImageView) view.findViewById(R.id.check);
//        holder.nope = (ImageView) view.findViewById(R.id.nope);

        fillViews(holder, questions.get(position));
        return view;
    }
    @Override
    public int getCount(){
        return this.questions.size();
    }

    @Override
    public String getItem(int position) {
        return this.questions.get(position);
    }

    private void fillViews(QuestionHolder holder, String question){
        holder.body.setText(question);

//        holder.check.setImageDrawable();
    }
    public void addQuestions(String s){
//        this.questions = new ArrayList<String>();

        this.questions.add(s);
        notifyDataSetChanged();
    }
    public void removeQuestions(int pos){
//        this.questions = new ArrayList<String>();

        this.questions.remove(pos);
        notifyDataSetChanged();
    }
    public void reset(){
        this.questions = new ArrayList<String>();
    }

}
