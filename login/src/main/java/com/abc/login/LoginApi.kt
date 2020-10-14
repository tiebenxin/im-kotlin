package com.abc.login

import com.abc.core.bean.ReturnBean
import com.abc.core.bean.TokenBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.POST

/**
 *@author Liszt
 *@date 2020/10/13
 *Description
 */
interface LoginApi {
    @POST("/pub/login-by-phone-password")
    abstract fun login(
        @Field("password") password: String, @Field("phone") phone: String,
        @Field("devid") devid: String, @Field("platform") platform: String,
        @Field("phoneModel") phoneModel: String, @Field("installChannel") installChannel: String, @Field(
            "deviceDetail"
        ) deviceDetail: String, @Field("deviceName") deviceName: String
    ): Observable<ReturnBean<TokenBean>>

}