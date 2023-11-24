package com.moutamid.instuitionbuilder.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.makeramen.roundedimageview.RoundedImageView;
import com.moutamid.instuitionbuilder.Home.WalkThroughActivity;
import com.moutamid.instuitionbuilder.R;

public class ProfileSelectActivity extends AppCompatActivity {
    RoundedImageView male_2, male_1, male_3, male_4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_select);
        male_2 = findViewById(R.id.female_2);
        male_1 = findViewById(R.id.female_1);
        male_3 = findViewById(R.id.female_3);
        male_4 = findViewById(R.id.female_4);
        male_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                male_1.setBorderColor(getResources().getColor(R.color.black));
                male_2.setBorderColor(getResources().getColor(R.color.black));
                male_3.setBorderColor(getResources().getColor(R.color.black));
                male_4.setBorderColor(getResources().getColor(R.color.colorAccent));
                getImageData(R.drawable.female_4);
            }
        });
        male_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                male_1.setBorderColor(getResources().getColor(R.color.colorAccent));
                male_2.setBorderColor(getResources().getColor(R.color.black));
                male_3.setBorderColor(getResources().getColor(R.color.black));
                male_4.setBorderColor(getResources().getColor(R.color.black));
                getImageData(R.drawable.female_1);

            }
        });
        male_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                male_1.setBorderColor(getResources().getColor(R.color.black));
                male_2.setBorderColor(getResources().getColor(R.color.colorAccent));
                male_3.setBorderColor(getResources().getColor(R.color.black));
                male_4.setBorderColor(getResources().getColor(R.color.black));
                getImageData(R.drawable.female_2);

            }
        });
        male_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                male_1.setBorderColor(getResources().getColor(R.color.black));
                male_2.setBorderColor(getResources().getColor(R.color.black));
                male_3.setBorderColor(getResources().getColor(R.color.colorAccent));
                male_4.setBorderColor(getResources().getColor(R.color.black));
                getImageData(R.drawable.female_3);

            }
        });
    }

    public void next(View view) {
        startActivity(new Intent(ProfileSelectActivity.this, WalkThroughActivity.class));
        finishAffinity();
    }

    public void getImageData(int image) {
        Stash.put("image_path", image);
    }}