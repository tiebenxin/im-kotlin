package com.abc.core.widgt

import android.content.Context
import android.text.Layout
import android.text.StaticLayout
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView

/**
 * @version V1.0
 * @description 超过一定行数显示固定行数时末尾添加 "..."的TextView(适配图文混排)
 * @copyright copyright(c)2019 ChangSha hm Technology Co., Ltd. Inc. All rights reserved.
 */
class EllipsizedTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatTextView(context, attrs, defStyle) {

    private var mMaxLines: Int = 0

    init {

        val a =
            context.obtainStyledAttributes(attrs, intArrayOf(android.R.attr.maxLines), defStyle, 0)

        mMaxLines = a.getInteger(0, 1)
        a.recycle()
    }

    override fun setText(text: CharSequence?, type: TextView.BufferType) {
        var text = text
        if (text != null && text.length > 0 && mMaxLines != Integer.MAX_VALUE && mMaxLines > 0 && width != 0) {
            val layout =
                StaticLayout(text, paint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false)
            //多行 需要显示的文字加上"..."的总宽度
            if (mMaxLines > 1) {
                var textAndEllipsizeWidth = 0f
                for (i in 0 until mMaxLines) {
                    //此处用getWidth()计算的话会有误差，所以用getLineWidth() getLineWidth
                    textAndEllipsizeWidth += layout.width.toFloat()
                }
                text = TextUtils.ellipsize(
                    text,
                    paint,
                    textAndEllipsizeWidth,
                    TextUtils.TruncateAt.END
                )
            } else {
                text = TextUtils.ellipsize(
                    text,
                    paint,
                    layout.width.toFloat(),
                    TextUtils.TruncateAt.END
                )
            }
        }
        super.setText(text, type)
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        if (width > 0 && oldWidth != width) {
            text = text
        }
    }

    override fun getMaxLines(): Int {
        return mMaxLines
    }

    override fun setMaxLines(maxLines: Int) {
        super.setMaxLines(maxLines)
        mMaxLines = maxLines
    }
}
