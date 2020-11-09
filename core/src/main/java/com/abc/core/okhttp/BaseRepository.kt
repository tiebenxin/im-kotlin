package com.abc.core.okhttp

import com.abc.core.okhttp.data.BaseResponse
import com.abc.core.okhttp.data.NetResult
import com.google.gson.GsonBuilder
import com.win.lib_net.exception.DealException

import com.win.lib_net.exception.ResultException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import okhttp3.MediaType
import okhttp3.RequestBody

open class BaseRepository {

    suspend fun <T : Any> callRequest(
        call: suspend () -> NetResult<T>
    ): NetResult<T> {
        return try {
            call()
        } catch (e: Exception) {
            //这里统一处理异常
            e.printStackTrace()
            NetResult.Error(DealException.handlerException(e))
        }
    }

    suspend fun <T : Any> handleResponse(
        response: BaseResponse<T>,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): NetResult<T> {
        return coroutineScope {
            if (response.code == -1L) {
                errorBlock?.let { it() }
                NetResult.Error(
                    ResultException(
                        response.code.toString(),
                        response.msg
                    )
                )
            } else {
                if (response.code == 0L) {
                    successBlock?.let { it() }
                    NetResult.Success(response.data!!)
                } else {
                    errorBlock?.let { it() }
                    NetResult.Error(
                        ResultException(
                            response.code.toString(),
                            response.msg
                        )
                    )
                }

            }
        }
    }

    fun getRequestBody(map: Map<String, String>): RequestBody {
        return RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            mapToJSON(map)
        )
    }

    /**
     * 将Map转化为Json
     */
    private fun mapToJSON(map: Map<String, String>): String {
        val gson = GsonBuilder().enableComplexMapKeySerialization().create()
        return gson.toJson(map)
    }

}