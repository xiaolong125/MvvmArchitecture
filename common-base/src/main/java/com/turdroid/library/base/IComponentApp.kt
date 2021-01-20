package com.turdroid.library.base

import com.turdroid.library.base.BaseApp

/**
 * 用于模块/组件的各个 Module 在 Application 中初始化一些配置
 * 让子模块实现此接口，然后在Manifest中配置
 */
interface IComponentApp {
    /**
     * 用于模块/组件的初始化
     *
     * @param app
     */
    fun onCreate(app: BaseApp)


    fun onTerminate(app: BaseApp){

    }
}