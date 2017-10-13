package com.xms.MySelfViewWidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.xms.R;

/**
 * Created by dell on 2017/6/20.
 */

public class RoundImage extends ImageView{
    /**
     * 图片的类型，圆形or圆角
     */
    private int type;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;
    /**
     * 圆角大小的默认值
     */
    private static final int BODER_RADIUS_DEFAULT = 10;
    /**
     * 圆角大小
     */
    private int mBorderRadius;
    /**
     * 绘图的Paint
     */
    private Paint mBitmapPaint;
    /**
     * 圆角半径
     */
    private int mRadius;
    /**
     * 3*3 矩阵，主要用于缩小放大
     */
    private Matrix matrix;
    /**
     * 渲染图像，使用图像为绘制图形着色
     */
    private BitmapShader mbitmapShader;
    /**
     * view的宽度
     */
    private int mWidth;
    private RectF mRoundRect;

    public RoundImage(Context context) {
        this(context,null);
    }
    //在构造方法中获取了我们的自定义属性，以及部分变量的初始化
    public RoundImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        matrix = new Matrix();
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);//抗锯齿

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImage);
        mBorderRadius = a.getDimensionPixelSize(R.styleable.RoundImage_borderRadius, (int) TypedValue.
                applyDimension(TypedValue.COMPLEX_UNIT_DIP,BODER_RADIUS_DEFAULT,getResources().
                        getDisplayMetrics()));//默认为10dp
        type = a.getInt(R.styleable.RoundImage_type,TYPE_CIRCLE);//默认circle
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 如果类型是圆形，则强制改变view的宽高一致，以小值为准
         */
        if (type == TYPE_CIRCLE){
            mWidth = Math.min(getMeasuredWidth(),getMeasuredHeight());//取小的宽高值为标准
            mRadius = mWidth/2;//半径就等于宽度除以二
            setMeasuredDimension(mWidth,mWidth);//重新设置宽高
        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        //super.onDraw(canvas);
          if (getDrawable()==null){
              return;
          }
          setUpShader();
        if (type == TYPE_ROUND){
            canvas.drawRoundRect(mRoundRect,mBorderRadius,mBorderRadius,mBitmapPaint);
        }else{
            canvas.drawCircle(mRadius,mRadius,mRadius,mBitmapPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        //圆角图片的范围
        if (type==TYPE_ROUND){
            mRoundRect = new RectF(0,0,getWidth(),getHeight());
        }
    }

    /**
     * 初始化BitmapShader
     */
    private void setUpShader(){
        Drawable drawable = getDrawable();
        if (drawable == null){
            return;
        }
        Bitmap bmp = drawableToBitmap(drawable);
        //已bmp作为着色器，就是在指定区域内绘制bmp
        mbitmapShader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        if (type == TYPE_CIRCLE){
            //拿到bitmap宽或高的小值
            int bSize = Math.min(bmp.getWidth(),bmp.getHeight());
            scale = mWidth * 1.0f /bSize;
        } else if (type == TYPE_ROUND){
            //如果图片的宽或者高与view的宽高不匹配，计算出需要缩小的比例；缩放后的图片的宽高，一定要大于我们的View的宽高，所以我们在这取最大值
            scale = Math.max(getWidth() * 1.0f / bmp.getWidth(),getHeight()*1.0f/bmp.getHeight());
        }
        //shader的变换矩阵，我们这里主要用于放大或者缩小
        matrix.setScale(scale,scale);
        //设置变换矩阵
        mbitmapShader.setLocalMatrix(matrix);
        //设置shader
        mBitmapPaint.setShader(mbitmapShader);
    }

    /**
     * drawable转bitmap
     */
    private Bitmap drawableToBitmap(Drawable drawable){
        if (drawable instanceof BitmapDrawable){
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0,0,w,h);
        drawable.draw(canvas);
        return bitmap;
    }

    //简单的存储一下，当前的type以及mBorderRadius
    private static  final String STATE_INSTANCE = "state_instance";
    private static final String STATE_TYPE = "state_type";
    private static  final String STATE_BORDER_RADIUS = "state_border_radius";

    @Override
    protected Parcelable onSaveInstanceState() {
//        return super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_INSTANCE,super.onSaveInstanceState());
        bundle.putInt(STATE_TYPE,type);
        bundle.putInt(STATE_BORDER_RADIUS,mBorderRadius);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(((Bundle) state).getParcelable(STATE_INSTANCE));
            this.type = bundle.getInt(STATE_TYPE);
            this.mBorderRadius = bundle.getInt(STATE_BORDER_RADIUS);
        } else {
            super.onRestoreInstanceState(state);
        }
    }


    //同时也对外公布了两个方法，用于动态修改圆角大小和type
    public void setBorderRadius(int borderRadius){
        int pxVal = dp2px(borderRadius);
        if (this.mBorderRadius != pxVal){
            this.mBorderRadius = pxVal;
            invalidate();
        }
    }

    public void setType(int type){
        if (this.type != type){
            this.type = type;
            if (this.type!=TYPE_ROUND&&this.type!=TYPE_CIRCLE){
                this.type = TYPE_CIRCLE;
            }
            requestLayout();
        }
    }

    public int dp2px(int dpVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpVal,
                getResources().getDisplayMetrics());
    }
}
