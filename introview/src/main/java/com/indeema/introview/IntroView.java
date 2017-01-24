/*
 *  Copyright (C) 2017.  IndeemaSoftware, Inc.
 *  http://indeema.com
 *  All rights reserved
 */

package com.indeema.introview;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

public class IntroView extends RelativeLayout {

    private final int DEFAULT_IMAGE_SIZE = 128;
    private final long SWITCH_ITEM_DURATION = 1200;
    private final long START_ANIMATION_DURATION = 1200;
    private final float ITEM_ALPHA_MIN = 0.3f;
    private final float ITEM_ALPHA = 0.5f;
    private final float ITEM_SCALE_MIN = 0.4f;
    private final float ITEM_SCALE = 0.6f;
    private final float ELLIPSIZE_X = 1f;
    private final float ELLIPSIZE_Y = 0.7f;

    private List<IntroModel> mItemList;
    private View[] mIntroItems;
    private int mSelectedItemIndex;
    private int mDisplayWidth;
    private int mQuarterWidth;
    private int mIntroRadius;
    private int[] mAngles;

    public IntroView(Context context, List<IntroModel> mItemList) {
        super(context);
        this.mItemList = mItemList;
        init((Activity) context);
    }

    public IntroView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init((Activity) context);
    }

    public IntroView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init((Activity) context);
    }

    private void init(Activity activity) {
        if (mItemList == null || activity == null) {
            return;
        }
        mSelectedItemIndex = -1;

        // Get base screen data
        mDisplayWidth = DisplayUtils.getDisplayWidth(activity);
        mQuarterWidth = mDisplayWidth / mItemList.size();
        mIntroRadius = (int) ((mDisplayWidth - DisplayUtils.getDensity(activity) * DEFAULT_IMAGE_SIZE) / 2);
        mAngles = new int[]{0, -90, -180, -270};
        RelativeLayout.LayoutParams containerParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) DisplayUtils.convertDpToPixel(activity, 300)
        );
        this.setLayoutParams(containerParams);

        mIntroItems = new View[mItemList.size()];
        addViewsToParent(activity, mIntroItems);

        ViewUtils.setZ(activity, 6, TypedValue.COMPLEX_UNIT_DIP, mIntroItems);
        ViewUtils.scaleViews(0.7f, mIntroItems);
        ViewUtils.setViewsAlpha(1f, mIntroItems);

        mSelectedItemIndex = mIntroItems.length - 1;
        startAnimation();
    }

    private void addViewsToParent(Activity activity, View[] mIntroItems) {
        // Hardcoded width/height for views
        RelativeLayout.LayoutParams viewParams = new RelativeLayout.LayoutParams(
                (int) ViewUtils.convertDpToPixel(activity, DEFAULT_IMAGE_SIZE),
                (int) ViewUtils.convertDpToPixel(activity, DEFAULT_IMAGE_SIZE));
        viewParams.addRule(CENTER_IN_PARENT);

        // Init views
        for (int i = 0; i < mItemList.size(); i++) {
            View view = new ImageView(activity);
            view.setId(i);
            ((ImageView) view).setImageDrawable(
                    ContextCompat.getDrawable(activity, mItemList.get(i).getmDrawableResourceId()));
            ViewUtils.setBackground(activity, R.drawable.bg_circle_white, view);
            view.setLayoutParams(viewParams);
            view.setPadding((int) ViewUtils.convertDpToPixel(activity, 20),
                    (int) ViewUtils.convertDpToPixel(activity, 20),
                    (int) ViewUtils.convertDpToPixel(activity, 20),
                    (int) ViewUtils.convertDpToPixel(activity, 20));
            this.addView(view);
            mIntroItems[i] = view;
        }
    }

    private void startAnimation() {
        int maxAmplitude = mQuarterWidth / 2;

        AnimationUtils.moveViewHorizontallySin(mIntroItems[0], mIntroRadius, maxAmplitude, START_ANIMATION_DURATION, null);
        AnimationUtils.moveViewVerticallySin(mIntroItems[1], (int) (-mIntroRadius * 0.7), maxAmplitude, START_ANIMATION_DURATION, null);
        AnimationUtils.moveViewHorizontallySin(mIntroItems[2], -mIntroRadius, -maxAmplitude, START_ANIMATION_DURATION, null);
        AnimationUtils.moveViewVerticallySin(mIntroItems[3], (int) (mIntroRadius * 0.7), -maxAmplitude, START_ANIMATION_DURATION,
                new AnimationUtils.AnimationCallback() {
                    @Override
                    public void onAnimationStart() {
                    }

                    @Override
                    public void onAnimationEnd() {
                        turnRight();
                    }
                });
    }

    public void turnRight() {
        for (int i = 0; i < mIntroItems.length; i++) {
            final int finalI = i;
            if (getNextItemIndex() == i) {
                AnimationUtils.moveViewCircularWithScaling(mIntroItems[i], mIntroRadius,
                        mAngles[i], mAngles[i] + 90, 1f, 1f, ELLIPSIZE_X, ELLIPSIZE_Y, SWITCH_ITEM_DURATION,
                        new AnimationUtils.AnimationEndCallback() {
                            @Override
                            public void onAnimationEnd() {
                                mAngles[finalI] += 90;
                            }
                        });
            } else if (mSelectedItemIndex == i) {
                AnimationUtils.moveViewCircularWithScaling(mIntroItems[i], mIntroRadius,
                        mAngles[i], mAngles[i] + 90, ITEM_SCALE, ITEM_ALPHA, ELLIPSIZE_X, ELLIPSIZE_Y, SWITCH_ITEM_DURATION,
                        new AnimationUtils.AnimationEndCallback() {
                            @Override
                            public void onAnimationEnd() {
                                mAngles[finalI] += 90;
                            }
                        });
            } else if (getNextItemIndex(getNextItemIndex()) == i) {
                AnimationUtils.moveViewCircularWithScaling(mIntroItems[i], mIntroRadius,
                        mAngles[i], mAngles[i] + 90, ITEM_SCALE, ITEM_ALPHA, ELLIPSIZE_X, ELLIPSIZE_Y, SWITCH_ITEM_DURATION,
                        new AnimationUtils.AnimationEndCallback() {
                            @Override
                            public void onAnimationEnd() {
                                mAngles[finalI] += 90;
                            }
                        });
            } else {
                AnimationUtils.moveViewCircularWithScaling(mIntroItems[i], mIntroRadius,
                        mAngles[i], mAngles[i] + 90, ITEM_SCALE_MIN, ITEM_ALPHA_MIN, ELLIPSIZE_X, ELLIPSIZE_Y, SWITCH_ITEM_DURATION,
                        new AnimationUtils.AnimationEndCallback() {
                            @Override
                            public void onAnimationEnd() {
                                mAngles[finalI] += 90;
                            }
                        });
            }
        }

        mSelectedItemIndex = getNextItemIndex();
    }

    private int getNextItemIndex() {
        return getNextItemIndex(mSelectedItemIndex);
    }

    private int getNextItemIndex(int currentIndex) {
        if (currentIndex == mIntroItems.length - 1) {
            return 0;
        } else {
            return currentIndex + 1;
        }
    }

    public int getSelectedPosition() {
        return mSelectedItemIndex;
    }
}
