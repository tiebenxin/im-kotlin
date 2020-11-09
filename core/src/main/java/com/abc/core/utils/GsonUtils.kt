package com.abc.core.utils

import com.google.gson.Gson

/**
 * @author Liszt
 * @date 2019/8/12
 * Description bean 转 json ，json转bean
 */
object GsonUtils {

    fun <T : Any> getObject(json: String?, clazz: Class<T>): T? {
        if (json == null) {
            return null
        }
        try {
            val gson = Gson()
            return gson.fromJson(json, clazz)
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }


    }


    fun <T : Any> optObject(t: T?): String? {
        if (t == null) {
            return null
        }
        try {
            val gson = Gson()
            return gson.toJson(t)
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }

    }

}
