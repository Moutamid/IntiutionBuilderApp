package com.moutamid.instuitionbuilder.Authentication;

import static java.net.HttpURLConnection.HTTP_OK;

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

import com.fxn.stash.Stash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moutamid.instuitionbuilder.Model.UserDetails;
import com.moutamid.instuitionbuilder.R;
import com.moutamid.instuitionbuilder.SplashActivity;
import com.moutamid.instuitionbuilder.config.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterPasswordActivity extends AppCompatActivity {
    private EditText passwordTextView, confirmPasswordTextView;
    private Button Btn;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;
    String email;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;
    UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);

        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("IntuitionBuilder");
        userDetails = new UserDetails();


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
                    int randomCode = ThreadLocalRandom.current().nextInt(1000, 10000);

                    progressbar.setVisibility(View.GONE);
                    if (!FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                        FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(EnterPasswordActivity.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    userDetails.setEmail(email);
                                    userDetails.setCode(randomCode+"");
                                    userDetails.setIsverified(false);
                                    databaseReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
//                                                RetrofitClient.getInstance()
//                                                        .getApi()
//                                                        .sendEmail("fizarandhawa15@gmail.com", "projectfinal263@gmail.com", "IntuitionBuilder App Verification Code", "\t\t\n" +
//                                                                "Dear User,\n" +
//                                                                "\n" +
//                                                                "We received a request to access your IntuitionBuilder App through your email address. Your verification code is:\n" +
//                                                                "\n\n" +
//                                                                randomCode+"\n\n" +
//                                                                "\n" +
//                                                                "If you did not request this code, it is possible that someone else is trying to access by using your email. Do not forward or give this code to anyone.\n" +
//                                                                "\n" +
//                                                                "Sincerely yours,\n" +
//                                                                "\n" +
//                                                                "The IntuitionBuilder Team")
//                                                        .enqueue(new Callback<ResponseBody>() {
//                                                            @Override
//                                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                                                if (response.code() == HTTP_OK) {
//                                                                    try {
//                                                                        Stash.put("code", randomCode);
//                                                                        JSONObject obj = new JSONObject(response.body().string());
                                                                        Stash.put("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
//                                                                        Toast.makeText(getApplicationContext(), "Please verify your email", Toast.LENGTH_LONG).show();
                                                                        Intent intent = new Intent(EnterPasswordActivity.this, UserDetailsActivity.class);
                                                                        intent.putExtra("email", email);
                                                                        startActivity(intent);
//                                                                    } catch (JSONException | IOException e) {
//                                                                        Toast.makeText(EnterPasswordActivity.this,  e.getMessage()+" message ", Toast.LENGTH_LONG).show();
//
//                                                                    }
//                                                                }
//                                                            }
//
//                                                            @Override
//                                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                                                Toast.makeText(EnterPasswordActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//
//                                                            }
//                                                        });

                                                  } else {
                                                Toast.makeText(EnterPasswordActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });


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