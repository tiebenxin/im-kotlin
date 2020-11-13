package com.abc.login.ui

import android.text.InputType
import android.view.View
import android.widget.ImageView
import com.abc.core.base.BaseVMActivity
import com.abc.core.utils.DeviceUtils
import com.abc.login.R
import com.abc.login.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.Int as Int1

/**
 *@author Liszt
 *@date 2020/10/14
 *Description
 */
class LoginActivity : BaseVMActivity<LoginViewModel, ActivityLoginBinding>() {
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
//        var psw = et_password_content.text.toString()
        var psw = "123456"
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


    fun showOrHidePassword(view: View) {
        val ivEye = view as ImageView
        val level = ivEye.drawable.level
        if (level == 0) {//隐藏转显示
            et_password_content.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            ivEye.setImageLevel(1)
            //光标定位到最后
            et_password_content.setSelection(et_password_content.text!!.length)
        } else {//显示转隐藏
            et_password_content.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            ivEye.setImageLevel(0)
            //光标定位到最后
            et_password_content.setSelection(et_password_content.text!!.length)
        }
    }
}