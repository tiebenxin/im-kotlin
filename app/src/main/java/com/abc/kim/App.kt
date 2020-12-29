package com.abc.kim

import androidx.multidex.MultiDexApplication
import com.abc.core.constans.AppConfig
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.mmkv.MMKV
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 *@author Liszt
 *@date 2020/10/13
 *Description
 */
class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        AppConfig.context = this;
        initMMKV()
        startKotlin()
        initArouter();
    }

    private fun initMMKV() {
        MMKV.initialize(this)
    }

    private fun startKotlin() {
        startKoin {
            androidContext(this@App)
            modules(allModule)
        }
    }

    private fun initArouter() {
        ARouter.init(this@App)
    }

}