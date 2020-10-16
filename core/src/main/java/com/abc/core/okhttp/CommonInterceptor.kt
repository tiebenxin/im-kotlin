package com.abc.core.okhttp

import android.content.Context
import android.os.Build
import android.text.TextUtils
import com.abc.core.constans.AppConfig
import com.abc.core.bean.TokenBean
import com.abc.core.utils.MMKVUtil
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 *@author Liszt
 *@date 2020/10/12
 *Description
 */
class CommonInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (isWifiProxy(AppConfig.context)) {
            chain.call().cancel()
        }
        val oldRequest: Request = chain.request()
        /**
         * 公共参数
         */
        val authorizedUrlBuilder = oldRequest.url()
            .newBuilder()
            .scheme(oldRequest.url().scheme())
            .host(oldRequest.url().host())

        /**
         * 新的请求
         */
        val requestBuilder = oldRequest.newBuilder()
            .method(oldRequest.method(), oldRequest.body())
            .url(authorizedUrlBuilder.build())
        requestBuilder.header("Content-Type", "application/json;charset=utf-8")

        /**
         * 已经登陆的用户 带上X-Access-Token
         */
        var bean = TokenBean()
        bean = MMKVUtil.mmkv!!.decodeParcelable("token", bean.javaClass)
        if (!TextUtils.isEmpty(bean.accessToken)) {
            requestBuilder.header("X-Access-Token", bean.accessToken)
        }
        requestBuilder.header("cli-platform", "Android")
        requestBuilder.header("cli-version", AppConfig.versionName)
        return chain.proceed(requestBuilder.build())
    }

    private fun isWifiProxy(context: Context?): Boolean {
        val isIcsOrLater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
        val proxyAddress: String
        val proxyPort: Int
        if (isIcsOrLater) {
            proxyAddress = System.getProperty("http.proxyHost").toString()
            val portStr = System.getProperty("http.proxyPort")
            proxyPort = Integer.parseInt(portStr ?: "-1")
        } else {
            proxyAddress = android.net.Proxy.getHost(context)
            proxyPort = android.net.Proxy.getPort(context)
        }
        return !TextUtils.isEmpty(proxyAddress) && proxyPort != -1
    }

}


