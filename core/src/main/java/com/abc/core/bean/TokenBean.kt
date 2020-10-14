package com.abc.core.bean

import android.os.Parcel
import android.os.Parcelable
import com.abc.core.base.BaseBean

class TokenBean() : BaseBean() ,Parcelable{
    var uid: Long? = null
    var accessToken: String? = null
    var neteaseAccid: String? = null// 网易id
    var neteaseToken: String? = null// 网易token
    var validTime: Long = 0//有效时间，有效时间= token获取时间+ 有效期7天的毫秒值
    var bankReqSignKey: String? = null//支付签名
    var isDeactivating: Boolean = false//是否注销账户
    var appealState: Int = 0// 申诉状态 (0:未申诉，1：申诉中)
    var lockUser: Int = 0// 用户状态 0正常 1封号

    constructor(parcel: Parcel) : this() {
        uid = parcel.readValue(Long::class.java.classLoader) as? Long
        accessToken = parcel.readString()
        neteaseAccid = parcel.readString()
        neteaseToken = parcel.readString()
        validTime = parcel.readLong()
        bankReqSignKey = parcel.readString()
        isDeactivating = parcel.readByte() != 0.toByte()
        appealState = parcel.readInt()
        lockUser = parcel.readInt()
    }

    /**
     * token是否有效,备注：imId登录问题，未兼容
     */
    fun isTokenValid(uid: Long?): Boolean {
        var isValid = false
        if (uid != null && uid == this.uid) {
            if (System.currentTimeMillis() < this.validTime) {
                isValid = true
            }
        }

        return isValid
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(uid)
        parcel.writeString(accessToken)
        parcel.writeString(neteaseAccid)
        parcel.writeString(neteaseToken)
        parcel.writeLong(validTime)
        parcel.writeString(bankReqSignKey)
        parcel.writeByte(if (isDeactivating) 1 else 0)
        parcel.writeInt(appealState)
        parcel.writeInt(lockUser)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TokenBean> {
        override fun createFromParcel(parcel: Parcel): TokenBean {
            return TokenBean(parcel)
        }

        override fun newArray(size: Int): Array<TokenBean?> {
            return arrayOfNulls(size)
        }
    }
}
