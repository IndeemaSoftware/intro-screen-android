/*
 *  Copyright (C) 2017.  IndeemaSoftware, Inc.
 *  http://indeema.com
 *  All rights reserved
 */

package com.indeema.introview;

/**
 * Created with AndroidStudio.
 * User: Michael Nester
 * Date: January, 19, 2017
 * Time: 6:34 PM
 */
public class IntroModel {

    private String mTitle;
    private int mDrawableResourceId;

    public IntroModel(String title) {
        this.mTitle = title;
    }

    public IntroModel(int mDrawableResourceId) {
        this.mDrawableResourceId = mDrawableResourceId;
    }

    public IntroModel(String mTitle, int mDrawableResourceId) {
        this.mTitle = mTitle;
        this.mDrawableResourceId = mDrawableResourceId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public int getmDrawableResourceId() {
        return mDrawableResourceId;
    }
}
