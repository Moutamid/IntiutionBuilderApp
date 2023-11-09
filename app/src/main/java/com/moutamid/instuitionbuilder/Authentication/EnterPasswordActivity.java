package com.moutamid.instuitionbuilder.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.moutamid.instuitionbuilder.R;

public class EnterPasswordActivity extends AppCompatActivity {
    private EditText passwordTextView, confirmPasswordTextView;
    private Button Btn;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);

        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // initialising all views through id defined above
        confirmPasswordTextView = findViewById(R.id.confirmPasswordTextView);
        passwordTextView = findViewById(R.id.password);
        Btn = findViewById(R.id.btnregister);
        progressbar = findViewById(R.id.progressbar);
        email = getIntent().getStringExtra("email");
        // Set on Click Listener on Registration button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser() {

        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);

        // Take the value of two edit texts in Strings
        String password, confirmPassword;
        password = passwordTextView.getText().toString();
        confirmPassword = confirmPasswordTextView.getText().toString();

        // Validations for input email and password

        if (TextUtils.isEmpty(password)) {
            progressbar.setVisibility(View.GONE);
            passwordTextView.setError("Please enter password!!");

            return;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            progressbar.setVisibility(View.GONE);
            confirmPasswordTextView.setError("Please enter confirm password!!");
            return;
        }
        if (!confirmPassword.equals(password)) {
            progressbar.setVisibility(View.GONE);
            confirmPasswordTextView.setError("Password does not match");

            return;
        }

        // create new user or register new user
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Please verify your email", Toast.LENGTH_LONG).show();

                    progressbar.setVisibility(View.GONE);

                    if (!FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                        FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(EnterPasswordActivity.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(EnterPasswordActivity.this, OTPVerificationActivity.class);
                                   intent.putExtra("email", email);
                                    startActivity(intent);
                                }
                            }
                        });
                    }

                } else {

                    // Registration failed
                    Toast.makeText(getApplicationContext(), "Registration failed!!" + " Please try again later", Toast.LENGTH_LONG).show();

                    // hide the progress bar
                    progressbar.setVisibility(View.GONE);
                }
            }
        });
    }

    public void back(View view) {
        onBackPressed();
    }
}