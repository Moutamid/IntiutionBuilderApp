package com.moutamid.instuitionbuilder.Home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.moutamid.instuitionbuilder.Adapter.NotificationAdapter;
import com.moutamid.instuitionbuilder.Model.NotificationModel;
import com.moutamid.instuitionbuilder.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    RecyclerView content_rcv;
    public List<NotificationModel> notificationModelList = new ArrayList<>();
    NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notifications);
        content_rcv = findViewById(R.id.content_rcv);
        content_rcv.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<NotificationModel> notification =  Stash.getArrayList("Notification", NotificationModel.class);
        notificationAdapter = new NotificationAdapter(this, notification);
        content_rcv.setAdapter(notificationAdapter);
    }

}