package com.example.homework4.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *   author:apple
 *   time:2021/11/19
 *   version:1.0
 */
//"https://dict.youdao.com/jsonapi?q=apple"
interface JsonRootService {
    @GET("jsonapi")
    fun getInfo(@Query("q") q: String): Call<JsonRootBean>
}