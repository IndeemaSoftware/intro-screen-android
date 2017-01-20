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
public class IntroItem {

    private String mTitle;
    private int mDrawableResourceId;

    public IntroItem(String title) {
        this.mTitle = title;
    }

    public IntroItem(int mDrawableResourceId) {
        this.mDrawableResourceId = mDrawableResourceId;
    }

    public IntroItem(String mTitle, int mDrawableResourceId) {
        this.mTitle = mTitle;
        this.mDrawableResourceId = mDrawableResourceId;
    }

}
