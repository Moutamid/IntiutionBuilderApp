package com.moutamid.instuitionbuilder.Home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.fxn.stash.Stash;
import com.google.firebase.messaging.FirebaseMessaging;
import com.makeramen.roundedimageview.RoundedImageView;
import com.masoudss.lib.WaveformSeekBar;
import com.moutamid.instuitionbuilder.R;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

//import space.siy.waveformview.WaveFormData;
//import me.jagar.chatvoiceplayerlibrary.VoicePlayerView;

public class WalkThroughActivity extends AppCompatActivity {
    ImageView gallery_icon;
    RelativeLayout gallery;
    ImageView audio_icon, play_icon, pause_icon;
    View view1;
    boolean touch = false;
    private TextView timerText;

    ArrayList<String> stringList = new ArrayList<>();
    TextView buttonOnBoardingAction;
    //    VoicePlayerView voicePlayerView;
    RelativeLayout audio;
    TextView textView;
    String name = "Elephant";
    CircleImageView dp;
    TextView user_name, animal_name;
    RoundedImageView animal_image;
    public MediaPlayer mediaPlayer;
    WaveformSeekBar waveformSeekBar;
    private CountDownTimer timer;
    TextView questionText;
    ImageView notification;
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_through);
        waveformSeekBar = findViewById(R.id.wave);
        notification = findViewById(R.id.notification);
        dp = findViewById(R.id.dp);
        animal_image = findViewById(R.id.animal_image);
        timerText = findViewById(R.id.timerText);
        animal_name = findViewById(R.id.animal_name);
        user_name = findViewById(R.id.user_name);
        gallery_icon = findViewById(R.id.gallery_icon);
        audio_icon = findViewById(R.id.audio_icon);
        gallery = findViewById(R.id.gallery);
        view1 = findViewById(R.id.view);
        audio = findViewById(R.id.audio);
        textView = findViewById(R.id.textView);
        play_icon = findViewById(R.id.play);
        pause_icon = findViewById(R.id.pause);
        questionText = findViewById(R.id.questionText);
        questionText.setText("1/10");
        dp.setImageResource(Stash.getInt("image_path"));
        user_name.setText(Stash.getString("name"));
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mediaPlayer = MediaPlayer.create(this, R.raw.cat);
        timer = new

                CountDownTimer(60000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // Update the timer display
                        timerText.setText(millisUntilFinished / 1000 + "s");
                    }

                    @Override
                    public void onFinish() {
                        timer.cancel();
                        startActivity(new Intent(WalkThroughActivity.this, TestStartedActivity.class));
                    }
                }.

                start();
        waveformSeekBar.setSampleFrom(R.raw.cat);

        //playing media


        play_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                pause_icon.setVisibility(View.VISIBLE);
                play_icon.setVisibility(View.GONE);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(WalkThroughActivity.this, NotificationsActivity.class
            ));
            }
        });

        pause_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                pause_icon.setVisibility(View.GONE);
                play_icon.setVisibility(View.VISIBLE);
            }
        });


        FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.app_name) + "general");
        buttonOnBoardingAction = findViewById(R.id.buttonOnBoardingAction);
        gallery_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gallery.setVisibility(View.VISIBLE);
            }
        });
        audio_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audio.setVisibility(View.VISIBLE);
            }
        });

        NumberPicker numberPicker = findViewById(R.id.number_picker);


        numberPicker.setDividerThickness(0);
        numberPicker.setFormatter(getString(R.string.number_picker_formatter));
        numberPicker.setFormatter(R.string.number_picker_formatter);
        numberPicker.setSelectedTextColor(ContextCompat.getColor(WalkThroughActivity.this, R.color.dark_grey));
        numberPicker.setSelectedTextSize(getResources().getDimension(R.dimen.selected_text_size));
        numberPicker.setSelectedTypeface(Typeface.create(getString(R.string.roboto_light), Typeface.NORMAL));
        numberPicker.setSelectedTypeface(getString(R.string.roboto_light), Typeface.NORMAL);

        numberPicker.setTextColorResource(R.color.dark_grey);

        // Set text size
        numberPicker.setTextSize(R.dimen.text_size);

        // Set typeface
        numberPicker.setTypeface(Typeface.create(getString(R.string.roboto_light), Typeface.NORMAL));
        numberPicker.setTypeface(getString(R.string.roboto_light), Typeface.NORMAL);
        String[] data = {"Cat", "Dog", "Tiger", "Rat", "Horse", "Lion", "Fish", "Elephant", "Deer", "Monkey"};
        numberPicker.setMinValue(1);
        numberPicker.setValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);
        numberPicker.setFadingEdgeEnabled(true);
        numberPicker.setFadingEdgeStrength(80);
        numberPicker.setScrollerEnabled(true);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setAccessibilityDescriptionEnabled(true);
        numberPicker.setSelectedTextColor(ContextCompat.getColor(WalkThroughActivity.this, R.color.white));

        numberPicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                touch = true;
                return false;
            }
        });
