package com.abc.login.api

import com.abc.core.bean.TokenBean
import com.abc.core.okhttp.BaseRepository
import com.abc.core.okhttp.RetrofitClient
import com.abc.core.okhttp.data.NetResult
import com.abc.core.utils.AppHostUtil

/**
 *@author Liszt
 *@date 2020/10/16
 *Description
 */
class LoginRepository(private val service: RetrofitClient) : BaseRepository() {
    suspend fun login(
        psw: String,
        phone: String,
        deviceId: String,
        platform: String,
        phoneModel: String,
        installChannel: String,
        deviceDetail: String,
        deviceName: String
    ): NetResult<TokenBean> {
        return callRequest(call = {
            requestLogin(
                psw,
                phone,
                deviceId,
                platform,
                phoneModel,
                installChannel,
                deviceDetail,
                deviceName
            )
        })
    }

    private suspend fun requestLogin(
        psw: String,
        phone: String,
        deviceId: String,
        platform: String,
        phoneModel: String,
        installChannel: String,
        deviceDetail: String,
        deviceName: String
    ) =
        handleResponse(
            service.createApi(LoginApi::class.java, AppHostUtil.httpHost)!!.login(
                psw,
                phone,
                deviceId,
                platform,
                phoneModel,
                installChannel,
                deviceDetail,
                deviceName
            )
        )
}