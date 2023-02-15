package com.antplay.antplayApp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.antplay.antplayApp.R;


public class ThanksActivity extends AppCompatActivity {
    TextView txtRegister;
    boolean isBackPressed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_thanks);
        txtRegister = findViewById(R.id.txtRegisterAgain);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBackPressed = true;
                Intent i = new Intent(ThanksActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

     /*   new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be execute once the timer is over
                if (!isBackPressed) {
                    Intent i = new Intent(ThanksActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 4000);*/
    }
}