package com.tellier.ange_marie.tp1.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.tellier.ange_marie.tp1.JSONParser;
import com.tellier.ange_marie.tp1.R;
import com.tellier.ange_marie.tp1.Session;
import com.tellier.ange_marie.tp1.adapter.UserAdapter;
import com.tellier.ange_marie.tp1.helper.NetworkHelper;
import com.tellier.ange_marie.tp1.model.Http_result;
import com.tellier.ange_marie.tp1.model.User;

import org.json.JSONException;

import java.util.List;

/**
 * Created by ange-marie on 25/10/17.
 */

public class UserFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle saved){
        super.onCreateView(inflater, viewGroup, saved);

        View v = inflater.inflate(R.layout.fragment_users, viewGroup, false);
        ListView list = (ListView) v.findViewById(R.id.usersList);
        Session.userAdapter = new UserAdapter(this.getActivity());
        list.setAdapter(Session.userAdapter);

        if(Session.token == null){
            // Dans le cas où il n'y a aucun token de valide on avertie l'utilisateur et on le quitte l'activité
            Toast.makeText(this.getActivity(), "Token invalide", Toast.LENGTH_SHORT).show();
            this.getActivity().finish();
        }

        new UserAsyncTask(this.getActivity()).execute();

        return v;
    }

    public class UserAsyncTask extends AsyncTask<String, Void, Http_result> {
        Context c;

        public UserAsyncTask(Context c){
            this.c = c;
        }

        @Override
        protected Http_result doInBackground(String... strings){
            if(!NetworkHelper.isInternetAvailable(c)){
                return null;
            }
            try {
                Http_result r = NetworkHelper.doGet("http://cesi.cleverapps.io/users", null, Session.token);

                if(r.status == 200){
                    return r;
                }
                return null;
            } catch (Exception e) {
                Log.e("NetworkHelper", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Http_result r){
            int nb = 0;
            if(r.status == 200){
                List<User> usrs = null;
                try {
                    usrs = JSONParser.getUsers(r.json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Session.userAdapter.setUsers(usrs);
            }
        }
    }
}
