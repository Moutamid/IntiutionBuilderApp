package com.moutamid.instuitionbuilder.Home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.instuitionbuilder.Adapter.ProgressAdapter;
import com.moutamid.instuitionbuilder.Model.ProgressModel;
import com.moutamid.instuitionbuilder.Model.UserDetails;
import com.moutamid.instuitionbuilder.R;
import com.moutamid.instuitionbuilder.config.Config;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {
    TextView score_txt;
    double percentage;
    ProgressBar progressBar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public List<UserDetails> progressModelList = new ArrayList<>();
    ProgressAdapter progressAdapter;
RecyclerView content_rcv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        progressBar = findViewById(R.id.progressBar);
        progressAdapter = new ProgressAdapter(StatisticsActivity.this, progressModelList);

        int score = getIntent().getIntExtra("score", 0);
        score_txt = findViewById(R.id.score);
        int totalScore = 4;
        percentage = (double) score / totalScore * 100;
        progressBar.setProgress((int) percentage);
        score_txt.setText(percentage + "%");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("IntuitionBuilder");
        content_rcv = findViewById(R.id.content_rcv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(StatisticsActivity.this, 1);
        content_rcv.setLayoutManager(gridLayoutManager);
        progressAdapter = new ProgressAdapter(StatisticsActivity.this, progressModelList);
        content_rcv.setAdapter(progressAdapter);
        if (Config.isNetworkAvailable(StatisticsActivity.this)) {
            getProducts();
        } else {
            Toast.makeText(StatisticsActivity.this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }

    }

    public void history(View view) {
        startActivity(new Intent(StatisticsActivity.this, StatChartActivity.class));
    }

    private void getProducts() {
        DatabaseReference databaseReference1 = databaseReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Progress");
        databaseReference1.orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressModelList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    UserDetails progressModel = ds.getValue(UserDetails.class);
                    progressModelList.add(progressModel);
                }
                progressAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}