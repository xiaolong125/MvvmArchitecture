package com.turdroid.library.network

import android.app.Application
import com.turdroid.library.config.Constants
import com.turdroid.library.network.interceptor.LoggingInterceptor
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.cahce.CacheMode
import rxhttp.wrapper.cookie.CookieStore
import rxhttp.wrapper.param.Param
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.ssl.HttpsUtils
import java.io.File
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

object RxHttpManager {


    fun init(context: Application?) {
        val file = File(context!!.externalCacheDir, "RxHttpCookie")
        val sslParams = HttpsUtils.getSslSocketFactory()
        val client = OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor())
//                .addInterceptor(TokenInterceptor())
                .cookieJar(CookieStore(file))
                .connectTimeout(Constants.DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(Constants.DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager) //添加信任证书
                .hostnameVerifier(HostnameVerifier { hostname: String?, session: SSLSession? -> true }) //忽略host验证
                //            .followRedirects(false)  //禁制OkHttp的重定向操作，我们自己处理重定向
                //            .addInterceptor(new RedirectInterceptor())
                //            .addInterceptor(new TokenInterceptor())
                .build()
        //RxHttp初始化，自定义OkHttpClient对象，非必须
        RxHttp.init(client, false)

        //设置缓存策略，非必须
        val cacheFile = File(context.externalCacheDir, "RxHttpCache")
        RxHttpPlugins.setCache(
                cacheFile,
                1000 * 100.toLong(),
                CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE
        )
        RxHttpPlugins.setExcludeCacheKeys("time") //设置一些key，不参与cacheKey的组拼

        //设置数据解密/解码器，非必须
//        RxHttp.setResultDecoder(s -> s);

        //设置全局的转换器，非必须
//        RxHttp.setConverter(FastJsonConverter.create());

        //设置公共参数，非必须
        RxHttp.setOnParamAssembly { p: Param<*>? ->
            /*根据不同请求添加不同参数，子线程执行，每次发送请求前都会被回调
                        如果希望部分请求不回调这里，发请求前调用Param.setAssemblyEnabled(false)即可
                         */
            val method = p!!.getMethod()
            if (method!!.isGet) { //Get请求
            } else if (method.isPost) { //Post请求
            }
            p
//                .add("request_time",System.currentTimeMillis())
//                .add("versionName", "1.0.0") //添加公共参数
//                .add("time", System.currentTimeMillis())
//                .addHeader("Content-Type","application/json")
//                    .addHeader("Authorization", AccountManager.getToken())
//                .addHeader("deviceType", "android") //添加公共请求头
        }
    }
}