package com.abc.im.bean

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

class MsgCancel : RealmObject(), IMsgContent {
    @PrimaryKey
    override var msgId: String? = null
        private set(value: String?) {
            super.msgId = value
        }
    var uid: Long? = null//被撤回人的uid(原来就有这个字段，证实一直没使用)
    var note: String? = null
    var msgidCancel: String? = null//被撤回消息的id
    var cancelContent: String? = null// 撤回内容
    var cancelContentType: Int? = null// 撤回内容类型
    var msgType: Int? = MSG_TYPE_DEFAULT
    var role: Int = 0//1群主 2群管理
    var alterantive_name: String? = null// 被撤回人的昵称

    @Ignore
    var time: Long = 0//源撤销消息时间

    fun setMsgid(msgid: String) {
        this.msgId = msgid
    }

    companion object {
        val MSG_TYPE_DEFAULT = 7897
    }
}
