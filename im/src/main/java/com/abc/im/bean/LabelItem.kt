package com.im.db.bean

import com.abc.core.base.BaseBean
import com.google.gson.annotations.SerializedName

/**
 * @author Liszt
 * @date 2019/12/14
 * Description
 */
class LabelItem :BaseBean(){

    @SerializedName("label_")
    var label = ""
    @SerializedName("value_")
    var value = ""
}
