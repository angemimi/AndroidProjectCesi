package com.tellier.ange_marie.tp1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tellier.ange_marie.tp1.helper.NetworkHelper;
import com.tellier.ange_marie.tp1.model.Http_result;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final EditText txt = (EditText) findViewById(R.id.txt);

        findViewById(R.id.btnHello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HelloAsyncTask().execute(txt.getText().toString());
            }
        });

        findViewById(R.id.btnPing).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new PingAsyncTask().execute();
            }
        });
    }

    public class HelloAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings){
            if(!NetworkHelper.isInternetAvailable(HomeActivity.this)){
                return "Error";
            }
            Map<String, String>params = new HashMap<>();
            params.put("name", strings[0]);
            Http_result r = NetworkHelper.doGet("http://cesi.cleverapps.io/hello", params, null);
            return r.json;
        }

        @Override
        protected void onPostExecute(String s){
            Toast.makeText(HomeActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    }

    public class PingAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings){
            if(!NetworkHelper.isInternetAvailable(HomeActivity.this)) {
                return "Error";
            }
            Http_result r = NetworkHelper.doPost("http://cesi.cleverapps.io/ping", null, null);
            return r.json;
        }

        @Override
        protected void onPostExecute(String s){
            Toast.makeText(HomeActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    }
}
