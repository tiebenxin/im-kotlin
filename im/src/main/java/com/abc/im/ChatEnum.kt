package com.abc.im

import androidx.annotation.IntDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 *@author Liszt
 *@date 2020/11/9
 *Description
 */
class ChatEnum {

    /*
     *1. 聊天布局枚举
     * */
    enum class EChatCellLayout private constructor(val LayoutId: Int) {
        // 文本消息，接收和发送布局
        TEXT_RECEIVED(R.layout.cell_txt_received),
        TEXT_SEND(R.layout.cell_txt_send),

        //图片消息
        IMAGE_RECEIVED(R.layout.cell_img_received),
        IMAGE_SEND(R.layout.cell_img_send),

        //语音消息
        VOICE_RECEIVED(R.layout.cell_voice_received),
        VOICE_SEND(R.layout.cell_voice_send),

        //视频消息
        VIDEO_RECEIVED(R.layout.cell_video_received),
        VIDEO_SEND(R.layout.cell_video_send),

        //位置消息
        MAP_RECEIVED(R.layout.cell_location_received),
        MAP_SEND(R.layout.cell_location_send),

        //名片消息
        CARD_RECEIVED(R.layout.cell_card_received),
        CARD_SEND(R.layout.cell_card_send),

        //红包消息
        RED_ENVELOPE_RECEIVED(R.layout.cell_redenvelope_received),
        RED_ENVELOPE_SEND(R.layout.cell_redenvelope_send),


        //转账消息，共用红包布局
        TRANSFER_RECEIVED(R.layout.cell_redenvelope_received),
        TRANSFER_SEND(R.layout.cell_redenvelope_send),

        //戳一下消息
        STAMP_RECEIVED(R.layout.cell_stamp_received),
        STAMP_SEND(R.layout.cell_stamp_send),

        //@消息, 共用文本消息布局
        AT_RECEIVED(R.layout.cell_txt_received),
        AT_SEND(R.layout.cell_txt_send),

        //文件消息
        FILE_RECEIVED(R.layout.cell_file_received),
        FILE_SEND(R.layout.cell_file_send),

        //音视频通话消息
        CALL_RECEIVED(R.layout.cell_call_received),
        CALL_SEND(R.layout.cell_call_send),

        //分享 web 或 游戏消息
        WEB_RECEIVED(R.layout.cell_web_received),
        WEB_SEND(R.layout.cell_web_send),

        //表情消息
        EXPRESS_RECEIVED(R.layout.cell_img_received),
        EXPRESS_SEND(R.layout.cell_img_send),

        //合并转发
        MULTI_RECEIVED(R.layout.cell_txt_received),
        MULTI_SEND(R.layout.cell_txt_send),

        //通知消息
        NOTICE(R.layout.cell_notice),

        //零钱助手消息
        BALANCE_ASSISTANT(R.layout.cell_balance_assistant),

        //常信小助手消息
        ASSISTANT(R.layout.cell_txt_received),
        //常信小助手消息 新版
        ASSISTANT_NEW(R.layout.cell_txt_received_new),

        //端到端加密消息
        LOCK(R.layout.cell_lock),

        //广告消息
        ADVERTISEMENT(R.layout.cell_ad_received),

        //回复文本消息
        REPLY_TEXT_RECEIVED(R.layout.cell_reply_txt_received),
        REPLY_TEXT_SEND(R.layout.cell_reply_txt_send),

        //回复图片消息
        REPLY_IMAGE_RECEIVED(R.layout.cell_reply_image_received),
        REPLY_IMAGE_SEND(R.layout.cell_reply_image_send),

        //未知消息
        UNRECOGNIZED_RECEIVED(R.layout.cell_txt_received),
        UNRECOGNIZED_SEND(R.layout.cell_txt_send);


        companion object {

            fun fromOrdinal(ordinal: Int): EChatCellLayout {
                var result: EChatCellLayout? = null
                for (item in EChatCellLayout.values()) {
                    if (item.ordinal == ordinal) {
                        result = item
                        break
                    }
                }
                requireNotNull(result) { "EChatCellLayout - fromOrdinal" }
                return result
            }

            fun fromLayoutId(layoutId: Int): EChatCellLayout {
                var result: EChatCellLayout? = null
                for (item in EChatCellLayout.values()) {
                    if (item.LayoutId == layoutId) {
                        result = item
                        break
                    }
                }
                requireNotNull(result) { "EChatCellLayout - fromLayoutId" }
                return result
            }

            fun size(): Int {
                return values().size
            }
        }
    }


