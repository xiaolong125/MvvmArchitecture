package com.turdroid.library.network.interceptor
import com.tordroid.network.exception.ServerException
import okhttp3.Response
import org.json.JSONObject

/**
 * 作者：ly-xuxiaolong
 * 版本：1.0
 * 创建日期：2020/8/12
 * 描述：
 * 修订历史：
 */
class HandleErrorInterceptor : ResponseBodyInterceptor() {

    override fun intercept(response: Response, url: String, body: String): Response {
        var jsonObject: JSONObject? = null
        try {
            jsonObject = JSONObject(body)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (jsonObject != null) {
            val code = jsonObject.optInt("code", -1)
            val msg = jsonObject.optString("msg", "")
            // TODO: 2020/8/12 定义错误码
            if (code != 200) {
                throw ServerException(code,msg)
            }
        }
        return response
    }
}
