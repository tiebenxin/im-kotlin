package com.abc.core.utils

import android.text.TextUtils
import android.util.Log
import com.abc.core.constans.FileConfig
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class LogUtil {

    fun init(open: Boolean) {
        isOpen = open
        if (isOpen) {
            Log.i("Log", "=================调试日志:开启================")
        }
        createDir(FileConfig.PATH_LOG)
    }

    private fun sp(TAG: String?, msg: String, state: Int) {
        var TAG = TAG
        if (!isOpen || TextUtils.isEmpty(msg))
            return
        if (TAG == null || TAG.length < 1) {
            TAG = "log"
        }
        val strLength = msg.length
        var start = 0
        var end = LOG_MAXLENGTH
        for (i in 0..99) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                p(TAG + i, msg.substring(start, end), state)

                start = end
                end = end + LOG_MAXLENGTH
            } else {

                p(TAG, msg.substring(start, strLength), state)
                break
            }
        }
    }

    private fun p(TAG: String, msg: String, state: Int) {
        var TAG = TAG
        TAG = "a===$TAG"
        when (state) {
            0 -> Log.i(TAG, msg)
            1 -> Log.d(TAG, msg)
            2 -> Log.e(TAG, msg)
        }

    }

    fun e(tag: String, msg: String) {
        sp(tag, msg, 2)
    }

    fun e(msg: String) {
        sp("=", msg, 2)
    }

    fun d(tag: String, msg: String) {
        sp(tag, msg, 1)
    }

    fun i(tag: String, msg: String) {
        sp(tag, msg, 0)
    }

    companion object {
        private var log: LogUtil? = null
        private var isOpen = true
        private val LOG_MAXLENGTH = 2000

        fun getLog(): LogUtil {
            log = if (log == null) LogUtil() else log
            return log!!
        }

        /**
         * 写入错误日志
         *
         * @param ex
         */
        fun writeError(ex: Throwable?) {
            if (ex == null || ex.stackTrace == null) {
                return
            }
            val job = GlobalScope.launch {
                try {
                    val dayFormat = SimpleDateFormat("yyyy_MM_dd")
                    val momentFormat = SimpleDateFormat("HH:mm:ss")
                    val curDate = Date(System.currentTimeMillis())// 获取当前时间
                    val day = dayFormat.format(curDate)
                    val moment = momentFormat.format(curDate)
                    val timeDivider = StringBuffer()
                    val overDivider = StringBuffer()
                    for (i in 0..19) {
                        timeDivider.append("-")
                        overDivider.append("=")
                    }
                    val sb = StringBuffer()
                    val element = ex!!.stackTrace
                    sb.append(moment + "\n")
                    sb.append(timeDivider.toString() + "\n")
                    sb.append(ex.message + "\n")
                    for (i in element.indices) {
                        sb.append(element[i].toString() + "\n")
                    }
                    sb.append(overDivider.toString() + "\n")
                    val file = File(FileConfig.PATH_LOG + "log" + day + ".txt")
                    if (!file.exists()) {
                        file.createNewFile()
                    }
                    val ops = FileOutputStream(file, true)
                    ops.write(sb.toString().toByteArray())
                    ops.flush()
                    ops.close()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        /**
         * 写入日志
         *
         * @param value
         */
        @Synchronized
        fun writeLog(value: String) {
            val job = GlobalScope.launch {
                try {
                    val dayFormat = SimpleDateFormat("yyyy_MM_dd")
                    val momentFormat = SimpleDateFormat("HH:mm:ss")
                    val curDate = Date(System.currentTimeMillis())// 获取当前时间
                    val day = dayFormat.format(curDate)
                    val moment = momentFormat.format(curDate)
                    val sb = StringBuffer()
                    sb.append("$moment  $value\n")
                    val file = File(FileConfig.PATH_LOG + "log" + day + ".txt")
                    if (!file.exists()) {
                        file.createNewFile()
                    }
                    val ops = FileOutputStream(file, true)
                    ops.write(sb.toString().toByteArray())
                    ops.flush()
                    ops.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }


        fun createDir(dirPath: String): String? {
            //因为文件夹可能有多层，比如:  a/b/c/ff.txt  需要先创建a文件夹，然后b文件夹然后...
            try {
                val file = File(dirPath)
                if (file.parentFile!!.exists()) {
                    file.mkdir()
                    return file.absolutePath
                } else {
                    createDir(file.parentFile!!.absolutePath)
                    file.mkdir()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return dirPath
        }
    }

}
