package com.abc.im.bean

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * @version V1.0
 * @createAuthor （Geoff）
 * @createDate 2020-01-11
 * @updateAuthor
 * @updateDate
 * @description
 * @copyright copyright(c)2020 ChangSha hm Technology Co., Ltd. Inc. All rights reserved.
 */
class ShippedExpressionMessage : RealmObject(), IMsgContent {

    @PrimaryKey
    override var msgId: String? = null
        private set(value: String?) {
            super.msgId = value
        }

    var id: String? = null

    fun setMsgid(msgid: String) {
        this.msgId = msgid
    }
}
