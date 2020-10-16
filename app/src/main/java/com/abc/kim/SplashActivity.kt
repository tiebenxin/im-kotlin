package com.abc.kim

import android.content.Intent
import android.os.Bundle
import com.abc.core.base.BaseActivity2
import com.abc.login.ui.LoginActivity

class SplashActivity : BaseActivity2() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startActivity(Intent(this, LoginActivity::class.java))
    }
}
