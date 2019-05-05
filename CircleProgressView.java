package com.mxnavi.mobile.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Circle Progress View
 *
 * @author Yeuham Wang
 */

public class CircleProgressView extends View {

    private static final float DEFAULT_RADIUS = 60.0f;
    private static final float DEFAULT_RING_WIDTH = 8.0f;
    private static final float DEFAULT_START_SWEEP_ANGLE = -90.0f;

    private Paint mBackgroundPaint;
    private Paint mRingPaint;
    private RectF mArcRectF;

    private int mBackgroundColor;
    private int mRingColor;
    private float mRingWidth;
    private float mStartSweepAngle;

    private float mCenterX;
    private float mCenterY;
    private float mRadius;
    private int mCurrentPercent;
    private int mTargetPercent;

    public CircleProgressView(Context context) {
        super(context);
        init();
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredDimension;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            measuredDimension = specSize;
        } else {
            measuredDimension = (int) (DEFAULT_RADIUS + mRingPaint.getStrokeWidth()) * 2;
            if (specMode == MeasureSpec.AT_MOST) {
                measuredDimension = Math.min(measuredDimension, specSize);
            }
        }
        setMeasuredDimension(measuredDimension, measuredDimension);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mCenterX = getMeasuredWidth() / 2;
        mCenterY = getMeasuredHeight() / 2;
        mRadius = Math.min(mCenterX, mCenterY) - mRingPaint.getStrokeWidth();
        mArcRectF.set(mCenterX - mRadius, mCenterY - mRadius, mCenterX + mRadius,
                mCenterY + mRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mBackgroundPaint);
        canvas.drawArc(mArcRectF, mStartSweepAngle, mCurrentPercent * 3.6f, false, mRingPaint);
        if (mCurrentPercent < mTargetPercent) {
            mCurrentPercent += 1;
        } else if (mCurrentPercent > mTargetPercent) {
            mCurrentPercent -= 1;
        }
        postInvalidateDelayed(10);
    }

    public int getProgressBackgroundColor() {
        return mBackgroundColor;
    }

    public void setProgressBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
        mBackgroundPaint.setColor(backgroundColor);
    }

    public int getRingColor() {
        return mRingColor;
    }

    public void setRingColor(int ringColor) {
        mRingColor = ringColor;
        mRingPaint.setColor(ringColor);
    }

    public float getRingWidth() {
        return mRingWidth;
    }

    public void setRingWidth(float ringWidth) {
        mRingWidth = ringWidth;
        mRingPaint.setStrokeWidth(ringWidth);
    }

    public float getStartSweepAngle() {
        return mStartSweepAngle;
    }

    public void setStartSweepAngle(float startSweepAngle) {
        mStartSweepAngle = startSweepAngle;
    }

    public int getCurrentPercent() {
        return mCurrentPercent;
    }

    public void setCurrentPercent(int currentPercent) {
        mCurrentPercent = currentPercent;
    }

    public int getTargetPercent() {
        return mTargetPercent;
    }

    public void setTargetPercent(int targetPercent) {
        mTargetPercent = targetPercent;
    }

    private void init() {
        mCurrentPercent = 0;
        mTargetPercent = 0;
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(mBackgroundColor);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mRingWidth);
        mArcRectF = new RectF();
        setProgressBackgroundColor(ContextCompat.getColor(getContext(),
                android.R.color.transparent));
        setRingColor(ContextCompat.getColor(getContext(), android.R.color.holo_blue_bright));
        setRingWidth(DEFAULT_RING_WIDTH);
        setStartSweepAngle(DEFAULT_START_SWEEP_ANGLE);
    }

}
