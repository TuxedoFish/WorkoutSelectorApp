package com.example.workoutselector.components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.workoutselector.R;

public class HorizontalWorkoutScrollerView extends FrameLayout {

    private static int ANIMATION_DUR = 200;
    private static int NUMBER_OF_VIEWS = 2;
    ImageView current_image, next_image;

    int last_result=0, old_value=0;

    IEventEnd eventEnd;

    public HorizontalWorkoutScrollerView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public HorizontalWorkoutScrollerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setEventEnd(IEventEnd eventEnd) {
        this.eventEnd = eventEnd;
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.horizontal_workout_scroller, this);
        current_image = (ImageView) getRootView().findViewById(R.id.current_view);
        next_image = (ImageView) getRootView().findViewById(R.id.next_view);

        next_image.setTranslationX(getWidth());
    }

    public void setValueRandom(final int image, final int rotate_count) {
        current_image.animate().translationX(-getWidth()).setDuration(ANIMATION_DUR).start();
        next_image.setTranslationX(current_image.getWidth());
        next_image.animate()
                .translationX(0)
                .setDuration(ANIMATION_DUR)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        setView(current_image, old_value%NUMBER_OF_VIEWS);
                        current_image.setTranslationY(0);
                        if(old_value != rotate_count) {
                            setValueRandom(image, rotate_count);
                            old_value ++;
                        } else {
                            last_result = 0;
                            old_value = 0;
                            setView(next_image, image);
                        }
                    }
                });
    }

    private void setView(ImageView imageView, int value) {
        if(value == 0) {
            imageView.setImageResource(R.drawable.ic_fitness_center_24px);
        } else {
            imageView.setImageResource(R.drawable.ic_play_arrow_24px);
        }

        imageView.setTag(value);
        last_result = value;
    }

    public int getValue() {
        return Integer.parseInt(next_image.getTag().toString());
    }
}
