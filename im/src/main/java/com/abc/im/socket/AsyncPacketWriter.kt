package com.abc.im.socket

import android.text.TextUtils
import com.abc.core.manager.excutor.ExecutorManager
import com.abc.core.utils.LogUtil
import com.abc.im.protof.MsgBean
import java.nio.ByteBuffer

/**
 * @author Liszt
 * @date 2020/2/27
 * Description 包写入器
 */
class AsyncPacketWriter(private val socketChannel: SSLSocketChannel2) {
    private val className = AsyncPacketWriter::class.java.simpleName

    private val executor = ExecutorManager.INSTANCE.getWriteThread()

    fun write(data: ByteArray, msgTag: MsgBean.UniversalMessage.Builder, requestId: String) {
        executor.execute(WriteTask(data, msgTag, requestId))
    }


    private inner class WriteTask(
        private val data: ByteArray,
        private val msgTag: MsgBean.UniversalMessage.Builder?,
        private val requestId: String
    ) : Runnable {
        private val sendTime = System.currentTimeMillis()

        override fun run() {
            var state = 0
            try {
                val writeBuf = ByteBuffer.allocate(data.size)
                writeBuf.put(data)
                writeBuf.flip()
                LogUtil.getLog().i(className, ">>>发送长度:" + data.size)
                if (data.size < 1024) {
                    LogUtil.getLog().i(className, ">>>发送:" + SocketPact.bytesToHex(data))
                }

                state = socketChannel.write(writeBuf)
                writeBuf.clear()
//                LogUtil.getLog().i(className, ">>>发送状态:$state")
                // TODO 回执上传成功，需要清除回执缓存队列，不在重发
                if (msgTag == null) {
                    if (!TextUtils.isEmpty(requestId)) {
                        SendList.removeSendListJust(requestId)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                LogUtil.getLog().e(className, ">>>发送失败" + SocketPact.bytesToHex(data))
                LogUtil.writeLog(">>>发送失败" + SocketPact.bytesToHex(data) + " Exception:" + e.message + ">>>发送状态:" + state)
                //取消发送队列,返回失败
                if (msgTag != null) {
                    SendList.removeSendList(msgTag!!.requestId)
                }

                SocketUtil.getSocketUtil().stop(false)
                SocketUtil.getSocketUtil().startSocket()
            }

        }
    }
}
