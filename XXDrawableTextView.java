package com.mxnavi.mobile.view;
/**
 * 可以设置textview上下左右侧图片及图片大小的textview相当于imageview和textview的联合使用
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.mxnavi.mobile.R;

/**
 * Created by P on 2018/7/5.
 */

public class XXDrawableTextView extends TextView {
    public final static int  POSITION_LEFT=0;
    public final static int  POSITION_TOP=1;
    public final static int  POSITION_RIGHT=2;
    public final static int  POSITION_BOTTOM=3;

    int leftDrawableWidth=10;
    int leftDrawableHeight=10;
    int topDrawableWidth=10;
    int topDrawableHeight=10;
    int rightDrawableWidth=10;
    int rightDrawableHeight=10;
    int bottomDrawableWidth=10;
    int bottomDrawableHeight=10;
    Paint mPaint;
    Paint mPaint2;
    Rect mBound;
    Drawable left;
    Drawable top;
    Drawable right;
    Drawable bottom;
    public XXDrawableTextView(Context context) {
        this(context,null,0);
    }

    public XXDrawableTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public XXDrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public XXDrawableTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getAttributes(context, attrs, defStyleAttr);
    }


    public void getAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.XXDrawableTextView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.XXDrawableTextView_drawableWidth_left:
                    leftDrawableWidth = a.getDimensionPixelSize(attr,10);
                    break;
                case R.styleable.XXDrawableTextView_drawableHeight_left:
                    leftDrawableHeight = a.getDimensionPixelSize(attr, 10);
                    break;

                case R.styleable.XXDrawableTextView_drawableWidth_top:
                    topDrawableWidth = a.getDimensionPixelSize(attr,10);
                    break;
                case R.styleable.XXDrawableTextView_drawableHeight_top:
                    topDrawableHeight = a.getDimensionPixelSize(attr, 10);
                    break;

                case R.styleable.XXDrawableTextView_drawableWidth_right:
                    rightDrawableWidth = a.getDimensionPixelSize(attr,10);
                    break;
                case R.styleable.XXDrawableTextView_drawableHeight_right:
                    rightDrawableHeight = a.getDimensionPixelSize(attr, 10);
                    break;
                case R.styleable.XXDrawableTextView_drawableWidth_bottom:
                    bottomDrawableWidth = a.getDimensionPixelSize(attr,10);
                    break;
                case R.styleable.XXDrawableTextView_drawableHeight_bottom:
                    bottomDrawableHeight = a.getDimensionPixelSize(attr, 10);
                    break;
