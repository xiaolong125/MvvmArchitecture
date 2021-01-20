package com.turdroid.library.base

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.*
import com.gyf.immersionbar.ImmersionBar
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.turdroid.library.loading.LoadingDialog
import com.turdroid.library.manager.NetworkStateManager


abstract class BaseActivity : DataBindingActivity() {

    protected val TAG = this.javaClass.simpleName

    val handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(NetworkStateManager.getInstance())
        if (translucentEnabled()) {
            ImmersionBar.with(this).init()
        }
    }

//    override fun getResources(): Resources? {
//        return if (ScreenUtils.isPortrait()) {
//            AdaptScreenUtils.adaptWidth(super.getResources(), 375)
//        } else {
//            AdaptScreenUtils.adaptHeight(super.getResources(), 667)
//        }
//    }

    override fun onResume() {
        super.onResume()
        registerLoadingEvent()
        registerMessageEvent()
    }

    override fun onPause() {
        super.onPause()
        unRegisterLoadingEvent()
        unRegisterMessageEvent()
    }

    protected fun showLoadingDialog() {
        LoadingDialog.Build.getInstance().show(supportFragmentManager, true)
    }

    protected fun hideLoadingDialog() {
        LoadingDialog.Build.getInstance().show(supportFragmentManager, false)
    }

    /**
     * 是否启用沉浸式状态栏
     */
    protected open fun translucentEnabled(): Boolean {
        return true
    }

    private fun registerMessageEvent() {
        AppStatus.mMessageEvent.observe(this, Observer {
            if (!TextUtils.isEmpty(it)) {
                ToastUtils.showShort(it)
//            showTipMessage(it)
            }

        })
    }

    protected fun showTipMessage(message: String) {
        val tipDialog = QMUITipDialog.Builder(this)
            .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
            .setTipWord(message)
            .create()
        tipDialog.show()
        delay(2000L, Runnable {
            tipDialog.dismiss()
        })
    }

    private fun registerLoadingEvent() {
        AppStatus.mLoadingEvent.observe(this, Observer {
            if (it != null) {
                if (it) {
                    showLoadingDialog()
                } else {
                    hideLoadingDialog()
                }
            }
        })
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }

    private fun unRegisterLoadingEvent() {
        AppStatus.mLoadingEvent.removeObservers(this)
    }

    private fun unRegisterMessageEvent() {
        AppStatus.mMessageEvent.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    protected fun delay(delayMillis: Long, r: Runnable) {
        handler.postDelayed(r, delayMillis)
    }

}