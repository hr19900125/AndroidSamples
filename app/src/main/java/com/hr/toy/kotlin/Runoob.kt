package com.hr.toy.kotlin

import android.util.Log

class Runoob constructor(name: String) {

    val url: String = "www.baidu.com"

    var country: String = "CN"

    var siteName = name

    init {
        Log.e("Runoob", "初始化网站名称: ${name}")
    }

    fun test() {
        Log.e("Runoob", "测试啊啊啊啊啊啊")
    }

    constructor(name: String, alexa: Int) : this(name) {
        Log.e("Runoob", "alexa 排名 $alexa")
    }
}