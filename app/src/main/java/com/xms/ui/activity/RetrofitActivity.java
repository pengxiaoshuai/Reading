package com.xms.ui.activity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xms.R;
import com.xms.base.BaseActivity;
import com.xms.bean.PhoneResult;
import com.xms.callback.MyStringCallback;
import com.xms.constants.InterfaceDefinition;
import com.xms.inteface.PhoneService;
import com.xms.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends BaseActivity {
    private static final String BASE_URL = "http://zhsd.tpddns.cn:21258/app/";
    private static final String API_KEY = "8e13586b86e4b7f3758ba3bd6c9c9135";

    @BindView(R.id.edittext)
    EditText edittext;
    @BindView(R.id.textview)
    TextView textview;

    @Override
    public int getContentViewId() {
        return R.layout.activity_retrofit;
    }

    @Override
    public void initData(){
        
    }

    @OnClick({R.id.btn,R.id.btn2})
    void onViewClicked(View view){
        switch (view.getId()){
            case R.id.btn:
                Request();
                break;
            case R.id.btn2:
                request();
                break;
            default:
                break;
        }
    }
    private void request(){
        //创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                .baseUrl(BASE_URL)
                .build();

        //创建访问API的请求
        PhoneService service = retrofit.create(PhoneService.class);
      //  UserBean user = new UserBean("15779160456","123456");
        Call<PhoneResult> call = service.getCapture("15779160456","123456");
        //发送请求
        call.enqueue(new Callback<PhoneResult>(){
            @Override
            public void onResponse(Call<PhoneResult> call, Response<PhoneResult> response){
                Log.e("0000",response.toString());
                Log.e("0001",response.message());
                    //处理结果
                if (response.isSuccess()){
                    PhoneResult result = response.body();
                    Log.e("0000",result.toString());
                }
            }

            @Override
            public void onFailure(Call<PhoneResult> call, Throwable t){

            }
        });
    }
    private void Request(){
        JSONObject object=new JSONObject();
        object.put("MOBILE","15779160456");
        object.put("PASSWORD","123456");
        Log.e("请求报文","{"+InterfaceDefinition.ICommonKey.REQUEST_DATA+":"+object.toString()+"}");
        OkHttpUtils
                .post()//
                .url(BASE_URL + "login.do")//
                .addParams(InterfaceDefinition.ICommonKey.REQUEST_DATA,object.toString())
                .build()
                .execute(new MyStringCallback(RetrofitActivity.this){
                    @Override
                    public void onResponse(String response){
                        try {
                            Log.e("请求数据",""+response);
                            JSONObject object= JSON.parseObject(response);
                            if (object.getBoolean("success")){
                            }else{
                                ToastUtil.TextToast(object.getString("info"));
                            }
                        } catch (Exception ioex) {
                            ToastUtil.TextToast("请检查网络连接是否正常");
                        }


                    }
                });
    }
}







