package com.abc.login.api

import com.abc.core.bean.TokenBean
import com.abc.core.okhttp.data.BaseResponse
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *@author Liszt
 *@date 2020/10/13
 *Description
 */
interface LoginApi {
    @POST("/pub/login-by-phone-password")
    suspend fun login(
        @Query("password") password: String, @Query("phone") phone: String,
        @Query("devid") devid: String, @Query("platform") platform: String,
        @Query("phoneModel") phoneModel: String, @Query("installChannel") installChannel: String,
        @Query("deviceDetail") deviceDetail: String, @Query("deviceName") deviceName: String
    ): BaseResponse<TokenBean>

}