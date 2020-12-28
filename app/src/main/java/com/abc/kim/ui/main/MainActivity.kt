package com.abc.kim.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.abc.core.base.BaseActivity
import com.abc.kim.R
import com.abc.kim.databinding.ActivityMainBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter

/**
 *@author Liszt
 *@date 2020/11/11
 *Description
 */
@Route(path = "/app/MainActivity")
class MainActivity : BaseActivity() {
    lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}