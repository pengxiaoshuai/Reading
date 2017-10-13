package com.xms.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.xms.R;

/**
 * 折叠视图（待完成）
 * Created by dell on 2017/6/15.
 */

public class MatrixPolyToPolyActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(new PolyToPolyView(this));
    }

    class  PolyToPolyView extends View{
        private Bitmap mBitmap;
    //    private Matrix mMatrix;

        //加点阴影渐变
        private Paint mShadowPaint;
        private Matrix mMatrix;
        private LinearGradient mlinear;

        public PolyToPolyView(Context context) {
            super(context);
            mBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.school6);
            mMatrix = new Matrix();

            mShadowPaint = new Paint();
            mShadowPaint.setStyle(Paint.Style.FILL);
            mlinear = new LinearGradient(0,0,0.5f,0, Color.BLACK,Color.TRANSPARENT, Shader.TileMode.CLAMP);
            mShadowPaint.setShader(mlinear);

            mMatrix = new Matrix();
            mMatrix.setScale(mBitmap.getWidth(),1);
            mlinear.setLocalMatrix(mMatrix);
            mShadowPaint.setAlpha((int) (0.9*255));
//            float[] src = {0,0,
//                    mBitmap.getWidth(),0,
//                    mBitmap.getWidth(),mBitmap.getHeight(),
//                    0,mBitmap.getHeight()};
//            float[] dst = {0,0,
//                    mBitmap.getWidth(),100,
//                    mBitmap.getWidth(),mBitmap.getHeight() - 100,
//                    0,mBitmap.getHeight()};
//            mMatrix.setPolyToPoly(src,0,dst,0,src.length >> 1);
        }

        @Override
        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);
          //  canvas.drawBitmap(mBitmap,mMatrix,null);
            canvas.save();
            float[] src = {0,0,
                    mBitmap.getWidth(),0,
                    mBitmap.getWidth(),mBitmap.getHeight(),
                    0,mBitmap.getHeight()};
            float[] dst = {0,0,
                    mBitmap.getWidth(),100,
                    mBitmap.getWidth(),mBitmap.getHeight() - 100,
                    0,mBitmap.getHeight()};
            mMatrix.setPolyToPoly(src,0,dst,0,src.length >> 1);
            canvas.concat(mMatrix);
            canvas.drawBitmap(mBitmap,0,0,null);
            //绘制阴影
            canvas.restore();
        }
    }

}
