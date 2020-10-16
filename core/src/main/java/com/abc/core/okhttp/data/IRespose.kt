package com.abc.core.okhttp.data

/**
 * 返回数据基类必须实现接口
 * 提供结果判断 和 通用消息返回
 */
interface IRespose {

    val isSuccess: Boolean

    val message: String

    val iStatus: Int
}
