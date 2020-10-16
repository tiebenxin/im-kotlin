package com.abc.core.utils.encrypt

import android.text.TextUtils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and

object MD5 {
    fun md5(string: String): String {
        if (TextUtils.isEmpty(string)) {
            return ""
        }
        var md5: MessageDigest? = null
        try {
            md5 = MessageDigest.getInstance("MD5")
            val bytes = md5!!.digest(string.toByteArray())
            var result = ""
            for (b in bytes) {
                var temp = Integer.toHexString((b and 0xff.toByte()).toInt())
                if (temp.length == 1) {
                    temp = "0$temp"
                }
                result += temp
            }
            return result.toLowerCase()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }

    @Throws(Exception::class)
    fun toByte(sourceString: String): ByteArray {
        val digest = MessageDigest.getInstance("MD5")
        digest.update(sourceString.toByteArray())
        return digest.digest()
    }
}
