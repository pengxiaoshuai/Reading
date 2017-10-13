package com.xms.MySelfViewWidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.xms.R;

/**
 * Created by dell on 2017/4/11.
 */

public class QQListView extends ListView {
    /**
     * 用户滑动的最小距离
     */
    private int touchSlop;
    /**
     * 是否响应滑动
     */
    private boolean isSliding;
    /**
     * 手指按下时的x坐标
     */
    private int xDown;
    /**
     * 手指按下时的y坐标
     */
    private int yDown;
    /**
     * 手指移动时的x坐标
     */
    private int xMove;
    /**
     * 手指移动时的y坐标
     */
    private int yMove;
    private LayoutInflater mInflater;
    private PopupWindow mPopupwindow;
    private int mPopupwindowHeight;
    private int mPopupwindowWidth;

    private Button mDelBtn;
    /**
     * 为删除按钮提供一个回调接口
     */
    private DelButtonClickListener mListener;

    /**
     * 当前手指触摸的View
     */
    private View mCurrentView;

    /**
     * 当前手指触摸的位置
     */
    private int mCurrentViewPos;

    /**
     * 必要的一些初始化
     * @param context
     * @param attrs
     */
    public QQListView(Context context, AttributeSet attrs){
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        View view = mInflater.inflate(R.layout.adapter_item_qqitem,null);
        mDelBtn = (Button) view.findViewById(R.id.adapter_item_qqitem_btn);
        mPopupwindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        /**
         *先调用下measure，否则拿不到宽和高
         */
        mPopupwindow.getContentView().measure(0,0);
        mPopupwindowHeight = mPopupwindow.getContentView().getMeasuredHeight();
        mPopupwindowWidth = mPopupwindow.getContentView().getMeasuredWidth();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                xDown = x;
                yDown = y;
                /**
                 * 如果当前popuwindow显示，则直接隐藏，然后屏蔽ListView的touch事件的下传
                 */
                if (mPopupwindow.isShowing()){
                 //   dismissPopWindow();
                    return false;
                }
                //获得当前手指按下时的item的位置
                mCurrentViewPos = pointToPosition(xDown,yDown);
                //获得当前手指按下时的item
                View view = getChildAt(mCurrentViewPos - getFirstVisiblePosition());
                mCurrentView = view;
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = x;
                yMove = y;
                int dx = xMove - xDown;
                int dy = yMove - yDown;
                /**
                 * 判断是否从右到左的滑动
                 */
                if (xMove< xDown&&Math.abs(dx)>touchSlop&&Math.abs(dy)<touchSlop){
                    isSliding=true;
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    interface DelButtonClickListener{
        public void clickHappend(int postion);
    }
}
