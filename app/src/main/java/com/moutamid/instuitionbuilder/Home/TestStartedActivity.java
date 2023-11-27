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
import com.moutamid.instuitionbuilder.Model.SteakModel;
import com.moutamid.instuitionbuilder.Model.UserDetails;
import com.moutamid.instuitionbuilder.R;
import com.moutamid.instuitionbuilder.config.Config;
import com.moutamid.instuitionbuilder.config.RankManager;

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
    private TextView scoreText, questionText;
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
    int streak = 0;
    ArrayList<SteakModel> userArrayList = Stash.getArrayList("StreakList", SteakModel.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_started);


        next = findViewById(R.id.next);
        dp = findViewById(R.id.dp);
        user_name = findViewById(R.id.user_name);
        questionText = findViewById(R.id.questionText);
        dp.setImageResource(Stash.getInt("image_path"));
        user_name.setText(Stash.getString("name"));
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("IntuitionBuilder");
        userDetails = new UserDetails();

        // Initialize the expected text list
        expectedTextList = new ArrayList<>();
        for (int j=0; j<Config.dataArrayList().size(); j++) {
            expectedTextList.add(Config.dataArrayList().get(j).text.toString());
        }
        enteredTextList = new ArrayList<>();
        attempts = new ArrayList<>();

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

        } else {
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                nextButton.setTextColor(Color.WHITE);
                nextButton.setBackgroundDrawable(ContextCompat.getDrawable(TestStartedActivity.this, R.drawable.btn_bg_black));
            } else {
                nextButton.setTextColor(Color.WHITE);
                nextButton.setBackground(ContextCompat.getDrawable(TestStartedActivity.this, R.drawable.btn_bg_black));
            }
            handleGameEnd(); // Common code for the end of the game
        }
    }

    private void checkUserInput() {
        String userEnteredText = userInput.getText().toString().trim().toLowerCase(); // Convert to lowercase for case-insensitive comparison
        if (!userEnteredText.isEmpty()) {
            if (enteredTextList.size() < expectedTextList.size()) {
                enteredTextList.add(userEnteredText);
                updateEnteredTextList();
                String expectedText = expectedTextList.get(enteredTextList.size() - 1);
                if (userEnteredText.equals(expectedText)) {
                    // User entered the correct text
                     attempts.add(score);

                } else {
                    userArrayList.add(new SteakModel(streak));
                    Stash.put("StreakList", userArrayList);
                    attempts.add(0);
                    streak = 0;

                }
                if (expectedTextList.contains(userEnteredText)) {
                    RankManager.updateStreak(true);
                    streak++;
                    Stash.put("streak", streak);
                    score++;
                }
                currentTextIndex++;
                updateScore();
                userInput.getText().clear();

                // Show the next expected text or handle accordingly
                showNextText();
            } else {
                showToast("Limit exceeded");
            }
        }
    }

    private void updateScore() {
        scoreText.setText(attempts.size()+"/"+expectedTextList.size());
//        questionText.setText("Answer No. "+ attempts.size()+"/"+expectedTextList.size() );
    }

    private void showToast(String message) {

        Toast.makeText(TestStartedActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void updateEnteredTextList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_layout, enteredTextList);
        enteredTextListView.setAdapter(adapter);
        enteredTextListView.smoothScrollToPosition(adapter.getCount() - 1);
        enteredTextListView.setSelection(enteredTextListView.getAdapter().getCount()-1);

    }

    private void handleGameEnd() {
        userInput.setVisibility(View.GONE);
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
        databaseReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Progress").child(key).setValue(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                userArrayList.add(new SteakModel(Stash.getInt("streak")));
                Stash.put("StreakList", userArrayList);
                timer.cancel();
                Intent intent = new Intent(TestStartedActivity.this, StatisticsActivity.class);
                intent.putExtra("score", score);
                startActivity(intent);
                finish();
            }
        });
    }
}
