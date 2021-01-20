package com.turdroid.library.web

import android.os.Bundle
import android.text.TextUtils
import android.view.ViewGroup
import android.webkit.WebView
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import com.turdroid.library.R
import kotlinx.android.synthetic.main.fragment_browser.*

open class BrowserFragment:BaseAgentWebFragment() {

    private lateinit var loadUrl:String
    private var showTopBar = false
    override fun dataBindingEnabled(): Boolean {
        return false
    }
    override fun getLayoutRes(): Int {
        return R.layout.fragment_browser
    }

    override fun initViewModel() {}

    override fun initDataBindingConfig() {}

    override fun initData() {
        initTopBar()

    }

    private fun initTopBar() {
        if (showTopBar && mTopBar != null){
            mTopBar.addLeftBackImageButton()
                .setOnClickListener {
                    mActivity.finish()
                }
        }
    }

    override fun setTitle(view: WebView?, title: String) {
        var realtitle = title
        super.setTitle(view, title)
        if (!TextUtils.isEmpty(realtitle)) {
            if (title.length > 10) {
                realtitle = title.substring(0, 10) + "..."
            }
        }
        if (showTopBar && mTopBar!= null){
            mTopBar.setTitle(realtitle)
        }
    }

    override fun getAgentWebParent(): ViewGroup {
        return container
    }

    override fun getIndicatorHeight(): Int {
        return 3
    }

    override fun getIndicatorColor(): Int {
        return ContextCompat.getColor(mActivity,R.color.design_default_color_primary)
    }

    @Nullable
    override fun getUrl(): String? {
        return loadUrl
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadUrl = arguments?.getString("url","")?:""
        showTopBar = arguments?.getBoolean("showTopBar",false)?:false
    }

    fun newInstance(url:String,showTopBar:Boolean): BrowserFragment {
        val args = Bundle()
        args.putString("url",url)
        args.putBoolean("showTopBar",showTopBar)
        val fragment = BrowserFragment()
        fragment.arguments = args
        return fragment
    }

}