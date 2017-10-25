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
    UserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
    }
}
