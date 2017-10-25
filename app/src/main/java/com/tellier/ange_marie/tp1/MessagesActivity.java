package com.tellier.ange_marie.tp1;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.tellier.ange_marie.tp1.adapter.MessagesAdapter;
import com.tellier.ange_marie.tp1.helper.NetworkHelper;
import com.tellier.ange_marie.tp1.model.Http_result;
import com.tellier.ange_marie.tp1.model.Message;

import org.json.JSONException;

import java.util.List;

public class MessagesActivity extends AppCompatActivity {
    MessagesAdapter adapter;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        ListView list = (ListView) findViewById(R.id.messages);

        adapter = new MessagesAdapter(MessagesActivity.this);
        list.setAdapter(adapter);

        token = this.getIntent().getExtras().getString("token");

        new GetMessageAsyncTask(MessagesActivity.this).execute();

    }

   public class GetMessageAsyncTask extends AsyncTask<String, Void, Http_result>{
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
               Http_result r = NetworkHelper.doGet("http://cesi.cleverapps.io/messages", null, token);

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

               adapter.setMessages(msgs);
           }
       }

   }


}
