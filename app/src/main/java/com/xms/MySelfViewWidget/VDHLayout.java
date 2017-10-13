package com.xms.MySelfViewWidget;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by dell on 2017/4/10.
 */

public class VDHLayout extends LinearLayout {
    private ViewDragHelper mdragger;
    private View mdragview;
    private View mAutoBackView;
    private View mEdgeTrackerView;
    private Point mAutoBackOriginpos = new Point();
    public VDHLayout(Context context,AttributeSet attrs){
        super(context, attrs);
        //创建实例需要三个参数，第一个就是当前的ViewGroup,第二个sensitivity，主要用于设置touchSlop:
        mdragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback(){
            //ViewDragHelper中拦截和处理事件时，需要会回调CallBack中的很多方法来决定一些事
            //比如：哪些子View可以移动，对个移动的View的边界的控制等等


            @Override
            public boolean tryCaptureView(View child, int pointerId){
            //tryCaptureView如返回true则表示可以捕获该view，你可以传入的第一个view参数决定哪些可以捕获
                return child == mdragview || child == mAutoBackView;//mEdgeTrackerView禁止直接移动
            }

            //如果控件有了点击事件或者clickable设置成了true的话要重写以下两个方法
            @Override
            public int getViewHorizontalDragRange(View child){
                return getMeasuredWidth()-child.getMeasuredWidth();//判断是否在屏幕里面,是的话正常捕捉
            }
            @Override
            public int getViewVerticalDragRange(View child){
                return getMeasuredHeight()-child.getMeasuredHeight();
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx){
            //clampViewPositionHorizontal、clampViewPositionVertical可以在该方法中对child移动的边界进行控制，left，top分别为即将移动到的位置
            //如横向的情况下，我希望只在ViewGroup的内部移动，即：最小>=paddingleft,最大<=ViewGroup.getWidth()-paddingright-child.getWidth
                final  int leftBound = getPaddingLeft();
                final  int rightBound = getWidth() - mdragview.getWidth() - leftBound;
                final  int newLeft = Math.min(Math.max(left,leftBound),rightBound);
                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy){
                final  int titlebar = GetBarHeight();
                final  int topBound = getPaddingTop() + titlebar; //顶部的最高高度要算上状态栏
                final  int bomBound = getHeight() - mdragview.getHeight() - topBound + titlebar;//底部的最高高度因为顶部高度算了状态栏，所以要加回来
                final int newtop = Math.min(Math.max(top,topBound),bomBound);
                return newtop;
            }
            //手指释放的时候回调
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel){
                //mAutoBackView手指释放时可以自动回去
                if (releasedChild==mAutoBackView){
                    //把控件松手后放回之前的位置
                    mdragger.settleCapturedViewAt(mAutoBackOriginpos.x,mAutoBackOriginpos.y);
                    invalidate();
                }
            }
            //在边界拖动时回调第三个View
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId){
                mdragger.captureChildView(mEdgeTrackerView,pointerId);
            }

        });
            //需要边界检测
            mdragger.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);

    }

    /**
     * 触摸相关方法
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
   //     return super.onInterceptTouchEvent(ev);
          return mdragger.shouldInterceptTouchEvent(ev);//把触摸事件交给mdragger
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        mdragger.processTouchEvent(event);//把触摸事件交给mdragger
        return true;//消费掉触摸事件
    }

    @Override
    public void computeScroll(){
        if (mdragger.continueSettling(true)){
            invalidate();
        }
    }
    //记住一开始布局放置后的位置
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mAutoBackOriginpos.x = mAutoBackView.getLeft();
        mAutoBackOriginpos.y = mAutoBackView.getTop();
    }

    @Override
    protected void onFinishInflate(){
        super.onFinishInflate();
        mdragview = getChildAt(0);
        mAutoBackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
    }
    /**
     * 获得状态栏的高度
     * @return
     */
    private int GetBarHeight(){
        int statusBarHeight2 = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusBarHeight2 = getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight2;
    }
//    列一下所有ViewDragHelper的Callback方法，看看还有哪些没用过的：
//
//    onViewDragStateChanged
//
//    当ViewDragHelper状态发生变化时回调（IDLE,DRAGGING,SETTING[自动滚动时]）
//    onViewPositionChanged
//
//            当captureview的位置发生改变时回调
//    onViewCaptured
//
//            当captureview被捕获时回调
//    onViewReleased 已用
//
//    onEdgeTouched
//
//    当触摸到边界时回调。
//    onEdgeLock
//
//    true的时候会锁住当前的边界，false则unLock。
//    onEdgeDragStarted 已用
//
//    getOrderedChildIndex
//
//    改变同一个坐标（x,y）去寻找captureView位置的方法。（具体在：findTopChildUnder方法中）
//    getViewHorizontalDragRange 已用
//
//    getViewVerticalDragRange 已用
//    tryCaptureView 已用
//    clampViewPositionHorizontal 已用
//    clampViewPositionVertical 已用
}
