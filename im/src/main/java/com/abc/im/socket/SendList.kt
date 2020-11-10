package com.abc.im.socket

import android.text.TextUtils
import com.abc.im.bean.MsgAllBean
import com.abc.im.protof.MsgBean


import java.util.HashMap
import java.util.concurrent.ConcurrentHashMap

/***
 * 发送队列
 */
object SendList {
    private val TAG = "SendList"
    //重发次数
    private val SEND_MAX_NUM = 3
    //重发时长
    private val SEND_RE_TIME = (3 * 1000).toLong()

    var SEND_LIST: MutableMap<String, SendListBean> = ConcurrentHashMap()
    private var sendSequence: MutableMap<String, MsgAllBean> = ConcurrentHashMap()

    @JvmStatic
    fun findMsgById(keyId: String): MsgBean.UniversalMessage.Builder? {
        return if (SEND_LIST.containsKey(keyId)) {
            SEND_LIST[keyId]!!.msg
        } else null
    }

    /***
     * 添加到列队中监听
     * @param keyId
     * @param msg
     */
    @JvmStatic
    fun addSendList(keyId: String, msg: MsgBean.UniversalMessage.Builder) {

//        LogUtil.getLog().d(TAG, "添加发送队列rid:$keyId")
//        LogUtil.writeLog("添加发送队列keyId:$keyId")
        if (SEND_LIST.containsKey(keyId)) {//已经在发送队列中了
            val sl = SEND_LIST[keyId]
            sl!!.reSendNum = sl.reSendNum + 1
//            LogUtil.getLog().d(TAG, ">>>" + sl.reSendNum + "次重发队列" + keyId)
        } else {//首次
//            LogUtil.getLog().d(TAG, ">>>添加到发送队列$keyId")
            val sl = SendListBean()
            sl.firstTimeSent = System.currentTimeMillis()
            sl.msg = msg
            //6.26 发送次数从1
            sl.reSendNum = 1
            SEND_LIST[keyId] = sl
            //5.28 如果非在线发送,直接失败
//            if (!SocketUtil.getSocketUtil().onlineState) {
//                removeSendList(keyId)
//            }
        }
    }

    /***
     * 添加到列队中监听
     * @param keyId
     * @param msg
     */
    @JvmStatic
    fun addSendList(keyId: String, msg: MsgBean.AckMessage.Builder?) {
        if (TextUtils.isEmpty(keyId) || msg == null) {
            return
        }
//        LogUtil.getLog().d(TAG, "添加发送队列rid:$keyId")
        //        LogUtil.writeLog("添加发送队列keyId:" +keyId);
        if (SEND_LIST.containsKey(keyId)) {//已经在发送队列中了
            val sl = SEND_LIST[keyId]
            sl!!.reSendNum = sl.reSendNum + 1
//            LogUtil.getLog().d(TAG, ">>>" + sl.reSendNum + "次重发队列" + keyId)
        } else {//首次
//            LogUtil.getLog().d(TAG, ">>>添加到发送队列$keyId")
            val sl = SendListBean()
            sl.firstTimeSent = System.currentTimeMillis()
            sl.msgAck = msg
            //6.26 发送次数从1
            sl.reSendNum = 1
            SEND_LIST[keyId] = sl
            //5.28 如果非在线发送,直接失败
//            if (!SocketUtil.getSocketUtil().onlineState) {
//                removeSendList(keyId)
//            }
        }
    }


    /***
     * 移除列队,返回发送失败
     * @param keyId
     */
    fun removeSendList(keyId: String) {
        try {
//            LogUtil.getLog().d(TAG, "移除发送队列rid:$keyId")
            if (!SEND_LIST.containsKey(keyId))
                return
//            LogUtil.getLog().e(TAG, "SocketUtil$移除队列[返回失败]$keyId")
            if (SEND_LIST[keyId]!!.msg != null) {
//                SocketUtil.getSocketUtil().getEvent().onSendMsgFailure(SEND_LIST[keyId]!!.msg)
            }
            //        else{
            //            if(SEND_LIST.get(keyId).getMsgAck()!=null){
            //                LogUtil.writeLog("--removeSendList 发送失败了--requestId=" + SEND_LIST.get(keyId).getMsgAck().getRequestId());
            //            }
            //        }
            SEND_LIST.remove(keyId)
        } catch (e: Exception) {
//            LogUtil.getLog().d(TAG, "移除发送队列异常:" + e.message)
        }

    }

    /***
     * 仅移除消息列队
     * @param keyId
     */
    @JvmStatic
    fun removeSendListJust(keyId: String) {
        if (!SEND_LIST.containsKey(keyId))
            return
//        LogUtil.getLog().i(TAG, "SocketUtil$移除队列$keyId")
//        LogUtil.writeLog("SocketUtil$移除队列:$keyId")
        SEND_LIST.remove(keyId)
    }


    /***
     * 循环队列
     */
    fun loopList() {
        val entrys = SEND_LIST.entries.iterator()
        val now = System.currentTimeMillis()
        while (entrys.hasNext()) {
            val entry = entrys.next()
            val kid = entry.key
            val bean = entry.value

            if (bean.reSendNum <= SEND_MAX_NUM) { //在正常发送范围之内
                if (now > bean.firstTimeSent + bean.reSendNum * SEND_RE_TIME) {
//                    LogUtil.getLog().e(TAG, ">>>>符合发送条件$kid")
                    if (bean.msg != null) {
//                        SocketUtil.getSocketUtil().sendData4Msg(bean.msg!!)
                    } else {
//                        LogUtil.writeLog(
//                            ">>>重新发送回执 RequestId:" + bean.msgAck.getRequestId() +
//                                    " MsgId:" + bean.msgAck.getMsgIdList() + " MsgIdCount:" + bean.msgAck.getMsgIdCount()
//                        )
                        // 添加到消息队中监听
                        addSendList(bean.msgAck!!.requestId, bean.msgAck)
//                        SocketUtil.getSocketUtil().sendData(SocketPacket.getPackage(SocketPacket.DataType.ACK, bean.msgAck!!.build().toByteArray()), null, bean.msgAck!!.requestI)
                    }
                } else {
//                    LogUtil.getLog().e(TAG, ">>>>符合重发条件但时间不满足$kid")
                }
            } else {//超过发送次数,取消队列,返回失败
//                LogUtil.getLog().e(TAG, ">>>>发送条件次数不符合$kid")
                removeSendList(kid)
            }
        }
    }


    /***
     * 结束列队
     */
    fun endList() {
        val entrys = SEND_LIST.entries.iterator()
        while (entrys.hasNext()) {
            val entry = entrys.next()
            val kid = entry.key
            removeSendList(kid)
        }
    }

    /*
     * 将消息添加到发送队列
     * */
    fun addMsgToSendSequence(requestId: String, msg: MsgAllBean) {
        if (TextUtils.isEmpty(requestId)) {
            return
        }
        if (sendSequence == null) {
            sendSequence = HashMap<String, MsgAllBean>()
        }
        sendSequence!![requestId] = msg
    }

    /*
     * 从发送队列获取消息
     * */
    fun getMsgFromSendSequence(requestId: String): MsgAllBean? {
        return if (sendSequence != null) {
            sendSequence!![requestId]
        } else null
    }

    /*
     * 从发送队列移出
     * */
    fun removeMsgFromSendSequence(requestId: String) {
        if (sendSequence != null) {
            sendSequence!!.remove(requestId)
        }
    }

    fun clearSendSequence() {
        if (sendSequence != null) {
            sendSequence!!.clear()
        }
    }


}
