package com.moutamid.instuitionbuilder.Home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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
import androidx.core.content.ContextCompat;

import com.moutamid.instuitionbuilder.R;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.List;

public class TestStartedActivity extends AppCompatActivity {
    View view1;
    private List<String> animalList;
    private List<String> enteredNamesList;
    private int currentAnimalIndex = 0;
    private TextView scoreText;
    private EditText userInput;
    private TextView nextButton;
    private TextView timerText;
    private ListView enteredNamesListView;

    private int score = 0;

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_started);


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
                if (enteredNamesList.size() < 3) {

                    checkUserInput();

                }
                else
                {
//                    startActivity(new Intent(TestStartedActivity.this, StatisticsActivity.class));
                }
            }
        });

        // Set up the timer for 1 minute
        timer = new

                CountDownTimer(60000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // Update the timer display
                        timerText.setText(millisUntilFinished / 1000 + "s");
                    }

                    @Override
                    public void onFinish() {
                        // Time is up, show a toast message
                        showToast("Time's up! Your final score is: " + score);
                        // You might want to handle the end of the game here
                    }
                }.

                start();


        view1 =

                findViewById(R.id.view);

        NumberPicker numberPicker = findViewById(R.id.number_picker);
        numberPicker.setDividerThickness(0);
        numberPicker.setFormatter(

                getString(R.string.number_picker_formatter));
        numberPicker.setFormatter(R.string.number_picker_formatter);
        numberPicker.setSelectedTextSize(

                getResources().

                        getDimension(R.dimen.selected_text_size));
        numberPicker.setSelectedTypeface(Typeface.create(

                getString(R.string.roboto_light), Typeface.NORMAL));
        numberPicker.setSelectedTypeface(

                getString(R.string.roboto_light), Typeface.NORMAL);

        numberPicker.setTextColorResource(R.color.dark_grey);

        // Set text size
        numberPicker.setTextSize(R.dimen.text_size);

        numberPicker.setTypeface(Typeface.create(

                getString(R.string.roboto_light), Typeface.NORMAL));
        numberPicker.setTypeface(

                getString(R.string.roboto_light), Typeface.NORMAL);
        String[] data = {"Cat", "Dog", "Fish", "Elephant", "Rabbit", "Cow", "Goat", "Lion", "Deer"};
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);
        numberPicker.setFadingEdgeEnabled(true);
//        numberPicker.setScrollerEnabled(true);
//        numberPicker.setWrapSelectorWheel(true);
//        numberPicker.setAccessibilityDescriptionEnabled(true);
        numberPicker.setSelectedTextColor(ContextCompat.getColor(TestStartedActivity.this, R.color.dark_grey));
        view1.setVisibility(View.VISIBLE);


    }

    public void statistics(View view) {
        startActivity(new Intent(TestStartedActivity.this, StatisticsActivity.class));
    }

    private void showNextAnimal() {
        if (currentAnimalIndex < animalList.size()) {
            String currentAnimal = animalList.get(currentAnimalIndex);
            // Set the current animal name in some TextView
//            animalNameTextView.setText(currentAnimal);
        } else {
            // All animals have been guessed, show a message or handle accordingly
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                nextButton.setTextColor(Color.WHITE);
                nextButton.setBackgroundDrawable(ContextCompat.getDrawable(TestStartedActivity.this, R.drawable.btn_bg_black));
            } else {
                nextButton.setTextColor(Color.WHITE);
                nextButton.setBackground(ContextCompat.getDrawable(TestStartedActivity.this, R.drawable.btn_bg_black));
            }
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
            } else {
                Toast.makeText(this, "Already write this answer", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "limit exceed", Toast.LENGTH_SHORT).show();
        }

    }


    private void updateScore() {
        scoreText.setText("04/0" + score);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void updateEnteredNamesList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_layout, enteredNamesList);
        enteredNamesListView.setAdapter(adapter);
    }
}