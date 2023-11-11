package com.moutamid.instuitionbuilder.Home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.instuitionbuilder.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class StatChartActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    LineChartView lineChartView;
    String[] axisData = {"", "", "", "", "", "", "", "", ""};
    int[] yAxisData = {0, 10, 20, 35, 45, 55, 60, 70, 80, 90};
    private List<Integer> yAxisDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_chart);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("IntuitionBuilder");
        Toast.makeText(this, getIntent().getStringExtra("key")+" "+getIntent().getStringExtra("progress"), Toast.LENGTH_SHORT).show();
        databaseReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Progress").child(getIntent().getStringExtra("key")).child("numbers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String childName = childSnapshot.getKey();
                    int childValue = childSnapshot.getValue(Integer.class);
                    childValue = childValue *10;
                    yAxisDataList.add(childValue);
                    Log.d("FirebaseData", "ChildName: " + childName + ", ChildValue: " + childValue);
                }
                int[] yAxisData = new int[yAxisDataList.size()];
                for (int i = 0; i < yAxisDataList.size(); i++) {
                    yAxisData[i] = yAxisDataList.get(i);
                }
                lineChartView = findViewById(R.id.chart);

                List yAxisValues = new ArrayList();
                List axisValues = new ArrayList();


                Line line = new Line(yAxisValues).setColor(Color.parseColor("#3DB65E"));

                for (int i = 0; i < axisData.length; i++) {
                    axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
                }

                for (int i = 0; i < yAxisData.length; i++) {
                    yAxisValues.add(new PointValue(i, yAxisData[i]));
                }

                List lines = new ArrayList();
                lines.add(line);

                LineChartData data = new LineChartData();
                data.setLines(lines);

                Axis axis = new Axis();
                axis.setValues(axisValues);
                axis.setTextSize(16);
                axis.setTextColor(Color.parseColor("#3DB65E"));
//        data.setAxisXBottom(axis);

                Axis yAxis = new Axis();
                yAxis.setTextColor(Color.parseColor("#3DB65E"));
                yAxis.setTextSize(16);
//        data.setAxisYLeft(yAxis);

                lineChartView.setLineChartData(data);
                Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
                viewport.top = 110;
                lineChartView.setMaximumViewport(viewport);
                lineChartView.setCurrentViewport(viewport);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public void history(View view) {
        startActivity(new Intent(StatChartActivity.this, StatChart2Activity.class));
    }
}
