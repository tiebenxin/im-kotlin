package com.abc.im.socket

import com.abc.im.exception.SocketEndException

import java.io.IOException
import java.net.Socket
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectableChannel
import java.nio.channels.SocketChannel

import javax.net.ssl.SSLContext
import javax.net.ssl.SSLEngine
import javax.net.ssl.SSLEngineResult
import javax.net.ssl.SSLException
import javax.net.ssl.SSLSession


/**
 * a SocketChannel with TLS/SSL encryption
 *
 * @author Alexander Kout
 * @created 25. Mai 2005
 */

class SSLSocketChannel2 {
    private var SSL: Int = 0
    private lateinit var clientIn: ByteBuffer
    private var clientOut: ByteBuffer? = null
    private lateinit var cTOs: ByteBuffer
    private lateinit var sTOc: ByteBuffer
    private lateinit var wbuf: ByteBuffer
    private var sc: SocketChannel? = null
    private lateinit var res: SSLEngineResult
    private var sslEngine: SSLEngine? = null

    val isConnected: Boolean
        get() = sc!!.isConnected

    val isInboundDone: Boolean
        get() = sslEngine!!.isInboundDone

    @Throws(IOException::class)
    constructor() {
        sc = SocketChannel.open()
    }

    //1.转换
    constructor(sc: SocketChannel) {
        this.sc = sc
    }

    //2.链接成功后启动 pssl=1
    fun tryTLS(pSSL: Int): Int {
        SSL = pSSL
        if (SSL == 0)
            return 0

        var sslContext: SSLContext? = null
        try {
            // create SSLContext
            sslContext = SSLContext.getInstance("TLS")
            //配置证书或者不配置
            sslContext!!.init(null, arrayOf(EasyX509TrustManager(null)), null)

            // create Engine
            sslEngine = sslContext.createSSLEngine()
            // begin
            sslEngine!!.useClientMode = true

            sslEngine!!.enableSessionCreation = true
            val session = sslEngine!!.session
            createBuffers(session)
            // wrap
            if (clientOut != null) {
                clientOut!!.clear()
                sc!!.write(wrap(clientOut))
                while (res.handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED) {
                    if (res.handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) {
                        // unwrap
                        sTOc.clear()
                        var readindex = 0
                        while (sc!!.read(sTOc) < 1) {
                            //TODO：线程睡眠200，修改于2020.5.12
                            Thread.sleep(20)
                            readindex++
                            if (readindex > 100) {
                                return 0
                            }

                        }

                        sTOc.flip()
                        unwrap(sTOc)
                        if (res.handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED) {
                            clientOut!!.clear()
                            sc!!.write(wrap(clientOut))
                        }
                    } else if (res.handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_WRAP) {
                        // wrap
                        clientOut!!.clear()
                        sc!!.write(wrap(clientOut))
                    } else {
                        Thread.sleep(1000)
                    }
                }
                clientIn.clear()
                clientIn.flip()
                SSL = 4

//                LogUtil.getLog().i(TAG, "SSL established\n")
            } else {
                return 0
            }
        } catch (e: Exception) {
            e.printStackTrace(System.out)
//            LogUtil.getLog().e(TAG, "连接LOG --SSL tryTLS的异常:$e")
//            LogUtil.writeLog(TAG + "--连接LOG--" + "连接异常--" + e.javaClass.simpleName + "--errMsg=" + e.toString())
            SSL = 0
            //  throw new NetworkErrorException();
        }

        return SSL
    }

    @Synchronized
    @Throws(SSLException::class)
    private fun wrap(b: ByteBuffer?): ByteBuffer {
        cTOs.clear()
        res = sslEngine!!.wrap(b, cTOs)
        cTOs.flip()
        // LogUtil.getLog().i(TAG,"wrap:\n"+res.toString()+"\n");
        return cTOs
    }

