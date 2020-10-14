package com.abc.core.dialog

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.abc.core.R
import com.abc.core.base.BaseDialog

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


/**
 * @author Liszt
 * @date 2019/12/19
 * Description 加载dialog
 */
class DialogLoadingProgress @JvmOverloads constructor(
    context: Context,
    theme: Int = R.style.MyDialogTheme
) : BaseDialog(context, theme) {

    private var ivProgress: ImageView? = null
    private var tvContent: TextView? = null

    override fun initView() {
        setContentView(R.layout.dialog_loading_progress)
        tvContent = findViewById(R.id.tv_content)
        ivProgress = findViewById(R.id.iv_progress)
        val options = RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        Glide.with(context).load(R.drawable.offline_loading).apply(options).into(ivProgress!!)
    }

    override fun processClick(view: View) {

    }

    fun setContent(s: String) {
        if (tvContent != null && !TextUtils.isEmpty(s)) {
            tvContent!!.text = s
        }
    }
}
