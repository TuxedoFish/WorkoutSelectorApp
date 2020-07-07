package com.liversedge.workoutselector.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.liversedge.workoutselector.R;

public class ImageHelper {

    public static RoundedBitmapDrawable getRoundedImage(Integer resourceID, Integer cornerRadius, Context context) {

        Bitmap authorSource = BitmapFactory.decodeResource(context.getResources(), resourceID);
        RoundedBitmapDrawable authorDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), authorSource);
        authorDrawable.setCornerRadius(cornerRadius);

        return authorDrawable;

    }
}
