package com.example.wyb.anti_abuse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button core_button = (Button) findViewById(R.id.core_button);
        Button heart_button = (Button) findViewById(R.id.heart_button);
        Button sound_button = (Button) findViewById(R.id.sound_button);
        Button user_button = (Button) findViewById(R.id.user_button);
        core_button.setOnClickListener(this);
        sound_button.setOnClickListener(this);
        user_button.setOnClickListener(this);
        heart_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.core_button:
                break;
            case R.id.heart_button:
                break;
            case R.id.sound_button:
                break;
            case R.id.user_button:
                break;
        }
    }
}
