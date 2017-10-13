package com.xms.MySelfViewWidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.xms.R;
import com.xms.utils.ToastUtil;


/**
 * Created by dell on 2017/3/29.
 *  自定义图片
 */

public class CustomImageView extends View {

    /**
     * 控件的宽
     */
    private Bitmap mImage;
    /**
     * 控件的缩放模式
     */
    private int mimagetype;
    private static final int IMAGE_SCALE_FITXY = 0;
    private static final int IMAGE_SCALE_CENTER = 1;
    /**
     * 文字说明
     */
    private String mtext;
    /**
     * 字体的颜色
     */
    private int mcolor;
    /**
     * 字体的大小
     */
    private int msize;
    /**
     * mrect是控制整体布局  mtextrect是对文本的约束
     */
    private Rect mrect,mtextrect;
    private Paint mPaint;
    private  int mwidth ;
    private int mheight ;
    public CustomImageView(Context context) {
        this(context,null);//在最初的构造方法调用  CustomImageView(Context context, AttributeSet attrs)
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);//然后再调用CustomImageView(Context context, AttributeSet attrs, int defStyleAttr)
                                //这样就一定会走CustomImageView(Context context, AttributeSet attrs, int defStyleAttr)这个方法了
    }

    /**
     * 初始化所特有自定义类型
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.custom, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.custom_image:
                    mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;
                case R.styleable.custom_imageScaleType:
                    mimagetype = a.getIndex(attr);
                    break;
                case R.styleable.custom_titleText:
                    mtext = a.getString(attr);
                    break;
                case R.styleable.custom_titleTextColor:
                    mcolor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.custom_titleTextSize:
                    msize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            16, getResources().getDisplayMetrics()));
                    break;
                default:
                    break;
            }
        }
        a.recycle();
        mrect=new Rect();
        mPaint = new Paint();
        mtextrect = new Rect();
        mPaint.setTextSize(msize);
        //计算了描绘字体需要的范围
        mPaint.getTextBounds(mtext,0,mtext.length(),mtextrect);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.TextToast("666");
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 设置宽度
         */
        int specMode = MeasureSpec.getMode(widthMeasureSpec);//设置控件的占位方式（match_parent、wrap_content、或者固定***dp什么的）
        int specSize = MeasureSpec.getSize(widthMeasureSpec);//能够占用的最大尺寸
        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            mwidth = specSize;
        }else {
            //由图片决定的宽
            int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
            // 由字体决定的宽
            int desirByTitle = getPaddingLeft() + getPaddingRight() + mtextrect.width();

            if (specMode==MeasureSpec.AT_MOST)//wrap_content
            {
                int desire = Math.max(desireByImg,desirByTitle);//取数最大的那个
                mwidth = Math.min(desire,specSize);//取数最小的那个（也就是取合适的那个数，不是最大的，是最合适的那个）
            }
        }
        /**
         * 设置高度
         */
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            mheight = specSize;//如果是match_parent 就把最大限度的空间赋给mheight
        }else {
            int desire = getPaddingTop() +getPaddingBottom()+mImage.getHeight()+mtextrect.height();
            if (specMode == MeasureSpec.AT_MOST)//wrap_content
            {
                mheight = Math.min(desire,specSize);
            }
        }
        setMeasuredDimension(mwidth,mheight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        /**
         * 边框
         */
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
       // canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);//在最外层画一圈线
        canvas.drawLine(10,10,180,180,mPaint); //这是划线 从哪个坐标（10,10）到哪个坐标（180,180）用什么画笔（mPaint）划线
        mrect.left = getPaddingLeft();
        mrect.right = mwidth - getPaddingRight();
        mrect.top = getPaddingTop();
        mrect.bottom = mheight - getPaddingBottom();
        mPaint.setColor(mcolor);
        mPaint.setStyle(Paint.Style.FILL);
        /**
         * 当前设置的宽度小于字体需要的宽度，将字体改为xxx...
         */
        if (mtextrect.width() > mwidth){
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mtext,paint,(float)mwidth - getPaddingLeft()-getPaddingRight(),TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), mheight - getPaddingBottom(),mPaint);
        } else {
            //正常情况，将字体居中
            canvas.drawText(mtext, mwidth / 2 - mtextrect.width() * 1.0f / 2, mheight - getPaddingBottom(), mPaint);
        }
        //取消使用掉的快
        mrect.bottom -= mtextrect.height();
        if (mimagetype == IMAGE_SCALE_FITXY){
            canvas.drawBitmap(mImage,null,mrect,mPaint);
        }else{
            //计算居中的矩形范围
            mrect.left = mwidth / 2 - mImage.getWidth() / 2;
            mrect.right = mwidth / 2 + mImage.getWidth() / 2;
            mrect.top = (mheight - mtextrect.height()) / 2 - mImage.getHeight()/2;
            mrect.bottom = (mheight - mtextrect.height()) / 2 + mImage.getHeight() / 2;
            canvas.drawBitmap(mImage,null,mrect,mPaint);
        }
    }
}