//                case R.styleable.XXDrawableTextView_testnumber:
//                    System.out.println("啦啦啦啦啦啦啦TextView2_testnumber:"+a.getDimensionPixelSize(attr,10));
//                    break;
//                case R.styleable.XXDrawableTextView_teststring:
//                    System.out.println("啦啦啦啦啦啦啦TextView2_teststring:"+a.getString(attr));
            }
        }
        a.recycle();

        /*
        * setCompoundDrawablesWithIntrinsicBounds方法会首先在父类的构造方法中执行，
        * 彼时执行时drawable的大小还都没有开始获取，都是0,
        * 这里获取完自定义的宽高属性后再次调用这个方法，插入drawable的大小
        * */
        setCompoundDrawablesWithIntrinsicBounds(
                left,top,right,bottom);


    }


    /**
     * Sets the Drawables (if any) to appear to the left of, above, to the
     * right of, and below the text. Use {@code null} if you do not want a
     * Drawable there. The Drawables' bounds will be set to their intrinsic
     * bounds.
     * <p>
     * Calling this method will overwrite any Drawables previously set using
     * {@link #setCompoundDrawablesRelative} or related methods.
     * 这里重写这个方法，来设置上下左右的drawable的大小
     *
     * @attr ref android.R.styleable#TextView_drawableLeft
     * @attr ref android.R.styleable#TextView_drawableTop
     * @attr ref android.R.styleable#TextView_drawableRight
     * @attr ref android.R.styleable#TextView_drawableBottom
     */
    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(@Nullable Drawable left,
                                                        @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;

        System.out.println("啦啦啦啦啦啦啦");

        if (left != null) {
            left.setBounds(0, 0, leftDrawableWidth,leftDrawableHeight);
        }
        if (right != null) {
            right.setBounds(0, 0, rightDrawableWidth,rightDrawableHeight);
        }
        if (top != null) {
            top.setBounds(0, 0, topDrawableWidth,topDrawableHeight);
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, bottomDrawableWidth,bottomDrawableHeight);
        }

        setCompoundDrawables(left, top, right, bottom);
    }

    /*
    * 代码中动态设置drawable的宽高度
    * */
    public void setDrawableSize(int width, int height,int position) {
        if (position==POSITION_LEFT) {
            leftDrawableWidth = width;
            leftDrawableHeight = height;
        }
        if (position==POSITION_TOP) {
            topDrawableWidth = width;
            topDrawableHeight = height;
        }
        if (position==POSITION_RIGHT) {
            rightDrawableWidth = width;
            rightDrawableHeight = height;
        }
        if (position==POSITION_BOTTOM) {
            bottomDrawableWidth = width;
            bottomDrawableHeight = height;
        }

        setCompoundDrawablesWithIntrinsicBounds(
                left,top,right,bottom);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        // Draw the background for this view
        super.onDraw(canvas);

       /*
       测试圆角的
       Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmap);
        super.onDraw(canvas2);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        //16种状态
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mPaint2 = new Paint();
        mPaint2.setColor(Color.YELLOW);
        mPaint2.setXfermode(null);
        int radius=100;
        Path path = new Path();
        path.moveTo(0, radius);
        path.lineTo(0, 0);
        path.lineTo(radius, 0);
        //arcTo的第二个参数是以多少度为开始点，第三个参数-90度表示逆时针画弧，正数表示顺时针
        path.arcTo(new RectF(0, 0, radius * 2, radius * 2), -90, -90);
        path.close();
        canvas2.drawPath(path, mPaint);
        canvas.drawBitmap(bitmap, 0, 0, mPaint2);
        bitmap.recycle();*/

/*
        final int compoundPaddingLeft = getCompoundPaddingLeft();
        final int compoundPaddingTop = getCompoundPaddingTop();
        final int compoundPaddingRight = getCompoundPaddingRight();
        final int compoundPaddingBottom = getCompoundPaddingBottom();
        final int scrollX = getScrollX();
        final int scrollY = getScrollY();
        final int right = getRight();
        final int left = getLeft();
        final int bottom = getBottom();
        final int top = getTop();
        final int offset =0;
        final int leftOffset = 0;
        final int rightOffset =0;
        *//*
        * 0-1-2-3
        * left-top-right-bottom
        * *//*
        Drawable[] drawables = getCompoundDrawables();
            *//*
             * Compound, not extended, because the icon is not clipped
             * if the text height is smaller.
             *//*
            int vspace = bottom - top - compoundPaddingBottom - compoundPaddingTop;
            int hspace = right - left - compoundPaddingRight - compoundPaddingLeft;
            // IMPORTANT: The coordinates computed are also used in invalidateDrawable()
            // Make sure to update invalidateDrawable() when changing this code.
            if (drawables[0] != null) {
                canvas.save();
                canvas.translate(scrollX + getPaddingLeft() + leftOffset,
                        scrollY + compoundPaddingTop +
                                (vspace - leftDrawableHeight) / 2);
                drawables[0].draw(canvas);
                canvas.restore();
            }
            // IMPORTANT: The coordinates computed are also used in invalidateDrawable()
            // Make sure to update invalidateDrawable() when changing this code.
            if (dr.mShowing[Drawables.RIGHT] != null) {
                canvas.save();
                canvas.translate(scrollX + right - left - mPaddingRight
                                - dr.mDrawableSizeRight - rightOffset,
                        scrollY + compoundPaddingTop + (vspace - dr.mDrawableHeightRight) / 2);
                dr.mShowing[Drawables.RIGHT].draw(canvas);
                canvas.restore();
            }
            // IMPORTANT: The coordinates computed are also used in invalidateDrawable()
            // Make sure to update invalidateDrawable() when changing this code.
            if (dr.mShowing[Drawables.TOP] != null) {
                canvas.save();
                canvas.translate(scrollX + compoundPaddingLeft +
                        (hspace - dr.mDrawableWidthTop) / 2, scrollY + mPaddingTop);
                dr.mShowing[Drawables.TOP].draw(canvas);
                canvas.restore();
            }
            // IMPORTANT: The coordinates computed are also used in invalidateDrawable()
            // Make sure to update invalidateDrawable() when changing this code.
            if (dr.mShowing[Drawables.BOTTOM] != null) {
                canvas.save();
                canvas.translate(scrollX + compoundPaddingLeft +
                                (hspace - dr.mDrawableWidthBottom) / 2,
                        scrollY + bottom - top - mPaddingBottom - dr.mDrawableSizeBottom);
                dr.mShowing[Drawables.BOTTOM].draw(canvas);
                canvas.restore();
            }
        canvas.restore();*/
    }

}
