package com.example.lyp1020k.launchmode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BAty extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baty);

        tv = (TextView) findViewById(R.id.tv);
        tv.setText(String.format("TaskID:%d\nCurrent Activity id%s", getTaskId(), toString()));

        findViewById(R.id.btnStartMainAty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BAty.this, MainActivity.class));
            }
        });

        findViewById(R.id.btnStartBAty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BAty.this, BAty.class));
            }
        });
    }
}
