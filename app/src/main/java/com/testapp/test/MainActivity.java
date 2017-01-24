/*
 * Copyright (c) 2017. IndeemaSoftware, Inc.
 * http://indeema.com
 * All rights reserved
 */

package com.testapp.test;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.indeema.introview.DisplayUtils;
import com.indeema.introview.IntroModel;
import com.indeema.introview.IntroView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final long SWITCH_ITEM_DURATION = 1200;
    private IntroView mIntroView;
    private RelativeLayout mContainer;
    private LinearLayout mInfoLayout;
    private TextView mItemTitleTV;
    private TextView mItemDescriptionTV;
    private PageIndicatorWidget mPageIndicator;
    private List<IntroModel> mItems;
    private Button mNextBtn;
    private int mSelectedItemIndex;
    private int mDisplayWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContainer = (RelativeLayout) findViewById(R.id.activity_main);
        mNextBtn = (Button) findViewById(R.id.next_btn);
        mNextBtn.setOnClickListener(this);

        mItems = new ArrayList<>();
        mItems.add(new IntroModel(R.drawable.ic_wifi));
        mItems.add(new IntroModel(R.drawable.ic_high));
        mItems.add(new IntroModel(R.drawable.ic_meating));
        mItems.add(new IntroModel(R.drawable.ic_money));
        mIntroView = new IntroView(this, mItems);
        mContainer.addView(mIntroView);

        mInfoLayout = (LinearLayout) findViewById(R.id.info_layout);
        mItemTitleTV = (TextView) findViewById(R.id.title_tv);
        mItemDescriptionTV = (TextView) findViewById(R.id.description_tv);
        mPageIndicator = (PageIndicatorWidget) findViewById(R.id.page_indicator);
        mPageIndicator.setSwitchItemDuration(SWITCH_ITEM_DURATION);

        mDisplayWidth = DisplayUtils.getDisplayWidth(this);
        mPageIndicator.setItemsCount(mItems.size());
        mPageIndicator.setSelectedItem(-1);
        mInfoLayout.setTranslationX(mDisplayWidth);
        mItemTitleTV.setText("");
        mItemDescriptionTV.setText("");

        replaceItemDescription();
    }

    @Override
    public void onClick(View view) {
        mNextBtn.setEnabled(false);
        mIntroView.turnRight();

        mSelectedItemIndex = mIntroView.getSelectedPosition();
        mPageIndicator.switchToNextItem();
        replaceItemDescription();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mNextBtn.setEnabled(true);
            }
        }, 1200);
    }

    private void replaceItemDescription() {
        final long duration = SWITCH_ITEM_DURATION / 2;
        AnimationUtils.moveViewHorizontallyWithAlpha(mInfoLayout, 0, -mDisplayWidth, 1f, -1f, duration,
                new AnimationUtils.AnimationCallback() {
            @Override
            public void onAnimationStart() {}

            @Override
            public void onAnimationEnd() {
                setItemTips();
                mInfoLayout.setTranslationX(mDisplayWidth);
                AnimationUtils.moveViewHorizontallyWithAlpha(mInfoLayout, mDisplayWidth, 0, -1f, 1f, duration, null);
            }
        });
    }

    private void setItemTips() {
        mItemTitleTV.setText(getItemTitle());
        mItemDescriptionTV.setText(getItemDescription());
    }

    private String getItemTitle() {
        switch (mSelectedItemIndex) {
            case 0:
                return getResources().getString(R.string.title_smart_technologies);
            case 1:
                return getResources().getString(R.string.title_money_income);
            case 2:
                return getResources().getString(R.string.title_team_meetings);
            case 3:
                return getResources().getString(R.string.title_save_money);
            default:
                return "";
        }
    }

    private String getItemDescription() {
        switch (mSelectedItemIndex) {
            case 0:
                return getResources().getString(R.string.description_smart_technologies);
            case 1:
                return getResources().getString(R.string.description_money_income);
            case 2:
                return getResources().getString(R.string.description_team_meetings);
            case 3:
                return getResources().getString(R.string.description_save_money);
            default:
                return "";
        }
    }
}
