package com.abc.kim

import android.app.Application
import com.abc.core.AppConfig
import com.tencent.mmkv.MMKV

/**
 *@author Liszt
 *@date 2020/10/13
 *Description
 */
class CXApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppConfig.context = this;
        initMMKV()
    }

    private fun initMMKV(){
        MMKV.initialize(this)
    }

}