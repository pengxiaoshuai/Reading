package com.xms.MySelfViewWidget.MySelfViewGroup;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dell on 2017/6/12.
 */

public class squareViewGroup extends ViewGroup {

    public squareViewGroup(Context context) {
        super(context);
    }

    public squareViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public squareViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec,heightMeasureSpec); //为其所有的子view设置宽和高
        /**
         * 记录如果是wrap_content是设置的宽和高
         */
        int width = 0;
        int height = 0;

        int cCount = getChildCount();

        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;

        //用于计算左边两个childView的高度
        int lheight = 0;
        //用于计算右边两个childView的高度，最终高度取二者之间最大值
        int rheight = 0;

        //用于计算右边两个childView的宽度
        int twidth = 0;
        //用于计算下面两个childView的宽度，最终宽度取二者之间最大值
        int bwidth = 0;

        /**
         * 根据childview计算的出的宽和高，以及设置的margin计算容器的宽和高，主要用于容器是
         * warp_content时
         */
        for (int i = 0; i < cCount; i++){
            View childview = getChildAt(i);
            cWidth = childview.getMeasuredWidth();//获取测量后的宽度
            cHeight = childview.getMeasuredHeight();//获取测量后的高度
            cParams = (MarginLayoutParams)childview.getLayoutParams();

            //上面两个childview
            if (i == 0 || i == 1){
                //宽度就等于测量后的宽度加上左右边距
                twidth += cWidth + cParams.leftMargin+cParams.rightMargin;
            }
            if (i ==2 || i==3){
                bwidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }
            if (i == 0 || i ==2){
                lheight +=cHeight + cParams.topMargin + cParams.bottomMargin;
            }
            if (i==1||i==3){
                rheight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }
        }

        width = Math.max(twidth,bwidth);
        height = Math.max(lheight,rheight);

        /**
         * 如果是wrap_content设置为我们计算的值
         * 否则：直接为父容器计算的值
         */
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY)? sizeWidth : width,(heightMode ==
        MeasureSpec.EXACTLY)? sizeHeight : height);
    }

    //onlayout对其所有childview进行定位(设置childview的绘制区域)
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int ccount = getChildCount();
        int cwidth = 0;
        int cheight = 0;
        MarginLayoutParams cparams = null;
        /**
         * 遍历所有childview根据其宽和高，以及margin进行布局
         */
        for (int i = 0; i < ccount; i++) {
            View childview = getChildAt(i);
            cwidth = childview.getMeasuredWidth();
            cheight = childview.getMeasuredHeight();
            cparams = (MarginLayoutParams) childview.getLayoutParams();
            int cl = 0,ct = 0,cr = 0,cb = 0;
            switch (i){
                case 0:
                    cl = cparams.leftMargin;
                    ct = cparams.topMargin+getStatusBarHeight();
                    break;
                case 1:
                    cl = getWidth()-cwidth-cparams.leftMargin-cparams.rightMargin;
                    ct = cparams.topMargin+getStatusBarHeight();
                    break;
                case 2:
                    cl = cparams.leftMargin;
                    //总高度减去控件的高度，再减去控件的底部边距
                    ct = getHeight()-cheight-cparams.bottomMargin;
                    break;
                case 3:
                    cl = getWidth() - cwidth - cparams.leftMargin - cparams.rightMargin;
                    ct = getHeight()-cheight-cparams.bottomMargin;
                default:
                    break;
            }
            cr = cl + cwidth;
            cb = cheight + ct;
            childview.layout(cl,ct,cr,cb);
        }
    }

    //重写父类该方法，返回MarginLayoutParams的实例，这样就为我们的ViewGroup指定了其LayoutParams为MarginLayoutParams

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    //获取顶部状态栏的高度
    private int getStatusBarHeight() {
        Resources resources = getContext().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Status height:" + height);
        return height;
    }
}


