//
        numberPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numberPicker.setSelectedTextColor(ContextCompat.getColor(WalkThroughActivity.this, R.color.white));
                view1.setVisibility(View.VISIBLE);
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    buttonOnBoardingAction.setTextColor(Color.WHITE);
                    buttonOnBoardingAction.setBackgroundDrawable(ContextCompat.getDrawable(WalkThroughActivity.this, R.drawable.btn_bg_black));
                } else {
                    buttonOnBoardingAction.setTextColor(Color.WHITE);
                    buttonOnBoardingAction.setBackground(ContextCompat.getDrawable(WalkThroughActivity.this, R.drawable.btn_bg_black));
                }
            }
        });


        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // Your code
//                final Handler handler = new Handler(Looper.getMainLooper());
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        numberPicker.setSelectedTextColor(ContextCompat.getColor(WalkThroughActivity.this, R.color.white));
//                        view1.setVisibility(View.VISIBLE);
//                        final int sdk = android.os.Build.VERSION.SDK_INT;
//                        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                            buttonOnBoardingAction.setTextColor(Color.WHITE);
//                            buttonOnBoardingAction.setBackgroundDrawable(ContextCompat.getDrawable(WalkThroughActivity.this, R.drawable.btn_bg_black));
//                        } else {
//                            buttonOnBoardingAction.setTextColor(Color.WHITE);
//                            buttonOnBoardingAction.setBackground(ContextCompat.getDrawable(WalkThroughActivity.this, R.drawable.btn_bg_black));
//                        }
//                        if (touch) {
//                            try {
                                questionText.setText(newVal+"/"+data.length);
                                touch = false;
                                name = data[newVal - 1].toString();
//                                Toast.makeText(WalkThroughActivity.this, "name"+ name, Toast.LENGTH_SHORT).show();
                                Log.d("TAG", String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal));
//                                buttonOnBoardingAction.setText(name);
                                switch (name) {
                                    case "Cat":
                                        animal_image.setImageResource(R.drawable.cat);
                                        mediaPlayer = MediaPlayer.create(WalkThroughActivity.this, R.raw.cat);
                                        break;
                                    case "Tiger":
                                        animal_image.setImageResource(R.drawable.tiger);
                                        mediaPlayer = MediaPlayer.create(WalkThroughActivity.this, R.raw.tiger);
                                        break;
                                    case "Elephant":
                                        animal_image.setImageResource(R.drawable.elephant);
                                        mediaPlayer = MediaPlayer.create(WalkThroughActivity.this, R.raw.elephant);
                                        break;
                                    case "Dog":
                                        animal_image.setImageResource(R.drawable.dog);
                                        mediaPlayer = MediaPlayer.create(WalkThroughActivity.this, R.raw.dog);
                                        break;
                                }
////                                Thread.sleep(900);
//                            } catch (Exception e) {
//                                Log.d("Exception", e.getMessage().toString());
//                            }
//                        }
//                    }
//                }, 2000);

            }
        });

    }

    public void test_start(View view) {
        timer.cancel();
        startActivity(new Intent(WalkThroughActivity.this, TestStartedActivity.class));
    }

    @Override
    public void onBackPressed() {
        gallery.setVisibility(View.GONE);
        audio.setVisibility(View.GONE);
        play_icon.setVisibility(View.VISIBLE);
        pause_icon.setVisibility(View.GONE);
        waveformSeekBar.setProgress(0);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }
}