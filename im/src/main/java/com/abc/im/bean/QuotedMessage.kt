package com.abc.im.bean

import com.abc.im.ChatEnum

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * @author Liszt
 * @date 2020/5/7
 * Description 被回复引用的消息内容
 */
class QuotedMessage : RealmObject(), IMsgContent {
    @PrimaryKey
    override var msgId: String? = ""
    var timestamp: Long = 0
    @ChatEnum.EMessageType
    var msgType: Int = 0
    var fromUid: Long = 0//发送者id
    var nickName: String? = ""//发送者昵称
    var avatar: String? = ""//发送者头像
    var url: String? = ""//视频或者图片缩略图
    var msg: String? = ""//文字内容
}
