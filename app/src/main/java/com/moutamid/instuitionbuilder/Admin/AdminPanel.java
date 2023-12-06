package com.moutamid.instuitionbuilder.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


//import com.moutamid.instuitionbuilder.Admin.Activities.AddNewItem;
import com.moutamid.instuitionbuilder.Admin.Activities.AddNewItem;
import com.moutamid.instuitionbuilder.Admin.Activities.NotifcationsActivity;
import com.moutamid.instuitionbuilder.Authentication.LoginActivity;
import com.moutamid.instuitionbuilder.R;

public class AdminPanel extends AppCompatActivity {
    CardView add_vide_btn, notification_btn;
    CardView logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        add_vide_btn = findViewById(R.id.btn);
        notification_btn = findViewById(R.id.notification_btn);
        logout = findViewById(R.id.logout);
          add_vide_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanel.this, AddNewItem.class));
            }
        });
        notification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanel.this, NotifcationsActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanel.this, LoginActivity.class));
                finishAffinity();
            }
        });
    }
}