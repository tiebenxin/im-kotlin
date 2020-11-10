package com.abc.im.socket

import java.util.*

/***
 * V2协议的定义
 */
object SocketPacket {
    //包头2位
    private val P_HEAD = byteArrayOf(0x20, 0x20)

    val P_HEART = byteArrayOf(0x7F)
    //长度2位
    //   private byte[] p_length = new byte[2];
    //校验位4位(未使用)
    //    private static byte[] P_CHECK = new byte[4];
    //版本2位,第一字节为大版本,第二位小版本,1.1
    private val P_VERSION = byteArrayOf(0x01, 0x01)
    //类型2位
    private val P_TYPE = ByteArray(2)


    //数据类型枚举
    enum class DataType {
        PROTOBUF_MSG, PROTOBUF_HEARTBEAT, AUTH, ACK, REQUEST_MSG, OTHER
    }

    fun getPackage(type: DataType, context: ByteArray?): ByteArray {
        //内容长度
        val contextSize = context?.size ?: 0
        //长度后面的包长
        val d_length = int2Byte(P_TYPE.size + contextSize)

        //类型
        var d_option = ByteArray(2)

        when (type) {
            SocketPacket.DataType.PROTOBUF_MSG//普通消息
            -> d_option = byteArrayOf(0x00, toByte(1, 0))
            SocketPacket.DataType.PROTOBUF_HEARTBEAT//心跳
            -> d_option = byteArrayOf(0x00, toByte(0, 1))
            SocketPacket.DataType.AUTH//鉴权
            -> d_option = byteArrayOf(0x00, toByte(1, 2))
            SocketPacket.DataType.ACK//回馈
            -> d_option = byteArrayOf(0x00, toByte(1, 3))
            SocketPacket.DataType.REQUEST_MSG//请求数据包
            -> d_option = byteArrayOf(0x00, toByte(1, 4))
        }

        //包大小
        val d_size = P_HEAD.size + P_VERSION.size + d_length.size + d_option.size + contextSize

        val rtData = ByteArray(d_size)
        System.arraycopy(P_HEAD, 0, rtData, 0, 2)
        System.arraycopy(P_VERSION, 0, rtData, 2, 2)
        System.arraycopy(d_length, 0, rtData, 4, 4)
        System.arraycopy(d_option, 0, rtData, 8, 2)
        if (context != null) {
            System.arraycopy(context, 0, rtData, 10, contextSize)
        }


        return rtData
    }

    /***
     * 时候是包头
     * @param data
     * @return
     */
    fun isHead(data: ByteArray?): Boolean {
        return if (data == null || data.size < 2) false else P_HEAD[0] == data[0] && P_HEAD[1] == data[1]

        /*        byte[] d = new byte[2];
        d[0] = data[0];
        d[1] = data[1];*/

    }


    /***
     * 获取长度
     * @return
     */
    fun getLength(data: ByteArray): Int {
        val d = ByteArray(4)
        d[0] = data[4]
        d[1] = data[5]
        d[2] = data[6]
        d[3] = data[7]
        return byte2Int(d)
    }

    /***
     * 获取消息类型，option  高位表示传输数据类型，地位 表示option类型
     * @return
     */
    fun getType(data: ByteArray): DataType {
        if (data.size >= 10) {
            val d = ByteArray(2)
            d[0] = data[8]//暂时不用
            d[1] = data[9]

            val h = byteH4(d[1])
            val l = byteL4(d[1])
            //TODO:没必要判断高位，因为没必要知道数据解析类型
            if (l == 0) {
                return DataType.PROTOBUF_MSG
            } else if (l == 1) {
                return DataType.PROTOBUF_HEARTBEAT
            } else if (l == 3) {
                return DataType.ACK
            } else if (l == 2) {
                return DataType.AUTH
            } else if (l == 4) {
                return DataType.REQUEST_MSG
            }
            //            if (h == 1 && l == 0) {
            //                return DataType.PROTOBUF_MSG;
            //            } else if (h == 0 && l == 1) {
            //                return DataType.PROTOBUF_HEARTBEAT;
            //            } else if (h == 1 && l == 3) {
            //                return DataType.ACK;
            //            } else if (h == 1 && l == 2) {
            //                return DataType.AUTH;
            //            } else if (h == 1 && l == 4) {
            //                return DataType.REQUEST_MSG;
            //            }
        }
        return DataType.OTHER


    }


    //------------------------转换工具-------------------

    /***
     * 合并数组
     * @param values
     * @return
     */
    fun listToBytes(values: List<ByteArray>): ByteArray {
        var length_byte = 0
        for (i in values.indices) {
            length_byte += values[i].size
        }
        val all_byte = ByteArray(length_byte)
        var countLength = 0
        for (i in values.indices) {
            val b = values[i]
            System.arraycopy(b, 0, all_byte, countLength, b.size)
            countLength += b.size
        }
        return all_byte
    }

    /***
     * 拆分数组
     * @return
     */
    fun bytesToLists(data: ByteArray, vararg sp_length: Int): List<ByteArray> {
        val list = ArrayList<ByteArray>()
        var i = 0
        for (l in sp_length) {
            val t = ByteArray(l)
            System.arraycopy(data, i, t, 0, t.size)
            list.add(t)
            i = l
        }

        val exl = data.size - i
        val ex = ByteArray(exl)
        System.arraycopy(data, i, ex, 0, exl)
        if (ex.size > 0) {
            list.add(ex)
        }

        return list
    }

    /***
     * 合并数组
     * @param values
     * @return
     */
    fun byteMergerAll(vararg values: ByteArray): ByteArray {
        var length_byte = 0
        for (i in values.indices) {
            length_byte += values[i].size
        }
        val all_byte = ByteArray(length_byte)
        var countLength = 0
        for (i in values.indices) {
            val b = values[i]
            System.arraycopy(b, 0, all_byte, countLength, b.size)
            countLength += b.size
        }
        return all_byte
    }

    /**
     * 将int型的数据转换成byte数组，四个字节
     *
     * @param intValue
     * @return
     */
    fun int2Byte(intValue: Int): ByteArray {
        val b = ByteArray(4)
        for (i in 0..3) {
            b[i] = (intValue shr 8 * (3 - i) and 0xFF).toByte()
        }
        return b
    }

    /**
     * 将byte数组转换成int型，4个字节的数组
     *
     * @param b
     * @return
     */
    fun byte2Int(b: ByteArray): Int {
        var intValue = 0
        val tempValue = 0xFF
        for (i in b.indices) {
            intValue += b[i].toInt() and tempValue shl 8 * (3 - i)
        }
        return intValue
    }


    /***
     * 合并字节
     * @param h
     * @param l
     * @return
     */
    private fun toByte(h: Int, l: Int): Byte {
        return (h shl 4 and 0xf0 or (l and 0x0f)).toByte()
    }

    //高4位
    fun byteH4(bt: Byte): Int {
        return bt.toInt() and 0xf0 shr 4
    }

    //低4位
    fun byteL4(bt: Byte): Int {
        return bt.toInt() and 0x0f
    }

    /**
     * 字节数组转16进制
     *
     * @param bytes 需要转换的byte数组
     * @return 转换后的Hex字符串
     */
    fun bytesToHex(bytes: ByteArray): String {
        val sb = StringBuffer()
        for (i in bytes.indices) {
            val hex = Integer.toHexString(bytes[i].toInt() and 0xFF)
            if (hex.length < 2) {
                sb.append(0)
            }
            sb.append("$hex ")
        }
        return sb.toString()
    }
}
