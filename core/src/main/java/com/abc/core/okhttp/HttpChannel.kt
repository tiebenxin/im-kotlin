package com.abc.core.okhttp

import com.abc.core.BuildConfig
import com.abc.core.base.BaseApi
import com.abc.core.utils.AppHostUtil
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Liszt
 * @date 2019/11/28
 * Description http
 */
class HttpChannel private constructor() {
    private var okHttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null
    var mApi: BaseApi? = null
        private set

    init {
        compositeDisposable = CompositeDisposable()
        if (okHttpClient == null) {
            okHttpClient = getOkHttpClient()
        }
        // 初始化Retrofit
        retrofit = createRetrofit()
        mApi = retrofit!!.create<BaseApi>(BaseApi::class.java!!)
    }

    fun resetHost() {
        if (okHttpClient != null) {
            retrofit = Retrofit.Builder()
                .baseUrl(AppHostUtil.httpHost)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .client(okHttpClient!!) // 打印请求参数
                .build()
            mApi = retrofit!!.create<BaseApi>(BaseApi::class.java!!)
        }
    }

    private fun createRetrofit(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .client(okHttpClient!!) // 打印请求参数
                .build()
        }
        return retrofit
    }

    private fun getOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        //        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        // 定制OkHttp
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
        builder.readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
        builder.writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
        //支持HTTPS请求，跳过证书验证
        builder.sslSocketFactory(createSSLSocketFactory()!!)
        builder.hostnameVerifier { hostname, session -> true }
        builder.addInterceptor(httpLoggingInterceptor)
        val commonInterceptor = CommonInterceptor()
        builder.addInterceptor(commonInterceptor)
        if (BuildConfig.DEBUG) {
            //            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        return builder.build()
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
    internal inner class TrustAllCerts : X509TrustManager {

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

    companion object {
        private val DEFAULT_TIME_OUT: Long = 12
        private lateinit var compositeDisposable: CompositeDisposable
        private var instance: HttpChannel? = null

        fun getInstance(): HttpChannel {
            if (instance == null) {
                instance = HttpChannel()
            }
            return instance as HttpChannel
        }
    }


}
