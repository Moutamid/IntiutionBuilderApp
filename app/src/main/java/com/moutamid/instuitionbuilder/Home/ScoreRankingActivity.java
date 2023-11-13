package com.moutamid.instuitionbuilder.Home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.instuitionbuilder.Adapter.HistoryProgressAdapter;
import com.moutamid.instuitionbuilder.Model.UserDetails;
import com.moutamid.instuitionbuilder.R;
import com.moutamid.instuitionbuilder.config.Config;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class ScoreRankingActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    LineChartView lineChartView;
    private List<Integer> yAxisDataList = new ArrayList<>();
    String color_graph = "#3DB65E";
    String red_color = "#E91E63";
    public List<UserDetails> progressModelList = new ArrayList<>();
    HistoryProgressAdapter progressAdapter;
    RecyclerView content_rcv;
    RatingBar ratingBar;
    TextView remakrs;
    TextView score_txt;
    double percentage;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_ranking);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("IntuitionBuilder");
        String key = getIntent().getStringExtra("key");
        float progress = getIntent().getFloatExtra("progress", 0);
//        int score = getIntent().getIntExtra("score", 0);
        int score=2;
        score_txt = findViewById(R.id.score);
        int totalScore = 4;
        percentage = (double) score / totalScore * 100;
        progressBar.setProgress((int) percentage);
        score_txt.setText(percentage + "%");
        ratingBar = findViewById(R.id.ratingBar);
        lineChartView = findViewById(R.id.chart);
        remakrs = findViewById(R.id.remakrs);
//        ratingBar.setRating(score);
        remakrs.setText("Great work remembering the words in the exact order");
//        Log.d("ranking",Stash.getArrayList("Streaks", String.class)+" data" );
        if (progress != 0) {
            Config.showProgressDialog(ScoreRankingActivity.this);
            databaseReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Progress").child(key).child("numbers").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        int childValue = childSnapshot.getValue(Integer.class);
                        childValue = childValue * 10;
                        yAxisDataList.add(childValue);
                        if (childValue == 0) {
                            remakrs.setText("You weren’t able to remember the words in the exact order");
                            color_graph = red_color;
                        }

                    }
                    List<PointValue> yAxisValues = new ArrayList<PointValue>();
                    int[] yAxisData = new int[yAxisDataList.size()];
                    for (int i = 0; i < yAxisDataList.size(); i++) {
                        yAxisData[i] = yAxisDataList.get(i);
                        yAxisValues.add(new PointValue(i, yAxisData[i]));
                    }
                    if (yAxisValues.size() > 0) {

                        Line line = new Line(yAxisValues).setColor(Color.parseColor(color_graph));
                        List<Line> lines = new ArrayList<Line>();
                        lines.add(line);
                        LineChartData data = new LineChartData();
                        data.setLines(lines);
                        lineChartView.setLineChartData(data);
                        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
                        viewport.top = 110;
                        lineChartView.setMaximumViewport(viewport);
                        lineChartView.setCurrentViewport(viewport);
                        Axis axis = new Axis();
                        axis.setTextSize(16);
                        axis.setTextColor(Color.parseColor(color_graph));
                        Axis yAxis = new Axis();
                        yAxis.setTextColor(Color.parseColor(color_graph));
                        yAxis.setTextSize(16);
                        Config.dismissProgressDialog();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else
        {
            remakrs.setText("You weren’t able to remember any words in the exact order");
        }
        content_rcv = findViewById(R.id.content_rcv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ScoreRankingActivity.this, 1);
        content_rcv.setLayoutManager(gridLayoutManager);
        progressAdapter = new HistoryProgressAdapter(ScoreRankingActivity.this, progressModelList);
        content_rcv.setAdapter(progressAdapter);
        if (Config.isNetworkAvailable(ScoreRankingActivity.this)) {
            getProducts();
        } else {
            Toast.makeText(ScoreRankingActivity.this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }
    }

    public void history(View view) {
        startActivity(new Intent(ScoreRankingActivity.this, StatChart2Activity.class));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, WalkThroughActivity.class));
        finishAffinity();
    }
}
