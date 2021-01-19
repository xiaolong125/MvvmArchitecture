package com.turdroid.architecture

import android.content.pm.ApplicationInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.forEach
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class DataBindingActivity : AppCompatActivity() {

    val mActivityProvider: ViewModelProvider by lazy {
        ViewModelProvider(this)
    }

    protected lateinit var binding: ViewDataBinding
    protected lateinit var dataBindingConfig: DataBindingConfig

    protected abstract fun initViewModel()
    protected abstract fun initDataBindingConfig()
    protected abstract fun initData()

    /**
     * 不启用DataBinding时一定要重写此方法
     */
    protected open fun getLayoutRes():Int{
        return 0
    }

    /**
     * 是否启用DataBinding,如果不启用那几个抽象方法可以空实现
     */
    protected open fun dataBindingEnabled():Boolean{
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (dataBindingEnabled()){
            initViewModel()
            initDataBindingConfig()
            val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, dataBindingConfig.layout)
            binding.lifecycleOwner = this
            binding.setVariable(dataBindingConfig.vmVariableId, dataBindingConfig.stateViewModel)
            val bindingParams = dataBindingConfig.bindingParams
            bindingParams.forEach { key, value ->
                binding.setVariable(key,value)
            }
            this.binding = binding
        }else{
            setContentView(getLayoutRes())
        }
        initData();

    }

    val isDebug: Boolean
        get() = applicationContext.applicationInfo != null &&
                applicationContext.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0


    protected fun <T : ViewModel> getActivityViewModel(modelClass: Class<T>): T {
        return mActivityProvider.get(modelClass)
    }

    protected inline fun <reified T : ViewModel> getActivityViewModel(): T {
        return mActivityProvider.get(T::class.java)
    }
}