package com.moutamid.instuitionbuilder.Home;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.moutamid.instuitionbuilder.R;

import java.util.ArrayList;
import java.util.List;

public class DummyActivity extends AppCompatActivity {

    private List<String> animalList;
    private List<String> enteredNamesList;
    private TextView scoreText;
    private TextView animalNameTextView;
    private EditText userInput;
    private Button nextButton;
    private TextView timerText;
    private ListView enteredNamesListView;

    private int currentAnimalIndex = 0;
    private int score = 0;

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        // Initialize the animal list
        animalList = new ArrayList<>();

        // Add your animal names to the list
        animalList.add("dog");
        animalList.add("cat");
        animalList.add("elephant");
        animalList.add("tiger");
        enteredNamesList = new ArrayList<>();

        // Initialize UI components
        scoreText = findViewById(R.id.scoreText);
        animalNameTextView = findViewById(R.id.animalNameTextView);
        userInput = findViewById(R.id.userInput);
        nextButton = findViewById(R.id.nextButton);
        timerText = findViewById(R.id.timerText);
        enteredNamesListView = findViewById(R.id.enteredNamesListView);

        // Set the initial score
        updateScore();

        // Set the first animal name
        showNextAnimal();

        // Set click listener for the Next button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserInput();
            }
        });

        // Set up the timer for 1 minute
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer display
                timerText.setText("Time: " + millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                // Time is up, show a toast message
                showToast("Time's up! Your final score is: " + score);
                // You might want to handle the end of the game here
            }
        }.start();
    }

    private void showNextAnimal() {
        if (currentAnimalIndex < animalList.size()) {
            String currentAnimal = animalList.get(currentAnimalIndex);
            // Set the current animal name in some TextView
            animalNameTextView.setText(currentAnimal);
        } else {
            // All animals have been guessed, show a message or handle accordingly
            showToast("End of the game! Your final score is: " + score);
            timer.cancel(); // Stop the timer
        }
    }

    private void checkUserInput() {
        String userEnteredText = userInput.getText().toString().trim();

        if (enteredNamesList.size() < 5) {
            if (!enteredNamesList.contains(userEnteredText)) {
                // Add the entered name to the list
                enteredNamesList.add(userEnteredText);

                // Update the entered names ListView
                updateEnteredNamesList();
                if ((animalList.contains(userEnteredText.toLowerCase()))) {
                    // User entered the correct name
                    score++;
                }

                // Move to the next animal
                currentAnimalIndex++;
                // Update the score
                updateScore();
                // Clear the EditText for the next input
                userInput.getText().clear();

                // Show the next animal or handle accordingly
                showNextAnimal();
            }
        else {
            Toast.makeText(this, "Already write this answer", Toast.LENGTH_SHORT).show();
        }
    }
     else

    {
        Toast.makeText(this, "limit exceed", Toast.LENGTH_SHORT).show();
    }

}


    private void updateScore() {
        scoreText.setText("10/" + score);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void updateEnteredNamesList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_layout, enteredNamesList);
        enteredNamesListView.setAdapter(adapter);
    }
}
