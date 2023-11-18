package com.moutamid.instuitionbuilder.onboadingOne;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.moutamid.instuitionbuilder.Authentication.LoginActivity;
import com.moutamid.instuitionbuilder.R;

import java.util.ArrayList;
import java.util.List;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.moutamid.instuitionbuilder.R;

public class OnBoardingDesignOne extends AppCompatActivity {

    private ViewPager viewPager;
    private ImageView backgroundImage;

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicator;
    Button buttonOnboardingAction;

    private int[] onboardingImages = {R.drawable.rank, R.drawable.cat, R.drawable.elephant};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_design_one);

        viewPager = findViewById(R.id.viewPager);
        backgroundImage = findViewById(R.id.backgroundImage);

        // Set up the ViewPager
        OnboardingPagerAdapter pagerAdapter = new OnboardingPagerAdapter(this, onboardingImages);
        viewPager.setAdapter(pagerAdapter);

//         }

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                OnBoardingDesignOne.this, R.style.Theme_Design);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.modal_bottom_sheet,
                        (ConstraintLayout) findViewById(R.id.modalBottomSheetCntainer));

        layoutOnboardingIndicator = bottomSheetView.findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = bottomSheetView.findViewById(R.id.button1);
        bottomSheetView.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("Record", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("boarding_view", "yes");
                editor.apply();
                Intent i = new Intent(OnBoardingDesignOne.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        setOnboardingItem();
        ViewPager2 onboardingViewPager = bottomSheetView.findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);

        setOnboadingIndicator();
        setCurrentOnboardingIndicators(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicators(position);
            }
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
        bottomSheetDialog.setCancelable(true);

    }

    private void setOnboadingIndicator() {
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(), R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicator.addView(indicators[i]);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setCurrentOnboardingIndicators(int index) {
        int childCount = layoutOnboardingIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutOnboardingIndicator.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive));
            }
        }
        if (index == onboardingAdapter.getItemCount() - 1) {
            buttonOnboardingAction.setText("Continue");

        } else {
            buttonOnboardingAction.setText("Next");
        }
    }

    private void setOnboardingItem() {
        List<OnBoardingItem> onBoardingItems = new ArrayList<>();
        OnBoardingItem itemFastFood = new OnBoardingItem();
        itemFastFood.setTitle("Earn great badges by maintaining your steak");
        itemFastFood.setDescription("Lorem ipsum dolor eiusmod sit amet consectetur adipiscing elit.");
        OnBoardingItem itemPayOnline = new OnBoardingItem();
        itemPayOnline.setTitle("Easily login to your account");
        itemPayOnline.setDescription("Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        OnBoardingItem itemEatTogether = new OnBoardingItem();
        itemEatTogether.setTitle("Walk Through your vocabulary");
        itemEatTogether.setDescription("Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        onBoardingItems.add(itemFastFood);
        onBoardingItems.add(itemPayOnline);
        onBoardingItems.add(itemEatTogether);
        onboardingAdapter = new OnboardingAdapter(onBoardingItems);

    }
}
//
//}


//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.core.content.ContextCompat;
//import androidx.viewpager2.widget.ViewPager2;
//
//import com.google.android.material.bottomsheet.BottomSheetDialog;
//import com.moutamid.instuitionbuilder.Authentication.LoginActivity;
//import com.moutamid.instuitionbuilder.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class OnBoardingDesignOne extends AppCompatActivity {
//

//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_on_boarding_design_one);
//
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
//                OnBoardingDesignOne.this, R.style.Theme_Design);
//        View bottomSheetView = LayoutInflater.from(getApplicationContext())
//                .inflate(R.layout.modal_bottom_sheet,
//                        (ConstraintLayout) findViewById(R.id.modalBottomSheetCntainer));
//
//        layoutOnboardingIndicator = bottomSheetView.findViewById(R.id.layoutOnboardingIndicators);
//        buttonOnboardingAction = bottomSheetView.findViewById(R.id.button1);
//        bottomSheetView.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences preferences = getSharedPreferences("Record", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.putString("boarding_view", "yes");
//                editor.apply();
//                Intent i = new Intent(OnBoardingDesignOne.this, LoginActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });
//        setOnboardingItem();
//        ViewPager2 onboardingViewPager = bottomSheetView.findViewById(R.id.onboardingViewPager);
//        onboardingViewPager.setAdapter(onboardingAdapter);
//
//        setOnboadingIndicator();
//        setCurrentOnboardingIndicators(0);
//
//        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                setCurrentOnboardingIndicators(position);
//            }
//        });
//
//        bottomSheetDialog.setContentView(bottomSheetView);
//        bottomSheetDialog.show();
//        bottomSheetDialog.setCancelable(true);
//
//    }
//
//    private void setOnboadingIndicator() {
//        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
//        );
//        layoutParams.setMargins(8, 0, 8, 0);
//        for (int i = 0; i < indicators.length; i++) {
//            indicators[i] = new ImageView(getApplicationContext());
//            indicators[i].setImageDrawable(ContextCompat.getDrawable(
//                    getApplicationContext(), R.drawable.onboarding_indicator_inactive
//            ));
//            indicators[i].setLayoutParams(layoutParams);
//            layoutOnboardingIndicator.addView(indicators[i]);
//        }
//    }
//
//    @SuppressLint("SetTextI18n")
//    private void setCurrentOnboardingIndicators(int index) {
//        int childCount = layoutOnboardingIndicator.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            ImageView imageView = (ImageView) layoutOnboardingIndicator.getChildAt(i);
//            if (i == index) {
//                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active));
//            } else {
//                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive));
//            }
//        }
//        if (index == onboardingAdapter.getItemCount() - 1){
//            buttonOnboardingAction.setText("Continue");
//           
//        }else {
//            buttonOnboardingAction.setText("Next");
//        }
//    }
//
//    private void setOnboardingItem() {
//        List<OnBoardingItem> onBoardingItems = new ArrayList<>();
//        OnBoardingItem itemFastFood = new OnBoardingItem();
//        itemFastFood.setTitle("Earn great badges by maintaining your steak");
//        itemFastFood.setDescription("Lorem ipsum dolor eiusmod sit amet consectetur adipiscing elit.");
//        OnBoardingItem itemPayOnline = new OnBoardingItem();
//        itemPayOnline.setTitle("Easily login to your account");
//        itemPayOnline.setDescription("Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
//        OnBoardingItem itemEatTogether = new OnBoardingItem();
//        itemEatTogether.setTitle("Walk Through your vocabulary");
//        itemEatTogether.setDescription("Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
//        onBoardingItems.add(itemFastFood);
//        onBoardingItems.add(itemPayOnline);
//        onBoardingItems.add(itemEatTogether);
//        onboardingAdapter = new OnboardingAdapter(onBoardingItems);
//
//    }
//
//
//
//}