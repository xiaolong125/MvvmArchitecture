package com.tordroid.network.exception

import java.lang.RuntimeException

/**
 * 作者：ly-xuxiaolong
 * 版本：1.0
 * 创建日期：2020/8/12
 * 描述：
 * 修订历史：
 */
class ServerException(val code:Int, val msg:String): RuntimeException() {
}