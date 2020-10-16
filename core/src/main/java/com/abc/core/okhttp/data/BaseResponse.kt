package com.abc.core.okhttp.data

import com.google.gson.annotations.SerializedName


/***
 * @author jyj
 * @date 2016/12/20
 */
class BaseResponse<T>(
    override val isSuccess: Boolean,
    override val message: String,
    override val iStatus: Int
) : IRespose {
    @SerializedName("errCode")
    var code: Long? = null
    @SerializedName("errMsg")
    lateinit var msg: String

    var data: T? = null

    val isOk: Boolean?
        get() = code!!.toLong() == 0L
}
