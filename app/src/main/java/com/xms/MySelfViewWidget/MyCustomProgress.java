package com.xms.MySelfViewWidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.xms.R;

import java.util.ArrayList;

/**
 * Created by dell on 2017/3/31.
 * 仿支付宝账单画圆环
 */

public class MyCustomProgress extends View{
    /**
     * 开始的颜色
     */
    private int mfirstcolor;
//    /**
//     * 交替的颜色
//     */
//    private int msecondcolor;
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
    /**
     * 是否继续绘制
     */
    private boolean isdraw = true;
    /**
     * 需要展示的double数据
     */
    private ArrayList<Double> mdata;

    /**
     * 需要绘制的各种color
     */
    private ArrayList<Integer> mcolors;

    /**
     * 外界往里面扔数据的方法(第0个数据是总数据和基础颜色)
     * @param mdata
     * @param mcolors
     *
     */
    public void setData(ArrayList<Double> mdata,ArrayList<Integer> mcolors){
        this.mdata=mdata;
        this.mcolors=mcolors;
        calculate();//初始化数据
        Draw();
    }

    /**
     * 绘制的第几步
     */
    private int index = -1 ;
    /**
     * 当前需要比较的数值
     */
    private int mindex = 0;
    /**
     * 当前绘制的断点(-90是最初在12点钟方向)
     */
    private int mbreakpoint = -90;
    /**
     * 各个数据所占总数的多少（个体数据/总数据*360）
     */
    private ArrayList<Integer> mints;

    /**
     * 目前需要绘制的progress是多少
     *
     */
    private int mprogress = 0;

    private ArrayList<Integer> mchange,mchange1;
    public MyCustomProgress(Context context){
        this(context,null);
    }

    public MyCustomProgress(Context context, @Nullable AttributeSet attrs){
        this(context, attrs,0);
    }

    public MyCustomProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomProgressBar,defStyleAttr,0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.CustomProgressBar_firstColor:
                    mfirstcolor = a.getColor(attr, Color.BLACK);
                     break;
//                case R.styleable.CustomProgressBar_secondColor:
//                    msecondcolor = a.getColor(attr,Color.BLACK);
//                    break;
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
//        mdata = new ArrayList<>();
//        mcolors = new ArrayList<>();
    }

    /**
     * 初始化数据
     */
    public void calculate(){
        mchange=new ArrayList<>();
        mints=new ArrayList<>();
        mchange1=new ArrayList<>();
        if (mdata.size()==1){
            mints.add(360);
            return;
        }
        double a = 0;
        for (int i = 0; i < mdata.size(); i++) {
            a += mdata.get(i);
        }
        for (int i = 0; i < mdata.size(); i++){
            mints.add((int) (mdata.get(i)/a*360));
            Log.e("001",""+mints.get(i));
        }
    }
    public void Draw(){
        mindex = mints.get(0);
        //绘画线程
        new Thread(){
            @Override
            public void run(){
                while (isdraw){
                    mProgress++;
                    mprogress++;
                    if (mProgress == 360){
                        isdraw = false;
                        return;
                    } else if (mindex==mProgress&&index<mints.size()-1){
                        index++;
                        mchange1.add(mindex);
                        mchange.add(mprogress);
                        mindex += mints.get(index);//下次需要比较的数值


                        mprogress=0;
                        mbreakpoint+=mints.get(index-1);
                        Log.e("111",""+mprogress);

                         //   108  108  108
                        //     18   126  234

     //                   Log.e("000","" + mints.get(index-1));
//                        Log.e("111","" + mbreakpoint);

                    }
                    postInvalidate(); //重新绘图的方法
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
        int centre = getWidth() / 2; // 获取圆心的x坐标
        int radius = centre - mpresswidth / 2;// 半径
        mPaint.setStrokeWidth(mpresswidth); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE);// 设置空心
        RectF oval = new RectF(centre - radius, centre - radius,centre + radius,centre+radius);//用于定义的圆弧的形状和大小的界限
        mPaint.setColor(mfirstcolor);// 设置圆环的颜色
        if (index<0){
            canvas.drawCircle(centre,centre,radius,mPaint);// 画出圆环
            mPaint.setColor(mfirstcolor);// 设置圆环的颜色
            canvas.drawCircle(centre,centre,radius,mPaint);//画出圆环
            index ++ ;
        } else {
            mPaint.setColor(mcolors.get(index));
            canvas.drawArc(oval,mbreakpoint,mprogress,false,mPaint);//划圈
        }
    }
}
