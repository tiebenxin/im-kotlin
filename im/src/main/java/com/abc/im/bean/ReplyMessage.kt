package com.abc.im.bean

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * @author Liszt
 * @date 2020/5/7
 * Description 单条回复消息
 */
class ReplyMessage : RealmObject(), IMsgContent {
    @PrimaryKey
    override var msgId: String? = ""
    var quotedMessage: QuotedMessage? = null
    var textMessage: TextMessage? = null
    var atMessage: AtMessage? = null
}
