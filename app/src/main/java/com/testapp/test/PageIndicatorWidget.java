package com.testapp.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Ivan Savenko on 12.01.17
 */
public class PageIndicatorWidget extends View {

    private final String TAG = this.getClass().getSimpleName();

    protected CountDownTimer mAnimationTimer;
    private Paint mDefaultPaint;
    private Paint mSelectedPaint;
    private int mWidth;
    private int mHeight;
    private Point[] mItemsPosition;
    private int mSelectedItem;
    private int mItemSize;
    private int mItemSpace;
    private int mItemRadius;
    private long mSwitchItemDuration;
    private int mSelectedRectLeft;
    private int mSelectedRectRight;
    private int mSelectedRectTop;
    private int mSelectedRectBottom;
    private RectF mSelectedLeftArcRectF;
    private RectF mSelectedRightArcRectF;

    public PageIndicatorWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initDefaultConfiguration(context);
        initAttributes(context, attrs);
    }

    private void initView(Context context) {
    }

    private void initDefaultConfiguration(Context context) {
        mDefaultPaint = new Paint();
        mDefaultPaint.setColor(ContextCompat.getColor(context, R.color.color_app_grey_dark));
        mDefaultPaint.setAntiAlias(true);
        mDefaultPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mDefaultPaint.setStrokeJoin(Paint.Join.ROUND);
        mDefaultPaint.setStrokeCap(Paint.Cap.ROUND);

        mSelectedPaint = new Paint();
        mSelectedPaint.setColor(ContextCompat.getColor(context, R.color.green));
        mSelectedPaint.setAntiAlias(true);
        mSelectedPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mSelectedPaint.setStrokeJoin(Paint.Join.ROUND);
        mSelectedPaint.setStrokeCap(Paint.Cap.ROUND);

        mItemSize = (int) (getContext().getResources().getDisplayMetrics().density * 4);
        mItemSpace = mItemSize * 3;

        mSelectedLeftArcRectF = new RectF();
        mSelectedRightArcRectF = new RectF();
        mSwitchItemDuration = 1600;
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        int itemCount = 1;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PageIndicatorWidget, 0, 0);
            try {
                mDefaultPaint.setColor(a.getColor(R.styleable.PageIndicatorWidget_piw_default_color,
                        mDefaultPaint.getColor()));
                mSelectedPaint.setColor(a.getColor(R.styleable.PageIndicatorWidget_piw_selected_color,
                        mSelectedPaint.getColor()));
                itemCount = a.getInt(R.styleable.PageIndicatorWidget_piw_items_count, 1);
                mSelectedItem = a.getInt(R.styleable.PageIndicatorWidget_piw_selected_item, mSelectedItem);
                mItemSize = a.getDimensionPixelSize(R.styleable.PageIndicatorWidget_piw_item_size, mItemSize);
                mItemSpace = a.getDimensionPixelSize(R.styleable.PageIndicatorWidget_piw_item_space, mItemSpace);

            } finally {
                a.recycle();
            }
        }

        mItemRadius = mItemSize / 2;
        mItemsPosition = new Point[itemCount];
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        calculatePositions();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Draw default items
        for (int i = 0; i < mItemsPosition.length; i++) {
            canvas.drawCircle(mItemsPosition[i].x, mItemsPosition[i].y, mItemRadius, mDefaultPaint);
        }

        //Draw selected item
        drawSelectedItem(canvas);
    }

    private void drawSelectedItem(Canvas canvas) {
        canvas.drawArc(mSelectedLeftArcRectF, 90, 180, false, mSelectedPaint);
        canvas.drawArc(mSelectedRightArcRectF, -90, 180, false, mSelectedPaint);
        canvas.drawRect(mSelectedRectLeft, mSelectedRectTop, mSelectedRectRight, mSelectedRectBottom, mSelectedPaint);
    }

    public void calculateSelectedItemBounds(Point currentPoint) {
        mSelectedRectLeft = currentPoint.x;
        mSelectedRectRight = currentPoint.x;
        mSelectedRectTop = currentPoint.y - mItemRadius;
        mSelectedRectBottom = currentPoint.y + mItemRadius;
        mSelectedLeftArcRectF.set(currentPoint.x - mItemRadius, mSelectedRectTop,
                currentPoint.x + mItemRadius, mSelectedRectBottom);
        mSelectedRightArcRectF.set(currentPoint.x - mItemRadius, mSelectedRectTop,
                currentPoint.x + mItemRadius, mSelectedRectBottom);
    }


    public void startAnimation(int currentItemIndex, boolean isIndexIncreased) {
        startAnimation(currentItemIndex, isIndexIncreased, mSwitchItemDuration);
    }

    public void startAnimation(int currentItemIndex, boolean isIndexIncreased, final long millisInFuture) {
        int nextItemIndex = isIndexIncreased ? currentItemIndex + 1 : currentItemIndex - 1;
        if (currentItemIndex >= 0 && nextItemIndex >= 0 &&
                currentItemIndex < mItemsPosition.length && nextItemIndex < mItemsPosition.length) {
            stopAnimation();
            Point currentPoint = mItemsPosition[currentItemIndex];
            calculateSelectedItemBounds(currentPoint);
            startExpandAnimation(currentPoint, isIndexIncreased, millisInFuture / 2);
        }
    }

    private void startExpandAnimation(final Point currentPoint, final boolean isIndexIncreased,
                                      final long millisInFuture) {
        stopAnimation();
        final int space = mItemSpace + mItemSize;
        mAnimationTimer = new CountDownTimer(millisInFuture, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (isIndexIncreased) {
                    mSelectedRectRight = currentPoint.x +
                            (int) (space * (millisInFuture - millisUntilFinished) / millisInFuture);
                    mSelectedRightArcRectF.set(mSelectedRectRight - mItemRadius,
                            mSelectedRectTop, mSelectedRectRight + mItemRadius, mSelectedRectBottom);
                } else {
                    mSelectedRectLeft = currentPoint.x -
                            (int) (space * (millisInFuture - millisUntilFinished) / millisInFuture);
                    mSelectedLeftArcRectF.set(mSelectedRectLeft - mItemRadius, mSelectedRectTop,
                            mSelectedRectLeft + mItemRadius, mSelectedRectBottom);
                }

                invalidate();
            }

            @Override
            public void onFinish() {
                if (isIndexIncreased) {
                    mSelectedRectRight = currentPoint.x + space;
                    mSelectedRightArcRectF.set(mSelectedRectRight - mItemRadius, mSelectedRectTop,
                            mSelectedRectRight + mItemRadius, mSelectedRectBottom);
                } else {
                    mSelectedRectLeft = currentPoint.x - space;
                    mSelectedLeftArcRectF.set(mSelectedRectLeft - mItemRadius, mSelectedRectTop,
                            mSelectedRectLeft + mItemRadius, mSelectedRectBottom);
                }

                invalidate();
                startCollapseAnimation(currentPoint, isIndexIncreased, millisInFuture);
            }
        }.start();
    }

    private void startCollapseAnimation(final Point currentPoint, final boolean isIndexIncreased,
                                        final long millisInFuture) {
        stopAnimation();
        final int space = mItemSpace + mItemSize;
        mAnimationTimer = new CountDownTimer(millisInFuture, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (isIndexIncreased) {
                    mSelectedRectLeft = currentPoint.x +
                            (int) (space * (millisInFuture - millisUntilFinished) / millisInFuture);
                    mSelectedLeftArcRectF.set(mSelectedRectLeft - mItemRadius, mSelectedRectTop,
                            mSelectedRectLeft + mItemRadius, mSelectedRectBottom);
                } else {
                    mSelectedRectRight = currentPoint.x -
                            (int) (space * (millisInFuture - millisUntilFinished) / millisInFuture);
                    mSelectedRightArcRectF.set(mSelectedRectRight - mItemRadius, mSelectedRectTop,
                            mSelectedRectRight + mItemRadius, mSelectedRectBottom);
                }
                invalidate();
            }

            @Override
            public void onFinish() {
                if (isIndexIncreased) {
                    mSelectedRectLeft = currentPoint.x + space;
                    mSelectedLeftArcRectF.set(mSelectedRectLeft - mItemRadius, mSelectedRectTop,
                            mSelectedRectLeft + mItemRadius, mSelectedRectBottom);
                } else {
                    mSelectedRectRight = currentPoint.x - space;
                    mSelectedRightArcRectF.set(mSelectedRectRight - mItemRadius, mSelectedRectTop,
                            mSelectedRectRight + mItemRadius, mSelectedRectBottom);
                }
                invalidate();
            }
        }.start();
    }

    public void stopAnimation() {
        if (mAnimationTimer != null) {
            mAnimationTimer.cancel();
            mAnimationTimer = null;
        }
    }

    private void calculatePositions() {
        int centerX = mWidth / 2;
        int centerY = mHeight / 2;
        int space = mItemSpace + mItemSize;
        int centerIndex;

        if (mItemsPosition.length % 2 == 0) {
            centerIndex = mItemsPosition.length / 2;
            centerX += space / 2;
        } else {
            centerIndex = mItemsPosition.length / 2;
        }

        for (int i = 0; i < mItemsPosition.length; i++) {
            mItemsPosition[i] = new Point(centerX + space * (i - centerIndex), centerY);
        }
    }

    public void setItemsCount(int count) {
        mItemsPosition = new Point[count];
        calculatePositions();
        invalidate();
    }

    public void switchToNextItem() {
        if (mSelectedItem == 3) {
            mSelectedItem = 0;
        }

        startAnimation(mSelectedItem, true);
        mSelectedItem++;
    }

    public void switchToPreviousItem() {
        startAnimation(mSelectedItem, false);
        mSelectedItem--;
    }

    public void setSelectedItem(int selectedItem) {
        if (selectedItem < 0) {
            mSelectedItem = 0;
        } else if (selectedItem >= mItemsPosition.length) {
            mSelectedItem = mItemsPosition.length - 1;
        } else {
            mSelectedItem = selectedItem;
        }
        calculateSelectedItemBounds(mItemsPosition[mSelectedItem]);
        invalidate();
    }

    public void setSwitchItemDuration(long switchItemDuration) {
        mSwitchItemDuration = switchItemDuration;
    }
}
