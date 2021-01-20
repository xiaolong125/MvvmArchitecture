package com.turdroid.library.base

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.tencent.bugly.crashreport.CrashReport
import com.turdroid.library.config.Constants
import com.turdroid.library.parser.AppManifestParser


/**
 * 作者：xuxiaolong
 * 版本：1.0
 * 创建日期：2020/8/12
 * 描述：
 * 修订历史：
 */
abstract class BaseApp : BaseApplication() {
    private var componentApps: MutableList<IComponentApp>? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: BaseApp
        lateinit var appViewModel: AppViewModel
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initUtils()
        initBugly()
        initARouter()
        initComponentApp()
//        RxHttpManager.init(this)
        appViewModel = getAppViewModel(AppViewModel::class.java)
    }

    private fun initUtils() {
        Utils.init(this)
        LogUtils.getConfig().globalTag = Constants.TAG
    }

    private fun initBugly() {
        CrashReport.initCrashReport(applicationContext, getBuglyAppId(), true)
    }

    private fun initARouter() {
        ARouter.openLog()
        // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.openDebug()
        ARouter.init(this)
    }

    /**
     * 初始化子模块
     */
    private fun initComponentApp() {
        this.componentApps = AppManifestParser(this).parse()
        componentApps?.let {
            for (app in it) {
                app.onCreate(this)
                LogUtils.d("componnentApp:${app::class.java}")
            }
        }
    }

    protected abstract fun getBuglyAppId(): String

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        componentApps?.let {
            for (app in it) {
                app.onTerminate(this)
            }
        }
    }
}