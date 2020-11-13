package com.abc.core.base

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.abc.core.R
import com.abc.core.constans.AppConfig
import com.abc.core.dialog.DialogLoadingProgress
import com.abc.core.utils.LogUtil
import com.abc.core.utils.StatusBarKt
import com.abc.core.utils.ThreadUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlin.reflect.KClass

/**
 *@author Liszt
 *@date 2020/10/14
 *Description
 */
abstract class BaseVMActivity<T : ViewModel, M : ViewDataBinding> : BaseActivity() {
    lateinit var mViewModel: T
    lateinit var mViewBinding: M
    var context: Context? = null
    lateinit var inflater: LayoutInflater
    var isFirstRequestPermissionsResult: Boolean? = true//第一次请求权限返回
    private var payWaitDialog: DialogLoadingProgress? = null
    //字体缩放倍数
    private var fontScan = 1.0f
    private val mExit = Finish()

    /**
     * 其他页面退出登录
     */
    private inner class Finish {
        @Subscribe(threadMode = ThreadMode.MAIN)
        fun onExitEvent(myEvent: String) {
            //退出登录,关闭其他页面
            if (!isFinishing) {
                finish()
            }
        }
    }

    abstract fun initData()

    abstract fun initView()

    abstract fun getLayoutResId(): Int


    override fun onCreate(savedInstanceState: Bundle?) {
        //initFont();
        context = applicationContext
        inflater = layoutInflater
        window.statusBarColor = resources.getColor(R.color.white)
        super.onCreate(savedInstanceState)
        StatusBarKt.fitSystemBar(this)
        mViewBinding = DataBindingUtil.setContentView(this, getLayoutResId())
        initViewModel()
        initData()
        initView()
        //注册关闭其他页面事件
        EventBus.getDefault().register(mExit)
        //友盟Push后台进行日活统计及多维度推送的必调用方法
        if (savedInstanceState != null) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
//            EventBus.getDefault().post(EventFactory.RestartAppEvent())
        }
    }

    override fun onResume() {
        super.onResume()
        taskClearNotification()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        //注销关闭其他页面事件
        EventBus.getDefault().unregister(mExit)
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(myEvent: Any) {
    }


    fun initFont() {
        if (fontScan == AppConfig.FONT)
            return

        setFontScan(AppConfig.FONT)
    }

    /***
     * 设置app字体缩放倍率
     * @param fontSize
     */
    private fun setFontScan(fontSize: Float) {
        this.fontScan = fontSize
        AppConfig.setFont(fontSize)
        val resources = resources

        resources.configuration.fontScale = fontSize
        resources.updateConfiguration(resources.configuration, resources.displayMetrics)
        // this.recreate();

        //  SharedPreferencesUtil sharedPreferencesUtil=new SharedPreferencesUtil(SharedPreferencesUtil.SPName.FONT_SCAN);

        //  sharedPreferencesUtil.save2Json(fontSize);
    }

    /***
     * 清理通知栏
     */
    private fun taskClearNotification() {
        val manager =
            context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancelAll()
    }


    /***
     * 直接跳转
     * @param c
     */
    fun go(c: Class<*>) {
        startActivity(Intent(context, c))
    }


    fun hideKeyboard() {
        val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im?.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }

    /**
     * 新增->强制弹出软键盘
     *
     * @param view 备注：延迟任务解决之前无法弹出问题
     */
    fun showSoftKeyword(view: View?) {
        if (view == null) {
            return
        }
        view.postDelayed({
            view.requestFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.showSoftInput(view, 0)
        }, 100)

    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            127 -> {//申请定位权限返回
                var hasPermissions: Boolean? = true
                for (i in grantResults.indices) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        hasPermissions = false
                    }
                }
                //                ToastUtil.show(this,"请打开定位权限");
                LogUtil.getLog().e("=申请定位权限返回=location=hasPermission=s=" + hasPermissions!!)

                if (!hasPermissions && (!isFirstRequestPermissionsResult!!)) {
//                    val alertYesNo = AlertYesNo()
//                    alertYesNo.init(
//                        this,
//                        "提示",
//                        "您拒绝了定位权限，打开定位权限吗？",
//                        "确定",
//                        "取消",
//                        object : AlertYesNo.Event() {
//                            override fun onON() {}
//
//                            override fun onYes() {
//                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                                val uri =
//                                    Uri.fromParts("package", applicationContext.packageName, null)
//                                intent.data = uri
//                                startActivity(intent)
//                            }
//                        })
//                    alertYesNo.show()
                }
                isFirstRequestPermissionsResult = false
            }
        }
    }

    fun showLoadingDialog() {
        ThreadUtil.getInstance().runMainThread(Runnable {
            if (payWaitDialog == null) payWaitDialog = DialogLoadingProgress(context!!)
            if (isActivityValid()) {
                payWaitDialog!!.setContent("正在加载中...")
                payWaitDialog!!.show()
            }
        })

    }

    fun showLoadingDialog(s: String) {
        ThreadUtil.getInstance().runMainThread(Runnable {
            if (payWaitDialog == null) {
                payWaitDialog = DialogLoadingProgress(context!!)
            }
            if (isActivityValid()) {
                payWaitDialog!!.setContent(s)
                payWaitDialog!!.show()
            }
        })

    }

    fun dismissLoadingDialog() {
        ThreadUtil.getInstance().runMainThread(Runnable {
            if (isActivityValid() && payWaitDialog != null) {
                payWaitDialog!!.dismiss()
            }
        })
    }

    //activity 是否有效
    private fun isActivityValid(): Boolean {
        return !(this == null || this.isDestroyed || this.isFinishing)
    }

    @SuppressLint("NewApi")
    private fun initViewModel() {
        val clazz =
            this.javaClass.kotlin.supertypes[0].arguments[0].type!!.classifier!! as KClass<T>
        mViewModel = getViewModel<T>(clazz) //koin 注入

    }
}