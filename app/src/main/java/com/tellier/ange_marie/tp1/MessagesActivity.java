package com.tellier.ange_marie.tp1;

import android.content.Context;
import android.content.Intent;
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
import com.tellier.ange_marie.tp1.fragment.MessageFragment;
import com.tellier.ange_marie.tp1.helper.NetworkHelper;
import com.tellier.ange_marie.tp1.model.Http_result;
import com.tellier.ange_marie.tp1.model.Message;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagesActivity extends AppCompatActivity {

    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        findViewById(R.id.btnUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MessagesActivity.this, UsersActivity.class);
                startActivity(i);
            }
        });
    }
}
