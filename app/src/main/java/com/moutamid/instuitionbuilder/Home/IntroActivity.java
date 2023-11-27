package com.moutamid.instuitionbuilder.Home;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.fxn.stash.Stash;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.moutamid.instuitionbuilder.Authentication.LoginActivity;
import com.moutamid.instuitionbuilder.R;

public class IntroActivity extends AppCompatActivity {

    private BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior;
    VideoView videoView;
    Button buttonNext;
    TextView register, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
        buttonNext = findViewById(R.id.buttonNext);
        videoView = findViewById(R.id.videoView);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.trainging_video); // Replace with your video resource ID
        videoView.setVideoURI(videoUri);
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Stash.put("video_seen", "yes");
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
                finish();
            }
        });
        showBottomSheetDialog();
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });

//       BottomSheetBehavior.from(findViewById(R.id.bottomSheet));
//        ConstraintLayout bottomSheet = findViewById(R.id.bottomSheet);
//        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
//
//        // #3 Listening to State Changes of BottomSheet
//        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onSlide(View bottomSheet, float slideOffset) {
//                // Optional: Implement onSlide behavior
//            }
//
//            @Override
//            public void onStateChanged(View bottomSheet, int newState) {
////                Button buttonBottomSheetPersistent = findViewById(R.id.buttonBottomSheetPersistent);
////                buttonBottomSheetPersistent.setText(getButtonText(newState));
//            }
//        });

        // #4 Changing the BottomSheet State on ButtonClick
//        Button buttonBottomSheetPersistent = findViewById(R.id.buttonBottomSheetPersistent);
//        buttonBottomSheetPersistent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int state = (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) ?
//                        BottomSheetBehavior.STATE_COLLAPSED : BottomSheetBehavior.STATE_EXPANDED;
//                bottomSheetBehavior.setState(state);
//            }
//        });
    }

    // Helper method to get the button text based on the BottomSheet state
//    private String getButtonText(int newState) {
//        switch (newState) {
//            case BottomSheetBehavior.STATE_EXPANDED:
//                return "Close Persistent Bottom Sheet";
//            case BottomSheetBehavior.STATE_COLLAPSED:
//                return "Open Persistent Bottom Sheet";
//            default:
//                return "Persistent Bottom Sheet";
//        }
//    }
//    }


    private void showBottomSheetDialog() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet);
        LinearLayout login_layout = bottomSheetDialog.findViewById(R.id.login_layout);
        LinearLayout register_layout = bottomSheetDialog.findViewById(R.id.register_layout);
        register = bottomSheetDialog.findViewById(R.id.register);
        login = bottomSheetDialog.findViewById(R.id.login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
register_layout.setVisibility(View.VISIBLE);
login_layout.setVisibility(View.GONE);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_layout.setVisibility(View.GONE);
                login_layout.setVisibility(View.VISIBLE);
            }
        });


//        LinearLayout download = bottomSheetDialog.findViewById(R.id.download);
//        LinearLayout delete = bottomSheetDialog.findViewById(R.id.delete);

        bottomSheetDialog.show();
    }
}