    @Synchronized
    @Throws(SSLException::class)
    private fun unwrap(b: ByteBuffer): ByteBuffer {
        clientIn.clear()
        val pos: Int
        // LogUtil.getLog().i(TAG,"b.remaining "+b.remaining()+"\n");
        while (b.hasRemaining()) {
            // LogUtil.getLog().i(TAG,"b.remaining "+b.remaining()+"\n");
            res = sslEngine!!.unwrap(b, clientIn)
            // LogUtil.getLog().i(TAG,"unwrap:\n"+res.toString()+"\n");
            if (res.handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_TASK) {
                // Task
                var task: Runnable
                while (sslEngine!!.delegatedTask != null) {
                    //  LogUtil.getLog().i(TAG,"task...\n");
                    task = sslEngine!!.delegatedTask
                    task.run()
                }
                //  LogUtil.getLog().i(TAG,"task:\n"+res.toString()+"\n");
            } else if (res.handshakeStatus == SSLEngineResult.HandshakeStatus.FINISHED) {
                return clientIn
            } else if (res.status == SSLEngineResult.Status.BUFFER_UNDERFLOW) {
                //   LogUtil.getLog().i(TAG,"underflow\n");
                //  LogUtil.getLog().i(TAG,"b.remaining "+b.remaining()+"\n");
                return clientIn
            }
        }
        return clientIn
    }

    private fun createBuffers(session: SSLSession) {

        val appBufferMax = session.applicationBufferSize
        val netBufferMax = session.packetBufferSize

        clientIn = ByteBuffer.allocate(65536)
        clientOut = ByteBuffer.allocate(appBufferMax)
        wbuf = ByteBuffer.allocate(65536)

        cTOs = ByteBuffer.allocate(netBufferMax)
        sTOc = ByteBuffer.allocate(netBufferMax)

    }

    @Synchronized
    @Throws(IOException::class)
    fun write(src: ByteBuffer): Int {
        return if (SSL == 4) {
            sc!!.write(wrap(src))
        } else sc!!.write(src)
    }

    @Throws(Exception::class)
    fun read(dst: ByteBuffer): Int {
        //  LogUtil.getLog().i(TAG,"read\n");
        var amount = 0
        var limit: Int
        if (SSL == 4) {
            // test if there was a buffer overflow in dst
            if (clientIn.hasRemaining()) {
                limit = Math.min(clientIn.remaining(), dst.remaining())
                for (i in 0 until limit) {
                    dst.put(clientIn.get())
                    amount++
                }
                return amount
            }
            // test if some bytes left from last read (e.g. BUFFER_UNDERFLOW)
            if (sTOc.hasRemaining()) {
                unwrap(sTOc)
                clientIn.flip()
                limit = Math.min(clientIn.limit(), dst.remaining())
                for (i in 0 until limit) {
                    dst.put(clientIn.get())
                    amount++
                }
                if (res.status != SSLEngineResult.Status.BUFFER_UNDERFLOW) {
                    sTOc.clear()
                    sTOc.flip()
                    return amount
                }
            }
            if (!sTOc.hasRemaining())
                sTOc.clear()
            else
                sTOc.compact()

            if (sc!!.read(sTOc) == -1) {
//                LogUtil.getLog().i(TAG, "close from SSLSocketChannel2" + "\n")
                sTOc.clear()
                sTOc.flip()
                throw SocketEndException()
            }
            sTOc.flip()
            unwrap(sTOc)
            // write in dst
            clientIn.flip()
            limit = Math.min(clientIn.limit(), dst.remaining())
            for (i in 0 until limit) {
                dst.put(clientIn.get())
                amount++
            }
            //LogUtil.getLog().i(TAG,"dst.remaining "+dst.remaining()+"\n");
            return amount
        }
        return sc!!.read(dst)
    }

    @Throws(IOException::class)
    fun close() {
        if (SSL == 4) {
            if (sslEngine != null) {
                sslEngine!!.closeOutbound()
            }
            if (clientOut != null) {
                clientOut!!.clear()
            }
            if (sc != null) {
                sc!!.write(wrap(clientOut))
                sc!!.close()
            }
        } else {
            if (sc != null) {
                sc!!.close()
            }
        }
    }

    @Throws(IOException::class)
    fun configureBlocking(b: Boolean): SelectableChannel {
        return sc!!.configureBlocking(b)
    }

    @Throws(IOException::class)
    fun connect(remote: SocketAddress): Boolean {
        return sc!!.connect(remote)
    }

    @Throws(IOException::class)
    fun finishConnect(): Boolean {
        return sc!!.finishConnect()
    }

    fun socket(): Socket {
        return sc!!.socket()
    }

    companion object {
        private val TAG = "SSLSocketChannel2"
    }
}








