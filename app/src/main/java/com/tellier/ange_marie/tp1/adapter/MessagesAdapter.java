package com.tellier.ange_marie.tp1.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tellier.ange_marie.tp1.R;
import com.tellier.ange_marie.tp1.model.Message;

import java.util.List;

/**
 * Created by ange-marie on 25/10/17.
 */

public class MessagesAdapter extends BaseAdapter {

    Activity c;
    List<Message> messages;

    public MessagesAdapter(Activity c){
        this.c = c;
    }

    @Override
    public int getCount() {
        if (messages == null){
            return 0;
        }
        return messages.size();
    }

    @Override
    public Message getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if(view == null){
            LayoutInflater inflater = c.getLayoutInflater();
            view = inflater.inflate(R.layout.item_message, viewGroup, false);
            vh = new ViewHolder();
            vh.username = (TextView)view.findViewById(R.id.item_username);
            vh.message = (TextView)view.findViewById(R.id.item_message);
            vh.date = (TextView)view.findViewById(R.id.item_date);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.username.setText(getItem(i).getUsername());
        vh.message.setText(getItem(i).getMessage());
        vh.date.setText(String.valueOf(getItem(i).getDate()));

        return view;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        this.notifyDataSetChanged();
    }

    private class ViewHolder {
        TextView username, message, date;
    }
}
