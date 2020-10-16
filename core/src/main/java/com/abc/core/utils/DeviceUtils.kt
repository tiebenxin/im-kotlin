package com.abc.core.utils

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import java.io.BufferedReader
import java.io.FileReader
import android.content.Context.TELEPHONY_SERVICE
import androidx.core.app.ActivityCompat
import com.abc.core.constans.AppConfig
import java.lang.Float
import kotlin.math.ceil

/**
 * @author Liszt
 * @date 2019/11/8
 * Description
 */
object DeviceUtils {
    var TAG = DeviceUtils::class.java.simpleName

    var HUA_WEI = "Huawei"
    var XIAO_MI = "Xiaomi"
    var VIVO = "Vivo"
    var OPPO = "Oppo"
    var SAMSUNG = "Samsung"


    /**
     * 手机型号
     */
    val deviceModel: String
        get() = Build.MODEL

    /**
     * 获取设备厂商
     *
     * 如Xiaomi
     *
     * @return 设备厂商
     */

    val manufacturer: String
        get() = Build.MANUFACTURER

    /**
     * 手机品牌
     */
    val brand: String
        get() = Build.BRAND

    val isViVoAndOppo: Boolean
        get() {
            LogUtil.getLog().d("a=", "$TAG--手机品牌名=$brand")
            return brand == VIVO || brand == OPPO
        }

    //获取运行内存大小,GB
    val totalRam: Int
        get() {
            val path = "/proc/meminfo"
            var firstLine: String? = null
            var totalRam = 0
            try {
                val fileReader = FileReader(path)
                val br = BufferedReader(fileReader, 8192)
                firstLine =
                    br.readLine().split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                br.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (firstLine != null) {
                totalRam =
                    ceil((Float.valueOf(firstLine) / (1024 * 1024)).toDouble())
                        .toInt()
            }
            LogUtil.getLog().d("a=", TAG + "--运行内存--" + totalRam + "GB")
            return totalRam
        }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    val systemVersion: String
        get() = Build.VERSION.RELEASE

    //获取设备名称，及手机型号
    val phoneModel: String
        get() = Build.BRAND + " " + Build.MODEL + " " + Build.VERSION.RELEASE

    //获取设备名称
    val deviceName: String
        get() {
            var name = Settings.Global.getString(
                AppConfig.context!!.contentResolver,
                Settings.Global.DEVICE_NAME
            )
            if (TextUtils.isEmpty(name) || name.equals("unknown", ignoreCase = true)) {
                val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                if (mBluetoothAdapter != null) {
                    name = mBluetoothAdapter.name
                }
                if (TextUtils.isEmpty(name) || name.equals("unknown", ignoreCase = true)) {
                    name = Build.MODEL
                }
            }
            return name
        }


    //获取设备ID
    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String? {
        val telephonyMgr = context
            .getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        val id: String
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_PHONE_STATE
                ) !== PackageManager.PERMISSION_GRANTED
            ) {
                return null
            }
            id = if (!TextUtils.isEmpty(telephonyMgr.deviceId)) {
                telephonyMgr.deviceId
            } else {
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            }
            LogUtil.getLog().d("a=", "$TAG--DeviceId:$id")
            return id

        } else {
            assert(telephonyMgr != null)
            if (!TextUtils.isEmpty(telephonyMgr.deviceId)) {
                id = telephonyMgr.deviceId
            } else {
                id = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            }
            LogUtil.getLog().d("a=", "$TAG--DeviceId:$id")

            return id
        }
    }

    /*
     * deviceId, 设备序列号，imei 都具备唯一性
     * */
    @SuppressLint("HardwareIds")
    fun getIMEI(context: Context?): String {
        var imei = ""
        try {
            val tm = context!!.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_PHONE_STATE
                ) !== PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < 29) {
                    val method = tm.javaClass.getMethod("getImei")
                    imei = method.invoke(tm) as String
                    if (TextUtils.isEmpty(imei)) {
                        imei = Settings.Secure.getString(
                            context!!.contentResolver,
                            Settings.Secure.ANDROID_ID
                        )
                    }
                } else if (Build.VERSION.SDK_INT >= 29) {
                    imei = Settings.Secure.getString(
                        context.contentResolver,
                        Settings.Secure.ANDROID_ID
                    )
                }
            } else {
                if (!TextUtils.isEmpty(tm.deviceId)) {
                    imei = tm.deviceId
                } else {
                    imei = Settings.Secure.getString(
                        context.contentResolver,
                        Settings.Secure.ANDROID_ID
                    )
                }
            }
            if (TextUtils.isEmpty(imei) || imei == "unknown") {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < 29) {
                    val method = tm.javaClass.getMethod("getImei")
                    imei = method.invoke(tm) as String
                    if (TextUtils.isEmpty(imei)) {
                        imei = Settings.Secure.getString(
                            context.contentResolver,
                            Settings.Secure.ANDROID_ID
                        )
                    }
                } else if (Build.VERSION.SDK_INT >= 29) {
                    imei = Settings.Secure.getString(
                        context.contentResolver,
                        Settings.Secure.ANDROID_ID
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (TextUtils.isEmpty(imei)) {
            imei = Settings.Secure.getString(context!!.contentResolver, Settings.Secure.ANDROID_ID)
        }
        return imei
    }
}
