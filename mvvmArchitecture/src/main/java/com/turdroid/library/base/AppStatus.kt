package com.turdroid.library.base

import android.text.TextUtils
import com.turdroid.library.livedata.SingleLiveEvent

object AppStatus {

    val mLoadingEvent: SingleLiveEvent<Boolean> by lazy { SingleLiveEvent<Boolean>() }

    val mMessageEvent: SingleLiveEvent<String> by lazy { SingleLiveEvent<String>() }

    fun showMessage(message: String) {
        if (!TextUtils.isEmpty(message)) {
            mMessageEvent.value = message
        }
    }


    fun showLoading() {
        mLoadingEvent.value = true
    }

    fun hideLoading() {
        mLoadingEvent.value = false
    }
}