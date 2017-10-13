package com.xms.callback;


import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Request;
import com.xms.R;
import com.xms.utils.ToastUtil;
import com.zhy.http.okhttp.callback.StringCallback;


/**
 * Created by dell on 2017/1/10.
 */

public class MyStringCallback extends StringCallback {
    private ProgressDialog mDialog;
    private Context mContext;
    private Boolean mIsDialogShow=true;
   // private PullToRefreshLayout pullToRefreshLayout;
    public MyStringCallback(Context context) {
        this.mContext = context;
    }

    /**
     * @param context
     * @param isDialogShow true 表示显示dialog ,false 表示不显示
     */
    public MyStringCallback(Context context, boolean isDialogShow) {
        mContext = context;
        mIsDialogShow = isDialogShow;
        if (isDialogShow)
            initDialogShow();
    }
//    /**
//     * @param context
//     * @param isDialogShow true 表示显示dialog ,false 表示不显示
//     */
//    public MyStringCallback(Context context, boolean isDialogShow) {
//        mContext = context;
//        mIsDialogShow = isDialogShow;
//        if (isDialogShow)
//            initDialogShow();
//      //  this.pullToRefreshLayout=pullToRefreshLayout;
//    }
    @Override
    public void onBefore(Request request)
    {
        initViews();
        mDialog.show();
        mDialog.setContentView(R.layout.progress_bar_dialog);
      //  setTitle("loading...");
    }

    @Override
    public void onAfter()
    {
        dismissProgressDialog();
    }

    private void initViews() {
        mDialog = new ProgressDialog(mContext, R.style.Dialog);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
    }

    protected void dismissProgressDialog() {
        if (mDialog.isShowing()) {
            // 对话框显示中
            mDialog.dismiss();
        }
    }
    private void initDialogShow(){
        initViews();
        mDialog.show();
        mDialog.setContentView(R.layout.progress_bar_dialog);
    }

    @Override
    public void onError(Request request, Exception e) {
//        if (pullToRefreshLayout!=null){
//            pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
//        }
        ToastUtil.TextToast("请检查网络环境是否正常");
    }

    @Override
    public void onResponse(String response) {
        Log.e("Json数据",response);

    }



}
