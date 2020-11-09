package com.abc.im.bean

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * @创建人 shenxin
 * @创建时间 2019/7/25 0025 10:45
 */
class AtMessage : RealmObject(), IMsgContent {
    @PrimaryKey
    override var msgId: String? = null

    var atType: Int = 0

    var msg: String? = null

    private var uid = RealmList<Long>()

    fun getUid(): List<Long> {
        return uid
    }

    fun setUid(uid: RealmList<Long>) {
        this.uid = uid
    }
}
