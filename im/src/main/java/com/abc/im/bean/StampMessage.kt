package com.abc.im.bean


import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class StampMessage : RealmObject(), IMsgContent {
    @PrimaryKey
    override var msgId: String? = null
        private set(value: String?) {
            super.msgId = value
        }
    var comment: String? = null

    fun setMsgid(msgid: String) {
        this.msgId = msgid
    }
}
