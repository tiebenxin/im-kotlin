package com.abc.kim

import com.abc.core.okhttp.RetrofitClient
import com.abc.login.api.loginRepoModule
import com.abc.login.api.loginViewModelModule
import com.google.gson.GsonBuilder

import org.koin.dsl.module

/**
 * Create by liwen on 2020/5/25
 */


val otherModule = module {

    single {
        RetrofitClient.instance
    }

    single {
        GsonBuilder().disableHtmlEscaping().create()
    }
}


val allModule = listOf(
    otherModule,
    loginRepoModule, loginViewModelModule
)