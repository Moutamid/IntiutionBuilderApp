package com.moutamid.instuitionbuilder.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.moutamid.instuitionbuilder.Home.WalkThroughActivity;
import com.moutamid.instuitionbuilder.R;

public class OTPVerificationActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        textView = findViewById(R.id.email_text);
        String email = getIntent().getStringExtra("email");

        textView.setText(Html.fromHtml("Enter the 4-digit code sent to you at <b>" + email + "</b>"));
    }

    public void walk_through(View view) {
//        if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(OTPVerificationActivity.this, UserDetailsActivity.class));
//        }
//        else {
//            Toast.makeText(this, "Please verify your email", Toast.LENGTH_SHORT).show();
//        }
    }
    public void back(View view) {
    onBackPressed();}
}