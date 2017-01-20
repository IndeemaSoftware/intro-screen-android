/*
 *  Copyright (C) 2017.  IndeemaSoftware, Inc.
 *  http://indeema.com
 *  All rights reserved
 */

package com.indeema.introview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

/**
 * Created with AndroidStudio.
 * User: Michael Nester
 * Date: January, 19, 2017
 * Time: 6:45 PM
 */
public class ViewUtils {

    public static void setZ(Context contexts, float value, int unit, View... views){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (View view : views) {
                view.setZ(TypedValue.applyDimension(unit, value, contexts.getResources().getDisplayMetrics()));
            }
        }
    }

    public static void scaleViews(float scale, View... views) {
        for (View view : views) {
            scaleView(view, scale);
        }
    }

    public static void scaleView(View view, float scale) {
        view.setScaleX(scale);
        view.setScaleY(scale);
    }

    public static void setViewsAlpha(float alpha, View... views) {
        for (View view : views) {
            setViewAlpha(view, alpha);
        }
    }

    public static void setViewAlpha(View view, float alpha) {
        view.setAlpha(alpha);
    }

    public static void setBackground(Context context, @DrawableRes int backgroundResId, View... views) {
        for (View view : views) {
            setBackground(view, ContextCompat.getDrawable(context, backgroundResId));
        }
    }

    public static void setBackground(Context context, @DrawableRes int backgroundResId, View view) {
        setBackground(view, ContextCompat.getDrawable(context, backgroundResId));
    }

    @SuppressWarnings("deprecation")
    public static void setBackground(View view, Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(Context context, float dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();

        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(Context context, float px) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();

        return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
