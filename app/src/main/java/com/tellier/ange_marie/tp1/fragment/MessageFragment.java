package com.tellier.ange_marie.tp1.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.tellier.ange_marie.tp1.JSONParser;
import com.tellier.ange_marie.tp1.MessagesActivity;
import com.tellier.ange_marie.tp1.R;
import com.tellier.ange_marie.tp1.Session;
import com.tellier.ange_marie.tp1.UsersActivity;
import com.tellier.ange_marie.tp1.adapter.MessagesAdapter;
import com.tellier.ange_marie.tp1.helper.NetworkHelper;
import com.tellier.ange_marie.tp1.model.Http_result;
import com.tellier.ange_marie.tp1.model.Message;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ange-marie on 25/10/17.
 */

public class MessageFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle saved){
        super.onCreateView(inflater, viewGroup, saved);

        View v = inflater.inflate(R.layout.fragment_messages, viewGroup, false);

        Session.adapterMsg = new MessagesAdapter(this.getActivity());

        ListView list = (ListView) v.findViewById(R.id.messages);
        list.setAdapter(Session.adapterMsg);

        final EditText msg = (EditText) v.findViewById(R.id.newmsg);

        if(Session.token == null) {
            Toast.makeText(this.getActivity(), "Token invalide", Toast.LENGTH_SHORT).show();
            this.getActivity().finish();
        }

        v.findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SendMsgAsyncTask(MessageFragment.this.getActivity()).execute(msg.getText().toString());
            }
        });

        new MessageFragment.GetMessageAsyncTask(this.getActivity()).execute();
        return v;
    }

    public static class GetMessageAsyncTask extends AsyncTask<String, Void, Http_result> {
        Context c;
        public GetMessageAsyncTask(final Context c){
            this.c = c;
        }

        @Override
        protected Http_result doInBackground(String... strings){
            if(!NetworkHelper.isInternetAvailable(c)){
                return null;
            }
            try {
                Http_result r = NetworkHelper.doGet("http://cesi.cleverapps.io/messages", null, Session.token);

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
                List<Message> msgs = null;
                try {
                    msgs = JSONParser.getMessages(r.json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Session.adapterMsg.setMessages(msgs);
            }
        }

    }


    public static class SendMsgAsyncTask extends AsyncTask<String, Void, Http_result> {
        Context c;

        public SendMsgAsyncTask(Context c){
            this.c = c;
        }

        protected Http_result doInBackground(String... strings){
            if(!NetworkHelper.isInternetAvailable(c)){
                return null;
            }

            try {
                Map<String, String > map = new HashMap();
                map.put("message", strings[0]);
                Http_result r = NetworkHelper.doPost("http://cesi.cleverapps.io/messages", map, Session.token);

                return r;
            }catch (Exception e){
                Log.e("NetworkHelper", e.getMessage());
                return null;
            }
        }

        protected void onPostExecute(Http_result r){
            if(r.status != 200){
                Toast.makeText(c, "Message non envoyé", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(c, "Message envoyé", Toast.LENGTH_SHORT).show();
                new GetMessageAsyncTask(c).execute();
            }
        }
    }
}
