package com.testapp.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.indeema.introview.IntroItem;
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

        List<IntroItem> items = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            items.add(new IntroItem(R.drawable.ic_avatar));
        }

        mIntroView = new IntroView(this, items);
        mContainer.addView(mIntroView);
    }
}
