package com.moutamid.instuitionbuilder.Home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.fxn.stash.Stash;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.messaging.FirebaseMessaging;
import com.makeramen.roundedimageview.RoundedImageView;
import com.masoudss.lib.WaveformSeekBar;
import com.moutamid.instuitionbuilder.R;
import com.moutamid.instuitionbuilder.config.Config;
import com.moutamid.instuitionbuilder.config.RankManager;
import com.moutamid.instuitionbuilder.config.SubscribeDialogClass;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;

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
    private RankManager rankManager;
    ArrayList<String> stringList = new ArrayList<>();
    TextView buttonOnBoardingAction;
    //    VoicePlayerView voicePlayerView;
    RelativeLayout audio;
    TextView textView;
    CircleImageView dp;
    TextView user_name, animal_name;
    RoundedImageView animal_image;
    public MediaPlayer mediaPlayer;
    WaveformSeekBar waveformSeekBar;
    private CountDownTimer timer;
    TextView questionText;
    ImageView notification;
    int current_position = 0;
    ImageView badge;
    private Handler autoScrollHandler = new Handler();
    private int autoScrollDuration = 5000; // Auto-scroll duration in milliseconds
    private boolean userScrolled = false;
    String[] displayedValues;
    NumberPicker numberPicker;
    private int lastManualScrollPosition = -1;
    ArrayList<Integer> integerList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_through);
        waveformSeekBar = findViewById(R.id.wave);
        notification = findViewById(R.id.notification);
        badge = findViewById(R.id.badge);
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
        numberPicker = findViewById(R.id.number_picker);

        dp.setImageResource(Stash.getInt("image_path"));
        user_name.setText(Stash.getString("name"));
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // Create RankManager instance
        SharedPreferences preferences = getSharedPreferences("appUsage", MODE_PRIVATE);
        rankManager = new RankManager(preferences);
        animal_name.setText("Church");
        animal_image.setImageResource(R.drawable.church);
        rankManager.checkRankAttainment();
        showBottomSheetDialog();
        mediaPlayer = MediaPlayer.create(this, R.raw.church);
        showsecondBottomSheetDialog();
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
        waveformSeekBar.setSampleFrom(R.raw.church);

        badge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WalkThroughActivity.this, BadgeActivity.class));

                timer.cancel();
            }
        });

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

                if (Stash.getString("first_time").equals("yes")) {
                    animal_image.setImageResource(Config.dataArrayList().get(current_position).image);
                    audio.setVisibility(View.GONE);
                    gallery.setVisibility(View.VISIBLE);
                } else {
                    SubscribeDialogClass completeDialogClass = new SubscribeDialogClass(WalkThroughActivity.this);
                    completeDialogClass.show();
                }
            }
        });
        audio_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Stash.getString("first_time").equals("yes")) {
                    gallery.setVisibility(View.GONE);
                    animal_name.setText(Config.dataArrayList().get(current_position).text);
                    mediaPlayer = MediaPlayer.create(WalkThroughActivity.this, Config.dataArrayList().get(current_position).audio);

                    waveformSeekBar.setSampleFrom(Config.dataArrayList().get(current_position).audio);
                    int duration = mediaPlayer.getDuration();
                    int seconds = duration / 1000;
                    int minutes = seconds / 60;
                    if (seconds == 0) {
                        textView.setText("00:01");

                    } else {
                        String formattedDuration = String.format("%02d:%02d", minutes % 60, seconds % 60);
                        textView.setText(formattedDuration + "");
                    }
                    audio.setVisibility(View.VISIBLE);
                } else {
                    SubscribeDialogClass completeDialogClass = new SubscribeDialogClass(WalkThroughActivity.this);
                    completeDialogClass.show();                }
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                pause_icon.setVisibility(View.GONE);
                play_icon.setVisibility(View.VISIBLE);
            }
        });


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


        if (Stash.getString("first_time").equals("no")) {

            questionText.setText("1/" + Config.secondRoundDataArrayList().size());
            displayedValues = new String[Config.secondRoundDataArrayList().size()];
            for (int i = 0; i < Config.secondRoundDataArrayList().size(); i++) {
                displayedValues[i] = Config.secondRoundDataArrayList().get(i).text.toString();
            }
        } else {
            questionText.setText("1/" + Config.dataArrayList().size());

            displayedValues = new String[Config.dataArrayList().size()];

            for (int i = 0; i < Config.dataArrayList().size(); i++) {
                displayedValues[i] = Config.dataArrayList().get(i).text.toString();
            }

        }


        numberPicker.setMinValue(1);
        numberPicker.setValue(1);
        numberPicker.setMaxValue(displayedValues.length);
        numberPicker.setDisplayedValues(displayedValues);
        numberPicker.setFadingEdgeEnabled(true);
        numberPicker.setFadingEdgeStrength(80);
        numberPicker.setScrollerEnabled(true);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setAccessibilityDescriptionEnabled(true);
        numberPicker.setSelectedTextColor(ContextCompat.getColor(WalkThroughActivity.this, R.color.white));

        autoScrollHandler.postDelayed(autoScrollRunnable, autoScrollDuration);

        numberPicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                if (!integerList.contains(numberPicker.getValue())) {
                    integerList.add(numberPicker.getValue());
                }
                Log.d("list_size", integerList.size()+ " ");
                if(integerList.size()>=14)
                {
                    buttonOnBoardingAction.setVisibility(View.VISIBLE);
                }
            }
        });

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                current_position = newVal - 1;
                lastManualScrollPosition = newVal - 1;

                if (Stash.getString("first_time").equals("no")) {
                    questionText.setText(newVal + "/" + Config.secondRoundDataArrayList().size());
                } else {
                    questionText.setText(newVal + "/" + Config.dataArrayList().size());
                }
                resetAutoScrollTimer();


            }
        });
    }


