package com.moutamid.instuitionbuilder.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.makeramen.roundedimageview.RoundedImageView;
import com.moutamid.instuitionbuilder.Home.WalkThroughActivity;
import com.moutamid.instuitionbuilder.R;

public class MaleProfileSelectActivity extends AppCompatActivity {
    RoundedImageView female_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_male_profile_select);
        female_2 = findViewById(R.id.male_2);
        female_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                female_2.setBorderColor(getResources().getColor(R.color.colorAccent));
                female_2.setBackgroundColor(0xffffffff);
            }
        });
    }

    public void next(View view) {
        startActivity(new Intent(MaleProfileSelectActivity.this, WalkThroughActivity.class));
        finishAffinity();
    }
}