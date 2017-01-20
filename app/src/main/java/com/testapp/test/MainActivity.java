/*
 * Copyright (c) 2017. IndeemaSoftware, Inc.
 * http://indeema.com
 * All rights reserved
 */

package com.testapp.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.indeema.introview.IntroModel;
import com.indeema.introview.IntroView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private IntroView mIntroView;
    private RelativeLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContainer = (RelativeLayout) findViewById(R.id.activity_main);

        List<IntroModel> items = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            items.add(new IntroModel(R.drawable.ic_avatar));
        }

        mIntroView = new IntroView(this, items);
        mContainer.addView(mIntroView);
    }

    public void onNextClick(View view) {
        mIntroView.turnRight();
    }
}
