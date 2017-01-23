package com.testapp.test;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created with AndroidStudio.
 * @author Michael Nester
 * Date: January, 23, 2017
 * Time: 10:57 PM
 */
public class AnimationUtils {

    public interface AnimationCallback {
        void onAnimationStart();

        void onAnimationEnd();
    }

    public static void moveViewHorizontallyWithAlpha(final View view, final int initialTranslationX, final int targetTranslationX,
                                                     final float initialAlpha, final float targetAlpha, long duration, final AnimationCallback listener) {
        if (listener != null) listener.onAnimationStart();
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                view.setTranslationX(((targetTranslationX - initialTranslationX) * interpolatedTime) + initialTranslationX);

                float alpha = ((targetAlpha - initialAlpha) * interpolatedTime) + initialAlpha;
                if (alpha >= 0) view.setAlpha(alpha);

                if (interpolatedTime == 1 && listener != null) listener.onAnimationEnd();
            }
        };
        animation.setDuration(duration);
        view.startAnimation(animation);
    }
}
