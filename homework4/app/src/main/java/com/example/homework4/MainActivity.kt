package com.example.homework4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.homework4.api.*
import com.example.homework4.interceptor.TimeConsumeInterceptor
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    var requestBtn: Button? = null
    var showText: TextView? = null
    var editText :EditText?= null

    val okhttpListener = object : EventListener() {
        override fun dnsStart(call: Call, domainName: String) {
            super.dnsStart(call, domainName)
            //showText?.text = showText?.text.toString() + "\nDns Search:" + domainName
        }

        override fun responseBodyStart(call: Call) {
            super.responseBodyStart(call)
            //showText?.text = showText?.text.toString() + "\nResponse Start"
        }
    }
    val client: OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(TimeConsumeInterceptor())
        .eventListener(okhttpListener).build()

    val gson = GsonBuilder().create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestBtn = findViewById(R.id.mybutton)
        showText = findViewById(R.id.mytext)
        editText = findViewById(R.id.editText)

        requestBtn?.setOnClickListener {
            showText?.text = ""
            click()
        }
    }

    fun request(url: String, callback: Callback) {
        val request: Request = Request.Builder()
            .url(url)
            .header("User-Agent", "Sjtu-Android-OKHttp")//区别名
            .build()//OKhttp自带的构造形式设置模式
        client.newCall(request).enqueue(callback)
    }

    fun click() {
        if (editText?.text.toString().isEmpty()){
            Toast.makeText(this,"try a complete word just not empty!!", Toast.LENGTH_LONG).show();
        }
        else{
            val url = "https://dict.youdao.com/jsonapi?q="+ editText?.text.toString()
            request(url, object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        showText?.text = e.message
                    }
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body?.string()
                    val jsonRootBean = gson.fromJson(bodyString, JsonRootBean::class.java)//bejson网站自动生成的java类供后续使用
                    if (jsonRootBean.input.indexOf(' ')!=-1){
                        val ec :  Fanyi= jsonRootBean.fanyi
                        runOnUiThread{
                            showText?.text = "${showText?.text.toString()} \n\n\n" +
                                    " ${ec.tran} \n"
                        }
                    }
                    else{
                        try{
                            val ec : Etym = jsonRootBean.etym
                            runOnUiThread{
                                showText?.text = "${showText?.text.toString()} \n\n\n" +
                                        " ${ec.etyms.zh[0].desc} \n"
                            }
                        }
                        catch (e: Exception) {
                            println("无法找到该单词${e.message}")
                            Looper.prepare();
                            Toast.makeText(baseContext,"大概率是翻译器还不够聪明，无法找到该单词,换一个再试试吧", Toast.LENGTH_LONG).show();
                            Looper.loop();
                        } finally {
                            println("谢谢您使用我们的翻译器")
                        }


                    }


                }
            })
        }

    }
}