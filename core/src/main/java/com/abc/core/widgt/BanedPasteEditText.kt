package com.abc.core.widgt

import android.content.Context
import android.util.AttributeSet

/**
 * @author Liszt
 * @date 2020/6/9
 * Description 禁止复制粘贴EditText, 应用场景密码输入
 */
class BanedPasteEditText : ClearEditText {
    constructor(context: Context) : super(context) {
        banLongClick()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        banLongClick()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        banLongClick()
    }

    private fun banLongClick() {
        isLongClickable = false
        setTextIsSelectable(false)
    }


    override fun onTextContextMenuItem(id: Int): Boolean {
        return if (id == android.R.id.paste) {
            false
        } else super.onTextContextMenuItem(id)
    }
}
