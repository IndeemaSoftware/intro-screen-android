/*
 * Copyright (c) 2017. IndeemaSoftware, Inc.
 * http://indeema.com
 * All rights reserved
 */

package com.indeema.introview;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created with AndroidStudio.
 * User: Michael Nester
 * Date: January, 20, 2017
 * Time: 4:41 PM
 */
public class AnimationUtils {

    public interface AnimationCallback {
        void onAnimationStart();

        void onAnimationEnd();
    }

    public interface AnimationEndCallback {
        void onAnimationEnd();
    }

    public static void moveViewVerticallySin(View view, int targetTranslationY, int maxTranslationX,
                                             long duration, AnimationCallback listener) {
        moveViewVerticallySin(view, 0, targetTranslationY, maxTranslationX, duration, listener);
    }

    public static void moveViewVerticallySin(final View view,
                                             final int initialTranslationY, final int targetTranslationY, final int maxTranslationX,
                                             long duration, final AnimationCallback listener) {

        if (listener != null) listener.onAnimationStart();

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                view.setTranslationY(((targetTranslationY - initialTranslationY) * interpolatedTime) + initialTranslationY);

                float translationX = (float) Math.sin((interpolatedTime) * Math.PI) * maxTranslationX;
                view.setTranslationX(translationX);

                if (interpolatedTime == 1 && listener != null) listener.onAnimationEnd();
            }
        };
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    public static void moveViewHorizontallySin(View view, int targetTranslationX, int maxTranslationY,
                                               long duration, AnimationCallback listener) {
        moveViewHorizontallySin(view, 0, targetTranslationX, maxTranslationY, duration, listener);
    }

    public static void moveViewHorizontallySin(final View view, final int initialTranslationX, final int targetTranslationX,
                                               final int maxTranslationY, long duration, final AnimationCallback listener) {
        if (listener != null) {
            listener.onAnimationStart();
        }

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                view.setTranslationX(((targetTranslationX - initialTranslationX) * interpolatedTime) + initialTranslationX);

                float translationY = (float) Math.sin((interpolatedTime) * Math.PI) * maxTranslationY;
                view.setTranslationY(translationY);

                if (interpolatedTime == 1 && listener != null) listener.onAnimationEnd();
            }
        };
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    public static void moveViewCircularWithScaling(final View view, final int radius, final int initialAngle, final int targetAngle,
                                                   final Float scale, final Float alpha,
                                                   final float ellipsizeX, final float ellipsizeY,
                                                   long duration, final AnimationEndCallback listener) {

        final boolean scaleView = scale != null && alpha != null;

        final float initialScaleX = view.getScaleX();
        final float initialScaleY = view.getScaleY();
        final float initialAlpha = view.getAlpha();

        Animation animation = new Animation() {
            boolean isEnded = false;

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                float angle = ((targetAngle - initialAngle) * interpolatedTime) + initialAngle;
                int xCoordinate = (int) (Math.cos(Math.toRadians(angle)) * radius * ellipsizeX);
                int yCoordinate = (int) (Math.sin(Math.toRadians(angle)) * radius * ellipsizeY);
                view.setTranslationX(xCoordinate);
                view.setTranslationY(yCoordinate);

                if (scaleView) {
                    view.setScaleX(((scale - initialScaleX) * interpolatedTime) + initialScaleX);
                    view.setScaleY(((scale - initialScaleY) * interpolatedTime) + initialScaleY);
                    view.setAlpha(((alpha - initialAlpha) * interpolatedTime) + initialAlpha);
                }

                if (interpolatedTime == 1 && !isEnded && listener != null) {
                    listener.onAnimationEnd();
                    isEnded = true;
                }
            }
        };

        animation.setDuration(duration);
        view.startAnimation(animation);
    }
}
