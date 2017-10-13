package com.xms.ui.activity;

import com.xms.MySelfViewWidget.MyCustomProgress;
import com.xms.R;
import com.xms.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by dell on 2017/3/24.
 */

public class MySelfViewActivity extends BaseActivity{
    @Override
    public int getContentViewId(){
        return R.layout.activity_myselftextview;
    }
    private ArrayList<Double> mdatas;
    private ArrayList<Integer> mcolors;
    @Override
    public void initData(){
            mdatas =new ArrayList<>();
//        mdatas.add(1000.0);
//        mdatas.add(457.0);
//        mdatas.add(366.0);
//        mdatas.add(110.0);
 //       mdatas.add(550.0);
        mdatas.add(300.0);
        mdatas.add(300.0);
        mdatas.add(300.0);
        mdatas.add(100.0);
        mcolors=new ArrayList<>();
       // mcolors.add(getResources().getColor(R.color.red));
        mcolors.add(getResources().getColor(R.color.color_yellow));
        mcolors.add(getResources().getColor(R.color.gray_dark_bg));
        mcolors.add(getResources().getColor(R.color.sidefont));
        mcolors.add(getResources().getColor(R.color.pink));
        cus.setData(mdatas,mcolors);
    }

    @BindView(R.id.mycustom)
    MyCustomProgress cus;

}
