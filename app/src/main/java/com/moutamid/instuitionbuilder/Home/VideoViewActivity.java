package com.moutamid.instuitionbuilder.Home;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.moutamid.instuitionbuilder.R;

public class VideoViewActivity extends AppCompatActivity {
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        videoView = findViewById(R.id.videoView);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.trainging_video); // Replace with your video resource ID
        videoView.setVideoURI(videoUri);
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(VideoViewActivity.this);
                bottomSheetDialog.setContentView(R.layout.after_see_video);
                Button btnGetStart = bottomSheetDialog.findViewById(R.id.btnGetStart);
                bottomSheetDialog.show();
                bottomSheetDialog.setCancelable(false);
                btnGetStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                        Stash.put("video_show", true);
                        finish();
                    }
                });
            }
        });
    }
}