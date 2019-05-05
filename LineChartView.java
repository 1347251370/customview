package com.mxnavi.mobile.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 驾驶行为分析中的平顺折线图
 * desc
 */
public class LineChartView extends View {

    private int[] mDataset;
    int mWidth ;
    int mHeight;
    Paint paint;
    int MAX = 30;
    public LineChartView(Context context) {
        super(context);
        init();
    }

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

//    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        radius = Math.min(h, w) / 2 * 0.6f;
//        centerX = w / 2;
//        centerY = h / 2;
        mWidth = w;
        mHeight = h;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }
    private void init(){
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true); //去锯齿
        paint.setColor(Color.rgb(0x00,0xa8,0xff));
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if(mDataset==null||mDataset.length==0){
            return;
        }


        int top = this.getPaddingTop();
        int padddingBottom = this.getPaddingBottom();
        int left = this.getPaddingLeft();
        int right = this.getPaddingRight();
        int width = mWidth-left-right;
        int height = mHeight-top-padddingBottom;


        int centerY = height/2+top;
        int startX = left;
        // X轴间隔
        float xInteval = (float)width/mDataset.length;

        int YMaxSub = getMaxabsDataset();
        float yInteval = 0;
        if(YMaxSub!=0){
            yInteval = (float)height/(2*YMaxSub);

        }
        paint.setColor(Color.TRANSPARENT);

        canvas.drawLine(startX, centerY, startX + width, centerY, paint);
        paint.setColor(Color.rgb(0x00,0xa8,0xff));
        paint.setStrokeWidth(2);
        canvas.drawLine(startX,centerY, startX+xInteval,centerY - mDataset[0] * yInteval,paint);
        for(int i=1;i<mDataset.length;i++){
            canvas.drawLine(startX+(i)*xInteval,centerY-mDataset[i-1]*yInteval,
                        startX+ (i+1)*xInteval,centerY-mDataset[i]*yInteval,paint);
        }




    }
    public void setDataset(int[] dataset){
        mDataset = dataset;
    }
    public int[]  getDataset(){
        return mDataset;
    }
    /**
     * 取最大绝对值
     * @return
     */
    private int getMaxabsDataset(){
        int max = Math.abs(mDataset[0]);

        for(int i=0;i<mDataset.length;i++)
        {
            if(Math.abs(mDataset[i])>max) // 判断最大值
            max= Math.abs(mDataset[i]);

        }
        if(MAX>max){
            return MAX;
        }
        return max;
    }
}
