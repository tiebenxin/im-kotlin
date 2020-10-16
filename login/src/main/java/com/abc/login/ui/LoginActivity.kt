package com.abc.login.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import com.abc.core.base.BaseActivity
import com.abc.core.constans.AppConfig
import com.abc.core.utils.DeviceUtils
import com.abc.core.utils.encrypt.MD5
import com.abc.login.R
import com.abc.login.api.LoginApi
import com.abc.login.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.Int as Int1

/**
 *@author Liszt
 *@date 2020/10/14
 *Description
 */
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {
    override fun initData() {
    }

    override fun initView() {
        mViewBinding.btnLogin.setOnClickListener { login() }
    }

//    override fun getLayoutResId(): Int {
//        return R.layout.activity_login
//    }

    override fun getLayoutResId(): Int1 = R.layout.activity_login

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
//    }

    fun login() {
        var phone = "15377311916"
        var psw = et_password_content.text.toString()
        mViewModel.login(
            psw,
            phone,
            DeviceUtils.getDeviceId(this)!!,
            "android",
            DeviceUtils.phoneModel,
            "",
            DeviceUtils.phoneModel,
            DeviceUtils.deviceName
        )

    }
}