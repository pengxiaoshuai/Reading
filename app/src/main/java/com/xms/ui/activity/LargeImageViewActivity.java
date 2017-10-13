package com.xms.ui.activity;

import com.xms.MySelfViewWidget.LargeImageviewPackage.LargeImageView;
import com.xms.R;
import com.xms.base.BaseActivity;

import java.io.InputStream;

import butterknife.BindView;

/**
 * Created by dell on 2017/5/10.
 */

public class LargeImageViewActivity extends BaseActivity {
    @BindView(R.id.act_largeimage_image)
    LargeImageView mimage;
    @Override
    public int getContentViewId() {
        return R.layout.act_largeimage;
    }

    @Override
    public void initData() {
        try{
            InputStream inputStream = getAssets().open("qm.jpg");
            mimage.setInputStream(inputStream);
         } catch (Exception io){

        }
    }
}
