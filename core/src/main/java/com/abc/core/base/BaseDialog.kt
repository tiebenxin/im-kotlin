package com.abc.core.base

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener


abstract class BaseDialog : Dialog, OnClickListener {


    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener
    ) : super(context, cancelable, cancelListener) {
        initView()
    }

    constructor(context: Context, theme: Int) : super(context, theme) {
        initView()
    }

    constructor(context: Context) : super(context) {
        initView()
    }

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        //		initView();
        initData()
        initEvent()
    }

    abstract fun initView()

    fun initData() {}

    fun initEvent() {}

    abstract fun processClick(view: View)

    override fun onClick(v: View) {
        processClick(v)

    }

}
