package com.example.james.bool;

import android.content.Context;
import android.widget.ArrayAdapter;

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

    private class ChatHolder {
        TextView name, body, time;
        ImageView picture;
        View background;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        ChatHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(resource, parent, false);
        holder = new ChatHolder();

        //TextViews
        holder.name = (TextView) view.findViewById(R.id.item_profile_name);
        holder.body = (TextView) view.findViewById(R.id.item_chat_body);
        holder.time = (TextView) view.findViewById(R.id.item_chat_time);

        //ImageViews
        holder.background = view.findViewById(R.id.item_profile_background);
        holder.picture = (ImageView) view.findViewById(R.id.item_profile_picture);
        fillViews(holder, chats.get(position));
        return view;
    }
    @Override
    public int getCount(){
        return this.chats.size();
    }

    @Override
    public Chat getItem(int position) {
        return this.chats.get(position);
    }

    private void fillViews(ChatHolder holder, Chat chat){
        holder.name.setText(chat.getName());
        holder.body.setText(chat.getMessage());
        holder.time.setText(String.valueOf(chat.getTime()));

        //holder.picture.setImageDrawable(getProfileDrawable(chat.userId));
    }


    public void addChat(Chat chat){
        this.chats.add(chat);
        notifyDataSetChanged();
    }
}

}
