package com.example.homework4.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
//拦截器、敏感词过滤器
class TimeConsumeInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val startTime = System.currentTimeMillis()
        val resp =  chain.proceed(chain.request())
        val endTime = System.currentTimeMillis()
        val url = chain.request().url.toString()
        Log.e("TimeConsumeInterceptor","request:$url cost time ${endTime - startTime}")
        return resp
    }
}