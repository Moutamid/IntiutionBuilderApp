package com.moutamid.instuitionbuilder.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.moutamid.instuitionbuilder.R;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
    }

    public void history(View view) {
        startActivity(new Intent(StatisticsActivity.this, StatChartActivity.class));
    }
}