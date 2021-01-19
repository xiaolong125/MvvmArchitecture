package com.turdroid.architecture

import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.os.Handler
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class DataBindingFragment : Fragment() {

    private val mFragmentProvider: ViewModelProvider by lazy {
        ViewModelProvider(this)
    }

    private val mActivityProvider: ViewModelProvider by lazy {
        ViewModelProvider(mActivity)
    }
    val HANDLER = Handler()

    protected var mAnimationLoaded = false
    protected lateinit var mActivity: AppCompatActivity
    protected lateinit var binding: ViewDataBinding
    protected lateinit var dataBindingConfig: DataBindingConfig


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    protected abstract fun initViewModel()
    protected abstract fun initDataBindingConfig()
    protected open fun initData() {}

    /**
     * 是否启用DataBinding,如果不启用那几个抽象方法可以空实现
     */
    protected open fun dataBindingEnabled():Boolean{
        return true
    }

    /**
     * 不启用DataBinding时一定要重写此方法
     */
    protected open fun getLayoutRes():Int{
        return 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (dataBindingEnabled()){
            initDataBindingConfig()
            val binding = DataBindingUtil.inflate<ViewDataBinding>(
                inflater,
                dataBindingConfig.layout,
                container,
                false
            )
            binding.lifecycleOwner = this
            binding.setVariable(dataBindingConfig.vmVariableId, dataBindingConfig.stateViewModel)
            val bindingParams: SparseArray<*> = dataBindingConfig.bindingParams
            var i = 0
            val length = bindingParams.size()
            while (i < length) {
                binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i))
                i++
            }
            this.binding = binding
            return binding.root
        }else{
            return inflater.inflate(getLayoutRes(), container, false);
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    val isDebug: Boolean
        get() = mActivity.applicationContext.applicationInfo != null &&
                mActivity.applicationContext
                    .applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0

    protected open fun <T : ViewModel> getFragmentViewModel(modelClass: Class<T>): T {
        return mFragmentProvider[modelClass]
    }

    protected open fun <T : ViewModel> getActivityViewModel(modelClass: Class<T>): T {
        return mActivityProvider[modelClass]
    }

}