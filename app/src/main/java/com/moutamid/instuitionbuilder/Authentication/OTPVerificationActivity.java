package com.moutamid.instuitionbuilder.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.moutamid.instuitionbuilder.R;

public class OTPVerificationActivity extends AppCompatActivity {
    TextView textView;
EditText number1, number2, number3, number4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
//        number1 = findViewById(R.id.number1);
//        number2 = findViewById(R.id.number2);
//        number3 = findViewById(R.id.number3);
//        number4 = findViewById(R.id.number4);
        textView = findViewById(R.id.email_text);
        String email = getIntent().getStringExtra("email");
        textView.setText(Html.fromHtml("Enter the 4-digit code sent to you at <b>" + email + "</b>"));
    }

    public void walk_through(View view) {
            startActivity(new Intent(OTPVerificationActivity.this, UserDetailsActivity.class));
    }
    public void back(View view) {
    onBackPressed();}
}