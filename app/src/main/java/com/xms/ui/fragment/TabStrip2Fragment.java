package com.xms.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.xms.R;
import com.xms.adapter.BaseRecyclerAdapter;
import com.xms.base.BaseFragment;
import com.xms.holder.BaseRecyclerHolder;
import com.xms.inteface.CommonListener;
import com.xms.utils.DividerGridItemDecoration;
import com.xms.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dell on 2017/2/24.
 */

public class TabStrip2Fragment extends BaseFragment{
    private View mRootview;
    private BaseRecyclerAdapter<String>mAdapter;//万能recyclerview适配器
    private List<String>mlist;

    @Override
    protected View initView(LayoutInflater inflater) {
        mRootview=inflater.inflate(R.layout.fragment_tabstrip2,null);
        return mRootview;
    }
    @BindView(R.id.fragment_tabstrip_recycleview_2)
    RecyclerView mrecycleview;
    @Override
    public void initData() {
        mlist=new ArrayList<>();
        for (int i=0;i<50;i++){
            mlist.add("第"+i+"个");
        }
        //万能recyclerview适配器
        mAdapter=new BaseRecyclerAdapter<String>(mContext,mlist,R.layout.adapter_test_layout) {
            @Override
            public void convert(BaseRecyclerHolder holder, String item, int position, boolean isScrolling) {
                holder.setText(R.id.adapter_test_layout_text,item);
                holder.setCommonListener(R.id.adapter_test_layout_text, position, new CommonListener() {
                    @Override
                    public void commonListener(View view, int position) {
                        ToastUtil.TextToast("删除了"+position+"第个");
                        mAdapter.delete(position);
                    }
                });
            }
        };

        GridLayoutManager manager=new GridLayoutManager(mContext,6);//设置grideview的样式 几列
        mrecycleview.setLayoutManager(manager);
        mrecycleview.addItemDecoration(new DividerGridItemDecoration(mContext));
        mrecycleview.setAdapter(mAdapter);
        // 设置item动画(recyclerview自带的动画)
     //    mrecycleview.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                ToastUtil.TextToast("第"+position+"项");
                mAdapter.insert("添加的"+position+"个",position);
            }
        });
    }
}