    /*
     * 发送状态
     * 0:正常,1:错误,2:发送中 -1 预发送 3阅读即焚
     * */
    @IntDef(ESendStatus.PRE_SEND, ESendStatus.NORMAL, ESendStatus.ERROR, ESendStatus.SENDING)
    @Retention(RetentionPolicy.SOURCE)
    annotation class ESendStatus {
        companion object {
            const val PRE_SEND = -1 //预发送
            const val NORMAL = 0 //正常
            const val ERROR = 1//错误
            const val SENDING = 2//发送中
        }
    }

    /*
     * 播放状态
     *
     * */
    @IntDef(
        EPlayStatus.NO_DOWNLOADED,
        EPlayStatus.DOWNLOADING,
        EPlayStatus.NO_PLAY,
        EPlayStatus.PLAYING,
        EPlayStatus.STOP_PLAY,
        EPlayStatus.PLAYED
    )
    @Retention(RetentionPolicy.SOURCE)
    annotation class EPlayStatus {
        companion object {
            const val NO_DOWNLOADED = 0 // 未下载
            const val DOWNLOADING = 1 // 开始下载
            const val NO_PLAY = 2 //下载成功，未播放
            const val PLAYING = 3 //正播放
            const val STOP_PLAY = 4 //停止播放
            const val PLAYED = 5//已播放
        }
    }

    /*
     * 消息type
     * */
    @IntDef(
        EMessageType.NOTICE,
        EMessageType.TEXT,
        EMessageType.STAMP,
        EMessageType.RED_ENVELOPE,
        EMessageType.IMAGE,
        EMessageType.BUSINESS_CARD,
        EMessageType.TRANSFER,
        EMessageType.VOICE,
        EMessageType.AT,
        EMessageType.ASSISTANT,
        EMessageType.MSG_CANCEL,
        EMessageType.UNRECOGNIZED,
        EMessageType.MSG_VIDEO,
        EMessageType.MSG_VOICE_VIDEO,
        EMessageType.LOCK,
        EMessageType.CHANGE_SURVIVAL_TIME,
        EMessageType.READ,
        EMessageType.MSG_VOICE_VIDEO_NOTICE,
        EMessageType.LOCATION,
        EMessageType.BALANCE_ASSISTANT,
        EMessageType.SHIPPED_EXPRESSION,
        EMessageType.FILE,
        EMessageType.WEB,
        EMessageType.TRANSFER_NOTICE,
        EMessageType.REPLY,
        EMessageType.GROUP_ANNOUNCEMENT,
        EMessageType.ASSISTANT_PROMOTION,
        EMessageType.ASSISTANT_NEW
    )
    @Retention(RetentionPolicy.SOURCE)
    annotation class EMessageType {
        companion object {
            const val UNRECOGNIZED = -1 //未识别
            const val NOTICE = 0 //公告
            const val TEXT = 1//文本
            const val STAMP = 2//戳一下
            const val RED_ENVELOPE = 3//红包
            const val IMAGE = 4//图片
            const val BUSINESS_CARD = 5//名片
            const val TRANSFER = 6//转账
            const val VOICE = 7//语音
            const val AT = 8//艾特@消息
            const val ASSISTANT = 9//小助手
            const val MSG_CANCEL = 10 //撤回消息
            const val MSG_VIDEO = 11 //短视频消息
            const val MSG_VOICE_VIDEO = 12 //音视频消息
            const val MSG_VOICE_VIDEO_NOTICE = 13 //音视频消息通知
            const val LOCATION = 14 //位置消息
            const val BALANCE_ASSISTANT = 15 //零钱助手消息
            const val SHIPPED_EXPRESSION = 16// 大表情
            const val FILE = 17//文件
            const val WEB = 18//web消息
            const val TRANSFER_NOTICE = 19 //转账提醒
            const val REPLY = 20 //回复消息
            const val ASSISTANT_PROMOTION = 21 //小助手广告消息
            const val ASSISTANT_NEW = 22 //小助手 新版消息

            const val LOCK = 100 //端到端加密提示消息,本地自定义消息
            const val CHANGE_SURVIVAL_TIME = 113//阅后即焚
            const val READ = 120//已读消息
            const val GROUP_ANNOUNCEMENT = 123//群公告
        }

    }
}