package com.tellier.ange_marie.tp1;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tellier.ange_marie.tp1.adapter.MessagesAdapter;
import com.tellier.ange_marie.tp1.helper.NetworkHelper;
import com.tellier.ange_marie.tp1.model.Http_result;
import com.tellier.ange_marie.tp1.model.Message;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagesActivity extends AppCompatActivity {
    MessagesAdapter adapter;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        final EditText msg = (EditText) findViewById(R.id.newmsg);
        ListView list = (ListView) findViewById(R.id.messages);

        adapter = new MessagesAdapter(MessagesActivity.this);
        list.setAdapter(adapter);
        token = this.getIntent().getExtras().getString("token");

        if(token == null) {
            Toast.makeText(MessagesActivity.this, "Token invalide", Toast.LENGTH_SHORT).show();
            MessagesActivity.this.finish();
        }

        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SendMsgAsyncTask(MessagesActivity.this).execute(msg.getText().toString());
            }
        });

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


    public class SendMsgAsyncTask extends AsyncTask<String, Void, Http_result> {
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
                Http_result r = NetworkHelper.doPost("http://cesi.cleverapps.io/messages", map, token);

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
