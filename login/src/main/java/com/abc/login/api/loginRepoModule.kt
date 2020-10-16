package com.abc.login.api

import com.abc.login.ui.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *@author Liszt
 *@date 2020/10/16
 *Description
 */

val loginRepoModule = module {
    single { LoginRepository(get()) }
}

val loginViewModelModule = module {
    viewModel { LoginViewModel(get()) }
}