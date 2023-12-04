package com.moutamid.instuitionbuilder;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.instuitionbuilder.Authentication.LoginActivity;
import com.moutamid.instuitionbuilder.Authentication.OnBoarding.OnBoardingActivity;
import com.moutamid.instuitionbuilder.Authentication.UserDetailsActivity;
import com.moutamid.instuitionbuilder.Home.IntroActivity;
import com.moutamid.instuitionbuilder.Home.WalkThroughActivity;
import com.moutamid.instuitionbuilder.Model.OnBoardingModel;
import com.moutamid.instuitionbuilder.config.Config;


public class SplashActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;
    OnBoardingModel onBoardingModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("IntuitionBuilder");
        onBoardingModel = new OnBoardingModel();

        if (Config.isNetworkAvailable(SplashActivity.this)) {
            databaseReference.child("OnBoardingText").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        String key = itemSnapshot.getKey();
                        String title = itemSnapshot.child("title").getValue(String.class);
                        String description = itemSnapshot.child("description").getValue(String.class);
                        String type = itemSnapshot.child("type").getValue(String.class);
                        onBoardingModel.title = title;
                        onBoardingModel.description = description;
                        onBoardingModel.type = type;
                        Stash.put("boarding" + key, onBoardingModel);

                    }
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
//                            if (Stash.getString("video_seen").equals("yes")) {
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                finish();
//                            } else {
//                                startActivity(new Intent(SplashActivity.this, IntroActivity.class));
//                                finish();
//                            }
                        }
                    } else {
                        Intent i = new Intent(SplashActivity.this, OnBoardingActivity.class);
                        startActivity(i);
                        finish();
                    }


                }


            @Override
            public void onCancelled (@NonNull DatabaseError error){

            }
        });

    } else

    {
        Toast.makeText(this, "Please turn on internet", Toast.LENGTH_SHORT).show();
    }
}

    }
