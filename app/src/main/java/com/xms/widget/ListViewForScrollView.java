package com.xms.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 解决listview在scrollview中显示不全的问题
 * @author 彭其煊
 * @version 1.0
 * @date 2017.2.24
 */
public class ListViewForScrollView extends ListView {

	public ListViewForScrollView(Context context) {
		super(context);
	}

	public ListViewForScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ListViewForScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
