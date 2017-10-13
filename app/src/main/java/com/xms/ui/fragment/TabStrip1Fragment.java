package com.xms.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xms.R;
import com.xms.adapter.CommonAdapter;
import com.xms.base.BaseFragment;
import com.xms.holder.ViewHolder;
import com.xms.utils.DividerItemDecoration;
import com.xms.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by dell on 2017/2/24.
 */

public class TabStrip1Fragment extends BaseFragment implements AdapterView.OnItemClickListener{
    private View mRootview;
    private CommonAdapter<String> adapter;//String类型的万能适配器
    private ArrayList<String> mData;
    private ArrayList<String> mData2;
    private DividerItemDecoration dividerItemDecoration;//recyclerview的分割线
    @Override
    protected View initView(LayoutInflater inflater) {
        mRootview=inflater.inflate(R.layout.fragment_tabstrip1,null);
        return mRootview;
    }
    @BindView(R.id.fragment_tabstrip_listview)
    ListView mlistview;
    @BindView(R.id.fragment_tabstrip_recycleview)
    RecyclerView mrecycle;
    private Myadapter mAdapter;//recyclerview的自定义适配器

    @Override
    public void initData(){
        mData=new ArrayList<>();
        for (int i=0;i<30;i++){
            mData.add("listview第"+i+"个");
        }
        mData2=new ArrayList<>();
        for (int i=0;i<30;i++){
            mData2.add("recyclerview第"+i+"个");
        }
        //万能适配器
        adapter=new CommonAdapter<String>(mContext,mData,R.layout.adapter_test_layout) {
            @Override
            public void convert(ViewHolder mHolder, String item, int position) {
                mHolder.setText(R.id.adapter_test_layout_text,item);
            }
        };
        mlistview.setAdapter(adapter);
        mlistview.setOnItemClickListener(this);//给listview添加点击事件
        mlistview.setVisibility(View.VISIBLE);
        mrecycle.setVisibility(View.GONE);
        //LinearLayoutManager 现行管理器，支持横向、纵向。
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);//设置垂直的布局管理器
        mrecycle.setLayoutManager(linearLayoutManager);//设置布局管理器
        mAdapter=new Myadapter(); //recyclerview的自定义适配器
        mrecycle.setAdapter(mAdapter);
        dividerItemDecoration=new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL_LIST);//分割线
        mrecycle.addItemDecoration(dividerItemDecoration);
        mAdapter.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void OnItemClickListener(View view, int postion) {
                ToastUtil.TextToast("第"+postion+"个recyclerview");
            }
        });
    }
    @OnClick({R.id.fragment_tabstrip_btn1,R.id.fragment_tabstrip_btn2,R.id.fragment_tabstrip_btn3})
        void  OnClick(View view){
        switch (view.getId()){
            case R.id.fragment_tabstrip_btn1:
                mlistview.setVisibility(View.VISIBLE);
                mrecycle.setVisibility(View.GONE);
                break;
            case R.id.fragment_tabstrip_btn2:
                mlistview.setVisibility(View.GONE);
                mrecycle.setVisibility(View.VISIBLE);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);//布局管理器
                linearLayoutManager.setOrientation(LinearLayout.HORIZONTAL);//设置横向的布局管理器
                mrecycle.setLayoutManager(linearLayoutManager);//设置布局管理器
                dividerItemDecoration.setOrientation(DividerItemDecoration.HORIZONTAL_LIST);//设置横向的分割线
                mAdapter.notifyDataSetChanged();//刷新适配器
                break;
            case R.id.fragment_tabstrip_btn3:
                mlistview.setVisibility(View.GONE);
                mrecycle.setVisibility(View.VISIBLE);
                LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(mContext);//布局管理器
                linearLayoutManager2.setOrientation(LinearLayout.VERTICAL);//设置垂直的布局管理器
                mrecycle.setLayoutManager(linearLayoutManager2);//设置布局管理器
                dividerItemDecoration.setOrientation(DividerItemDecoration.VERTICAL_LIST);//设置垂直的分割线
                mAdapter.notifyDataSetChanged();//刷新适配器
                break;
            default:
                break;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ToastUtil.TextToast("第"+i+"listview");
    }


    class  Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder>{
        OnItemClickListener mlisten;
        public void setOnItemClickListener(OnItemClickListener mlisten){
            this.mlisten=mlisten;
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
            MyViewHolder holder=new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_test_layout,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            holder.mtv.setText(mData2.get(position));
        }

        @Override
        public int getItemCount() {
            return mData2.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView mtv;
            private MyViewHolder(View view) {
                super(view);
                mtv= (TextView) view.findViewById(R.id.adapter_test_layout_text);
            }
        }

    }

    interface OnItemClickListener{
        void OnItemClickListener(View view,int postion);
    }
}
