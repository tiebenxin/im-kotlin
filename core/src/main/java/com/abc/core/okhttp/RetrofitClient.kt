package com.abc.core.okhttp


import com.abc.core.BuildConfig
import com.abc.core.constans.AppConfig
import com.abc.core.utils.AppHostUtil
import com.abc.core.utils.NetWorkUtils
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * 当前项目使用retrofit 作为http请求引擎
 */

class RetrofitClient private constructor() {

//    private var retrofit: Retrofit

    companion object {
        val instance: RetrofitClient by lazy { RetrofitClient() }
    }


//    init {
//        retrofit = Retrofit.Builder()
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(AppHostUtil.httpHost)
//            .build()
//    }

    /**
     * 使用自定义logInterceptor 支持 chrome输出，在debug 或 beta版本
     */
    /**
     * 本地缓存
     */
    /**
     * 请求缓存策略
     */
    /**
     * ssl
     */
    /**
     * ok give me five
     */
    val client: OkHttpClient
        get() {
            val logInterceptor = JHttpLoggingInterceptor()
            logInterceptor.setLevel(JHttpLoggingInterceptor.Level.BODY)
            val cacheFile = File(AppConfig.context!!.cacheDir, "cache")
            val cache = Cache(cacheFile, (1024 * 1024 * 100).toLong())
            val mRewriteCacheControlInterceptor = interceptor

            val okHttpClient = OkHttpClient.Builder()
            //支持HTTPS请求，跳过证书验证
            okHttpClient.readTimeout(READ_TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
                .addInterceptor(mRewriteCacheControlInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .sslSocketFactory(createSSLSocketFactory()!!)
                .cache(cache)

            if (BuildConfig.DEBUG) {
                okHttpClient.addInterceptor(logInterceptor)
            }

            return okHttpClient.build()
        }

    //读超时长，单位：毫秒
    val READ_TIME_OUT = 7676L
    //连接时长，单位：毫秒
    val CONNECT_TIME_OUT = 7676L
    //写入时长，单位：毫秒
    val WRITE_TIME_OUT = 7676L

    /*************************缓存设置 */
    /*
    1. noCache 不使用缓存，全部走网络

    2. noStore 不使用缓存，也不存储缓存

    3. onlyIfCached 只使用缓存

    4. maxAge 设置最大失效时间，失效则不使用 需要服务器配合

    5. maxStale 设置最大失效时间，失效则不使用 需要服务器配合

    6. minFresh 设置有效时间，依旧如上

    7. FORCE_NETWORK 只走网络

    8. FORCE_CACHE 只走缓存
    */

    /**
     * 设缓存有效期为两天
     */
    private val CACHE_STALE_SEC = (60 * 60 * 24 * 2).toLong()
    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
    private val CACHE_CONTROL_CACHE = "only-if-cached, max-stale=$CACHE_STALE_SEC"
    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private val CACHE_CONTROL_AGE = "max-age=0"

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
    private val interceptor: Interceptor
        get() = Interceptor { chain ->
            var request = chain.request()
            if (!NetWorkUtils.isNetworkConnected) {
                request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
            }
            val originalResponse = chain.proceed(request)
            if (NetWorkUtils.isNetworkConnected) {
                val cacheControl = request.cacheControl().toString()
                originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build()
            } else {
                originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$CACHE_STALE_SEC")
                    .removeHeader("Pragma")
                    .build()
            }
        }

    fun <T> createApi(service: Class<T>, baseUrl: String): T? {
        val builder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
        return builder.create(service)
    }

    /**
     * 生成安全套接字工厂，用于https请求的证书跳过
     */
    private fun createSSLSocketFactory(): SSLSocketFactory? {
        var ssfFactory: SSLSocketFactory? = null
        try {
            val sc = SSLContext.getInstance("TLS")
            sc.init(null, arrayOf<TrustManager>(TrustAllCerts()), SecureRandom())
            ssfFactory = sc.socketFactory
        } catch (e: Exception) {
        }

        return ssfFactory
    }

    /**
     * 用于信任所有证书
     */
    internal class TrustAllCerts : X509TrustManager {

        @Throws(CertificateException::class)
        override fun checkClientTrusted(x509Certificates: Array<X509Certificate>, s: String) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(x509Certificates: Array<X509Certificate>, s: String) {

        }

        override fun getAcceptedIssuers(): Array<X509Certificate?> {
            return arrayOfNulls(0)
        }
    }
}
