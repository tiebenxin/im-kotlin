package com.abc.im.bean


import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class TextMessage : RealmObject(), IMsgContent {
    @PrimaryKey
    override var msgId: String? = null

    var msg: String? = null // 消息内容

}
