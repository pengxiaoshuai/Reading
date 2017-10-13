package com.xms.inteface;

import com.xms.bean.PhoneResult;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dell on 2017/7/4.
 */

public interface PhoneService {
    @POST("login.do")
    Call<PhoneResult> getCapture(@Query("MOBILE") String phone, @Query("PASSWORD") String password );
//    @POST("login.do")
//    Call<PhoneResult> getCapture(@Body UserBean user);
}
