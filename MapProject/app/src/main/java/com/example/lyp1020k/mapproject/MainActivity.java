package com.example.lyp1020k.mapproject;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.et);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SpeechUtility.createUtility(MainActivity.this, "appid=" + getString(R.string.app_id));
                //初始化 SDK
                SpeechSynthesizer speaker = SpeechSynthesizer.createSynthesizer(MainActivity.this, null);//创建语音合成对象
                speaker.setParameter(SpeechConstant.VOICE_NAME,"xiaoyan");
                //初始化语音合成相关设置
                speaker.setParameter(SpeechConstant.SPEED,"50");
                speaker.setParameter(SpeechConstant.PITCH,"50");
                speaker.setParameter(SpeechConstant.VOLUME,"50");
                speaker.setParameter(SpeechConstant.STREAM_TYPE,"3");
                speaker.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
                String speak = editText.getText().toString();
                speaker.startSpeaking(speak, null);
            }
        });
    }
}
