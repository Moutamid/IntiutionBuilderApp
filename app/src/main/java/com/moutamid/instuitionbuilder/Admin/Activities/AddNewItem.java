package com.moutamid.instuitionbuilder.Admin.Activities;


import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moutamid.instuitionbuilder.Model.OnBoardingModel;
import com.moutamid.instuitionbuilder.R;


public class AddNewItem extends AppCompatActivity {
    EditText edt_message, edt_title;
    TextView send_notification;
    ProgressBar progress_bar;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;
    OnBoardingModel onBoardingModel;

    RadioGroup radio_group;
    String type = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.add_ne_text);
        radio_group = findViewById(R.id.radio_group);
        
        edt_title = findViewById(R.id.edt_title);
        edt_message = findViewById(R.id.edt_message);
        send_notification = findViewById(R.id.send_notification);
        progress_bar = findViewById(R.id.progress_bar);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("IntuitionBuilder");
        onBoardingModel = new OnBoardingModel();
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                if (radioButton.getText().equals("1")) {
                    type = "1";
                } else if (radioButton.getText().equals("2")) {
                    type = "2";
                } else {
                    type = "3";
                }
            }
        });

        send_notification.setOnClickListener(view -> {
            if (edt_title.getText().toString().isEmpty()) {
                edt_title.setError("Please enter title!");
            } else  if (edt_message.getText().toString().isEmpty()) {
                edt_message.setError("Please enter message!");
            }{
                onBoardingModel.title= edt_title.getText().toString();
                onBoardingModel.description= edt_message.getText().toString();
                onBoardingModel.type= type;
                databaseReference.child("OnBoardingText").child(type).setValue(onBoardingModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddNewItem.this, "Data is successfully added", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(AddNewItem.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }


}