package com.turdroid.library.base

import android.os.Handler
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment


abstract class BaseFragment: DataBindingFragment() {

    protected val TAG = this.javaClass.simpleName

    val handler: Handler = Handler()

    protected open fun nav(): NavController? {
        return NavHostFragment.findNavController(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }

    protected fun delay( delayMillis:Long,r:Runnable){
        handler.postDelayed(r,delayMillis)
    }
}