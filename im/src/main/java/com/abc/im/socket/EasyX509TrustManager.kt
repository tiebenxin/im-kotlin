package com.abc.im.socket

import android.text.TextUtils
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager
import javax.security.cert.CertificateExpiredException
import javax.security.cert.CertificateNotYetValidException

class EasyX509TrustManager @Throws(NoSuchAlgorithmException::class, KeyStoreException::class)
constructor(keystore: KeyStore?) : X509TrustManager {
    private var standardTrustManager: X509TrustManager? = null

    init {
        try {
            val factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            factory.init(keystore)
            val trustmanagers = factory.trustManagers
            if (trustmanagers.size == 0) {
                throw NoSuchAlgorithmException(
                    "SunX509 trust manager not supported"
                )
            }
            this.standardTrustManager = trustmanagers[0] as X509TrustManager
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @Throws(CertificateException::class)
    override fun checkClientTrusted(
        certificates: Array<X509Certificate>,
        authType: String
    ) {
        this.standardTrustManager!!.checkClientTrusted(certificates, authType)
    }

    @Throws(CertificateException::class)
    override fun checkServerTrusted(
        certificates: Array<X509Certificate>?,
        authType: String
    ) {
        try {
            if (certificates != null && certificates.size == 1) {
                val certificate = certificates[0]
                certificate.checkValidity()
            } else {
                this.standardTrustManager!!.checkServerTrusted(certificates, authType)
            }
        } catch (e: Exception) {
            var cause = ""
            if (e.cause != null) {
                cause = e.cause.toString()
            }
            //证书过期，不管
            while (e != null) {
                if (e is CertificateExpiredException || e is CertificateNotYetValidException) {
                    return
                } else if (!TextUtils.isEmpty(cause) && cause.contains("ExtCertPathValidatorException")) {
                    return
                }
            }
            throw e
        }

    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        return this.standardTrustManager!!.acceptedIssuers
    }
}
