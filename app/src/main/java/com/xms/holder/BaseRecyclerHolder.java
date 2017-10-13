package com.xms.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xms.R;
import com.xms.inteface.CommonListener;


/**
 * 万能的RecyclerView的ViewHolder
 * Created by 南尘 on 16-7-30.
 */
public class BaseRecyclerHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;
    private Context context;

    private BaseRecyclerHolder(Context context,View itemView) {
        super(itemView);
        this.context = context;
        //指定一个初始为8
        views = new SparseArray<>();
    }

    /**
     * 取得一个RecyclerHolder对象
     * @param context 上下文
     * @param itemView 子项
     * @return 返回一个RecyclerHolder对象
     */
    public static BaseRecyclerHolder getRecyclerHolder(Context context,View itemView){
        return new BaseRecyclerHolder(context,itemView);
    }

    public SparseArray<View> getViews(){
        return this.views;
    }

    /**
     * 通过view的id获取对应的控件，如果没有则加入views中
     * @param viewId 控件的id
     * @return 返回一个控件
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId){
        View view = views.get(viewId);
        if (view == null ){
            view = itemView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (T) view;
    }

    /**
     * 设置字符串
     */
    public BaseRecyclerHolder setText(int viewId,String text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }
    /**
     * 设置字符串
     */
    public BaseRecyclerHolder setCommonListener(int viewId, final int position, final CommonListener listener){
        View view=getView(viewId);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.commonListener(view,position);
            }
        });
        return this;
    }
    /**
     * 设置图片
     */
    public BaseRecyclerHolder setImageResource(int viewId,int drawableId){
        ImageView iv = getView(viewId);
        iv.setImageResource(drawableId);
        return this;
    }

    /**
     * 设置图片
     */
    public BaseRecyclerHolder setImageBitmap(int viewId, Bitmap bitmap){
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

    /**
     * @param viewId 图片控件id
     * @param url 图片地址
     * @param context 上下文对象
     * @param options 图片参数的选项
     * @return
     */
    public BaseRecyclerHolder setImageByUrl(final int viewId, String url, Context context, DisplayImageOptions options)
    {
        ImageLoader.getInstance().displayImage(url, (ImageView)getView(viewId), options);
        return this;
    }

    public BaseRecyclerHolder setImageAddPhoto(final int viewId, Context context)
    {
        ImageLoader.getInstance().displayImage("mipmap://" + R.mipmap.tj, (ImageView)getView(viewId));
        return this;
    }

    public BaseRecyclerHolder setImageByUrl(final int viewId, String url, Context context)
    {
        ImageLoader.getInstance().displayImage(url, (ImageView)getView(viewId));
        return this;
    }
}