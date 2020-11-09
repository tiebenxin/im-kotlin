package com.abc.im.bean


import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/***
 * 类型为0的
 * 通知消息
 */
class MsgNotice : RealmObject(), IMsgContent {

    @PrimaryKey
    override var msgId: String? = null
        set(msgId) {
            field = this.msgId
        }
    var uid: Long = 0
    var note: String? = null
    var msgType = MSG_TYPE_DEFAULT
        private set
    var remark: String? = null//申请入群备注
    var joinGroupType: Int = 0//申请入群方式  0 扫码 1 正常邀请
    var ids: RealmList<String>? = null//申请入群aid，方便查询入群申请记录

    //7,8,17为红包消息类型, 通知消息类型
    fun setMsgType(msgType: Int?) {
        this.msgType = msgType!!
    }

    companion object {
        val MSG_TYPE_DEFAULT = 7897
    }
}
