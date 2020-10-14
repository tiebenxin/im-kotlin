package com.abc.core.utils

import android.text.TextUtils
import com.abc.core.BuildConfig
import com.abc.core.okhttp.HttpChannel


/**
 * @author Liszt
 * @date 2020/3/26
 * Description
 */
object AppHostUtil {
    private val HTTP_PORT = 8080
    val TCP_PORT = 19991
    private val HTTPS = "https://"

    private var connectHostApi: String? = null

    val httpHost: String
        get() {
            if (isEmpty) {
                getConnectHostApi()
            }
            LogUtil.getLog().i("AppHostUtil", "getTcpHost：" + connectHostApi!!)
            return "$HTTPS$connectHostApi:$HTTP_PORT"
        }

    val tcpHost: String?
        get() {
            if (isEmpty) {
                getConnectHostApi()
            }
            LogUtil.getLog().i("AppHostUtil", "getTcpHost：" + connectHostApi!!)
            return connectHostApi
        }

    private val isEmpty: Boolean
        get() = TextUtils.isEmpty(connectHostApi) || connectHostApi == "null"

    private fun getConnectHostApi(): String? {
        if (isEmpty) {
            val type = MMKVUtil.mmkv!!.getInt("ipType", 0)
            if (type == 0) {
                when (BuildConfig.BUILD_TYPE) {
                    "debug" -> connectHostApi = BuildConfig.HOST_DEV
                    "pre" -> connectHostApi = BuildConfig.HOST_BETA
                    "release" -> connectHostApi = BuildConfig.HOST_RELEASE
                    else -> connectHostApi = BuildConfig.API_HOST
                }
            } else {
                if (type == 1) {
                    connectHostApi = BuildConfig.HOST_DEV
                } else if (type == 2) {
                    connectHostApi = BuildConfig.HOST_BETA
                } else {
                    connectHostApi = BuildConfig.HOST_RELEASE
                }
            }
        }
        if (isEmpty) {
            throw NullPointerException("请检查config.gradle#host配置")
        }
        LogUtil.getLog().i("AppHostUtil", "主机地址：" + connectHostApi!!)
        return connectHostApi
    }

    //切换服务器
    fun setHostUrl(url: String) {
        connectHostApi = url
        HttpChannel.getInstance().resetHost()
    }

}
