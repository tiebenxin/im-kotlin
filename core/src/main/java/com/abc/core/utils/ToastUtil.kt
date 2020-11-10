package com.abc.core.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import com.abc.core.R
import com.abc.core.constans.AppConfig


/***
 * @author jyj
 * @date 2016/11/29
 * @备注 TODO 2020/5/28 zjy 全部改为居中显示,效果统一
 */
object ToastUtil {
    private var toast: Toast? = null
    private var isShow = true

    /**
     * 默认显示toast
     *
     * @param context
     * @param txt
     */
    fun show(context: Context, txt: String?) {
        try {
            if (context.resources.getString(R.string.forward_success) == txt) {// 用于处理转发多条，有禁言的时候，只弹出转发成功提示
                isShow = false
                Handler().postDelayed({ isShow = true }, 1000)
            } else {
                isShow = true
            }
        } catch (e: Exception) {

        }

        if (txt != null && txt.length > 0) {
            if (toast != null)
                toast!!.cancel()
            try {
                //  Looper.prepare();
                toast = Toast.makeText(context, txt, Toast.LENGTH_SHORT)
                toast!!.setGravity(Gravity.CENTER, 0, 0)
                //    Looper.loop();
            } catch (e: Exception) {
                e.printStackTrace()
            }

            toast!!.show()
        }
    }

    /**
     * 自定义context的居中显示toast
     *
     * @param context
     * @param txt
     */
    fun showCenter(context: Context, txt: String?) {
        if (isShow) {// 用于处理转发多条，有禁言的时候，只弹出转发成功提示
            if (txt != null && txt.length > 0) {
                if (toast != null)
                    toast!!.cancel()
                try {
                    //  Looper.prepare();
                    toast = Toast.makeText(context, txt, Toast.LENGTH_SHORT)
                    toast!!.setGravity(Gravity.CENTER, 0, 0)
                    //    Looper.loop();
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                toast!!.show()
            }
        } else {
            isShow = true
        }
    }

    /**
     * 自定义context的居中显示toast
     *
     * @param context
     * @param txt
     */
    fun show(context: Context, txt: Int) {
        if (toast != null)
            toast!!.cancel()
        try {
            // Looper.prepare();
            toast = Toast.makeText(context, txt, Toast.LENGTH_SHORT)
            // Looper.loop();
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        }

        toast!!.setGravity(Gravity.CENTER, 0, 0)
        toast!!.show()
    }

    /**
     * 居中的长提示toast
     *
     * @param context
     * @param txt
     */
    fun showLong(context: Context, txt: String?) {
        if (txt != null && txt.length > 0) {
            if (toast != null)
                toast!!.cancel()
            try {
                //  Looper.prepare();
                toast = Toast.makeText(context, txt, Toast.LENGTH_LONG)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            toast!!.setGravity(Gravity.CENTER, 0, 0)
            toast!!.show()
        }

    }

    /**
     * 系统级context的居中toast
     *
     * @param txt
     */
    fun show(txt: String?) {
        if (txt != null && txt.length > 0 && AppConfig.context != null) {
            if (toast != null) {
                toast!!.cancel()
            }
            try {
                toast = Toast.makeText(AppConfig.context, null, Toast.LENGTH_SHORT)
                toast!!.setText(txt)//修复小米会显示项目名问题如: "常信:点赞成功"
                toast!!.setGravity(Gravity.CENTER, 0, 0)
                toast!!.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

    /**
     * 自定义风格toast
     *
     * @param context
     * @param txt
     * @param type    0 默认样式  1 收藏样式
     */
    fun showToast(context: Context, txt: String, type: Int) {
        if (toast != null)
            toast!!.cancel()
        try {
            toast = Toast.makeText(context, txt, Toast.LENGTH_SHORT)
            if (type == 0) {
                val inflate =
                    (context as Activity).layoutInflater.inflate(R.layout.view_custom_toast, null)
                val toast_msg = inflate.findViewById<TextView>(R.id.txt_msg)
                toast_msg.text = txt
                toast!!.view = inflate
            } else {
                val inflate =
                    (context as Activity).layoutInflater.inflate(R.layout.view_collect_toast, null)
                val toast_msg = inflate.findViewById<TextView>(R.id.txt_msg)
                toast_msg.text = txt
                toast!!.view = inflate
            }
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        }

        toast!!.setGravity(Gravity.CENTER, 0, 0)
        toast!!.show()
    }
}
