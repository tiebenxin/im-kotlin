package com.abc.login.api

import com.abc.core.bean.TokenBean
import com.abc.core.okhttp.BaseRepository
import com.abc.core.okhttp.RetrofitClient
import com.abc.core.okhttp.data.NetResult
import com.abc.core.utils.encrypt.MD5

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
//        return callRequest(call = {
//            requestLogin(
//                psw,
//                phone,
//                deviceId,
//                platform,
//                phoneModel,
//                installChannel,
//                deviceDetail,
//                deviceName
//            )
//        })
        return callRequest(call = {
            var map: Map<String, String> = mutableMapOf(
                "password" to MD5.md5(psw),
                "phone" to phone,
                "devid" to deviceId,
                "platform" to platform,
                "phoneModel" to phoneModel,
                "installChannel" to installChannel,
                "deviceDetail" to deviceDetail,
                "deviceName" to deviceName
            )
            requestLogin2(
                map
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
            service.create(LoginApi::class.java)!!.login(
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

    private suspend fun requestLogin2(map: Map<String, String>) =
        handleResponse(
            service.create(LoginApi::class.java)!!.login(getRequestBody(map))
        )
}