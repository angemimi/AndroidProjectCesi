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

public class SigninActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        final EditText username = (EditText) findViewById(R.id.username);
        final EditText pwd = (EditText) findViewById(R.id.pwd);

        findViewById(R.id.btnSignin).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new SignInAsynTask().execute(username.getText().toString(), pwd.getText().toString());
            }
        });

        findViewById(R.id.btnSignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });
    }

    public class SignInAsynTask extends AsyncTask<String, Void, Http_result> {
        protected Http_result doInBackground(String... strings) {
            if(!NetworkHelper.isInternetAvailable(SigninActivity.this)){
                return new Http_result(0, null);
            }
            Map<String, String> params = new HashMap<>();
            params.put("username", strings[0]);
            params.put("pwd", strings[1]);
            Http_result r = NetworkHelper.doPost("http://cesi.cleverapps.io/signin", params, null);
            return r;
        }
        protected void onPostExecute(Http_result r){
            if(r.status != 200) {
                Toast.makeText(SigninActivity.this, "Authenticated error", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent(SigninActivity.this, MessagesActivity.class);
                i.putExtra("token", JSONParser.getToken(r.json));
                startActivity(i);
            }
        }
    }
}
