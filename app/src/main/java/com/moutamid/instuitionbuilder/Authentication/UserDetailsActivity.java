package com.moutamid.instuitionbuilder.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.moutamid.instuitionbuilder.Home.WalkThroughActivity;
import com.moutamid.instuitionbuilder.R;

public class UserDetailsActivity extends AppCompatActivity {
    private EditText name;
    RadioGroup radio_group;
    String gender = "not";
    Button btnregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        name = findViewById(R.id.name);
        radio_group = findViewById(R.id.radio_group);
        btnregister = findViewById(R.id.btnregister);
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                if (radioButton.getText().equals("Male")) {
                    gender = "Male";
                } else if (radioButton.getText().equals("Female")) {
                    gender = "Female";
                } else {
                    gender = "Not Preferred";
                }
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (gender) {
                    case "Male":
                        startActivity(new Intent(UserDetailsActivity.this, MaleProfileSelectActivity.class));
//                    finish();
                        break;
                    case "Female":
                        startActivity(new Intent(UserDetailsActivity.this, ProfileSelectActivity.class));
//                    finish();
                        break;
                    case "Not Preferred":
                        startActivity(new Intent(UserDetailsActivity.this, WalkThroughActivity.class));
//                    finish();
                        break;
                }

            }
        });
    }

}