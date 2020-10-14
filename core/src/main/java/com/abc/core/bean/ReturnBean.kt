package com.abc.core.bean

import com.abc.core.base.BaseBean
import com.google.gson.annotations.SerializedName


/***
 * @author jyj
 * @date 2016/12/20
 */
class ReturnBean<T> : BaseBean() {
    @SerializedName("errCode")
    var code: Long? = null
    @SerializedName("errMsg")
    lateinit var msg: String
    var data: T? = null

    val isOk: Boolean?
        get() = code!!.toLong() == 0L
}
