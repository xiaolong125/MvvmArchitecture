package com.turdroid.library.network.model

/**
 * 作者：ly-xuxiaolong
 * 版本：1.0
 * 创建日期：2020/8/12
 * 描述：
 * 修订历史：
 */
data class ApiResponse<T>(
    var data: T?,
    var code: Int,
    var msg: String
)