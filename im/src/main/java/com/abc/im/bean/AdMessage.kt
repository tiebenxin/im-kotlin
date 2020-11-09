package com.abc.im.bean

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * @author Liszt
 * @date 2020/6/8
 * Description 小助手广告消息
 */
class AdMessage : RealmObject(), IMsgContent {
    @PrimaryKey
    override var msgId: String? = null
    var title: String? = null
    var summary: String? = null
    var thumbnail: String? = null
    var buttonTxt: String? = null
    var appId: String? = null
    var schemeUrl: String? = null//第三方应用唤起页面
    var webUrl: String? = null
}
