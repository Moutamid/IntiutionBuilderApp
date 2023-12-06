package com.moutamid.instuitionbuilder.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.moutamid.instuitionbuilder.R;

import java.util.Objects;

public class GoogleActivity extends AppCompatActivity {
    // Initialize variables
    SignInButton btSignIn;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);
        btSignIn = findViewById(R.id.bt_sign_in);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)

                .requestIdToken("10469575736-uglg2p8a7sns11qkbfdc7j7gsrhg4rl5.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(GoogleActivity.this
                , googleSignInOptions);
        btSignIn.setOnClickListener(view -> {
            Intent intent = googleSignInClient.getSignInIntent();
            startActivityForResult(intent, 100);
        });
        firebaseAuth = FirebaseAuth.getInstance();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
// Check condition


        if (requestCode == 100) {

// When request code is equal to 100
// Initialize task
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn
                    .getSignedInAccountFromIntent(data);
// check condition
            if (signInAccountTask.isSuccessful()) {
// When google sign in successful
// Initialize string
                String s = "Google sign in successful";
// Display Toast
                displayToast(s);
// Initialize sign in account
                try {
// Initialize sign in account
                    GoogleSignInAccount googleSignInAccount = signInAccountTask
                            .getResult(ApiException.class);
// Check condition
                    if (googleSignInAccount != null) {
// When sign in account is not equal to null
// Initialize auth credential
                        AuthCredential authCredential = GoogleAuthProvider
                                .getCredential(googleSignInAccount.getIdToken()
                                        , null);
// Check credential
                        firebaseAuth.signInWithCredential(authCredential)
                                .addOnCompleteListener(this, task -> {
// Check condition
                                    if (task.isSuccessful()) {

// Redirect to profile activity

// Display Toast
                                            displayToast("Firebase authentication successful");
                                    } else {
// When task is unsuccessful
// Display Toast
                                        displayToast("Authentication Failed :" + Objects.requireNonNull(task.getException())
                                                .getMessage());
                                    }
                                });

                    }
                } catch (ApiException e) {
                    displayToast(e.getMessage()+ "Firebase authentication successful");

                    e.printStackTrace();
                }
            } else {
                displayToast(signInAccountTask.getException()+ "Firebase authentication successful");

                System.out.println(signInAccountTask.getException());
//                displayToast(signInAccountTask.getException() + "dfsdf");

            }
        }
    }


    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    public void google(View view) {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, 100);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}