package com.moutamid.instuitionbuilder.Home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.masoudss.lib.SeekBarOnProgressChanged;
import com.masoudss.lib.WaveformSeekBar;
import com.moutamid.instuitionbuilder.R;
import com.shawnlin.numberpicker.NumberPicker;

import java.io.FileDescriptor;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

//import space.siy.waveformview.WaveFormData;
//import me.jagar.chatvoiceplayerlibrary.VoicePlayerView;

public class WalkThroughActivity extends AppCompatActivity {
    ImageView gallery_icon;
    RelativeLayout gallery;
    ImageView audio_icon, play_icon, pause_icon;
    View view1;
    int a = 0;
    TextView buttonOnBoardingAction;
    //    VoicePlayerView voicePlayerView;
    RelativeLayout audio;
    WaveformSeekBar waveformSeekBar;
    TextView textView;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_through);
        gallery_icon = findViewById(R.id.gallery_icon);
        audio_icon = findViewById(R.id.audio_icon);
        gallery = findViewById(R.id.gallery);
        view1 = findViewById(R.id.view);
        audio = findViewById(R.id.audio);
        textView = findViewById(R.id.textView);
        waveformSeekBar = findViewById(R.id.waveformSeekBar);
        play_icon = findViewById(R.id.play);
        pause_icon = findViewById(R.id.pause);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Timer timer = new Timer();
        MediaPlayer player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            int rawResourceId = R.raw.cat;

            FileDescriptor fileDescriptor = getResources().openRawResourceFd(rawResourceId).getFileDescriptor();
            player.setDataSource(fileDescriptor);
            player.prepare();

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    // Audio playback completed
                    pause_icon.setVisibility(View.GONE);
                    play_icon.setVisibility(View.VISIBLE);
                    timer.cancel();
//                    timerTask.cancel();
                    textView.setText("00:00");
                    waveformSeekBar.setProgress(0);
                }
            });

            waveformSeekBar.setOnProgressChanged(new SeekBarOnProgressChanged() {
                @Override
                public void onProgressChanged(@NonNull WaveformSeekBar waveformSeekBar, float v, boolean b) {
                    player.seekTo((int) (player.getDuration() * v));
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            int duration = player.getDuration();
                            int currentPosition = player.getCurrentPosition();
                            int remainingTime = duration - currentPosition;
                            Toast.makeText(WalkThroughActivity.this, "timer"+ remainingTime, Toast.LENGTH_SHORT).show();
                            String formattedTime = String.format("%02d:%02d", remainingTime / 1000, remainingTime % 1000 / 100);
                            textView.setText(formattedTime);
                        }
                    };

                }
            });

            play_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    player.start();
                    pause_icon.setVisibility(View.VISIBLE);
                    play_icon.setVisibility(View.GONE);
                }
            });

            pause_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    player.pause();
                    pause_icon.setVisibility(View.GONE);
                    play_icon.setVisibility(View.VISIBLE);
                }
            });
        } catch (Exception e) {
            Log.d("data", e.getMessage());
        }

        FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.app_name) + "general");
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
//                WalkThroughActivity.this, R.style.Theme_Design);
//        View bottomSheetView = LayoutInflater.from(getApplicationContext())
//                .inflate(R.layout.modal_bottom_sheet,
//                        (RelativeLayout)findViewById(R.id.bottomSheet));
//        bottomSheetDialog.setContentView(bottomSheetView);
//        bottomSheetDialog.show();
//        voicePlayerView = findViewById(R.id.voicePlayerView);
//        voicePlayerView.setAudio("https://firebasestorage.googleapis.com/v0/b/sweet-nutrition.appspot.com/o/Meowing-noise.mp3?alt=media&token=5da9659f-e980-4c42-af22-006d51f6bd91&_gl=1*p6hdvq*_ga*MjA4NzQ2NjI5NC4xNjkzMzk0ODc5*_ga_CW55HF8NVT*MTY5OTEwMTgxNy45Mi4xLjE2OTkxMDE4NzAuNy4wLjA");
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
        numberPicker.setSelectedTextColor(ContextCompat.getColor(this, R.color.dark_grey));
        numberPicker.setSelectedTextSize(getResources().getDimension(R.dimen.selected_text_size));
        numberPicker.setSelectedTypeface(Typeface.create(getString(R.string.roboto_light), Typeface.NORMAL));
        numberPicker.setSelectedTypeface(getString(R.string.roboto_light), Typeface.NORMAL);

        numberPicker.setTextColorResource(R.color.dark_grey);

        // Set text size
        numberPicker.setTextSize(R.dimen.text_size);

        // Set typeface
        numberPicker.setTypeface(Typeface.create(getString(R.string.roboto_light), Typeface.NORMAL));
        numberPicker.setTypeface(getString(R.string.roboto_light), Typeface.NORMAL);
        String[] data = {"Cat", "Dog", "Tiger", "Elephant"};
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);

        numberPicker.setFadingEdgeEnabled(true);
        numberPicker.setScrollerEnabled(true);

        // Set wrap selector wheel
        numberPicker.setWrapSelectorWheel(true);

        // Set accessibility description enabled
        numberPicker.setAccessibilityDescriptionEnabled(true);

        // OnClickListener
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


        // OnValueChangeListener
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // Your code
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
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
                        Log.d("TAG", String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal));
                    }
                }, 400);
            }

        });


    }

    public void test_start(View view) {
        startActivity(new Intent(WalkThroughActivity.this, TestStartedActivity.class));
    }

    @Override
    public void onBackPressed() {
        gallery.setVisibility(View.GONE);
        audio.setVisibility(View.GONE);

    }

}