//    @Override
//    public void onBackPressed() {
//        gallery.setVisibility(View.GONE);
//        audio.setVisibility(View.GONE);
//        play_icon.setVisibility(View.VISIBLE);
//        pause_icon.setVisibility(View.GONE);
//        waveformSeekBar.setProgress(0);

//    }

    @Override
    protected void onResume() {
        super.onResume();
        rankManager.updateLastUsageDate();
//        timer.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
//        timer.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        timer.cancel();
    }

    private void showBottomSheetDialog() {

        if (!Stash.getBoolean("banner_show")) {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(R.layout.main_banner);
            Button btnGetStart = bottomSheetDialog.findViewById(R.id.btnGetStart);
            bottomSheetDialog.show();
            bottomSheetDialog.setCancelable(false);
            btnGetStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomSheetDialog.dismiss();
                    Stash.put("banner_show", true);
                }
            });
        }
    }

    private void showsecondBottomSheetDialog() {
        if (Stash.getString("first_time").equals("no") && !Stash.getBoolean("second")) {
            Stash.put("second", true);
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(R.layout.second_attempt_banner);
            Button btnGetStart = bottomSheetDialog.findViewById(R.id.btnGetStart);
            bottomSheetDialog.show();
            bottomSheetDialog.setCancelable(false);
            btnGetStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomSheetDialog.dismiss();
                }
            });
        }
    }

    private Runnable autoScrollRunnable = new Runnable() {
        @Override
        public void run() {
            // Auto-scroll logic
            int nextPosition;

            if (lastManualScrollPosition != -1) {
                // If the user manually scrolled, continue from that position
                nextPosition = lastManualScrollPosition + 1;
                lastManualScrollPosition = -1; // Reset the manual scroll position
            } else {
                // Otherwise, continue with the regular auto-scroll logic
                nextPosition = current_position + 1;
            }
            if (nextPosition < displayedValues.length) {
                current_position = nextPosition;
                numberPicker.setValue(nextPosition + 1);
                autoScrollHandler.postDelayed(this, autoScrollDuration);
            }
            if (!integerList.contains(numberPicker.getValue())) {
                integerList.add(numberPicker.getValue());
            }
            if (Stash.getString("first_time").equals("no")) {
                if(integerList.size()>=15)
                {
                    buttonOnBoardingAction.setVisibility(View.VISIBLE);
                }
                questionText.setText(nextPosition + "/" + Config.secondRoundDataArrayList().size());
            } else {
                if(integerList.size()>=10)
                {
                    buttonOnBoardingAction.setVisibility(View.VISIBLE);
                }
                questionText.setText(nextPosition + "/" + Config.dataArrayList().size());
            }

            Log.d("list_size", integerList.size()+ " ");


        }
    };

    private void scheduleAutoScroll() {
        autoScrollHandler.postDelayed(autoScrollRunnable, autoScrollDuration);
    }

    private void resetAutoScrollTimer() {
        // Remove existing callbacks to prevent multiple auto-scrolls scheduled
        autoScrollHandler.removeCallbacks(autoScrollRunnable);
        autoScrollHandler.postDelayed(autoScrollRunnable, autoScrollDuration);

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                })
                .show();
    }
}