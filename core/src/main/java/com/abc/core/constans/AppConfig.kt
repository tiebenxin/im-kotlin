package com.abc.core.constans

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.LocaleList
import android.text.TextUtils
import androidx.core.content.ContextCompat
import java.util.*


/***
 * 公共配置
 */
@SuppressLint("StaticFieldLeak")
object AppConfig {
    var context: Context? = null
    var FONT = 1.0f


    /***
     * 获取系统语言zh0
     * @return
     */
    //系统语言
    val language: String
        get() {
            val locale: Locale
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locale = LocaleList.getDefault().get(0)
            } else {
                locale = Locale.getDefault()
            }
            return locale.language
        }

    /***
     * 获取系统国家cn
     * @return
     */
    //系统语言
    private val country: String
        get() {
            val locale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                LocaleList.getDefault().get(0)
            } else {
                Locale.getDefault()
            }
            return locale.country
        }

    val isChina: Boolean
        get() = country == "CN"

    /**
     * 获取渠道名
     *
     * @return 如果没有获取成功，那么返回值为空
     */
    val channelName: String?
        get() {
            if (context == null) {
                return null
            }

            var resultData: String? = ""
            try {
                val packageManager = context!!.packageManager
                if (packageManager != null) {
                    val applicationInfo = packageManager.getApplicationInfo(
                        context!!.packageName,
                        PackageManager.GET_META_DATA
                    )
                    if (applicationInfo != null) {
                        if (applicationInfo.metaData != null) {
                            resultData = applicationInfo.metaData.getString("UMENG_CHANNEL")
                        }
                    }
                }
            } catch (e: PackageManager.NameNotFoundException) {
                return ""
            }

            if (TextUtils.isEmpty(resultData)) {
                resultData = ""
            }
            return resultData
        }

    //注意此处为ApplicationInfo，因为友盟设置的meta-data是在application标签中
    val versionName: String
        get() {
            if (context == null) {
                return ""
            }
            try {
                val packageManager = context!!.packageManager
                if (packageManager != null) {
                    val packageInfo = packageManager.getPackageInfo(context!!.packageName, 0)
                    if (packageInfo != null) {
                        return packageInfo.versionName
                    }
                }
            } catch (e: PackageManager.NameNotFoundException) {

            }

            return ""
        }

    //设置全局字体
    fun setFont(font: Float) {
        FONT = font
    }

    fun getString(id: Int): String {
        return if (context == null) {
            ""
        } else context!!.getString(id)
    }

    fun getColor(rid: Int): Int? {
        return context?.let { ContextCompat.getColor(it, rid) }
    }
}
