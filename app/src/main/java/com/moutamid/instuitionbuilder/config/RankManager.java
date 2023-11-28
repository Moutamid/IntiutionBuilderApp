package com.moutamid.instuitionbuilder.config;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class RankManager {

    private static SharedPreferences preferences;
    private static long firstUsageDate;
    private static  long lastUsageDate;
    private static  int streak;
    public static String currentRank;

    public RankManager(SharedPreferences preferences) {
        this.preferences = preferences;

        // Load initial values
        firstUsageDate = preferences.getLong("firstUsageDate", 0);
        lastUsageDate = preferences.getLong("lastUsageDate", 0);
        streak = preferences.getInt("streak", 0);
        currentRank = preferences.getString("rank", "Cerebral");
    }

    public void checkRankAttainment() {
        // Calculate days used
        long daysUsed = lastUsageDate - firstUsageDate;

        // Check for each rank
        if (daysUsed >= 3 && streak >= 8) {
            // Award Psychic rank
            awardRank("Psychic");
        } else if (daysUsed >= 5 && streak >= 13) {
            awardRank("Lucid dreamer");
        } else if (daysUsed >= 7 && streak >= 21) {
            awardRank("Juggernaut");
        } else if (daysUsed >= 13 && streak >= 34) {
            awardRank("Titan");
        } else if (daysUsed >= 21 && streak >= 55) {
            awardRank("Pure Intuition");
        }
    }

    private void awardRank(String rank) {
        // Update rank in SharedPreferences
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("rank", rank);
        editor.commit();
        // Notify user of rank achievement
        Log.d("Rank", rank+  "rank");
        currentRank = rank;
    }

    public static void updateStreak(boolean isCorrectAnswer) {
        // Update streak counter
        if (isCorrectAnswer) {
            streak++;
        } else {
            streak = 0;
        }

        // Save streak to SharedPreferences
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("streak", streak);
        editor.commit();
    }

    public void updateLastUsageDate() {
        // Update lastUsageDate to current date
        long currentDate = System.currentTimeMillis();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("lastUsageDate", currentDate);
        editor.commit();

        if (firstUsageDate == 0) {
            firstUsageDate = currentDate;
            checkRankAttainment();
        }
    }

    public String getCurrentRank() {
        return currentRank;
    }
}
