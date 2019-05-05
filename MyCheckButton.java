package com.mxnavi.mobile.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.mxnavi.mobile.R;

/**
 * 可以设置图片大小的CheckBox
 */


public class MyCheckButton extends CheckBox {
    //xml文件中设置的高度
    private int mDrawableHeight;
    //xml文件中设置的宽度
    private int mDrawableWidth;

    public MyCheckButton(Context context) {
        this(context, null, 0);
    }

    public MyCheckButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCheckButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        Drawable drawableLeft = null, drawableTop = null, drawableRight = null, drawableBottom = null;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MyCheckBox);
        setClickable(true);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.MyCheckBox_drawableHeight:
                    mDrawableHeight = a.getDimensionPixelSize(R.styleable.MyCheckBox_drawableHeight, 50);
                    break;
                case R.styleable.MyCheckBox_drawableWidth:
                    mDrawableWidth = a.getDimensionPixelSize(R.styleable.MyCheckBox_drawableWidth, 50);
                    break;
                case R.styleable.MyCheckBox_drawableTop:
                    drawableTop = a.getDrawable(attr);
                    break;
                case R.styleable.MyCheckBox_drawableBottom:
                    drawableRight = a.getDrawable(attr);
                    break;
                case R.styleable.MyCheckBox_drawableRight:
                    drawableBottom = a.getDrawable(attr);
                    break;
                case R.styleable.MyCheckBox_drawableLeft:
                    drawableLeft = a.getDrawable(attr);
                    break;
            }
        }
        a.recycle();

        setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);

    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left,
                                                        Drawable top, Drawable right, Drawable bottom) {

        if (left != null) {
            left.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
        }
        if (right != null) {
            right.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
        }
        if (top != null) {
            top.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
        }
        setCompoundDrawables(left, top, right, bottom);
    }
}
