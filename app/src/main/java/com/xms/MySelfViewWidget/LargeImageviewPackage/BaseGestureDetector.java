package com.xms.MySelfViewWidget.LargeImageviewPackage;

import android.content.Context;
import android.view.MotionEvent;

/**
 * Created by dell on 2017/5/10.
 */

public abstract class BaseGestureDetector {
    protected  boolean mGestureInprogress;
    protected MotionEvent mPreMotionEvent;
    protected  MotionEvent mCurrentMotionEvent;
    protected Context mContext;
    public  BaseGestureDetector(Context context){
        this.mContext = context;
    }
    public boolean onTouchEvent(MotionEvent event){

        if (!mGestureInprogress){
                handleStartProgressEvent(event);
        } else
        {
            handleInProgressEvent(event);
        }

        return true;
    }
    protected  abstract void handleInProgressEvent(MotionEvent event);
    protected  abstract  void handleStartProgressEvent(MotionEvent event);
    protected  abstract  void updateStateByEvent(MotionEvent event);
    protected  void resetState(){
        if (mPreMotionEvent != null){
            mPreMotionEvent.recycle();
            mPreMotionEvent = null;
        }
        if (mCurrentMotionEvent!=null){
            mCurrentMotionEvent.recycle();
            mCurrentMotionEvent=null;
        }
        mGestureInprogress = false;
    }
}
