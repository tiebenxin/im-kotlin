package com.abc.core.okhttp


import android.text.TextUtils
import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @see https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/src/main/java/okhttp3/logging/HttpLoggingInterceptor.java
 */

class JHttpLoggingInterceptor @JvmOverloads constructor(private val logger: Logger = Logger.DEFAULT) :
    Interceptor {

    @Volatile
    private var level = Level.NONE

    enum class Level {
        /**
         * No logs.
         */
        NONE,
        /**
         * Logs request and response lines.
         *
         *
         *
         * Example:
         * <pre>`--> POST /greeting HTTP/1.1 (3-byte body)
         *
         * <-- HTTP/1.1 200 OK (22ms, 6-byte body)
        `</pre> *
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         *
         *
         *
         * Example:
         * <pre>`--> POST /greeting HTTP/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- HTTP/1.1 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
        `</pre> *
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         *
         *
         *
         * Example:
         * <pre>`--> POST /greeting HTTP/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END GET
         *
         * <-- HTTP/1.1 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
        `</pre> *
         */
        BODY
    }

    interface Logger {
        fun log(isSuccess: Boolean, vararg message: String)

        companion object {

            /**
             * A [Logger] defaults output appropriate for the current platform.
             */
            val DEFAULT: Logger = object : Logger {
                override fun log(isSuccess: Boolean, vararg message: String) {
                    // Platform.get().log(message);
                    /**
                     * 使用自定义Log 输出
                     */
                    if (isSuccess) {
                        //                    DLog.i(message);
                        return
                    }

                    /**
                     * 失败的请求不需要输出，由上层输出
                     */
                    //                DLog.e(message);
                }
            }
        }
    }

    /**
     * Change the level at which this interceptor logs.
     */
    fun setLevel(level: Level?): JHttpLoggingInterceptor {
        if (level == null) throw NullPointerException("level == null. Use Level.NONE instead.")
        this.level = level
        return this
    }

    fun getLevel(): Level {
        return level
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val startNs = System.nanoTime()
        val response = chain.proceed(request)
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        logger.log(response.isSuccessful, *getHttpLog(request, response, tookMs))

        return response
    }

    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers.get("Content-Encoding")
        return contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true)
    }

    companion object {

        private val UTF8 = Charset.forName("UTF-8")

        @Throws(IOException::class)
        fun getHttpLog(request: Request, response: Response, tookMs: Long): Array<String> {

            val charset = UTF8

            /**
             * header
             */
            val headersStringBuilder = StringBuilder()
            var headers = request.headers()
            run {
                var i = 0
                val count = headers.size()
                while (i < count) {
                    if (i != 0) {
                        headersStringBuilder.append("\n")
                    }
                    headersStringBuilder.append(headers.name(i) + ": " + headers.value(i))
                    i++
                }
            }
            headers = response.headers()
            var i = 0
            val count = headers.size()
            while (i < count) {
                if (!TextUtils.isEmpty(headersStringBuilder.toString())) {
                    headersStringBuilder.append("\n")
                }
                headersStringBuilder.append(headers.name(i) + ": " + headers.value(i))
                i++
            }


            /**
             * request body
             */
            val bufferRequest = Buffer()
            if (request.body() != null) {
                request.body()!!.writeTo(bufferRequest)

            }
            val requestBody = bufferRequest.clone().readString(charset)

            /**
             * response body
             */
            val responseBody = response.body()
            val contentLength = responseBody!!.contentLength()
            var buffer: Buffer? = null
            try {
                val source = responseBody.source()
                source.request(java.lang.Long.MAX_VALUE)
                buffer = source.buffer()
            } catch (e: Exception) {
            }

            val stringList = ArrayList<String>()
            stringList.add(response.code().toString() + " --> (" + tookMs + "ms" + (if (buffer != null) "," + buffer.size() + "byte" else "") + ')'.toString())
            if (!TextUtils.isEmpty(response.message())) {
                stringList.add(response.message())
            }
            stringList.add("" + response.request().url())
            if (!TextUtils.isEmpty(headersStringBuilder)) {
                stringList.add(headersStringBuilder.toString())
            }
            if (!TextUtils.isEmpty(requestBody)) {
                stringList.add(requestBody)
            }
            if (buffer != null) {
                stringList.add(buffer.clone().readString(charset))
            }

            return stringList.toTypedArray()
        }

        private fun protocol(protocol: Protocol): String {
            return if (protocol == Protocol.HTTP_1_0) "HTTP/1.0" else "HTTP/1.1"
        }
    }
}