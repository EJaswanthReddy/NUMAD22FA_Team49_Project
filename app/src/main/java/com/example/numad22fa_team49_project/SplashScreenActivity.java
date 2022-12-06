package com.example.numad22fa_team49_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("storeHunt", MODE_PRIVATE);

        FirebaseUser user = mAuth.getCurrentUser();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(user == null){
                    startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));
                    finish();
                }else if(sharedPreferences.getBoolean("asSeller",false)){
                    startActivity(new Intent(SplashScreenActivity.this,SellerProfileActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(SplashScreenActivity.this,HomeActivity.class));
                    finish();
                }
            }
        },1000);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}