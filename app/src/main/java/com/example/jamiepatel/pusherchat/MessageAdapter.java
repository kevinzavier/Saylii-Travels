package com.example.jamiepatel.pusherchat;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;



public class MessageAdapter extends BaseAdapter {

    Context messageContext;
    List<Message> messageList;
    String profileColor;


    public MessageAdapter(Context context, List<Message> messages){
        messageList = messages;
        messageContext = context;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageViewHolder holder;
        if (convertView == null){
            LayoutInflater messageInflater = (LayoutInflater) messageContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = messageInflater.inflate(R.layout.message, null);
            holder = new MessageViewHolder();
            holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.img_thumbnail);
            holder.senderView = (TextView) convertView.findViewById(R.id.message_sender);
            holder.bodyView = (TextView) convertView.findViewById(R.id.message_body);

            convertView.setTag(holder);
        } else {
            holder = (MessageViewHolder) convertView.getTag();
        }

        Message message = (Message) getItem(position);

        holder.bodyView.setText(message.text);
        holder.senderView.setText(message.name);

//http://i.imgur.com/Tny2C2o.png THIS IS THE BLUE ONE
        //http://i.imgur.com/7Gem69x.png THIS IS ORANGE

        //Log.i("THE thumbnail", message.name);
        //Log.i("THE TEXT", message.text);

        //TODO maybe just use the ones that are saved, 'android.resource://com.kevinzavier/drawable/user.png'
        if(LoginActivity.username.equals(message.name)){
            profileColor = "http://i.imgur.com/Tny2C2o.png";
        }
        else {
            profileColor = "http://i.imgur.com/7Gem69x.png";
        }
        Picasso.with(messageContext).
                load(profileColor).
                placeholder(R.mipmap.ic_launcher).
                into(holder.thumbnailImageView);

        return convertView;
    }

    public void add(Message message){
        messageList.add(message);
        notifyDataSetChanged();
    }

    private static class MessageViewHolder {
        public ImageView thumbnailImageView;
        public TextView senderView;
        public TextView bodyView;
    }
}
