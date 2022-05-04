package com.example.okhttp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.okhttp.AppUtil;
import com.example.okhttp.R;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        loadData();

    }

    private void loadData() {
        if (AppUtil.isNeworkAvailable(this)){
            //Network Connected
            //load Data
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent =new Intent(splashscreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },3000);
        }else {
            //Network disconnnected
            Toast.makeText(this, "Network disconnnected", Toast.LENGTH_SHORT).show();
        }
    }
}
