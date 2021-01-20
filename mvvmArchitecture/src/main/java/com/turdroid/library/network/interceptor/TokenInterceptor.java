package com.turdroid.library.network.interceptor;


import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.turdroid.library.network.model.ApiResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

/**
 * token 失效，自动刷新token，然后再次发送请求，用户无感知
 * User: ljx
 * Date: 2019-12-04
 * Time: 11:56
 */
//public class TokenInterceptor implements Interceptor {
//
//    //token刷新时间
//    private static volatile long SESSION_KEY_REFRESH_TIME = 0;
//
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Request request = chain.request();
//        Response originalResponse = chain.proceed(request);
//        BufferedSource source = originalResponse.body().source();
//        source.request(Long.MAX_VALUE); // Buffer the entire body.
//        Buffer buffer = source.buffer();
//        Charset charset = StandardCharsets.UTF_8;
//        MediaType contentType = originalResponse.body().contentType();
//        if (contentType != null) {
//            charset = contentType.charset(StandardCharsets.UTF_8);
//        }
//        //获取响应体的字符串
//        String bodyString = buffer.clone().readString(charset);
//        try {
//            ApiResponse apiResponse = GsonUtils.fromJson(bodyString, ApiResponse.class);
//            if (apiResponse.getCode() == 401) { //token 失效
//                return handleTokenInvalid(originalResponse,chain, request);
//            }
//        } catch (IOException e) {
//            LogUtils.e(Log.getStackTraceString(e));
//        }
//        return originalResponse;
//    }


    //处理token失效问题
//    private Response handleTokenInvalid(Response originalResponse,Chain chain, Request request) throws IOException {
//        HashMap<String, String> mapParam = new HashMap<>();
//        RequestBody body = request.body();
//        String requestTime = null;
//        if (request.method().equals("GET")){
//            requestTime = request.url().queryParameter("request_time");
//        } else if (body instanceof FormBody) {
//            FormBody formBody = (FormBody) body;
//            for (int i = 0; i < formBody.size(); i++) {
//                mapParam.put(formBody.name(i), formBody.value(i));  //2、保存参数
//            }
//            requestTime = mapParam.get("request_time");
//        }
//
//        boolean success = refreshToken(requestTime);
//        Request newRequest;
//        if (success) { //刷新成功，重新签名
//            newRequest = request.newBuilder().header("Authorization", AccountManager.INSTANCE.getToken())
//                    .build();
//        } else {
//            newRequest = request;
//        }
//        originalResponse.close();
//        return chain.proceed(newRequest);
//    }

    //刷新token
//    private boolean refreshToken(Object value) {
//        long requestTime = 0;
//        try {
//            requestTime = Long.parseLong(value.toString());
//        } catch (Exception ignore) {
//        }
//        //请求时间小于token刷新时间，说明token已经刷新，则无需再次刷新
//        if (requestTime <= SESSION_KEY_REFRESH_TIME) return true;
//        synchronized (this) {
//            //再次判断是否已经刷新
//            if (requestTime <= SESSION_KEY_REFRESH_TIME) return true;
//            try {
//                //获取到最新的token，这里需要同步请求token,千万不能异步  5、根据自己的业务修改
//                String json = RxHttp.postForm(Url.Login.REFRESH_TOKEN)
//                        .setAssemblyEnabled(false)
//                        .addHeader("Authorization", "Basic dXNlci1jbGllbnQ6dXNlci1zZWNyZXQtODg4OA==")
//                        .add("grant_type", "refresh_token")
//                        .add("refresh_token", AccountManager.INSTANCE.getRefreshToken())
//                        .execute(SimpleParser.get(String.class));
//                Account account = GsonUtils.fromJson(json,Account.class);
//                if (TextUtils.isEmpty(account.getAccess_token()) || TextUtils.equals(account.getAccess_token(),"null")){
//                    return false;
//                }
//                LogUtils.d("刷新token成功："+account);
//                SESSION_KEY_REFRESH_TIME = System.currentTimeMillis() / 1000;
//                AccountManager.INSTANCE.updateAccount(account);
//                return true;
//            } catch (IOException e) {
//                LogUtils.e(e);
//                return false;
//            }
//        }
//    }
//}
