package com.abc.im.bean

import android.text.TextUtils

import com.im.db.utils.StringUtil

import java.io.File

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class ImageMessage : RealmObject(), IMsgContent {
    @PrimaryKey
    private var msgId: String? = null
    private var origin: String? = null //原图
    private var preview: String? = null //预览图
    private var thumbnail: String? = null //缩略图
    var localUrl: String? = null//本地图
    var isReadOrigin = false//是否已经阅读原图
    var width: Long = 0 //图宽
    var height: Long = 0 //图高
    var size: Long = 0//文件大小

    //                LogUtil.getLog().i(ImageMessage.class.getSimpleName(), "本地图片不存在");
    val originShow: String?
        get() {
            var img = origin
            if (StringUtil.isNotNull(origin) && StringUtil.isNotNull(localUrl)) {
                val file = File(localUrl!!)
                if (file.exists()) {
                    img = localUrl
                } else {
                }
            }

            return img
        }

    //                LogUtil.getLog().i(ImageMessage.class.getSimpleName(), "本地图片不存在");
    val previewShow: String?
        get() {
            var img = preview
            if (StringUtil.isNotNull(preview) && StringUtil.isNotNull(localUrl)) {
                val file = File(localUrl!!)
                if (file.exists()) {
                    img = localUrl
                } else {
                }
            }

            return img

        }

    //                LogUtil.getLog().i(ImageMessage.class.getSimpleName(), "本地图片不存在");
    val thumbnailShow: String?
        get() {
            var img = thumbnail
            if (StringUtil.isNotNull(thumbnail) && StringUtil.isNotNull(localUrl)) {
                val file = File(localUrl!!)
                if (file.exists()) {
                    img = localUrl
                } else {
                }
            }

            return img
        }

    fun getOrigin(): String? {
        return if (!TextUtils.isEmpty(origin)) origin else ""
    }

    fun setOrigin(origin: String) {
        this.origin = origin
    }

    fun getPreview(): String? {
        return if (!TextUtils.isEmpty(preview)) preview else ""
    }

    fun setPreview(preview: String) {
        this.preview = preview
    }

    fun getThumbnail(): String? {
        return if (!TextUtils.isEmpty(thumbnail)) thumbnail else ""
    }

    fun setThumbnail(thumbnail: String) {
        this.thumbnail = thumbnail
    }

    override fun getMsgId(): String? {
        return msgId
    }

    fun setMsgId(msgId: String) {
        this.msgId = msgId
    }
}
