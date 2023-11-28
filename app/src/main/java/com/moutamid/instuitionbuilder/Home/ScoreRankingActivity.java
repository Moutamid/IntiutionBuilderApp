package com.moutamid.instuitionbuilder.Home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.instuitionbuilder.Adapter.HistoryProgressAdapter;
import com.moutamid.instuitionbuilder.Adapter.StreakAdapter;
import com.moutamid.instuitionbuilder.Model.SteakModel;
import com.moutamid.instuitionbuilder.Model.UserDetails;
import com.moutamid.instuitionbuilder.R;
import com.moutamid.instuitionbuilder.config.Config;
import com.moutamid.instuitionbuilder.config.StreakModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public List<StreakModel> steakModelList = new ArrayList<>();
    HistoryProgressAdapter progressAdapter;
    StreakAdapter streakAdapter;
    RecyclerView content_rcv, content_steak;
    RatingBar ratingBar;
    TextView remakrs;
    TextView score_txt;
    double percentage;
    LinearLayout ranks;
    ProgressBar progressBar;
    List<StreakModel> modelListWithoutDuplicates;
    boolean is_3_TO_8 = false, is_7_TO_13 = false, is_12_TO_21 = false, is_20_TO_34 = false, is_33_TO_55 = false, is_54_TO_87 = false, is_86_TO_144 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_ranking);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("IntuitionBuilder");
        float rating = Stash.getFloat("rating");
        float aFloat = Stash.getFloat("progress");
        String key = Stash.getString("key");
        ranks = findViewById(R.id.ranks);
        ArrayList<SteakModel> userArrayList = Stash.getArrayList("StreakList", SteakModel.class);

        for (int i = 0; i < userArrayList.size(); i++) {

            if (userArrayList.get(i).getStreak() != 0) {

                if (userArrayList.get(i).getStreak() > 3 && userArrayList.get(i).getStreak() < 8) {
                    if (!is_3_TO_8) {
                        ranks.setVisibility(View.VISIBLE);
                        is_3_TO_8 = true;
                        StreakModel streakModel1 = new StreakModel();
                        streakModel1.text = Config.streakArrayList().get(0).text;
                        streakModel1.image = Config.streakArrayList().get(0).image;
                        steakModelList.add(streakModel1);
                    }
                }
                if (userArrayList.get(i).getStreak() > 7 && userArrayList.get(i).getStreak() < 13) {
                    if (!is_7_TO_13) {
                        ranks.setVisibility(View.VISIBLE);

                        is_7_TO_13 = true;
                        StreakModel streakModel2 = new StreakModel();
                        streakModel2.text = Config.streakArrayList().get(1).text;
                        streakModel2.image = Config.streakArrayList().get(1).image;
                        steakModelList.add(streakModel2);
                    }
                }
                if (userArrayList.get(i).getStreak() > 12 && userArrayList.get(i).getStreak() < 21) {
                    if (!is_12_TO_21) {
                        ranks.setVisibility(View.VISIBLE);

                        is_12_TO_21 = true;
                        StreakModel streakModel3 = new StreakModel();

                        streakModel3.text = Config.streakArrayList().get(2).text;
                        streakModel3.image = Config.streakArrayList().get(2).image;
                        steakModelList.add(streakModel3);
                    }
                }
                if (userArrayList.get(i).getStreak() > 20 && userArrayList.get(i).getStreak() < 34) {
                    if (!is_20_TO_34) {
                        ranks.setVisibility(View.VISIBLE);

                        is_20_TO_34 = true;
                        StreakModel streakModel4 = new StreakModel();
                        streakModel4.text = Config.streakArrayList().get(3).text;
                        streakModel4.image = Config.streakArrayList().get(3).image;
                        steakModelList.add(streakModel4);
                    }
                }
                if (userArrayList.get(i).getStreak() > 33 && userArrayList.get(i).getStreak() < 55) {
                    if (!is_33_TO_55) {
                        ranks.setVisibility(View.VISIBLE);

                        is_33_TO_55 = true;
                        StreakModel streakModel5 = new StreakModel();
                        streakModel5.text = Config.streakArrayList().get(4).text;
                        streakModel5.image = Config.streakArrayList().get(4).image;
                        steakModelList.add(streakModel5);
                    }
                }
                if (userArrayList.get(i).getStreak() > 54 && userArrayList.get(i).getStreak() < 87) {
                    if (!is_54_TO_87) {
                        ranks.setVisibility(View.VISIBLE);

                        is_54_TO_87 = true;
                        StreakModel streakModel6 = new StreakModel();
                        streakModel6.text = Config.streakArrayList().get(5).text;
                        streakModel6.image = Config.streakArrayList().get(5).image;
                        steakModelList.add(streakModel6);
                    }
                }
                if (userArrayList.get(i).getStreak() > 86 && userArrayList.get(i).getStreak() < 144) {
                    if (!is_86_TO_144) {
                        ranks.setVisibility(View.VISIBLE);
                        is_86_TO_144 = true;
                        StreakModel streakModel7 = new StreakModel();
                        streakModel7.text = Config.streakArrayList().get(6).text;
                        streakModel7.image = Config.streakArrayList().get(6).image;
                        steakModelList.add(streakModel7);
                    }
                }
            }
            Set<StreakModel> uniqueModels = new HashSet<>(steakModelList);
            modelListWithoutDuplicates = new ArrayList<>(uniqueModels);

            content_steak = findViewById(R.id.content_steak);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            content_steak.setLayoutManager(layoutManager);
            streakAdapter = new StreakAdapter(ScoreRankingActivity.this, modelListWithoutDuplicates);
            content_steak.setAdapter(streakAdapter);
        }
//
        progressBar = findViewById(R.id.progressBar);
        score_txt = findViewById(R.id.score);
        Log.d("data", aFloat + " data");
        progressBar.setProgress((int) aFloat);
        String formattedNumber = String.format("%.2f", aFloat);

        score_txt.setText(formattedNumber + "%");

//        score_txt.setText(aFloat + "%");
        ratingBar = findViewById(R.id.ratingBar);
        lineChartView = findViewById(R.id.chart);
        remakrs = findViewById(R.id.remakrs);
        ratingBar.setRating(rating);
        remakrs.setText("Great work remembering the words in  exact order");
        if (rating > 0) {
//            Config.showProgressDialog(ScoreRankingActivity.this);
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
//                        Config.dismissProgressDialog();
                    }
//                    Config.dismissProgressDialog();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
//                    Config.dismissProgressDialog();
                }
            });

        } else {
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
//        Config.showProgressDialog(ScoreRankingActivity.this);
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
//                Config.dismissProgressDialog();
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

    private List<StreakModel> removeDuplicates(List<StreakModel> inputList) {
        Set<StreakModel> uniqueModels = new HashSet<>(inputList);
        return new ArrayList<>(uniqueModels);
    }
}
