package com.abc.im.bean

import com.abc.im.Constants


import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

/***
 * 会话
 */
class Session : RealmObject(), Comparable<Session> {
    @PrimaryKey
    var sid: String? = null
    //会话类型,0:单人,1群
    var type = 0
    var from_uid: Long = 0
        private set
    var gid: String? = null
    var up_time = 0L
        private set//更新时间
    var unread_count = 0
    //草稿
    var draft: String? = null
    //是否置顶
    var isTop = 0
    //是否静音，是，免打扰
    var isMute = 0
    // 1000普通消息  0.@我单个人 1.@所有人 2.草稿 3 红包发送失败 4、申请进群通知
    var messageType = 1000

    var atMessage: String? = null

    @Ignore
    private var isSelect = false//本地字段

    var markRead = 0//标记已读0，未读1

    var select: Boolean?
        get() = isSelect
        set(select) {
            isSelect = select!!
        }

    //判断该用户是否官方系统用户
    val isSystemUser: Boolean
        get() {
            if (type != 0 || from_uid <= 0) {
                return false
            }
            return from_uid == Constants.CX888_UID || from_uid == Constants.CX999_UID || from_uid == Constants.CX_HELPER_UID || from_uid == Constants.CX_BALANCE_UID
        }

    fun setFrom_uid(from_uid: Long?) {
        this.from_uid = from_uid!!
    }

    fun setUp_time(up_time: Long?) {
        this.up_time = up_time!!
    }


    fun setName(name: String) {

    }


    fun setMessage(message: MsgAllBean) {

    }


    fun setAvatar(avatar: String) {}


    fun setSenderName(senderName: String) {}


    override fun equals(obj: Any?): Boolean {
        if (obj == null) {
            return false
        }
        return if (obj is Session) {
            if (obj.type == 1) {
                obj.gid == this.gid
            } else {
                if (obj.from_uid > 0 && from_uid > 0) {
                    obj.from_uid == this.from_uid
                } else {
                    false
                }
            }
        } else false
    }

    override fun compareTo(o: Session): Int {
        if (o != null && this != null) {
            //降序
            if (o.up_time > this.up_time) {
                return 1
            } else if (o.up_time < this.up_time) {
                return -1
            }
            return 0
        } else return if (o == null || o.up_time <= 0) {
            -1
        } else if (this == null || this.up_time <= 0) {
            1
        } else {
            0
        }
    }
}
