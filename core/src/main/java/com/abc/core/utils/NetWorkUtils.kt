package com.abc.core.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import com.abc.core.constans.AppConfig


/**
 * @author Liszt
 * @date 2019/11/26
 * Description
 */
object NetWorkUtils {

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络   1：WIFI网络   2：WAP网络    3：NET网络
     */
    val NETTYPE_WIFI = 0x01
    val NETTYPE_CMWAP = 0x02
    val NETTYPE_CMNET = 0x03

    /**
     * (获取网络类型.)
     * <h3>Version</h3>1.0
     * <h3>CreateTime</h3> 2016/7/20,10:00
     * <h3>UpdateTime</h3> 2016/7/20,10:00
     * <h3>CreateAuthor</h3>（Geoff）
     * <h3>UpdateAuthor</h3>
     * <h3>UpdateInfo</h3> (此处输入修改内容,若无修改可不写.)
     *
     * @return
     */
    val networkType: Int
        get() {
            var netType = 0
            val connectivityManager =
                AppConfig.context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo ?: return netType
            val nType = networkInfo.type
            if (nType == ConnectivityManager.TYPE_MOBILE) {
                val extraInfo = networkInfo.extraInfo
                if (extraInfo != null && extraInfo !== "") {
                    if (extraInfo.toLowerCase() == "cmnet") {
                        netType = NETTYPE_CMNET
                    } else {
                        netType = NETTYPE_CMWAP
                    }
                }
            } else if (nType == ConnectivityManager.TYPE_WIFI) {
                netType = NETTYPE_WIFI
            }
            return netType
        }

    /***
     * 网络连接检测
     *
     * @return
     */
    val isNetworkConnected: Boolean
        get() {
            val cm =
                AppConfig.context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = cm.activeNetworkInfo
            return info != null && info.isConnected
        }

    /**
     * 获取当前ip地址
     *
     * @param context
     * @return
     */
    fun getLocalIpAddress(context: Context): String {
        try {
            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo
            val i = wifiInfo.ipAddress
            return int2ip(i)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return ""
    }

    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    fun int2ip(ipInt: Int): String {
        val sb = StringBuilder()
        sb.append(ipInt and 0xFF).append(".")
        sb.append(ipInt shr 8 and 0xFF).append(".")
        sb.append(ipInt shr 16 and 0xFF).append(".")
        sb.append(ipInt shr 24 and 0xFF)
        return sb.toString()
    }


}
