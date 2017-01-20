/*
 *  Copyright (C) 2017.  IndeemaSoftware, Inc.
 *  http://indeema.com
 *  All rights reserved
 */

package com.indeema.introview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;

public class IntroView extends RelativeLayout {

    private List<IntroItem> mItemList;

    public IntroView(Context context, List<IntroItem> mItemList) {
        super(context);
        this.mItemList = mItemList;
        init(context);
    }

    public IntroView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IntroView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (mItemList == null) {
            return;
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                (int) IntroUtils.convertDpToPixel(context, 50),
                (int) IntroUtils.convertDpToPixel(context, 50));

        for (IntroItem introItem : mItemList) {
            View view = new View(context);
            view.setBackgroundResource(R.color.image_background);
            view.setLayoutParams(params);
            this.addView(view);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    public void moveToNext() {

    }

    public void moveToPrevious() {

    }
}
