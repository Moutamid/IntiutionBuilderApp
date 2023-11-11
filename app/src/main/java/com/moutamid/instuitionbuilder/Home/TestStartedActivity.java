package com.moutamid.instuitionbuilder.Home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.fxn.stash.Stash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.moutamid.instuitionbuilder.Model.UserDetails;
import com.moutamid.instuitionbuilder.R;
import com.moutamid.instuitionbuilder.config.Config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class TestStartedActivity extends AppCompatActivity {
    private List<String> expectedTextList;
    private List<String> enteredTextList;
    private List<Integer> attempts;
    private int currentTextIndex = 0;
    private TextView scoreText;
    private EditText userInput;
    private TextView nextButton, next;
    private TextView timerText;
    private ListView enteredTextListView;

    private int score = 0;

    private CountDownTimer timer;
    CircleImageView dp;
    TextView user_name;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;
    UserDetails userDetails;
    View view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_started);

        next = findViewById(R.id.next);
        dp = findViewById(R.id.dp);
        user_name = findViewById(R.id.user_name);
        dp.setImageResource(Stash.getInt("image_path"));
        user_name.setText(Stash.getString("name"));
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("IntuitionBuilder");
        userDetails = new UserDetails();

        // Initialize the expected text list
        expectedTextList = new ArrayList<>();
        // Add your expected texts to the list
        expectedTextList.add("dog");
        expectedTextList.add("cat");
        expectedTextList.add("elephant");
        expectedTextList.add("tiger");
        enteredTextList = new ArrayList<>();
        attempts = new ArrayList<>();

        // Initialize UI components
        scoreText = findViewById(R.id.scoreText);
        userInput = findViewById(R.id.userInput);
        nextButton = findViewById(R.id.nextButton);
        timerText = findViewById(R.id.timerText);
        enteredTextListView = findViewById(R.id.enteredTextListView);

        // Set the initial score
        updateScore();

        // Set the first expected text
        showNextText();

        // Set click listener for the Next button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserInput();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                handleGameEnd();
            }
        });

        // Set up the timer for 1 minute
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer display
                timerText.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                // Time is up, show a toast message
//                showToast("Time's up!");
                timer.cancel();
                handleGameEnd();
            }
        }.start();

        view1 = findViewById(R.id.view);
        view1.setVisibility(View.VISIBLE);
    }

    private void showNextText() {
        if (currentTextIndex < expectedTextList.size()) {
            String currentText = expectedTextList.get(currentTextIndex);
            // Set the current text in some TextView or handle accordingly
            // ...
        } else {
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                nextButton.setTextColor(Color.WHITE);
                nextButton.setBackgroundDrawable(ContextCompat.getDrawable(TestStartedActivity.this, R.drawable.btn_bg_black));
            } else {
                nextButton.setTextColor(Color.WHITE);
                nextButton.setBackground(ContextCompat.getDrawable(TestStartedActivity.this, R.drawable.btn_bg_black));
            }
            timer.cancel(); // Stop the timer
            handleGameEnd(); // Common code for the end of the game
        }
    }

    private void checkUserInput() {
        String userEnteredText = userInput.getText().toString().trim().toLowerCase(); // Convert to lowercase for case-insensitive comparison
        if (!userEnteredText.isEmpty()) {
            if (enteredTextList.size() < expectedTextList.size()) {
                // Add the entered text to the list
                enteredTextList.add(userEnteredText);
                // Update the entered text ListView
                updateEnteredTextList();

                // Compare the entered text with the expected text
                String expectedText = expectedTextList.get(enteredTextList.size() - 1);
                if (userEnteredText.equals(expectedText)) {
                    // User entered the correct text
                    score++;

                    attempts.add(score);
                } else {
                    attempts.add(0);
                }

                // Move to the next expected text
                currentTextIndex++;
                // Update the score
                updateScore();

                // Clear the EditText for the next input
                userInput.getText().clear();

                // Show the next expected text or handle accordingly
                showNextText();
            } else {
                showToast("Limit exceeded");
            }
        }
    }

    private void updateScore() {
        scoreText.setText("Score: " + score);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void updateEnteredTextList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, enteredTextList);
        enteredTextListView.setAdapter(adapter);
    }

    private void handleGameEnd() {
        expectedTextList.size();
        if (score > 0) {
            int totalScore = expectedTextList.size();
            double percentage = (double) score / totalScore * 100;
            Config.showProgressDialog(TestStartedActivity.this);
            userDetails.setProgress(percentage + "");
        } else {
            userDetails.setProgress("0");

        }
        DatabaseReference child = databaseReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Progress");
        String key = child.push().getKey();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",
                Locale.getDefault());
        String date_ = sfd.format(date);
        userDetails.setNumbers(attempts);
        userDetails.setKey(key);
        userDetails.setTimeStamp(date_);
        databaseReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Progress").push().setValue(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                timer.cancel();
                Intent intent = new Intent(TestStartedActivity.this, StatisticsActivity.class);
                intent.putExtra("score", score);
                startActivity(intent);
                finish();
            }
        });
    }
}
