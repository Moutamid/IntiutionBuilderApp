package com.moutamid.instuitionbuilder.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.moutamid.instuitionbuilder.R;

public class RegisterActivity extends AppCompatActivity {
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.email);

    }
    public void login(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

    }

    public void create_Account(View view) {
        if (email.getText().toString().isEmpty()) {
            email.setError("Please Enter");
        } else {
            Intent intent = new Intent(RegisterActivity.this, EnterPasswordActivity.class);
            intent.putExtra("email", email.getText().toString());
            startActivity(intent);
        }
    }
}