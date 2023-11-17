package com.moutamid.instuitionbuilder.Home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.moutamid.instuitionbuilder.Model.SteakModel;
import com.moutamid.instuitionbuilder.Model.UserDetails;
import com.moutamid.instuitionbuilder.R;
import com.moutamid.instuitionbuilder.config.Config;

import java.util.ArrayList;
import java.util.Collections;
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

    TextView rank1, rank2, rank3, rank4;
    ImageView img1, img2, img3, img4;
    LinearLayout layout1, layout2, layout3, layout4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_ranking);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        rank1 = findViewById(R.id.rank1);
        rank2 = findViewById(R.id.rank2);
        rank3 = findViewById(R.id.rank3);
        rank4 = findViewById(R.id.rank4);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
        layout4 = findViewById(R.id.layout4);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("IntuitionBuilder");
        float rating = Stash.getFloat("rating");
        float aFloat = Stash.getFloat("progress");
        String key = Stash.getString("key");

        ArrayList<SteakModel> userArrayList = Stash.getArrayList("StreakList", SteakModel.class);
        ArrayList<Integer> newList = new ArrayList<>();
        for (int i = 0; i < userArrayList.size(); i++) {
            if (!newList.contains(userArrayList.get(i).getStreak())) {
                if (userArrayList.get(i).getStreak() != 0) {
                    newList.add(userArrayList.get(i).getStreak());
                }
            }
        }
        Collections.sort(newList, Collections.reverseOrder());
        for (int j = 0; j < newList.size(); j++) {
            Log.d("streak", "1"+newList.get(3).toString());
            Log.d("streak", "2"+newList.get(2).toString());
            Log.d("streak", "3" +newList.get(1).toString());
            Log.d("streak", "4"+newList.get(0).toString());

            if (j == 0) {

                layout1.setVisibility(View.VISIBLE);
                img1.setImageResource(R.drawable.img_1);
                rank1.setText("X" + newList.get(3).toString());
            }
            if (j == 1) {
                layout2.setVisibility(View.VISIBLE);
                img2.setImageResource(R.drawable.img_2);
                rank2.setText("X" + newList.get(2).toString());
            }
            if (j == 2) {
                layout3.setVisibility(View.VISIBLE);
                img3.setImageResource(R.drawable.img_3);
                rank3.setText("X" + newList.get(1).toString());
            }
            if (j == 3) {
                layout4.setVisibility(View.VISIBLE);
                img4.setImageResource(R.drawable.img_4);
                rank4.setText("X" + newList.get(0).toString());
            }
        }

        progressBar = findViewById(R.id.progressBar);
        score_txt = findViewById(R.id.score);
        Log.d("data", aFloat + " data");
        progressBar.setProgress((int) aFloat);
        score_txt.setText(aFloat + "%");
        ratingBar = findViewById(R.id.ratingBar);
        lineChartView = findViewById(R.id.chart);
        remakrs = findViewById(R.id.remakrs);
        ratingBar.setRating(rating);
        remakrs.setText("Great work remembering the words in  exact order");
        if (rating > 0) {
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
                    Config.dismissProgressDialog();
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
                Config.dismissProgressDialog();

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
