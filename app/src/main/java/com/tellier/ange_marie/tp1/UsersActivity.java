package com.tellier.ange_marie.tp1;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.tellier.ange_marie.tp1.adapter.UserAdapter;
import com.tellier.ange_marie.tp1.helper.NetworkHelper;
import com.tellier.ange_marie.tp1.model.Http_result;
import com.tellier.ange_marie.tp1.model.User;

import org.json.JSONException;

import java.util.List;

public class UsersActivity extends AppCompatActivity {
    String token;
    UserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        token = this.getIntent().getExtras().getString("token");

        ListView list = (ListView) findViewById(R.id.usersList);
        adapter = new UserAdapter(UsersActivity.this);
        list.setAdapter(adapter);
        if(token == null){
            // Dans le cas où il n'y a aucun token de valide on avertie l'utilisateur et on le quitte l'activité
            Toast.makeText(this, "Token invalide", Toast.LENGTH_SHORT).show();
            UsersActivity.this.finish();
        }

        new UserAsyncTask(UsersActivity.this).execute();

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
                Http_result r = NetworkHelper.doGet("http://cesi.cleverapps.io/users", null, token);

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

                adapter.setUsers(usrs);
            }
        }
    }
}
