package com.abc.im.socket

import com.abc.im.protof.MsgBean

class SendListBean {
    var reSendNum = 0
    var firstTimeSent: Long = 0//首次发送时间
    var msg: MsgBean.UniversalMessage.Builder? = null
    var msgAck: MsgBean.AckMessage.Builder? = null
}