package com.tellier.ange_marie.tp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class secondActivity extends AppCompatActivity {

    public static String KEY = "my_key_content_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView txt = (TextView) findViewById(R.id.txt);

        String value = this.getIntent().getExtras().getString(KEY);
        txt.setText(value);
    }
}
