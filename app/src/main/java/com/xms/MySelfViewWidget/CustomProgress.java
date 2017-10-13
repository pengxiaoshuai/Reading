package com.xms.MySelfViewWidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.xms.R;

/**
 * Created by dell on 2017/3/31.
 * 画圆环
 */

public class CustomProgress extends View{
    /**
     * 开始的颜色
     */
    private int mfirstcolor;
    /**
     * 交替的颜色
     */
    private int msecondcolor;
    /**
     * 圆的宽度
     */
    private int mpresswidth;
    /**
     * 交替的速度
     */
    private int mspeed;
    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 当前进度
     */
    private int mProgress;

    /**
     * 是否应该开始下一个
     */
    private boolean isNext = false;

    public CustomProgress(Context context){
        this(context,null);
    }

    public CustomProgress(Context context, @Nullable AttributeSet attrs){
        this(context, attrs,0);
    }

    public CustomProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomProgressBar,defStyleAttr,0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.CustomProgressBar_firstColor:
                    mfirstcolor = a.getColor(attr, Color.BLACK);
                     break;
                case R.styleable.CustomProgressBar_secondColor:
                    msecondcolor = a.getColor(attr,Color.BLACK);
                    break;
                case R.styleable.CustomProgressBar_mywidth:
                    mpresswidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,20,getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomProgressBar_speed:
                    mspeed = a.getInt(attr,20);
                    break;
                default:
                    break;
            }
        }
        a.recycle();
        mPaint = new Paint();
        //绘画线程
        new Thread(){
            @Override
            public void run(){
                //super.run();
                while (true){
                    mProgress++;
                    if (mProgress == 360){
                        mProgress = 0;
                        if (!isNext)
                            isNext = true;
                        else
                            isNext = false;
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(mspeed);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    /**
     * 直接重写onDraw,这不需要重写onMeasure
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        int centre = getWidth() / 2; //获取圆心的x坐标
        int radius = centre - mpresswidth / 2;//半径
        mPaint.setStrokeWidth(mpresswidth); //设置圆环的宽度
        mPaint.setAntiAlias(true); //消除锯齿
        mPaint.setStyle(Paint.Style.STROKE);//设置空心
        RectF oval = new RectF(centre - radius, centre - radius,centre + radius,centre+radius);//用于定义的圆弧的形状和大小的界限
        if (!isNext){//第一颜色的圈完整，第二颜色跑(先把第一圈圆圈绘制完成)
            mPaint.setColor(mfirstcolor);//设置圆环的颜色
            canvas.drawCircle(centre,centre,radius,mPaint);//画出圆环
            mPaint.setColor(msecondcolor);//设置圆环的颜色
            canvas.drawArc(oval,-90,mProgress,false,mPaint);//根据进度画圆弧
        }else{
            mPaint.setColor(msecondcolor); // 设置圆环的颜色
            canvas.drawCircle(centre,centre,radius,mPaint);//画出圆环
            mPaint.setColor(mfirstcolor);//设置圆环的颜色
            canvas.drawArc(oval,-90,mProgress,false,mPaint);//根据进度画圆弧
        }


    }
}
