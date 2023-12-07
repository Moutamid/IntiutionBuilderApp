package com.moutamid.instuitionbuilder.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moutamid.instuitionbuilder.Home.WalkThroughActivity;
import com.moutamid.instuitionbuilder.Model.UserDetails;
import com.moutamid.instuitionbuilder.R;

public class UserDetailsActivity extends AppCompatActivity {
    private EditText name;
    RadioGroup radio_group;
    String gender = "not";
    Button btnregister;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;
    UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        name = findViewById(R.id.name);
        radio_group = findViewById(R.id.radio_group);
        btnregister = findViewById(R.id.btnregister);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("IntuitionBuilder");
        userDetails = new UserDetails();
        if (!Stash.getString("name").isEmpty()) {
            name.setText(Stash.getString("name"));
        }
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

                if (name.getText().toString().isEmpty()) {
                    name.setError("Enter Name");
                } else if (gender.equals("not")) {
                    Toast.makeText(UserDetailsActivity.this, "Please select gender", Toast.LENGTH_SHORT).show();
                } else
                {

//                if(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).isEmailVerified()) {
                    Stash.put("name", name.getText().toString());
                    Stash.put("gender", gender);
                    Stash.put("first_time", "yes");
                    userDetails.setName(name.getText().toString());
                    userDetails.setGender(gender);
                    databaseReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                switch (gender) {
                                    case "Male":
                                        startActivity(new Intent(UserDetailsActivity.this, MaleProfileSelectActivity.class));
                                        finish();
                                        break;
                                    case "Female":
                                        startActivity(new Intent(UserDetailsActivity.this, ProfileSelectActivity.class));
                                        finish();
                                        break;
                                    case "Not Preferred":
                                        Stash.put("image_path", R.drawable.blank_image);
                                        startActivity(new Intent(UserDetailsActivity.this, WalkThroughActivity.class));
                                        finish();
                                        break;
                                }
                            } else {
                                Toast.makeText(UserDetailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
//                else  {
//                    Toast.makeText(UserDetailsActivity.this, "Please verify your email", Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }
}