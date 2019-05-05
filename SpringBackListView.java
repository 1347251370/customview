package com.mxnavi.mobile.view;

/**
 * Created by Administrator on 2017/10/12.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

/**
 * 有回弹效果的ListView
 *
 * @author zhaohaijiao
 */
public class SpringBackListView extends ListView implements OnScrollListener {

    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;

    private boolean isPull;
    private boolean isTop;
    private boolean isBottom;

    private int scrollState;

    private float mDownY;
    private float mLastY;

    private Handler mHandler = new Handler();

    private int speed = 20;

    /**
     * 设置回弹的速度。值越大,速度越快。默认为20。
     */
    public void setSpringBackSpeed(int speed) {
        if (speed <= 0) {
            throw new RuntimeException("speed 不能小于或者等于0");
        }
        this.speed = speed;
    }

    public SpringBackListView(Context context) {
        super(context);
        init();
    }

    public SpringBackListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpringBackListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        // 初始化padding的值
        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();
        paddingTop = getPaddingTop();
        paddingBottom = getPaddingBottom();

        setOnScrollListener(this);

        // 禁用下拉到两端发荧光的效果
        setOverScrollMode(OVER_SCROLL_NEVER);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                // 清空所有消息队列
                mHandler.removeCallbacksAndMessages(null);

                mLastY = ev.getY();
                int offset = (int) ((mLastY - mDownY) / 2.5);
                isPull = offset > 0;
                // 下拉操作
                if (isPull) {
                    if (isTop && scrollState != SCROLL_STATE_FLING) {
                        offset += paddingTop;
                        setPadding(paddingLeft, offset, paddingRight, paddingBottom);
                        // 选中第一个item.不然没有下拉效果
                        setSelection(0);
                    }

                } // 上拉
                else {
                    if (isBottom && scrollState != SCROLL_STATE_FLING) {
                        offset -= paddingBottom;
                        setPadding(paddingLeft, paddingTop, paddingRight, -offset);
                        // 选中最后一个item.不然没有上拉效果
                        setSelection(getCount() - 1);
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                // 下拉操作
                if (isPull) {
                    int top = getPaddingTop();
                    int duration = 0;

                    while (top > paddingTop) {
                        top -= speed;
                        duration += 10;
                        final int pt = top;

                        mHandler.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                if (pt < paddingTop) {
                                    // 如果回弹的距离小于初始的paddingTop值，则恢复原始状态
                                    setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                                } else {
                                    setPadding(paddingLeft, pt, paddingRight, paddingBottom);
                                }
                            }

                        }, duration);

                    }

                } else { // 上拉
                    int bottom = getPaddingBottom();
                    int duration = 0;

                    while (bottom > paddingBottom) {
                        bottom -= speed;
                        duration += 10;
                        final int pb = bottom;

                        mHandler.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                if (pb < paddingBottom) {
                                    // 如果回弹的距离小于初始的paddingBottom值，则恢复原始状态
                                    setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                                } else {
                                    setPadding(paddingLeft, paddingTop, paddingRight, pb);
                                }
                            }

                        }, duration);

                    }

                }
                break;
            default:
                break;
        }

        return super.onTouchEvent(ev);

    }

    @Override
    public void onScroll(AbsListView lv, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        isTop = firstVisibleItem == 0;
        isBottom = firstVisibleItem + visibleItemCount == totalItemCount;
    }

    @Override
    public void onScrollStateChanged(AbsListView lv, int scrollState) {
        this.scrollState = scrollState;
    }

    /**
     * 这是一个很奇怪的方法。哈哈
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                   int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY,
                scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY,
                isTouchEvent);
    }

}
