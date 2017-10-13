package com.xms.MySelfViewWidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.xms.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by dell on 2017/3/24.
 */

public class myselftextview extends View {
    /**
     * 设置的文本
     */
    private String mtext;
    /**
     * 设置的颜色
     */
    private int mcolor;
    /**
     * 设置的字体大小
     */
    private int msize;
    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mRect;
    private Paint mPaint;

    public myselftextview(Context context) {
        this(context, null);
    }

    public myselftextview(Context context,  AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public myselftextview(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.myselfview, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.myselfview_selfname:
                    mtext = a.getString(attr);
                    break;
                case R.styleable.myselfview_selfcolor:
                    //如无颜色设置就设置默认颜色为黑色
                    mcolor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.myselfview_selfsize:
                    //默认设置为16sp，TypeValue也可以把sp转化为px
                    msize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                default:
                    break;
            }

        }
        a.recycle();
        /**
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        //设置画笔的字体大小
        mPaint.setTextSize(msize);
        mRect = new Rect();
        mPaint.getTextBounds(mtext, 0, mtext.length(), mRect);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mtext=gettext();
                postInvalidate();//刷新view 重新测量并绘制
            }
        });
    }

    private String gettext(){
        Random random = new Random();
        Set<Integer> set = new HashSet<>();
        while(set.size()<4){
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb =new StringBuffer();
        for (Integer i : set){
            sb.append(""+i);
        }
        return sb.toString();
    }
    //    MeasureSpec的specMode,一共三种类型：
    //    EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
    //    AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
    //    UNSPECIFIED：表示子布局想要多大就多大，很少使用
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //进行自己测量
        int width = 0;
        int height = 0;
        /**
         * 设置宽度
         */
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (specMode)//判断他的模式
        {
            case MeasureSpec.EXACTLY:// 明确指定了
                width = getPaddingLeft() + getPaddingRight() + specSize;
                break;
            case MeasureSpec.AT_MOST:// 一般为WARP_CONTENT
                width = getPaddingLeft() + getPaddingRight() + mRect.width();
                break;
        }

        /**
         * 设置高度
         */
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode)//判断他的模式
        {
            case MeasureSpec.EXACTLY:// 明确指定了
                height = getPaddingTop() + getPaddingBottom() + specSize;
                break;
            case MeasureSpec.AT_MOST:// 一般为WARP_CONTENT
                height = getPaddingTop() + getPaddingBottom() + mRect.height();
                break;
        }

        setMeasuredDimension(width, height);

     //   super.onMeasure(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //设置初始画笔颜色为黄色
        mPaint.setColor(Color.YELLOW);
        //把当前控件全部绘制成黄色
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        //再设置用户设置的颜色
        mPaint.setColor(mcolor);
        //mtext.getwidth()/2就是当前控件一半的宽度  减去mRect.width()/2是减去自身控件一半的宽度
        //mRect.height()/2是当前控件一半的高度  mPaint是代表用哪只画笔绘制
        canvas.drawText(mtext, getWidth() / 2 - mRect.width() / 2, getHeight() / 2 + mRect.height() / 2, mPaint);
        // super.onDraw(canvas);
    }
}
