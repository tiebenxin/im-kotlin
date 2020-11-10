package com.abc.im.socket

import com.abc.im.protof.MsgBean

interface SocketEvent {
    fun onHeartbeat()
    fun onACK(bean: MsgBean.AckMessage)
    fun onMsg(bean: MsgBean.UniversalMessage)
    fun onSendMsgFailure(bean: MsgBean.UniversalMessage.Builder)
    fun onLine(state: Boolean)
}
