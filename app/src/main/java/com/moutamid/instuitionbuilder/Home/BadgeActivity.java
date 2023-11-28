package com.moutamid.instuitionbuilder.Home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.moutamid.instuitionbuilder.R;
import com.moutamid.instuitionbuilder.config.RankManager;

public class BadgeActivity extends AppCompatActivity {
    TextView title_rank;
    ImageView rankImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge);
        title_rank = findViewById(R.id.title);
        rankImageView = findViewById(R.id.rankImageView);
        SharedPreferences preferences = getSharedPreferences("appUsage", MODE_PRIVATE);
        RankManager rankManager = new RankManager(preferences);
        rankManager.checkRankAttainment();
        title_rank.setText(RankManager.currentRank);
        switch (RankManager.currentRank) {
            case "Cerebral":
                rankImageView.setImageResource(R.drawable.cerebral);
                break;
            case "Psychic":
                rankImageView.setImageResource(R.drawable.psychic);
                break;
            case "Lucid Dreamer":
                rankImageView.setImageResource(R.drawable.lucidreamer);
                break;
            case "Juggernaut":
                rankImageView.setImageResource(R.drawable.juggernaunt);
                break;
            case "Titan":
                rankImageView.setImageResource(R.drawable.titan);
                break;
            case "Pure Intuition":
                rankImageView.setImageResource(R.drawable.pure_intuition);
                break;
            default:
                break;
        }
    }
}