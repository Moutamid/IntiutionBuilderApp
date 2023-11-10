package com.moutamid.instuitionbuilder.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moutamid.instuitionbuilder.Model.UserDetails;
import com.moutamid.instuitionbuilder.R;
import com.moutamid.instuitionbuilder.config.Config;

public class StatisticsActivity extends AppCompatActivity {
    TextView score_txt;
      double percentage;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        progressBar=findViewById(R.id.progressBar);
          int score = getIntent().getIntExtra("score", 0);
        score_txt = findViewById(R.id.score);
        int totalScore = 4;
        percentage = (double) score / totalScore * 100;
        progressBar.setProgress((int) percentage);
        score_txt.setText(percentage + "%");


    }

    public void history(View view) {
          startActivity(new Intent(StatisticsActivity.this, StatChartActivity.class));
    }
}