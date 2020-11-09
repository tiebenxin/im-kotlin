package com.abc.im.bean

import android.text.TextUtils

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class VideoMessage : RealmObject(), IMsgContent {
    @PrimaryKey
    override var msgId: String? = null
    var duration: Long = 0
    private var bgUrl: String? = null
    var width: Long = 0
    var height: Long = 0
    private var url: String? = null
    var isReadOrigin = false
    private var localUrl: String? = null


    fun getLocalUrl(): String? {
        return if (!TextUtils.isEmpty(localUrl)) localUrl else ""
    }

    fun setLocalUrl(localUrl: String) {
        this.localUrl = localUrl
    }

    override fun toString(): String {
        return "VideoMessage{" +
                "msgId='" + msgId + '\''.toString() +
                ", duration=" + duration +
                ", bgUrl='" + bgUrl + '\''.toString() +
                ", width=" + width +
                ", height=" + height +
                ", url='" + url + '\''.toString() +
                ", isReadOrigin=" + isReadOrigin +
                ", localUrl='" + localUrl + '\''.toString() +
                '}'.toString()
    }

    fun getBgUrl(): String? {
        return if (!TextUtils.isEmpty(bgUrl)) bgUrl else ""
    }

    fun setBgUrl(bgUrl: String) {
        this.bgUrl = bgUrl
    }

    fun getUrl(): String? {
        return if (!TextUtils.isEmpty(url)) url else ""
    }

    fun setUrl(url: String) {
        this.url = url
    }
}
