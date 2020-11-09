package com.im.db.bean

import android.text.TextUtils
import com.abc.core.utils.GsonUtils
import com.google.gson.JsonArray
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import org.json.JSONArray
import org.json.JSONException
import java.util.*

/**
 * @author Liszt
 * @date 2019/8/6
 * Description 小助手消息
 */
class AssistantMessage : RealmObject(), IMsgContent {
    @PrimaryKey
    private var msgId: String? = null
    var msg: String? = null// 为老版本兼容
    var version: Int = 0// 默认是老版本 1表示新版本
    var title: String? = null// 标题
    var time: Long = 0// 时间
    var content: String? = null// 内容
    var signature: String? = null// 落款
    var signature_time: Long = 0// 落款时间
    @Ignore
    var dispatch_type: Int = 0// 0:分发给多端|1:分发给手机端
    @Ignore
    var uid_list: List<Long>? = null// 接收人列表，未指定则发送给所有人
    private var items: String? = null
    @Ignore
    internal var labelItems: MutableList<LabelItem>? = null// 子项列表

    override fun getMsgId(): String? {
        return msgId
    }

    fun setMsgId(msgId: String) {
        this.msgId = msgId
    }

    fun getLabelItems(): List<LabelItem>? {
        if (labelItems == null && !TextUtils.isEmpty(items)) {
            try {
                labelItems = ArrayList<LabelItem>()
                val array = JSONArray(items)
                if (array != null && array.length() > 0) {
                    for (i in 0 until array.length()) {
                        val item = GsonUtils.getObject(array.getString(i), LabelItem::class.java)
                        if (item != null) {
                            labelItems!!.add(item)
                        }
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
        return labelItems
    }

    fun setLabelItems(labelItems: MutableList<LabelItem>) {
        this.labelItems = labelItems
    }

    fun getItems(): String? {
        if (TextUtils.isEmpty(items) && labelItems != null) {
            val jsonArray = JsonArray()
            for (i in labelItems!!.indices) {
                val item = labelItems!![i]
                val s = GsonUtils.optObject(item)
                if (!TextUtils.isEmpty(s)) {
                    jsonArray.add(s)
                }
            }
            items = jsonArray.toString()
        }
        return items
    }

    fun setItems(items: String) {
        this.items = items
    }
}
