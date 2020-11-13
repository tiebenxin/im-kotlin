package com.abc.kim.ui

import android.content.Intent
import android.os.Bundle
import com.abc.core.base.BaseActivity
import com.abc.kim.R
import com.abc.login.ui.LoginActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startActivity(Intent(this, LoginActivity::class.java))
    }
}
