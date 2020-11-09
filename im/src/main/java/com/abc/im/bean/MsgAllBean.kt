package com.abc.im.bean


import android.text.TextUtils

import com.abc.core.utils.StringUtil
import com.abc.im.ChatEnum
import com.im.db.bean.AssistantMessage

import java.util.Arrays

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class MsgAllBean : RealmObject(), IChatModel {
    @PrimaryKey
    var msgId: String? = null
    var timeStamp: Long = 0
    //0:正常,1:错误,2:发送中
    /***
     * //0:正常,1:错误,2:发送中
     * @param sendState
     */
    @ChatEnum.ESendStatus
    @get:ChatEnum.ESendStatus
    var sendState = 0
    //重发的数据对象
    var sendData: ByteArray? = null
    //自己是否已读
    private var isRead = false
    private var requestId: String? = null
    private var fromUid: Long = 0
    private var fromNickname: String? = null
    private var fromAvatar: String? = null
    private var fromGroupNickname: String? = null
    private var toUid: Long = 0
    private var gid: String? = null
    //对方已读
    private var read = 0 //0 未读  ， 1  已读

    @ChatEnum.EMessageType
    private var msgType: Int? = null

    private var survivalTime: Int = 0
    //阅后即焚结束时间
    private var endTime: Long = 0
    private var readTime: Long = 0 //已读时间
    private var startTime: Long = 0
    private var serverTime: Long = 0 //服务器时间
    private var isLocal: Int = 0//是否是本地创建消息：0，不是本地，1 是本地消息
    private var isReplying: Int = 0//是否是正在回复的消息，0，未被回复或已经回复过了， 1正在回复

    private var textMessage: TextMessage? = null
    private var image: ImageMessage? = null
    private var videoMessage: VideoMessage? = null
    private var stamp: StampMessage? = null
    private var businessCard: BusinessCardMessage? = null
    private var msgNotice: MsgNotice? = null
    private var msgCancel: MsgCancel? = null
    private var voiceMessage: VoiceMessage? = null
    private var atMessage: AtMessage? = null
    private var assistantMessage: AssistantMessage? = null
    private var shippedExpressionMessage: ShippedExpressionMessage? = null
    private var fileMessage: FileMessage? = null
    private var replyMessage: ReplyMessage? = null//回复消息
    private var adMessage: AdMessage? = null//小助手广告消息

    /***
     * 把类型转换为消息
     * @return
     */
    val msgTypeStr: String?
        get() {
            var str: String? = ""
            try {
                if (msgType == ChatEnum.EMessageType.NOTICE) {
                    if (msgNotice != null) {
                        str = "" + StringUtil.delHTMLTag(msgNotice!!.note!!)
                    }
                } else if (msgType == ChatEnum.EMessageType.TEXT) {
                    str = textMessage!!.msg
                } else if (msgType == ChatEnum.EMessageType.STAMP) {
                    str = "[戳一下]" + stamp!!.comment!!
                } else if (msgType == ChatEnum.EMessageType.IMAGE) {
                    str = "[图片]"
                } else if (msgType == ChatEnum.EMessageType.BUSINESS_CARD) {
                    str = "[名片]"
                } else if (msgType == ChatEnum.EMessageType.VOICE) {
                    str = "[语音]"
                } else if (msgType == ChatEnum.EMessageType.AT) {
                    str = atMessage!!.msg
                } else if (msgType == ChatEnum.EMessageType.ASSISTANT || msgType == ChatEnum.EMessageType.ASSISTANT_NEW) {
                    if (assistantMessage != null && !TextUtils.isEmpty(assistantMessage!!.title)) {
                        str = assistantMessage!!.title
                    } else {
                        str = "常信通知"
                    }
                } else if (msgType == ChatEnum.EMessageType.MSG_CANCEL) {
                    str = "" + StringUtil.delHTMLTag(msgCancel!!.note!!)
                } else if (msgType == ChatEnum.EMessageType.MSG_VIDEO) {
                    str = "[视频]"
                } else if (msgType == ChatEnum.EMessageType.CHANGE_SURVIVAL_TIME) {
                    str = msgCancel!!.note
                } else if (msgType == ChatEnum.EMessageType.LOCATION) {
                    str = "[位置]"
                } else if (msgType == ChatEnum.EMessageType.FILE) {
                    if (fileMessage != null) {
                        if (!TextUtils.isEmpty(fileMessage!!.fileName)) {
                            str = "[文件]" + fileMessage!!.fileName
                        } else {
                            str = "[文件]"
                        }
                    }
                } else if (msgType == ChatEnum.EMessageType.SHIPPED_EXPRESSION) {
                    str = "[动态表情]"
                } else if (msgType == ChatEnum.EMessageType.TRANSFER_NOTICE) {
                    str = "你有一笔等待收款的转账"
                } else if (msgType == ChatEnum.EMessageType.REPLY) {
                    val reply = replyMessage
                    var content: String? = ""
                    if (reply!!.atMessage != null) {
                        content = reply.atMessage!!.msg
                    } else if (reply.textMessage != null) {
                        content = reply.textMessage!!.msg
                    }
                    str = content
                } else if (msgType == ChatEnum.EMessageType.ASSISTANT_PROMOTION) {
                    val adMessage = adMessage
                    var content: String? = ""
                    if (adMessage != null) {
                        if (!TextUtils.isEmpty(adMessage.summary)) {
                            content = adMessage.summary
                        } else if (!TextUtils.isEmpty(adMessage.title)) {
                            content = adMessage.title
                        }
                    }
                    str = "[必看]" + content!!
                }
            } catch (e: Exception) {
            }

            return str
        }

    /***
     * 根据类型获取标题
     * @return
     */
    val msgTypeTitle: String
        get() {
            var str = ""
            try {
                if (msgType == ChatEnum.EMessageType.BUSINESS_CARD) {
                    str = "[名片]"
                } else if (msgType == ChatEnum.EMessageType.LOCATION) {
                    str = "[位置]"
                } else if (msgType == ChatEnum.EMessageType.FILE) {
                    if (fileMessage != null) {
                        str = "[文件]"
                    }
                } else if (msgType == ChatEnum.EMessageType.WEB) {
                    str = "[链接]"
                }
            } catch (e: Exception) {
            }

            return str
        }

    /***
     * 是否为自己
     * @return
     */
    val isMe: Boolean
        get() = false


    /*
     * 2. 根据messageType绑定布局
     * */
    override//通知
    val chatCellLayoutId: ChatEnum.EChatCellLayout
        get() {
            @ChatEnum.EMessageType val msgType = getMsgType()!!
            val isMe = isMe
            var layout: ChatEnum.EChatCellLayout?
            when (msgType) {
                ChatEnum.EMessageType.NOTICE, ChatEnum.EMessageType.MSG_CANCEL, ChatEnum.EMessageType.CHANGE_SURVIVAL_TIME -> layout =
                    ChatEnum.EChatCellLayout.NOTICE
                ChatEnum.EMessageType.TEXT, ChatEnum.EMessageType.TRANSFER_NOTICE -> layout = if (isMe) {
                    ChatEnum.EChatCellLayout.TEXT_SEND
                } else {
                    ChatEnum.EChatCellLayout.TEXT_RECEIVED
                }
                ChatEnum.EMessageType.IMAGE -> layout = if (isMe) {
                    ChatEnum.EChatCellLayout.IMAGE_SEND
                } else {
                    ChatEnum.EChatCellLayout.IMAGE_RECEIVED
                }
                ChatEnum.EMessageType.MSG_VIDEO -> layout = if (isMe) {
                    ChatEnum.EChatCellLayout.VIDEO_SEND
                } else {
                    ChatEnum.EChatCellLayout.VIDEO_RECEIVED
                }
                ChatEnum.EMessageType.BUSINESS_CARD -> layout = if (isMe) {
                    ChatEnum.EChatCellLayout.CARD_SEND
                } else {
                    ChatEnum.EChatCellLayout.CARD_RECEIVED
                }
                ChatEnum.EMessageType.RED_ENVELOPE -> layout = if (isMe) {
                    ChatEnum.EChatCellLayout.RED_ENVELOPE_SEND
                } else {
                    ChatEnum.EChatCellLayout.RED_ENVELOPE_RECEIVED
                }
                ChatEnum.EMessageType.TRANSFER -> layout = if (isMe) {
                    ChatEnum.EChatCellLayout.TRANSFER_SEND
                } else {
                    ChatEnum.EChatCellLayout.TRANSFER_RECEIVED
                }
                ChatEnum.EMessageType.VOICE -> layout = if (isMe) {
                    ChatEnum.EChatCellLayout.VOICE_SEND
                } else {
                    ChatEnum.EChatCellLayout.VOICE_RECEIVED
                }
                ChatEnum.EMessageType.STAMP -> layout = if (isMe) {
                    ChatEnum.EChatCellLayout.STAMP_SEND
                } else {
                    ChatEnum.EChatCellLayout.STAMP_RECEIVED
                }
                ChatEnum.EMessageType.AT -> layout = if (isMe) {
                    ChatEnum.EChatCellLayout.AT_SEND
                } else {
                    ChatEnum.EChatCellLayout.AT_RECEIVED
                }
                ChatEnum.EMessageType.ASSISTANT -> layout = ChatEnum.EChatCellLayout.ASSISTANT
                ChatEnum.EMessageType.ASSISTANT_NEW -> layout =
                    ChatEnum.EChatCellLayout.ASSISTANT_NEW
                ChatEnum.EMessageType.LOCK -> layout = ChatEnum.EChatCellLayout.LOCK
                ChatEnum.EMessageType.FILE -> layout = if (isMe) {
                    ChatEnum.EChatCellLayout.FILE_SEND
                } else {
                    ChatEnum.EChatCellLayout.FILE_RECEIVED
                }
                ChatEnum.EMessageType.LOCATION -> layout = if (isMe) {
                    ChatEnum.EChatCellLayout.MAP_SEND
                } else {
                    ChatEnum.EChatCellLayout.MAP_RECEIVED
                }
                ChatEnum.EMessageType.MSG_VOICE_VIDEO -> layout = if (isMe) {
                    ChatEnum.EChatCellLayout.CALL_SEND
                } else {
                    ChatEnum.EChatCellLayout.CALL_RECEIVED
                }
                ChatEnum.EMessageType.SHIPPED_EXPRESSION -> layout = if (isMe) {
                    ChatEnum.EChatCellLayout.EXPRESS_SEND
                } else {
                    ChatEnum.EChatCellLayout.EXPRESS_RECEIVED
                }
                ChatEnum.EMessageType.REPLY -> {
                    if (replyMessage == null) {
                        if (isMe) {
                            layout = ChatEnum.EChatCellLayout.UNRECOGNIZED_SEND
                        } else {
                            layout = ChatEnum.EChatCellLayout.UNRECOGNIZED_RECEIVED
                        }
                        return layout
                    }
                    val quotedMessage = replyMessage!!.quotedMessage
                    if (quotedMessage == null) {
                        if (isMe) {
                            layout = ChatEnum.EChatCellLayout.UNRECOGNIZED_SEND
                        } else {
                            layout = ChatEnum.EChatCellLayout.UNRECOGNIZED_RECEIVED
                        }
                        return layout
                    }
                    layout = getReplyLayout(quotedMessage.msgType, isMe)
                    if (layout == null) {
                        if (isMe) {
                            layout = ChatEnum.EChatCellLayout.UNRECOGNIZED_SEND
                        } else {
                            layout = ChatEnum.EChatCellLayout.UNRECOGNIZED_RECEIVED
                        }
                    }
                }
                ChatEnum.EMessageType.WEB -> layout = if (isMe) {
                    ChatEnum.EChatCellLayout.WEB_SEND
                } else {
                    ChatEnum.EChatCellLayout.WEB_RECEIVED
                }
                ChatEnum.EMessageType.ASSISTANT_PROMOTION -> layout = if (isMe) {
                    ChatEnum.EChatCellLayout.UNRECOGNIZED_SEND
                } else {
                    ChatEnum.EChatCellLayout.ADVERTISEMENT
                }
                ChatEnum.EMessageType.BALANCE_ASSISTANT -> layout = if (isMe) {
                    ChatEnum.EChatCellLayout.UNRECOGNIZED_SEND
                } else {
                    ChatEnum.EChatCellLayout.BALANCE_ASSISTANT
                }
                else -> if (isMe) {
                    layout = ChatEnum.EChatCellLayout.UNRECOGNIZED_SEND
                } else {
                    layout = ChatEnum.EChatCellLayout.UNRECOGNIZED_RECEIVED
                }
            }
            return layout
        }

    val msgContent: IMsgContent?
        get() {
            var content: IMsgContent? = null
            if (msgType == null || msgType == ChatEnum.EMessageType.UNRECOGNIZED) {
                return null
            }
            when (msgType) {
                ChatEnum.EMessageType.NOTICE -> {
                }
                ChatEnum.EMessageType.MSG_CANCEL -> content = msgCancel
                ChatEnum.EMessageType.TEXT -> content = textMessage
                ChatEnum.EMessageType.IMAGE -> content = image
                ChatEnum.EMessageType.MSG_VIDEO -> content = videoMessage
                ChatEnum.EMessageType.BUSINESS_CARD -> content = businessCard
                ChatEnum.EMessageType.VOICE -> content = voiceMessage
                ChatEnum.EMessageType.STAMP -> content = stamp
                ChatEnum.EMessageType.AT -> content = atMessage
                ChatEnum.EMessageType.ASSISTANT, ChatEnum.EMessageType.ASSISTANT_NEW -> {
                }
                ChatEnum.EMessageType.LOCK -> {
                }
                ChatEnum.EMessageType.FILE -> content = fileMessage
                ChatEnum.EMessageType.SHIPPED_EXPRESSION -> content = shippedExpressionMessage
                else -> {
                }
            }
            return content
        }

    fun isRead(): Boolean {
        return isRead
    }

    fun setRead(read: Boolean) {
        isRead = read
    }

    fun getFromUid(): Long? {
        return this.fromUid
    }

    fun setFromUid(fromUid: Long?) {
        this.fromUid = fromUid!!
    }

    fun getToUid(): Long? {
        return this.toUid
    }

    fun setToUid(toUid: Long?) {
        this.toUid = toUid!!
    }

    @ChatEnum.EMessageType
    fun getMsgType(): Int? {
        if (msgType == null) {
            msgType = ChatEnum.EMessageType.UNRECOGNIZED
        }
        return this.msgType
    }

    fun setMsgType(@ChatEnum.EMessageType msgType: Int?) {
        var msgType = msgType
        if (msgType == null) {
            msgType = ChatEnum.EMessageType.UNRECOGNIZED
        }
        this.msgType = msgType
    }

    //获取回复消息layout
    private fun getReplyLayout(msgType: Int, isMe: Boolean): ChatEnum.EChatCellLayout? {
        var layout: ChatEnum.EChatCellLayout? = null
        when (msgType) {
            ChatEnum.EMessageType.TEXT, ChatEnum.EMessageType.AT, ChatEnum.EMessageType.STAMP, ChatEnum.EMessageType.REPLY -> layout =
                if (isMe) ChatEnum.EChatCellLayout.REPLY_TEXT_SEND else ChatEnum.EChatCellLayout.REPLY_TEXT_RECEIVED
            ChatEnum.EMessageType.IMAGE, ChatEnum.EMessageType.SHIPPED_EXPRESSION, ChatEnum.EMessageType.MSG_VIDEO, ChatEnum.EMessageType.VOICE, ChatEnum.EMessageType.BUSINESS_CARD, ChatEnum.EMessageType.FILE -> layout =
                if (isMe) ChatEnum.EChatCellLayout.REPLY_IMAGE_SEND else ChatEnum.EChatCellLayout.REPLY_IMAGE_RECEIVED
        }
        return layout
    }


    override fun equals(obj: Any?): Boolean {
        if (obj == null) {
            return false
        }
        if (obj is MsgAllBean) {
            if (obj.msgId == this.msgId) {
                return true
            }
        }
        return false
    }


    override fun toString(): String {
        return "MsgAllBean{" +
                "msgId='" + msgId + '\''.toString() +
                ", timeStamp=" + timeStamp +
                ", sendState=" + sendState +
                ", sendData=" + Arrays.toString(sendData) +
                ", isRead=" + isRead +
                ", requestId='" + requestId + '\''.toString() +
                ", fromUid=" + fromUid +
                ", fromNickname='" + fromNickname + '\''.toString() +
                ", fromAvatar='" + fromAvatar + '\''.toString() +
                ", fromGroupNickname='" + fromGroupNickname + '\''.toString() +
                ", toUid=" + toUid +
                ", gid='" + gid + '\''.toString() +
                ", read=" + read +
                ", msgType=" + msgType +
                ", survivalTime=" + survivalTime +
                ", endTime=" + endTime +
                ", readTime=" + readTime +
                ", startTime=" + startTime +
                ", serverTime=" + serverTime +
                ", textMessage=" + textMessage +
                ", image=" + image +
                ", videoMessage=" + videoMessage +
                ", stamp=" + stamp +
                ", businessCard=" + businessCard +
                ", msgNotice=" + msgNotice +
                ", msgCancel=" + msgCancel +
                ", voiceMessage=" + voiceMessage +
                ", atMessage=" + atMessage +
                ", assistantMessage=" + assistantMessage +
                '}'.toString()
    }
}

