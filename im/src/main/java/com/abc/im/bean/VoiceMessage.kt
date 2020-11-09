package com.abc.im.bean

import android.text.TextUtils

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class VoiceMessage : RealmObject(), IMsgContent {
    @PrimaryKey
    override var msgId: String? = null
    var url: String? = null
        get() = if (TextUtils.isEmpty(field)) "" else field//上传成功后网络路径
    var time: Int = 0
    var playStatus: Int = 0
    var localUrl: String? = null
        get() = if (TextUtils.isEmpty(field)) "" else field//本地路径
}
