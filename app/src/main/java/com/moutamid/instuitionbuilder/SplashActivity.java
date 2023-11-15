package com.moutamid.instuitionbuilder;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.google.firebase.auth.FirebaseAuth;
import com.moutamid.instuitionbuilder.Authentication.LoginActivity;
import com.moutamid.instuitionbuilder.Authentication.UserDetailsActivity;
import com.moutamid.instuitionbuilder.Home.WalkThroughActivity;
import com.moutamid.instuitionbuilder.onboadingOne.OnBoardingDesignOne;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences shared = getSharedPreferences("Record", MODE_PRIVATE);
                String boarding_view = (shared.getString("boarding_view", ""));
                if (!boarding_view.isEmpty()) {
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                                if (Stash.getString("name").isEmpty()) {
                                    startActivity(new Intent(SplashActivity.this, UserDetailsActivity.class));
                                    finish();
                                } else {

                                    startActivity(new Intent(SplashActivity.this, WalkThroughActivity.class));
                                    finish();
                                }
                    } else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }
                } else {
                    Intent i = new Intent(SplashActivity.this, OnBoardingDesignOne.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 2000);

    }
}