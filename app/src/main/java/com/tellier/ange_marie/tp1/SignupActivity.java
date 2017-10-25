package com.tellier.ange_marie.tp1;

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

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText pwd = (EditText) findViewById(R.id.pwd);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SignupAsyncTask().execute(username.getText().toString(), pwd.getText().toString());
            }
        });
    }

    public class SignupAsyncTask extends AsyncTask<String, Void, Http_result> {
        protected Http_result doInBackground(String... strings) {
            if(!NetworkHelper.isInternetAvailable(SignupActivity.this)){
                return new Http_result(0, null);
            }
            Map<String, String > params = new HashMap<>();
            params.put("username", strings[0]);
            params.put("pwd", strings[1]);
            Http_result r = NetworkHelper.doPost("http://cesi.cleverapps.io/signup",params,null);
            return r;
        }

        protected void onPostExecute(Http_result s){
            if(s.status == 200) {
                Toast.makeText(SignupActivity.this, "Create account success", Toast.LENGTH_SHORT).show();
                SignupActivity.this.finish();
            } else {
                Toast.makeText(SignupActivity.this, "Create account error", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
