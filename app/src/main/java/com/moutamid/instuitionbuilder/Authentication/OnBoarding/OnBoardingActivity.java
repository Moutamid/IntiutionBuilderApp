package com.moutamid.instuitionbuilder.Authentication.OnBoarding;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.moutamid.instuitionbuilder.Authentication.LoginActivity;
import com.moutamid.instuitionbuilder.R;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingActivity extends AppCompatActivity {
    List<OnBoardingModel> listBoarding;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_onboarding);
        initViews();


        final LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                final View itemView = mLayoutInflater.inflate(R.layout.pager_row, container, false);
                Button btnGetStart = itemView.findViewById(R.id.btnGetStart);
                ImageView imageView = itemView.findViewById(R.id.imgMain);
                ImageView main_img = itemView.findViewById(R.id.main_img);
                ImageView main_img1 = itemView.findViewById(R.id.main_img1);
                ImageView imgMain1 = itemView.findViewById(R.id.imgMain1);
                ImageView page_no = itemView.findViewById(R.id.page_no);
                TextView txtTitle = itemView.findViewById(R.id.txtTitle);
                TextView txtMain = itemView.findViewById(R.id.txtMain);
                page_no.setImageResource(listBoarding.get(position).getPage_no());
                imageView.setImageResource(listBoarding.get(position).getVector());
                txtTitle.setText(listBoarding.get(position).getText());
                txtMain.setText(listBoarding.get(position).getTextMain());
                imageView.setVisibility(View.VISIBLE);
                txtTitle.setVisibility(View.VISIBLE);

//                imageView.clearAnimation();
//                txtTitle.clearAnimation();
//                imageView.setAnimation(null);
//
//
//                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.view_to_top);
//                imageView.setAnimation(animation);
//                txtTitle.setAnimation(animation);

                btnGetStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == 2) {
//                            SharedPreferences preferences = getSharedPreferences("Record", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = preferences.edit();
//                            editor.putString("boarding_view", "yes");
//                            editor.apply();
                            startActivity(new Intent(OnBoardingActivity.this, LoginActivity.class));
                            finishAffinity();
                        }
                        else
                        {
                            viewPager.setCurrentItem(position + 1);
                        }
                    }
                });

                if (position == 2) {
                    main_img.setVisibility(View.VISIBLE);
                    imgMain1.setVisibility(View.GONE);
                    main_img1.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);

                    btnGetStart.setText("Continue");
                }
                else if(position==1)
                {
                    main_img.setVisibility(View.GONE);
                    imgMain1.setVisibility(View.VISIBLE);
                    main_img1.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                    btnGetStart.setText("Next");

                }
                else  if(position==0)
                {
                    main_img.setVisibility(View.VISIBLE);
                    imgMain1.setVisibility(View.GONE);
                    main_img1.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    btnGetStart.setText("Next");
                }


                container.addView(itemView);
                return itemView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((RelativeLayout) object);
            }
        });

    }

    private void initViews() {
        listBoarding = new ArrayList<>();
        viewPager = findViewById(R.id.view_pager);
        prepareBoardingData();
        viewPager.setOnTouchListener((view, motionEvent) -> true);


    }

    private void doBounceAnimation(View targetView) {
        Interpolator interpolator = v -> {
            return getPowOut(v, 2);//Add getPowOut(v,3); for more up animation
        };
        ObjectAnimator animator = ObjectAnimator.ofFloat(targetView, "translationY", 0, 25, 0);
        animator.setInterpolator(interpolator);
        animator.setStartDelay(200);
        animator.setDuration(800);
        animator.setRepeatCount(2);

        runOnUiThread(() -> animator.start());

    }

    private float getPowOut(float elapsedTimeRate, double pow) {
        return (float) ((float) 1 - Math.pow(1 - elapsedTimeRate, pow));
    }

    void prepareBoardingData() {
        OnBoardingModel bm = new OnBoardingModel("Lorem ipsum dolor eiusmod sit amet consectetur adipiscing elit", "Earn great badges by maintaining your steak", R.drawable.mockup_1, R.drawable.page_1);
        listBoarding.add(bm);

        bm = new OnBoardingModel("Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua", "Easily login to your account", R.drawable.mockup_2, R.drawable.page_2);
        listBoarding.add(bm);
        bm = new OnBoardingModel("Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua", "Walk Through your vocabulary ", R.drawable.mockup_3, R.drawable.page_3);
        listBoarding.add(bm);


     }
}
