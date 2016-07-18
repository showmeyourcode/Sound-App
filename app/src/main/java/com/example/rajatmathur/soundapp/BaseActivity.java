package com.example.rajatmathur.soundapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BaseActivity extends Activity {

    protected EditText editText;
    protected TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.activity);
        textView = (TextView) findViewById(R.id.text);
        if(this instanceof MainActivity) {
            textView.setText("AC_MAIN");
        } else if(this instanceof ActivityOne) {
            textView.setText("AC_ONE");
        } else if(this instanceof ActivityTwo) {
            textView.setText("AC_TWO");
        }
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("1".equals(editText.getText().toString())) {
                    Intent intent = new Intent(BaseActivity.this, ActivityOne.class);
                    startActivity(intent);
                    finish();
                } else if("2".equals(editText.getText().toString())) {
                    Intent intent = new Intent(BaseActivity.this, ActivityTwo.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(BaseActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Intent.ACTION_USER_PRESENT);
        if( (getIntent().hasCategory("android.intent.category.LAUNCHER") && "android.intent.action.MAIN".equals(getIntent().getAction())) ||
                (getSharedPreferences("My", Context.MODE_PRIVATE).getBoolean("closed", false))) {
            MediaPlayer mMediaPlayer = MediaPlayer.create(this, R.raw.beep13);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setLooping(false);
            mMediaPlayer.start();
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();

        SharedPreferences sharedPreferences = getSharedPreferences("My", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("closed", false);
        editor.apply();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        SharedPreferences sharedPreferences = getSharedPreferences("My", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("closed", true);
        editor.apply();
    }


}
