package com.liversedge.workoutselector.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.liversedge.workoutselector.R;

public class AnimationHandler {

    private static long ANIMATION_DUR = 200;

    public static void slide_down(final View v){

        v.setVisibility(View.VISIBLE);
        v.animate()
                .translationY(0)
                .setDuration(ANIMATION_DUR)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setTranslationY(0);
                    }
                });
    }

    public static void slide_up(final View v) {
        v.animate()
                .translationY(-v.getHeight())
                .setDuration(ANIMATION_DUR)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setVisibility(View.GONE);
                    }
                });
    }
}
