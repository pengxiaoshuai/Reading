package com.xms.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xms.R;
import com.xms.utils.DefaultDisplayImageOptions;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	ImageView imageview;
//	private int[] mImgs;//也可以用本地图片轮播 把本地图片放到数组中一样使用
	private ArrayList<String> str;//网络图片的地址

	public ImageAdapter(Context context, ArrayList<String> str) {
		mContext = context;
		this.str = str;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}


	@Override
	public View getView(int position, View view, ViewGroup viewgroup) {
		ViewHolder mHolder = null;
		if (view == null) {
			mHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(
					R.layout.adapter_viewpager_item, null);
			mHolder.mImgv = (ImageView) view.findViewById(R.id.item_img_id);
			view.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) view.getTag();
		}
	//	imageview= mHolder.mImgv;
		ImageLoader.getInstance().displayImage(str.get(position % str.size()),mHolder.mImgv, DefaultDisplayImageOptions.getDefaultDisplayImageOptions(mContext));
		return view;
	}

	class ViewHolder {
		ImageView mImgv = null;
	}
}
