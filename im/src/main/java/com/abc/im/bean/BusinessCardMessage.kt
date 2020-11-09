package com.abc.im.bean


import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class BusinessCardMessage : RealmObject(), IMsgContent {
    @PrimaryKey
    override var msgId: String? = null
    var avatar: String? = null // 头像地址
    var nickname: String? = null // 昵称
    var comment: String? = null // 备注
    var uid: Long = 0
}
