package com.tellier.ange_marie.tp1.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tellier.ange_marie.tp1.R;
import com.tellier.ange_marie.tp1.model.User;

import java.util.List;

/**
 * Created by ange-marie on 25/10/17.
 */

public class UserAdapter extends BaseAdapter {
    Activity act;
    List<User> users;

    public UserAdapter(Activity act) {
        this.act = act;
    }

    @Override
    public int getCount() {
        if(users == null){
            return 0;
        }
        return users.size();
    }

    @Override
    public User getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if(view == null){
            LayoutInflater inflater = act.getLayoutInflater();
            view = inflater.inflate(R.layout.item_user, viewGroup, false);
            vh = new ViewHolder();
            vh.username = (TextView)view.findViewById(R.id.item_username);
            vh.date = (TextView)view.findViewById(R.id.item_date);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        vh.username.setText(getItem(i).getUsername());
        vh.date.setText("Inscrit le "+String.valueOf(getItem(i).getDate()));

        return view;
    }

    public void setUsers(List<User> users) {
        this.users = users;
        this.notifyDataSetChanged();
    }


    private class ViewHolder {
        TextView username, date;
    }
}
