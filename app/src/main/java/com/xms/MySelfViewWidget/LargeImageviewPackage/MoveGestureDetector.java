package com.xms.MySelfViewWidget.LargeImageviewPackage;

import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;

/**
 * Created by dell on 2017/5/10.
 */

public class MoveGestureDetector extends BaseGestureDetector{

    private PointF mCurrentPoint;
    private PointF mPrePoint;
    //仅仅为了减少创建内存
    private PointF mDeltaPoint = new PointF();

    //用于记录最终结果，并返回
    private PointF mExtenalPointer = new PointF();

    private  OnMoveGestureListener mListenter;


    public MoveGestureDetector(Context context, OnMoveGestureListener listener) {
        super(context);
        mListenter = listener;
    }


    @Override
    protected void handleInProgressEvent(MotionEvent event) {
        int actionCode = event.getAction() & MotionEvent.ACTION_MASK;
        switch (actionCode){
            case  MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mListenter.onMoveEnd(this);
                resetState();
                break;
            case MotionEvent.ACTION_MOVE:
                updateStateByEvent(event);
                boolean update = mListenter.onMove(this);
                if (update){
                    mPreMotionEvent.recycle();
                    mPreMotionEvent = MotionEvent.obtain(event);
                }
                break;
        }
    }

    @Override
    protected void handleStartProgressEvent(MotionEvent event) {
        int actionCode = event.getAction() & MotionEvent.ACTION_MASK;
        switch (actionCode){
            case  MotionEvent.ACTION_DOWN:
                resetState();//防止没有接收到CANCEL or UP，保险起见
                mPreMotionEvent = MotionEvent.obtain(event);
                updateStateByEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mGestureInprogress = mListenter.onMoveBegin(this);
                break;
        }
    }

    @Override
    protected void updateStateByEvent(MotionEvent event) {
        final  MotionEvent prev = mPreMotionEvent;

    }

    /**
     * 根据event计算多指中心点
     */
    private PointF caculateFocalPointer(MotionEvent event){
        final  int count = event.getPointerCount();
        float x = 0,y= 0 ;
        for (int i = 0; i < count; i++){
            y+=event.getX(i);
            y+=event.getY(i);
        }
        x/=count;
        y/=count;
        return  new PointF(x,y);
    }
    public float getMoveX()
    {
        return  mExtenalPointer.x;
    }
    public float getMoveY(){
        return  mExtenalPointer.y;
    }

    public interface  OnMoveGestureListener{
        public boolean onMoveBegin(MoveGestureDetector detector);
        public boolean onMove(MoveGestureDetector detector);
        public void onMoveEnd(MoveGestureDetector detector);
    }
    public static class SimpleMoveGestureDetector implements  OnMoveGestureListener{

        @Override
        public boolean onMoveBegin(MoveGestureDetector detector) {
            return true;
        }

        @Override
        public boolean onMove(MoveGestureDetector detector) {
            return false;
        }

        @Override
        public void onMoveEnd(MoveGestureDetector detector) {

        }
    }
}
