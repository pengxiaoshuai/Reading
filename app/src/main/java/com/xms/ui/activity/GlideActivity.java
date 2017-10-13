package com.xms.ui.activity;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.xms.R;
import com.xms.base.BaseActivity;

import butterknife.BindView;

/**
 * 图片加载框架guide4.0的使用
 */
public class GlideActivity extends BaseActivity {

    @BindView(R.id.glide_image1)
    ImageView mimage1;
    @BindView(R.id.glide_image2)
    ImageView mimage2;
    @BindView(R.id.glide_image3)
    ImageView mimage3;
    @BindView(R.id.glide_image4)
    ImageView mimage4;
    private String url = "http://img3.imgtn.bdimg.com/it/u=3269061743,2028437678&fm=27&gp=0.jpg";
    @Override
    public int getContentViewId(){
        return R.layout.activity_glide;
    }

    @Override
    public void initData(){
        //圆形且添加背景图片加载
        Glide.with(this)
                .load(url)
                .apply(new RequestOptions().circleCrop().placeholder(R.mipmap.ic_launcher))//加载图片为圆形居中，且添加背景图ic_launcher
                .transition(new DrawableTransitionOptions().crossFade(2000))//2S渐变
                .into(mimage1);
        //常规加载，添加默认背景图和加载报错图片
        //填充整个布局
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(android.R.drawable.stat_notify_error) //加载报错图片
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        Glide.with(this)
                .load(url)
                .apply(options)
                .into(mimage2);

        //保留原有比例的图片加载，不改变图片的宽高比例
        Glide.with(this)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                        return false;
                    }
                })
                .apply(new RequestOptions().centerInside())
                .thumbnail(Glide.with(this).load(R.mipmap.ic_launcher))
                .into(mimage3);

        //圆形填充加载，加载后渐变2S
        RequestBuilder<Drawable> mRequestBuilder = Glide.with(this).load(url);
        mRequestBuilder.listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                return false;
            }
        }).transition(new DrawableTransitionOptions().crossFade(2000))
                .apply(new RequestOptions().circleCrop())
                .into(mimage4);

    }

}
