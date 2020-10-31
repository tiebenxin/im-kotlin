package com.abc.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abc.core.bean.TokenBean
import com.abc.core.okhttp.data.NetResult
import com.abc.core.utils.MMKVUtil
import com.abc.login.api.LoginRepository
import kotlinx.coroutines.launch

/**
 *@author Liszt
 *@date 2020/10/16
 *Description
 */
class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    private var tokenBean: TokenBean? = null
    fun login(
        psw: String,
        phone: String,
        deviceId: String,
        platform: String,
        phoneModel: String,
        installChannel: String,
        deviceDetail: String,
        deviceName: String
    ) {
        viewModelScope.launch {
            val result = loginRepository.login(
                psw,
                phone,
                deviceId,
                platform,
                phoneModel,
                installChannel,
                deviceDetail,
                deviceName
            )
            if (result is NetResult.Success) {
                tokenBean = result.data
                MMKVUtil.encode("token", tokenBean)
                val token: TokenBean? = MMKVUtil.decodeParcelable("token", TokenBean::class.java)
                println("token=" + token!!.accessToken)
            } else if (result is NetResult.Error) {

            }
        }
    }
}