package com.tplink.halohoop.floatanimationdemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.halohoop.bounceprogressbar.views.BounceProgressBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView mIv;
    private BounceProgressBar mBpb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIv = (ImageView) findViewById(R.id.iv);

        //just for test
        mBpb = (BounceProgressBar) findViewById(R.id.bpb);
        mBpb.startTotalAnimation();


        //juet for review
//        ObjectAnimator anim = ObjectAnimator.ofFloat(mIv, "alpha", 0f, 1f, 0f);
//        anim.setDuration(1000)
//                .setRepeatCount(ValueAnimator.INFINITE);
//        anim.start();


//        floatAnim(mIv, 1);
//        floatAnim2(mIv, 1);
        //final use version
        floatAnim3(mIv, 0, 10.0f, 1500);

    }

    /**
     * 有加入插值器
     *
     * @param view
     * @param delay
     */
    private void floatAnim(View view, int delay) {
        List<Animator> animators = new ArrayList<>();
//        ObjectAnimator translationXAnim = ObjectAnimator.ofFloat(view, "translationX", -6.0f,
//                6.0f, -6.0f);
//        translationXAnim.setDuration(1500);
//        translationXAnim.setRepeatCount(ValueAnimator.INFINITE);//无限循环
//        translationXAnim.setRepeatMode(ValueAnimator.INFINITE);//
//        translationXAnim.start();
//        animators.add(translationXAnim);
        ObjectAnimator translationYAnimUp = ObjectAnimator.ofFloat(view, "translationY",
                0, -100.0f);
        translationYAnimUp.setDuration(1000);
        translationYAnimUp.setInterpolator(new DecelerateInterpolator());
        translationYAnimUp.setRepeatCount(ValueAnimator.INFINITE);
        translationYAnimUp.setRepeatMode(ValueAnimator.INFINITE);
//        translationYAnimUp.start();
        animators.add(translationYAnimUp);

        ObjectAnimator translationYAnimDown = ObjectAnimator.ofFloat(view, "translationY",
                -100.0f, 0);
        translationYAnimDown.setDuration(1000);
        translationYAnimDown.setInterpolator(new AccelerateInterpolator());
        translationYAnimDown.setRepeatCount(ValueAnimator.INFINITE);
        translationYAnimDown.setRepeatMode(ValueAnimator.INFINITE);
//        translationYAnimUp.start();
        animators.add(translationYAnimDown);

        AnimatorSet btnSexAnimatorSet = new AnimatorSet();
        btnSexAnimatorSet.playSequentially(animators);
        btnSexAnimatorSet.setStartDelay(delay);
        btnSexAnimatorSet.start();
    }

    /**
     * 没有加入插值器
     *
     * @param view
     * @param delay
     */
    private void floatAnim2(View view, int delay) {
        List<Animator> animators = new ArrayList<>();
//        ObjectAnimator translationXAnim = ObjectAnimator.ofFloat(view, "translationX", -6.0f,
//                6.0f, -6.0f);
//        translationXAnim.setDuration(1500);
//        translationXAnim.setRepeatCount(ValueAnimator.INFINITE);//无限循环
//        translationXAnim.setRepeatMode(ValueAnimator.INFINITE);//
//        translationXAnim.start();
//        animators.add(translationXAnim);
        ObjectAnimator translationYAnim = ObjectAnimator.ofFloat(view, "translationY", -200.0f,
                200.0f, -200.0f);
        translationYAnim.setDuration(2000);
        translationYAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        translationYAnim.setRepeatCount(ValueAnimator.INFINITE);
        translationYAnim.setRepeatMode(ValueAnimator.INFINITE);
        translationYAnim.start();
        animators.add(translationYAnim);

        AnimatorSet btnSexAnimatorSet = new AnimatorSet();
        btnSexAnimatorSet.playSequentially(animators);
        btnSexAnimatorSet.setStartDelay(delay);
        btnSexAnimatorSet.start();
    }


    /**
     * 最终决定使用的
     *
     * @param view
     * @param delay
     */
    private void floatAnim3(View view, int delay, float animDistance, int totalDuration) {
        List<Animator> animators = new ArrayList<>();
//        ObjectAnimator translationXAnim = ObjectAnimator.ofFloat(view, "translationX", -6.0f,
//                6.0f, -6.0f);
//        translationXAnim.setDuration(1500);
//        translationXAnim.setRepeatCount(ValueAnimator.INFINITE);//无限循环
//        translationXAnim.setRepeatMode(ValueAnimator.INFINITE);//
//        translationXAnim.start();
//        animators.add(translationXAnim);
        ObjectAnimator translationYAnim = ObjectAnimator.ofFloat(view, "translationY",
                -animDistance,
                animDistance, -animDistance);
        translationYAnim.setDuration(totalDuration);
        translationYAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        translationYAnim.setRepeatCount(ValueAnimator.INFINITE);
        translationYAnim.setRepeatMode(ValueAnimator.INFINITE);
        translationYAnim.start();
        animators.add(translationYAnim);

        AnimatorSet btnSexAnimatorSet = new AnimatorSet();
        btnSexAnimatorSet.playSequentially(animators);
        btnSexAnimatorSet.setStartDelay(delay);
        btnSexAnimatorSet.start();
    }
}
